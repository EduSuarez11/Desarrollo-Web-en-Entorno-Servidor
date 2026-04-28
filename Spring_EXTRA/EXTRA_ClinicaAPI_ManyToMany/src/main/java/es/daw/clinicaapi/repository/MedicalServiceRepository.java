package es.daw.clinicaapi.repository;

import es.daw.clinicaapi.dto.report.ServiceSummaryReport;
import es.daw.clinicaapi.entity.MedicalService;
import es.daw.clinicaapi.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {

    @Query("""
    select new es.daw.clinicaapi.dto.report.ServiceSummaryReport(
        s.id,
        s.name,
        coalesce(count(l), 0L),
        coalesce(sum(l.quantity),0L),
        coalesce(cast(sum(l.unitPrice) as BigDecimal ), 0)
        ) from MedicalService s
            left join s.lines l
                on l.invoice.issuedAt >= :from
                        and l.invoice.issuedAt <= :to
                        and (:status is null or l.invoice.status = :status)
        group by s.id
            order by s.name asc
    """)
    List<ServiceSummaryReport> getServicesSummary(LocalDateTime from, LocalDateTime to, InvoiceStatus status);
}

