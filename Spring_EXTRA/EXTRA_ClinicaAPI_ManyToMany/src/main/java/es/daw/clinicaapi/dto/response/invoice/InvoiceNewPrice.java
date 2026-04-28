package es.daw.clinicaapi.dto.response.invoice;

import java.math.BigDecimal;

public record InvoiceNewPrice(
        Long invoiceId,
        Long invLineId,
        String vatRate,
        BigDecimal newTaxTotal,
        BigDecimal newTotalPrice,
        BigDecimal newLineTotal

) {
}
