package es.daw.clinicaapi.service;

import es.daw.clinicaapi.dto.request.invoice.InvoiceIssueRequest;
import es.daw.clinicaapi.dto.request.invoice.InvoiceLineCreateRequest;
import es.daw.clinicaapi.dto.request.invoice.InvoicePayRequest;
import es.daw.clinicaapi.dto.response.invoice.InvoiceResponse;
import es.daw.clinicaapi.dto.response.invoice.InvoiceUpdateResponse;
import es.daw.clinicaapi.entity.*;
import es.daw.clinicaapi.enums.AppointmentStatus;
import es.daw.clinicaapi.enums.DiscountType;
import es.daw.clinicaapi.enums.InvoiceStatus;
import es.daw.clinicaapi.mapper.InvoiceMapper;
import es.daw.clinicaapi.exception.BadRequestException;
import es.daw.clinicaapi.exception.BusinessRuleException;
import es.daw.clinicaapi.exception.ConflictException;
import es.daw.clinicaapi.exception.NotFoundException;
import es.daw.clinicaapi.repository.AppointmentRepository;
import es.daw.clinicaapi.repository.InvoiceRepository;
import es.daw.clinicaapi.repository.MedicalServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;
    private final MedicalServiceRepository medicalServiceRepository;

    @Value("${discount.price}")
    private BigDecimal reduction;

    @Transactional
    public InvoiceResponse issueInvoiceForAppointment(Long appointmentId, InvoiceIssueRequest req) {

        // 1. No puede haber servicios duplicados en el json request; si no → BadRequestException (400)
        checkDuplicateServices(req.lines());

        // 2. La cita debe existir; si no → NotFoundException (404).
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment not found with id: "+appointmentId));

        // 3. La cita no puede estar ya facturada; si no → ConflictException (409).
        // solo refleja lo que hay en ese objeto en memoria (y cómo se haya cargado).
        // No es una garantía de unicidad en BD.
        // Si invoice es LAZY, el campo puede no estar cargado. Revisad la entidad Appointment.
        if (appointment.getInvoice() != null || invoiceRepository.existsByAppointmentId(appointmentId)) {
            throw new ConflictException("Appointment already has an invoice");
        }

        // 4 La cita no puede estar CANCELLED. si no → BusinessRuleException (422).
        if (appointment.getStatus() == AppointmentStatus.CANCELLED)
            throw new BusinessRuleException("Cannot issue invoice for cancelled appointment");

        // 5 La cita debe estar COMPLETED si no → BussinessRuleException (422).
        if (appointment.getStatus() != AppointmentStatus.COMPLETED)
            throw new BusinessRuleException("Cannot issue invoice for non-completed appointment");

        // 6. Cada medicalServiceId debe existir y estar active=true; si no: inexistente → 404 / inactivo → 422
        //validateMedicalServices(req.lines());

        // Construir la factura y sus líneas a partir de la cita y los servicios indicados en el request.
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.PENDING); //acorde a la captura del enunciado
        invoice.setIssuedAt(LocalDateTime.now());

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal taxTotal = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (InvoiceLineCreateRequest lineReq : req.lines()) {

            // 6. Cada medicalServiceId debe existir y estar activo=true; si no: inexistente → 404 / inactivo → 422
            MedicalService service = medicalServiceRepository.findById(lineReq.medicalServiceId())
                    .orElseThrow(() -> new NotFoundException("Servicio médico inexistente"));


            if (!service.isActive()) {
                throw new BusinessRuleException("Medical service is not active: " + lineReq.medicalServiceId());
            }

            InvoiceLine line = new InvoiceLine();
            line.setService(service);
            line.setQuantity(lineReq.qty());

            // Las líneas de factura se emitirán sin aplicar ningún tipo de descuento.
            line.setDiscountType(DiscountType.NONE);
            line.setDiscountValue(BigDecimal.ZERO);

            // El tipo de IVA aplicado en las líneas de factura será siempre del 21% (VAT_21).
            //line.setVatRate(VatRate.VAT_21);
            //line.setVatRate(service.getVatRate());

            // unitPrice viene del precio base del servicio médico
            BigDecimal unitPrice = service.getBasePrice();
            BigDecimal qty = BigDecimal.valueOf(lineReq.qty());
            BigDecimal base;
            BigDecimal tax;
            line.setUnitPrice(unitPrice);

            if (appointment.getPatient().isHasInsurance()) {
                // Aplicar descuento de 20% a los pacientes con seguro
                BigDecimal discount = DiscountType.INSURANCE.apply(unitPrice, reduction);

                // Multiplicar por la cantidad de líneas de factura, sin IVA
                base = discount.multiply(qty);
                subtotal = subtotal.add(base);

                // Aplicar IVA al precio total
                tax = subtotal.multiply(service.getVatRate().getRate());
                taxTotal = taxTotal.add(tax);
            } else {
                base = unitPrice.multiply(qty); // contiene el precio total sin descuento ni impuestos
                tax = base.multiply(service.getVatRate().getRate()); // contiene el importe de impuestos sin descuento
                subtotal = subtotal.add(base); //sin iva
                taxTotal = taxTotal.add(tax);
            }

            // ¿Qué diferencia hay entre redondear por línea vs redondear al final?
            // Que puede cambiar el total por acumulación de decimales (y depende de normativa/criterio de negocio).
            BigDecimal lineTotal = base.add(tax).setScale(2, RoundingMode.HALF_UP); // 125.1234 → 125.12; 125.1267 → 125.13
            //BigDecimal lineTotal = base.add(tax);
            line.setLineTotal(lineTotal);

            // Si no se añade la linea de factura, esta no se guarda en la base de datos.
            invoice.addLine(line); // mantener bidireccionalidad. Si no hacemos esto, el mappedBy puede quedar incoherente
        }

        // redondeo una vez que he hecho el sumatorio del precio sin iva (subtotal) y del importe de impuestos (taxTotal),
        // para evitar la acumulación de decimales que puede ocurrir al redondear por línea.
        // De esta forma, el total de la factura reflejará exactamente la suma del subtotal y el taxTotal,
        // sin desviaciones por redondeos intermedios.
        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
        taxTotal = taxTotal.setScale(2, RoundingMode.HALF_UP);
        total = subtotal.add(taxTotal).setScale(2, RoundingMode.HALF_UP);

        invoice.setSubtotal(subtotal);
        invoice.setTaxTotal(taxTotal);
        invoice.setTotal(total);

        // lo dejamos para el final, porque invoice es el dueño de la relación,
        // y al hacer invoice.setAppointment() se hace el setInvoice() en el otro lado de la relación,
        // y si appointment.getInvoice() ya tenía un valor
        // (ej: por un error de lógica o por cómo se haya cargado la cita), entonces se perdería esa referencia y se quedaría con null, lo que podría causar problemas en las validaciones posteriores (ej: checkDuplicateServices) o en la lógica de negocio (ej: al cargar la cita y querer acceder a su factura). Al hacer invoice.setAppointment() al final, nos aseguramos de que no haya referencias previas que puedan interferir con la lógica de negocio o las validaciones.
        invoice.setAppointment(appointment);
        appointment.setInvoice(invoice); // mantener la consistencia de la relación bidireccional

        // Guardar la factura (con cascada, se guardan las líneas también)
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Mapear y retornar la respuesta
        return InvoiceMapper.toResponse(savedInvoice);
    }


    /**
     * Comprueba que no haya servicios duplicados
     * @param lines
     */
    private void checkDuplicateServices(List<InvoiceLineCreateRequest> lines) {
        Set<Long> ids = new HashSet<>();
        Set<Long> errorLines = new HashSet<>();
        for (InvoiceLineCreateRequest line : lines) {
            if(!ids.add(line.medicalServiceId())) {
                //throw new BadRequestException("Duplicate medical service id in lines: " + line.medicalServiceId());
                //errorLines = ": " + line.medicalServiceId();
                errorLines.add(line.medicalServiceId());
            }
        }

        if (!errorLines.isEmpty()) {
            throw new BadRequestException("Duplicate medical service id in lines: " + errorLines);
        }
    }

    // Método_ que finalmente no uso porque lo integro en el servicio de creación de la factura, pero lo dejo aquí para que veáis cómo se haría la validación de cada servicio y el lanzamiento de las excepciones correspondientes.
    // private void validateMedicalServices(List<InvoiceLineCreateRequest> lines) {
    //     for (InvoiceLineCreateRequest line : lines) {
    //         Long serviceId = line.medicalServiceId();
    //         MedicalService service = medicalServiceRepository.findById(serviceId)
    //                 .orElseThrow(() -> new NotFoundException("Medical service not found with id: " + serviceId));

    //         if (!service.isActive()) {
    //             throw new BusinessRuleException("Medical service is not active: " + serviceId);
    //         }
    //     }
    // }


    // Método_ para pagar una factura. Se utilizará la factura exclusivamente para pagarla.
    @Transactional
    public InvoiceUpdateResponse payInvoice(Long invoiceId, InvoicePayRequest request) {

        // La factura debe existir
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + invoiceId));

        // La factura debe estar pendiente de pago, solo en ese estado
        if (invoice.getStatus() != InvoiceStatus.PENDING) {
            throw new ConflictException("Invoice cannot be paid from status: " + invoice.getStatus());
        }

        // Actualizar el estado de la factura a PAGADA y la fecha de pago
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaidAt(LocalDateTime.now());
        invoice.setPaymentMethod(request.paymentMethod());

        // Al hacer save con un invoice existente, este solo actualiza los datos que han cambiado
        // (y los que no han cambiado se mantienen igual), y como invoice ya tiene cargadas
        // sus líneas (gracias al mapeo de la relación en la entidad), estas no se ven afectadas
        // por el save, y se mantienen sin cambios en la base de datos.
        //Invoice updateInvoice = invoiceRepository.save(invoice);

        //return InvoiceMapper.toUpdateResponse(updateInvoice);
        return InvoiceMapper.toUpdateResponse(invoice);
    }



    /**
     * Metodo para implementar el IVA correspondiente a su factura.
     * @param invoiceId
     * @return
     */
