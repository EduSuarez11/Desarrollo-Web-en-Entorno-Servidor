package es.daw.extra_api_evaluaciones_ii.dto;

public record NotaResponse(
        Integer id,
        AlumnoDto alumno,
        EvaluacionDto evaluacion,
        Integer calificacion
) {}
