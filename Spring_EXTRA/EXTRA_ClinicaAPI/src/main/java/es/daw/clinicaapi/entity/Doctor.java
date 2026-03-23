package es.daw.clinicaapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=40)
    private String licenseNumber;

    @Column(nullable=false, length=120)
    private String fullName;

    @Column(nullable=false, length=150)
    private String email;

    @Column(nullable=false)
    private boolean active = true;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment a) {
        appointments.add(a);
        a.setDoctor(this);
    }

    // -------- ASOCIACIÓN ENTRE DOCTOR Y ESPECIALIDADES ------------
    // Un doctor puede tener varias especialidades
    // Un doctor NO puede repetir la misma especialidad
    // Distintos doctores pueden compartir especialidad
    // 1:N desde doctor a especilidad

//    En términos JPA:
//    Doctor → lado inverso (no propietario). Es la que tiene mappedBy
//    DoctorSpecialty → lado propietario (tiene la FK)

//    CascadeType.ALL Propaga operaciones del Doctor a sus DoctorSpecialty.
//    Si haces: entityManager.persist(doctor);
//    También se persisten sus especialidades.
//    orphanRemoval = true Si una especialidad deja de estar asociada al doctor, se elimina de la base de datos.

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DoctorSpecialty> specialties = new HashSet<>();

    // HELPERS
//    En una relación bidireccional JPA, el lado propietario es el que tiene la FK
//    y es el único que realmente actualiza la base de datos.

//    Los métodos helper existen para mantener sincronizado el grafo de objetos en memoria.
//    En una relación bidireccional:
//    JPA NO sincroniza automáticamente ambos lados en memoria.
//    Si solo haces: specialties.add(ds);
//    El objeto DoctorSpecialty no sabe que pertenece a ese doctor.
//    El estado del grafo de objetos queda inconsistente.
//
//    Y como el lado propietario es DoctorSpecialty,
//    si no haces ds.setDoctor(this) → la FK no se actualiza en BD.
    public void addSpecialty(DoctorSpecialty s) {
        specialties.add(s);
        s.setDoctor(this);
    }

    public void removeSpecialty(DoctorSpecialty s) {
        specialties.remove(s);
        s.setDoctor(null);
    }


}
