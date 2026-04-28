package es.daw.clinicaapi.dto.patient;

import es.daw.clinicaapi.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PatientResponse(
        Long idPatient,
        Long invoiceId,
        String fullName,
        String email,
        BigDecimal totalAmount,
        DiscountType discountType,
        LocalDateTime issuedAt,
        boolean hasInsurance
) {
}
