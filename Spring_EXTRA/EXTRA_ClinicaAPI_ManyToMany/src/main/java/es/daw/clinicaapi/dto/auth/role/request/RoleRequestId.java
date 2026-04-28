package es.daw.clinicaapi.dto.auth.role.request;

import jakarta.validation.constraints.Max;
import java.util.List;

public record RoleRequestId(
        @Max(value = 2, message = "Solo puedes asignar como máximo 2 roles a un usuario")
        List<Long> rolesId
) {}
