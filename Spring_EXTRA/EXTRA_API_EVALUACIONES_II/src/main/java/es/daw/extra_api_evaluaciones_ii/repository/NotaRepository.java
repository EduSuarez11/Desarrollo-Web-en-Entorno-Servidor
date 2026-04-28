package es.daw.extra_api_evaluaciones_ii.repository;

import es.daw.extra_api_evaluaciones_ii.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    // Método derivado. Mejor con _ para separar atributos de entidades
    Optional<Nota> findByEvaluacion_IdAndAlumno_Nia(Integer evaluacionId, String nia);
}
