package es.daw.extra_api_evaluaciones_ii.service;

import es.daw.extra_api_evaluaciones_ii.dto.NotaResponse;
import es.daw.extra_api_evaluaciones_ii.dto.PatchNotaRequest;
import es.daw.extra_api_evaluaciones_ii.entity.Nota;
import es.daw.extra_api_evaluaciones_ii.mapper.NotaMapper;
import es.daw.extra_api_evaluaciones_ii.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final NotaMapper notaMapper;

    @Override
    public NotaResponse patchCalificacion(Integer idEvaluacion, String nia, PatchNotaRequest req) {
        Nota nota = notaRepository.findByEvaluacion_IdAndAlumno_Nia(idEvaluacion, nia)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        nota.setCalificacion(req.calificacion());

        return notaMapper.mapToResponse(nota);
    }
}
