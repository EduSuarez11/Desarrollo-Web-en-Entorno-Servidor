package es.daw.extra_api_evaluaciones_ii.dto;

public record EvaluacionDto(
        Integer id,
        CursoDto curso,
        TipoEvaluacionDto tipoEvaluacion
) {}
