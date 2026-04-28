package es.daw.extra_api_evaluaciones_ii.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "curso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    /*
     - Un crso tiene muchas evaluaciones
     - La relación está mapeada por el lado hijo (Evaluacion.curso)
     - Curso no es el lado propietario (no tiene la FK)
     - Evaluacion tiene la FK (curso_id)

     ---------------- CASCADA -----------------
     cascade = todas las operaciones....!!!!

    // PERSIST
    Curso curso = new Curso();
    curso.setNombre("1º DAW");

    Evaluacion ev = new Evaluacion("nueva");
    ev.setCurso(curso);

    curso.addEvaluacion(ev);

    cursoRepository.save(curso);

    Se guarda Curso
    Se guardan automáticamente sus Evaluacion

    // MERGE (UPDATE)
    curso.getEvaluaciones().get(0).setTipoEvaluacion(...);
    cursoRepository.save(curso);

    Se actualiza TODO (curso + evaluaciones)

    // // DELETE

    cursoRepository.delete(curso);


    Se borra el Curso
    Se borran TODAS sus Evaluacion

    Si no tienes cascade REMOVE → error de FK
    O se quedan registros colgando

    -----  HUÉRFANOS ---------

    Esto NO es lo mismo que cascade.

    Esto controla qué pasa cuando un hijo deja de estar asociado al padre.

    curso.removeEvaluacion(ev);
    cursoRepository.save(curso);

    Con orphanRemoval = true: DELETE FROM evaluacion WHERE id = ?

    Sin orphanRemoval: UPDATE evaluacion SET curso_id = NULL


    ------------- RESUMEN ----------------
    delete(curso); --> SE APLICA CASCADE REMOVE

    curso.removeEvaluacion(ev); --> se aplica orphanRemoval


     */
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones;

    // -------- métodos helper
    public void removeEvaluacion(Evaluacion evaluacion) {
        evaluaciones.remove(evaluacion);
        evaluacion.setCurso(null);

    }

    public void addEvaluacion(Evaluacion evaluacion) {
        evaluaciones.add(evaluacion);
        evaluacion.setCurso(this);
    }

}
