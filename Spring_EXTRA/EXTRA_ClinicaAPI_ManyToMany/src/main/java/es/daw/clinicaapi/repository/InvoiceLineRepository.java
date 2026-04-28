package es.daw.clinicaapi.repository;

import es.daw.clinicaapi.dto.report.TopServiceReport;
import es.daw.clinicaapi.entity.InvoiceLine;
import es.daw.clinicaapi.enums.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {

    @Query("""
        select new es.daw.clinicaapi.dto.report.TopServiceReport(
           s.id,
           s.name,
           count(l),
           sum(l.quantity),
           sum(l.lineTotal)
        )
            from InvoiceLine l
                join l.invoice i
                join l.service s
                where i.issuedAt is not null
                    and i.issuedAt >= :from
                    and i.issuedAt <= :to
                    and :status = i.status or :status is null
                    group by s.id
                        order by sum(l.lineTotal)
    """)
    Page<TopServiceReport> topServicesByIssuedAt(
            LocalDateTime from,
            LocalDateTime to,
            InvoiceStatus status,
            Pageable pageable
    );

}