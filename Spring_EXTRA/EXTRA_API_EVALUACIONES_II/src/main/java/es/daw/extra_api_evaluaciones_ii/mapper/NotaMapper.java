package es.daw.extra_api_evaluaciones_ii.mapper;


import es.daw.extra_api_evaluaciones_ii.dto.*;
import es.daw.extra_api_evaluaciones_ii.entity.Nota;
import org.springframework.stereotype.Component;

@Component
public class NotaMapper {
    public NotaResponse mapToResponse(Nota nota) {
        return new NotaResponse(
                nota.getId(),
                new AlumnoDto(
                        String.valueOf(nota.getAlumno().getNia()),
                        nota.getAlumno().getNombre(),
                        nota.getAlumno().getApellidos()
                ),
                new EvaluacionDto(
                        nota.getEvaluacion().getId(),
                        new CursoDto(
                                nota.getEvaluacion().getCurso().getId(),
                                nota.getEvaluacion().getCurso().getNombre(),
                                nota.getEvaluacion().getCurso().getDescripcion()
                        ),
                        new TipoEvaluacionDto(
                                nota.getEvaluacion().getTipoEvaluacion().getId(),
                                nota.getEvaluacion().getTipoEvaluacion().getNombre()
                        )
                ),
                nota.getCalificacion()
        );
    }
}
