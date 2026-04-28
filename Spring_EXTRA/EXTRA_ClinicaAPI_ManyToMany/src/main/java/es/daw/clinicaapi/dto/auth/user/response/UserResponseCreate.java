package es.daw.clinicaapi.dto.auth.user.response;

import java.util.List;

public record UserResponseCreate(
        Long id,
        String username,
        String password,
        boolean enabled,
        List<String> roles
) {}
