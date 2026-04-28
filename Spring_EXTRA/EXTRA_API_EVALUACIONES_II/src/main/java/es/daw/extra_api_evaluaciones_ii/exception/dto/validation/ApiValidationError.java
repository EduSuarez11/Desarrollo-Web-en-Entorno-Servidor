package es.daw.extra_api_evaluaciones_ii.exception.dto.validation;

import java.util.List;

public record ApiValidationError(
        String message,
        List<FieldValidationError> errors
) {
}
