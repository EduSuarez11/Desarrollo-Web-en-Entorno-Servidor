package es.daw.clinicaapi.dto.doctorspecialty;

import es.daw.clinicaapi.enums.SpecialtyLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DoctorSpecialtyRequest(
        Long specialtyId,
        SpecialtyLevel level,
        LocalDate sinceDate,
        BigDecimal consultationFeeOverride
) {}
