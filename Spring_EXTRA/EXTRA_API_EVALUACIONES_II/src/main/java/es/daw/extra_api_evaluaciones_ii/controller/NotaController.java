package es.daw.extra_api_evaluaciones_ii.controller;

import es.daw.extra_api_evaluaciones_ii.dto.NotaResponse;
import es.daw.extra_api_evaluaciones_ii.dto.PatchNotaRequest;
import es.daw.extra_api_evaluaciones_ii.service.NotaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;


    @PatchMapping("/evaluaciones/{evaluacionId}/notas/{nia}")
    public ResponseEntity<NotaResponse> patchNota(
            @PathVariable Integer evaluacionId,
            @PathVariable @Valid @Pattern(regexp = "^[1-9]\\d{4}$", message = "Formato de NIA incorrecto") String nia,
            @Valid @RequestBody PatchNotaRequest request
    ) {
        return ResponseEntity.ok(notaService.patchCalificacion(evaluacionId, nia, request));
    }
}
