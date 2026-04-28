package es.daw.clinicaapi.controller;


import es.daw.clinicaapi.dto.request.invoice.InvoicePayRequest;
import es.daw.clinicaapi.dto.response.invoice.InvoiceUpdateResponse;
import es.daw.clinicaapi.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    /* ------------------- NUEVO -------------------- */
    @PatchMapping("/{id}/pay")
    @PreAuthorize("hasAnyRole('ADMIN','BILLING')")
    public ResponseEntity<InvoiceUpdateResponse> updateInvoice(
            @PathVariable("id") Long invoiceId,
            @RequestBody @Valid InvoicePayRequest invoicePayRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.payInvoice(invoiceId, invoicePayRequest));
    }


//    @GetMapping("/newIva/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<List<InvoiceNewPrice>> applyVat(
//            @PathVariable("id") Long invoiceId
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.applyVat(invoiceId));
//    }
}
