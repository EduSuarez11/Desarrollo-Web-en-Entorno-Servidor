package es.daw.clinicaapi.dto.auth.user.request;

import java.util.Set;

public record UserRequestCreate(
        String username,
        String password,
        Set<String> roles
) {
}