//    public List<InvoiceNewPrice> applyVat(Long invoiceId) {
//        Invoice invoice = invoiceRepository.findById(invoiceId)
//                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + invoiceId));
//
//        List<InvoiceLine> lines = invoice.getLines();
//        List<InvoiceNewPrice> invoices = new ArrayList<>();
//
//        for (InvoiceLine lin : lines) {
//            // Modificar el vat_rate según su tipo de servicio.
//            MedicalService service = lin.getService();
//            lin.setVatRate(service.getVatRate());
//
//            BigDecimal taxNewIva;
//            BigDecimal total;
//            if (lin.getVatRate() != VatRate.VAT_0) {
//                // Calcular el nuevo IVA y el total de la factura.
//                taxNewIva = invoice.getSubtotal().multiply(service.getVatRate().getRate()).setScale(2, RoundingMode.HALF_UP);
//                total = taxNewIva.add(invoice.getSubtotal());
//
//            } else {
//                taxNewIva = BigDecimal.ZERO;
//                total = invoice.getSubtotal();
//            }
//
//            // Modificar el subtotal y el total de la factura.
//            invoice.setTaxTotal(taxNewIva);
//            invoice.setTotal(total.setScale(2, RoundingMode.HALF_UP));
//
//            // Modificar la linea de factura total
//            BigDecimal taxLine = lin.getUnitPrice().multiply(lin.getVatRate().getRate());
//            BigDecimal lineTotal = (lin.getUnitPrice().add(taxLine)).setScale(2, RoundingMode.HALF_UP);
//            lin.setLineTotal(lineTotal);
//            //lin.setLineTotal(total.setScale(2, RoundingMode.HALF_UP));
//
//            invoices.add(new InvoiceNewPrice(
//                   invoice.getId(),
//                   lin.getId(),
//                   lin.getVatRate().name(),
//                   taxNewIva,
//                   invoice.getTotal(),
//                   lin.getLineTotal()
//            ));
//        }
//
//        return invoices;
//    }







}
