package es.daw.clinicaapi.service;


import es.daw.clinicaapi.dto.report.ServiceSummaryReport;
import es.daw.clinicaapi.dto.report.TopServiceReport;
import es.daw.clinicaapi.enums.InvoiceStatus;
import es.daw.clinicaapi.exception.BadRequestException;
import es.daw.clinicaapi.exception.ConflictException;
import es.daw.clinicaapi.repository.InvoiceLineRepository;
import es.daw.clinicaapi.repository.MedicalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReportService {

    private final InvoiceLineRepository invoiceLineRepository;
    private final MedicalServiceRepository medicalServiceRepository;

    public Page<TopServiceReport> getTopServices(LocalDateTime from, LocalDateTime to, InvoiceStatus status, Pageable pageable) {

        if (from.isBefore(LocalDateTime.now()) || to.isBefore(LocalDateTime.now())) {
            if (pageable.getPageSize() < 1 && pageable.getPageSize() > 5) {
                throw new BadRequestException("El minimo de pagina es 1 y el maximo es 5");
            }
        } else {
            throw new BadRequestException("La fecha debe ser anterior a la fecha actual");
        }

        Page<TopServiceReport> billedServices = invoiceLineRepository.topServicesByIssuedAt(from, to, status, pageable);

        System.out.println("Lista de servicios: " + billedServices);
        return billedServices;
    }

    public List<ServiceSummaryReport> getServiceSummary(LocalDateTime from, LocalDateTime to, InvoiceStatus status) {
        try {
            if (to.isBefore(from)) {
                throw new BadRequestException("La fecha de inicio debe ser anterior a la fecha final");
            }

            InvoiceStatus invoiceStatus = InvoiceStatus.valueOf(status.name());

            List<ServiceSummaryReport> services = medicalServiceRepository.getServicesSummary(from, to, invoiceStatus);

            return services;
        } catch(ConflictException ex) {
            throw new ConflictException(ex.getMessage());
        }

    }
}
