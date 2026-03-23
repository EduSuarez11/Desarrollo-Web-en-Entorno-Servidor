package es.daw.clinicaapi.controller;

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

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/top-services")
    @PreAuthorize("hasRole('BILLING')") // Solo los usuarios billing pueden acceder a este endpoint
    public ResponseEntity<Page<TopServiceReport>> topServices(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false) InvoiceStatus status,
            @PageableDefault(size=3) Pageable pageable
    ) {
        Page<TopServiceReport> report = reportService.getTopServices(from, to, status, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }
}


