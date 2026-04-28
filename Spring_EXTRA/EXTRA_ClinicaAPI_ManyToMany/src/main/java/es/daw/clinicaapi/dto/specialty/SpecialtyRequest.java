package es.daw.clinicaapi.dto.specialty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyRequest {

    private String code;
    private String name;

}
