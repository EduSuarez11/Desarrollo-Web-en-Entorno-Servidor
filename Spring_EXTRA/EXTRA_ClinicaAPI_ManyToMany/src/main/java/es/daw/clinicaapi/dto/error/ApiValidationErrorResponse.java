package es.daw.clinicaapi.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiValidationErrorResponse {
    private String message;
    private List<FieldValidationError> errors;
}
