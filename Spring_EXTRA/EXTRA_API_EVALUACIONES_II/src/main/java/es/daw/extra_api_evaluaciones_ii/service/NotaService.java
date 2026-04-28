package es.daw.extra_api_evaluaciones_ii.service;

import es.daw.extra_api_evaluaciones_ii.dto.NotaResponse;
import es.daw.extra_api_evaluaciones_ii.dto.PatchNotaRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public interface NotaService {

    @Transactional
    NotaResponse patchCalificacion(Integer idEvaluacion, String nia, PatchNotaRequest req);
}
