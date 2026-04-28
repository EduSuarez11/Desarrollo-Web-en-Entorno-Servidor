package es.daw.clinicaapi.dto.doctorspecialty;

import es.daw.clinicaapi.enums.SpecialtyLevel;

import java.math.BigDecimal;

public record UpdateDoctorSpecialtyRequest(
        SpecialtyLevel level,
        BigDecimal consultationFeeOverride
) {}
