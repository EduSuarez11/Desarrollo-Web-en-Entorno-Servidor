package es.daw.clinicaapi.dto.doctor;

public record DoctorRequest(
        String licenseNumber,
        String fullName,
        String email
) {
}
