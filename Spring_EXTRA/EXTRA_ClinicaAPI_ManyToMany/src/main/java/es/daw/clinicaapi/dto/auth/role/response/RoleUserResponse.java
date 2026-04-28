package es.daw.clinicaapi.dto.auth.role.response;

import java.util.List;

public record RoleUserResponse(
        Long userId,
        String username,
        List<Long> rolesId,
        List<String> rolesName
) {

}
