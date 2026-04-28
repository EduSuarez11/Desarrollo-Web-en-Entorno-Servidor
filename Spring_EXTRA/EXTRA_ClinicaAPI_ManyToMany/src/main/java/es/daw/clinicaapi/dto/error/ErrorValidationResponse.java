package es.daw.clinicaapi.dto.error;

import java.util.HashMap;

public record ErrorValidationResponse(
        HashMap<String, String> errors
) {
}
