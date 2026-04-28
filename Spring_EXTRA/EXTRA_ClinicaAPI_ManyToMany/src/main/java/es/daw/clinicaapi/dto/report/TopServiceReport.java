package es.daw.clinicaapi.dto.report;

import java.math.BigDecimal;

public record TopServiceReport(
    Long serviceId,
    String serviceName,
    Long linesCount,
    Long unitsSold,
    BigDecimal totalBilled
) {}
