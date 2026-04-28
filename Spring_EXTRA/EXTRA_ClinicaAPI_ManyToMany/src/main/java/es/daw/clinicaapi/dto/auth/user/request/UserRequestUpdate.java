package es.daw.clinicaapi.dto.auth.user.request;

public record UserRequestUpdate(
        String username,
        String password
) {
}
