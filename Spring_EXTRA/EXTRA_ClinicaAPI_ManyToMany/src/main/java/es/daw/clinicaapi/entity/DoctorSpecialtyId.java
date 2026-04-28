package es.daw.clinicaapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// siempre que uses @Embeddable como clave primaria
// JPA: Las clases que representen claves primarias compuestas deben implementar Serializable siempre.
public class DoctorSpecialtyId implements Serializable {

    // Identicador de version para la serializacion Java
    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Column(name = "doctor_id")
    private Long doctorId;

    @EqualsAndHashCode.Include
    @Column(name = "specialty_id")
    private Long specialtyId;
}
