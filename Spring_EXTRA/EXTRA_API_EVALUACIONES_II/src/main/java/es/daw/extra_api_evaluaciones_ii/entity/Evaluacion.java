package es.daw.extra_api_evaluaciones_ii.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
// ---------------------- RESTRICCIÓN ----------------------------
/*
Si tu modelo exige que por cada curso y tipo de evaluación solo exista una Evaluación
(es decir: la pareja curso+tipo debe ser única),
añade la restricción única en la base de datos (usando @Table(uniqueConstraints = ...) o una migración DDL).
Esto es la forma fiable (DB-enforced) de mantener integridad incluso con múltiples instancias de la app o errores en la lógica.
Mantén además nullable=false en las @JoinColumn porque es complementario: evita NULLs y la restricción evita duplicados.
Si quieres permitir varias evaluaciones del mismo tipo para un mismo curso, no pongas la restricción única.
 */
//Una clave primaria compuesta ya implica unicidad por definición, por lo que la @UniqueConstraint sería redundante.
// En este caso, el usar una clave primaria simple y no compuesta hace que URls del api sean más sencillas
// (/evaluaciones/{id})
// Además de simplificar repositorios y queries....
// ----------------------------------------------------------------
@Table(name = "evaluacion", uniqueConstraints =
    @UniqueConstraint(columnNames = {"curso_id","tipo_evaluacion_id"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="curso_id", nullable=false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tipo_evaluacion_id",nullable = false)
    private TipoEvaluacion tipoEvaluacion;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas;

}
