package es.daw.clinicaapi.entity;


import es.daw.clinicaapi.enums.Specialty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
// siempre que uses @Embeddable como clave primaria
// JPA: Las clases que representen claves primarias compuestas deben implementar Serializable.
public class DoctorSpecialtyId implements Serializable {

    private Long doctorId;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Specialty specialty;
}

