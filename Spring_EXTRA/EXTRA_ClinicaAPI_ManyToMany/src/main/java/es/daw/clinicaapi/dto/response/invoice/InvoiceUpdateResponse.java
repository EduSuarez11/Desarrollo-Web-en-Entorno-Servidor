package es.daw.clinicaapi.dto.response.invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceUpdateResponse(
        Long id,
        Long appointmentId,
        String status,
        String paymentMethod,
        BigDecimal subtotal,
        BigDecimal taxTotal,
        BigDecimal total,
        LocalDateTime issuedAt,
        LocalDateTime paidAt,
        List<InvoiceLineResponse> lines
) {
}
