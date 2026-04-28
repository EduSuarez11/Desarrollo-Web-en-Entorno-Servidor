package es.daw.extra_api_evaluaciones_ii.exception.dto.validation;

public record FieldValidationError(
        String field,
        String message
) {
}
