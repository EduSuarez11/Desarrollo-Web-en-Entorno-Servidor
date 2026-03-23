package es.daw.clinicaapi.dto.request.invoice;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InvoiceLineCreateRequest(
    Long medicalServiceId,
    @NotNull(message = "message.null.error")
    @Positive(message = "message.minLines.error")
    @Max(value = 20, message = "message.quantity.error")
    Integer qty
) {}

// @Min : solo para tipos primitivos (int, long, double, etc) y sus wrappers (Integer, Long, Double, etc), no funciona con BigDecimal; para eso se usaría @DecimalMin

