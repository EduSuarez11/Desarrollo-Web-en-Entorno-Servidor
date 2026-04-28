package es.daw.clinicaapi.entity;

import es.daw.clinicaapi.enums.SpecialtyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "doctor_specialties")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DoctorSpecialty {

    /*
        No puede existir dos veces (doctor_id = 5, CARDIOLOGY)
        Pero sí puede existir (doctor_id = 6, CARDIOLOGY)
     */
    // @IdClass: es otra forma de definir claves primarias compuestas. Es más antiguo
    @EmbeddedId
    private DoctorSpecialtyId id;

    // El valor de la FK doctor_id se usa también como parte de la clave primaria compuesta,
    // concretamente como el campo doctorId dentro del EmbeddedId.
    // doctor_id es FK
    // doctor_id es parte de la PK
    // @MapsId sincroniza ambas cosas -> Le dices a JPA, el campo doctorId dentro de la PK se obtiene automáticamente del Doctor asociado.
    // Evita duplicidad y errores de sincronización

    // ----------- relación bidireccional con Doctor -------------
    @MapsId("doctorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Tiene la FK
    @JoinColumn(name="doctor_id",nullable = false)
    private Doctor doctor;


    @MapsId("specialtyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="specialty_id",nullable = false)
    private Specialty specialty;

    @Column(nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private SpecialtyLevel level;

    @Column(nullable=false)
    private boolean active = true;

    @Column(nullable=false)
    private LocalDate sinceDate;

    private BigDecimal consultationFeeOverride;


    // ---------- Métodos heredados de objecto --------


}
