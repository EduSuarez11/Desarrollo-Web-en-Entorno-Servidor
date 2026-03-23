package es.daw.clinicaapi.mapper;

import es.daw.clinicaapi.dto.response.invoice.InvoiceLineResponse;
import es.daw.clinicaapi.dto.response.invoice.InvoiceResponse;
import es.daw.clinicaapi.dto.response.invoice.InvoiceUpdateResponse;
import es.daw.clinicaapi.entity.Invoice;
import es.daw.clinicaapi.entity.InvoiceLine;

import java.util.stream.Collectors;

public final class InvoiceMapper {

    private InvoiceMapper() {}

    public static InvoiceResponse toResponse(Invoice inv) {
        return new InvoiceResponse(
            inv.getId(),
            inv.getAppointment().getId(),
            inv.getStatus().toString(),
            //inv.getPaymentMethod().name() != null ? inv.getPaymentMethod().name() : null, // También se puede hacer asi
            inv.getSubtotal(),
            inv.getTaxTotal(),
            inv.getTotal(),
            inv.getIssuedAt(),
            //inv.getPaidAt(),
            inv.getLines().stream()
                .map(InvoiceMapper::toLineResponse)
                .collect(Collectors.toList())
        );
    }

    public static InvoiceLineResponse toLineResponse(InvoiceLine l) {
        return new InvoiceLineResponse(
            l.getID(),
            l.getService().getId(),
            l.getService().getName(),
            l.getQuantity(),
            l.getUnitPrice(),
            l.getVatRate().toString(),
            l.getLineTotal()
        );
    }

    public static InvoiceUpdateResponse toUpdateResponse(Invoice inr) {
        return new InvoiceUpdateResponse(
                inr.getId(),
                inr.getAppointment().getId(),
                inr.getStatus().toString(),
                inr.getPaymentMethod().toString(),
                inr.getSubtotal(),
                inr.getTaxTotal(),
                inr.getTotal(),
                inr.getIssuedAt(),
                inr.getPaidAt(),
                inr.getLines().stream()
                        .map(InvoiceMapper::toLineResponse)
                        .collect(Collectors.toList())
        );
    }
}
