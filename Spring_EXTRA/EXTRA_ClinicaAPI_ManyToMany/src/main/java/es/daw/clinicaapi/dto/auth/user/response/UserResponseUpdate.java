package es.daw.clinicaapi.dto.auth.user.response;

public record UserResponseUpdate(
        Long id,
        String username,
        String password,
        boolean enabled
) {
}
