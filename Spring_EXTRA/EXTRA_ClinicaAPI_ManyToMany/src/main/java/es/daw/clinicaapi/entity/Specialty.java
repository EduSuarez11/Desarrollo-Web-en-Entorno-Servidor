package es.daw.clinicaapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specialties")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=30)
    private String code;

    @Column(nullable=false, length=80)
    private String name;

    @Column(nullable=false)
    private boolean active = true;

    @OneToMany(mappedBy = "specialty", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<DoctorSpecialty> doctorSpecialties = new HashSet<>();
}
