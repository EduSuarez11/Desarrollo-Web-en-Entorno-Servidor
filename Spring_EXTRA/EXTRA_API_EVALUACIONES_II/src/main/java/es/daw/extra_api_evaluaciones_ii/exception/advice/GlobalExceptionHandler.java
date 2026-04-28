package es.daw.extra_api_evaluaciones_ii.exception.advice;

import es.daw.extra_api_evaluaciones_ii.exception.ResourceNotFoundException;
import es.daw.extra_api_evaluaciones_ii.exception.dto.ApiErrorResponse;
import es.daw.extra_api_evaluaciones_ii.exception.dto.validation.ApiValidationError;
import es.daw.extra_api_evaluaciones_ii.exception.dto.validation.FieldValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        log.info("Pasa por aqui ResourceNotFound");
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        log.info("Pasa por aqui MethodArgumentNotValidException");
        Map<String, String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage() == null ? "Valor invalido" : fieldError.getDefaultMessage(),
                        (first, second) -> first
                ));

//        List<FieldValidationError> fieldErrors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(er -> new FieldValidationError(er.getField(), er.getDefaultMessage()))
//                .toList();
//        ApiValidationError validationErrors = new ApiValidationError("Error de validación", fieldErrors);

        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error de validacion",
                validationErrors

        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiValidationError> handleValidationPat(HandlerMethodValidationException ex) {
        log.info("Pasa por aqui HandlerMethodValidationException");

        List<FieldValidationError> fieldErrors = ex.getAllErrors()
                .stream()
                .map(er -> new FieldValidationError("Expresiones regulares", er.getDefaultMessage()))
                .toList();

        ApiValidationError validationErrors = new ApiValidationError("Errores de validación", fieldErrors);
        return ResponseEntity.badRequest().body(validationErrors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.info("Pasa por aqui IllegalArgumentException");
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }
}
