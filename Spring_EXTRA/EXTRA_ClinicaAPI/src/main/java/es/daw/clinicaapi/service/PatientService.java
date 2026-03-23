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

    public List<PatientResponse> listAllPatient(LocalDateTime startDate, LocalDateTime endDate) {
        List<Invoice> invoices = invoiceRepository.findPatientFilterDate(startDate, endDate);
        List<PatientResponse> patientsResponse = new ArrayList<>();

        for (Invoice i : invoices) {
            Appointment appointment = appointmentRepository.findById(i.getAppointment().getId())
                    .orElseThrow(() -> new NotFoundException("No se ha encontrado la cita: " + i.getAppointment().getId()));

            List<InvoiceLine> invoicesLine = i.getLines();
            DiscountType discountType = DiscountType.NONE;

            Patient patient = appointment.getPatient();
            if (patient.getDni().equals("444D")) {
                patient.setHasInsurance(true);
                System.out.println("Cantidad reducida por HasInsurence: " + i.getTotal());
            }

            if (invoicesLine != null && !invoicesLine.isEmpty()) {
                for (InvoiceLine il : invoicesLine) {
                    if (patient.isHasInsurance()) {
                        BigDecimal totalAmount = i.getSubtotal().multiply(BigDecimal.valueOf(0.20));
                        i.setTotal(i.getSubtotal().subtract(totalAmount).add(i.getTaxTotal()).setScale(2, RoundingMode.HALF_UP));
                        il.setDiscountType(DiscountType.INSURANCE);
                        discountType = DiscountType.INSURANCE;
                    } else {
                        discountType = il.getDiscountType();
                    }
                }
            }

            //patients.add(patient);
            patientsResponse.add(
                    new PatientResponse(
                            patient.getId(),
                            patient.getFullName(),
                            patient.getEmail(),
                            i.getTotal(),
                            discountType,
                            patient.getDateOfBirth(),
                            patient.isHasInsurance()
                    )
            );
        }
        System.out.println("Lista de pacientes: " + patientsResponse);
        return patientsResponse;
    }
}
