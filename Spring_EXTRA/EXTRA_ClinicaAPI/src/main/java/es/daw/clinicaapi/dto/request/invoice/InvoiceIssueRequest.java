package es.daw.clinicaapi.dto.request.invoice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record InvoiceIssueRequest(
    @Valid @NotEmpty @Size(max = 10, message = "máximo 10 líneas por factura") List<InvoiceLineCreateRequest> lines
) {}

