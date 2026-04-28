package es.daw.extra_api_evaluaciones_ii.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nota",
uniqueConstraints = @UniqueConstraint(columnNames = {"nia","evaluacion_id"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="evaluacion_id", nullable=false)
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="nia",nullable = false)
    private Alumno alumno;

    @Column(nullable = false)
    private Integer calificacion;
}
