package es.daw.clinicaapi.controller;

import es.daw.clinicaapi.dto.report.ServiceSummaryReport;
import es.daw.clinicaapi.dto.report.TopServiceReport;
import es.daw.clinicaapi.enums.InvoiceStatus;
import es.daw.clinicaapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/top-services")
    @PreAuthorize("hasRole('BILLING')") // Solo los usuarios billing pueden acceder a este endpoint
    public ResponseEntity<Page<TopServiceReport>> topServices(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to,
            @RequestParam(required = false) InvoiceStatus status,
//            @RequestParam(required = false) Pageable pageable,
            @PageableDefault(page=0, size=3) Pageable pageable
    ) {
        Page<TopServiceReport> report = reportService.getTopServices(from, to, status, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }


    /**
     * ------- RETO 2 -------
     * @return
     */
    @GetMapping("/services-summary")
    public ResponseEntity<List<ServiceSummaryReport>> getServicesSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false) InvoiceStatus status
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getServiceSummary(from, to, status));
    }
}


