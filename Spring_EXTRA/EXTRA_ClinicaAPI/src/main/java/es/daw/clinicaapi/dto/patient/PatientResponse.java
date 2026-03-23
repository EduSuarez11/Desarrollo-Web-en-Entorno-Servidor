package es.daw.clinicaapi.dto.patient;

import es.daw.clinicaapi.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PatientResponse(
        Long id,
        String fullName,
        String email,
        BigDecimal totalAmount,
        DiscountType discountType,
        LocalDate dateOfBirth,
        Boolean hasInsurance
) {
}
