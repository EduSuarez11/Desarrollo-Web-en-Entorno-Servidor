package es.daw.clinicaapi.repository;

import es.daw.clinicaapi.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    boolean existsByAppointmentId(Long appointmentId);


    @Query("""
    SELECT i
    FROM Invoice i
    WHERE i.issuedAt >= :startDate
        AND i.issuedAt <= :endDate
    """)
    List<Invoice> findPatientFilterDate(LocalDateTime startDate, LocalDateTime endDate);
}

