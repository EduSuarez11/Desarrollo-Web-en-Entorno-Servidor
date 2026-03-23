package es.daw.clinicaapi.service;


import es.daw.clinicaapi.dto.report.TopServiceReport;
import es.daw.clinicaapi.enums.InvoiceStatus;
import es.daw.clinicaapi.exception.BadRequestException;
import es.daw.clinicaapi.exception.MissingServletRequestParameterException;
import es.daw.clinicaapi.repository.InvoiceLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ReportService {

    private final InvoiceLineRepository invoiceLineRepository;

    @Value("${report.pagination.max-lines}")
    private int maxPageSize;

    @Value("${report.pagination.min-lines}")
    private int minPageSize;

    public Page<TopServiceReport> getTopServices(LocalDateTime from, LocalDateTime to, InvoiceStatus status, Pageable pageable) {
        // Mejora: si no mando un parametro obligatorio salta esta excepcion: MissingServletRequestParameterException: Required String parameter 'from' is not present
        if (from == null || to == null) {
            throw new MissingServletRequestParameterException("from y to son obligatorios");
        }

        if (from.isBefore(to) && to.isAfter(LocalDateTime.now())) {
            if (pageable.getPageSize() < minPageSize || pageable.getPageSize() > maxPageSize) {
                throw new BadRequestException("El tamaño no puede ser menor que 1 o mayor que 5");
            }
        } else {
            throw new BadRequestException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        Page<TopServiceReport> billedServices = invoiceLineRepository.topServicesByIssuedAt(from, to, status, pageable);

        //System.out.println("Lista de servicios: " + billedServices);
        return billedServices;
    }
}
