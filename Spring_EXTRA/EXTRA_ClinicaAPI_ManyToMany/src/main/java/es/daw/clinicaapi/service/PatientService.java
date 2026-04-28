package es.daw.clinicaapi.service;

import es.daw.clinicaapi.dto.patient.PatientResponse;
import es.daw.clinicaapi.entity.Appointment;
import es.daw.clinicaapi.entity.Invoice;
import es.daw.clinicaapi.entity.InvoiceLine;
import es.daw.clinicaapi.entity.Patient;
import es.daw.clinicaapi.enums.DiscountType;
import es.daw.clinicaapi.exception.NotFoundException;
import es.daw.clinicaapi.repository.AppointmentRepository;
import es.daw.clinicaapi.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final InvoiceRepository invoiceRepository;
    private final AppointmentRepository appointmentRepository;


    //@Transactional
    public List<PatientResponse> getPatientsForDates(LocalDateTime startDate, LocalDateTime endDate) {
        // Buscamos las facturas que coincidan con el rango de fechas. MÉTODO_ INVENTADO
        List<Invoice> invoices = invoiceRepository.findPatientFilterDate(startDate, endDate);
        List<PatientResponse> patientsResponse = new ArrayList<>();

        // Recorremos todas las facturas que están en el rango de fechas
        for (Invoice inv : invoices) {
            Appointment appointment = appointmentRepository.findById(inv.getAppointment().getId())
                    .orElseThrow(() -> new NotFoundException("No se ha encontrado la cita: " + inv.getAppointment().getId()));

            List<InvoiceLine> invoicesLine = inv.getLines();
            DiscountType discountType = DiscountType.NONE;
            Patient patient = appointment.getPatient();

            // Recorremos su linea de factura para modificar el total de la factura y el descuento
            if (invoicesLine != null && !invoicesLine.isEmpty()) {
                for (InvoiceLine il : invoicesLine) {
                    if (patient.isHasInsurance()) {
                        // Aplicar descuento de 20% a los pacientes con seguro
                        BigDecimal subtotal = DiscountType.INSURANCE.apply(inv.getSubtotal(), BigDecimal.valueOf(0.20));
                        BigDecimal total = subtotal.add(inv.getTaxTotal());
                        BigDecimal roundedTotal = total.setScale(2, RoundingMode.HALF_UP);

                        // Modificar el total de la factura y la linea total de la factura
                        inv.setTotal(roundedTotal);
                        il.setLineTotal(roundedTotal);
                        il.setDiscountType(DiscountType.INSURANCE);
                        discountType = DiscountType.INSURANCE;
                    } else {
                        discountType = il.getDiscountType();
                    }
                }
            }

            patientsResponse.add(
                    new PatientResponse(
                            patient.getId(),
                            inv.getId(),
                            patient.getFullName(),
                            patient.getEmail(),
                            inv.getTotal(),
                            discountType,
                            inv.getIssuedAt(),
                            patient.isHasInsurance()
                    )
            );
        }
        System.out.println("Lista de pacientes: " + patientsResponse);
        return patientsResponse;
    }
}
