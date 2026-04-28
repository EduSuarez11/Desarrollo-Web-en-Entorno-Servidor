package es.daw.clinicaapi.service;

import es.daw.clinicaapi.dto.doctor.DoctorRequest;
import es.daw.clinicaapi.dto.doctorspecialty.DoctorSpecialtyRequest;
import es.daw.clinicaapi.dto.doctorspecialty.UpdateDoctorSpecialtyRequest;
import es.daw.clinicaapi.entity.Doctor;
import es.daw.clinicaapi.entity.DoctorSpecialty;
import es.daw.clinicaapi.entity.DoctorSpecialtyId;
import es.daw.clinicaapi.entity.Specialty;
import es.daw.clinicaapi.exception.ConflictException;
import es.daw.clinicaapi.repository.DoctorRepository;
import es.daw.clinicaapi.repository.DoctorSpecialtyRepository;
import es.daw.clinicaapi.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorSpecialtyRepository doctorSpecialtyRepository;
    private final SpecialtyRepository specialtyRepository;


    /// Crear un doctor
    public Doctor createDoctor(DoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setLicenseNumber(request.licenseNumber());
        doctor.setFullName(request.fullName());
        doctor.setEmail(request.email());

        Doctor saved = doctorRepository.save(doctor);
        return saved;
    }


    /// Añadir especialidad -- P3
    public DoctorSpecialty addSpecialty(DoctorSpecialtyRequest request, Long doctorId) {
        Specialty specialty = specialtyRepository.findById(request.specialtyId())
                .orElseThrow(() -> new RuntimeException("Specialty not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (doctorSpecialtyRepository.findByDoctorIdAndSpecialtyId(doctorId, request.specialtyId()) != null) {
            throw new ConflictException("Doctor already has this specialty");
        }

        DoctorSpecialty doctorSpecialty = new DoctorSpecialty();
        doctorSpecialty.setDoctor(doctor);
        doctorSpecialty.setSpecialty(specialty);
        doctorSpecialty.setLevel(request.level());
        doctorSpecialty.setSinceDate(request.sinceDate());
        doctorSpecialty.setConsultationFeeOverride(request.consultationFeeOverride());

        DoctorSpecialtyId id = new DoctorSpecialtyId(doctor.getId(), specialty.getId());
        doctorSpecialty.setId(id);

        doctor.addSpecialty(doctorSpecialty);

        //DoctorSpecialty saved = doctorSpecialtyRepository.save(doctorSpecialty);
        doctorRepository.save(doctor);
        return doctorSpecialty;
    }


    /// Obtener especialidades de un doctor -- P5
    public List<DoctorSpecialty> getSpecialtiesByDoctor(Long doctorId) {
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<DoctorSpecialty> doctorSpecialties = doctorSpecialtyRepository.findByDoctorId(doctorId);

        return doctorSpecialties;
    }



    /// Actualizar especialidad -- P7
    public DoctorSpecialty updateDoctorSpecialty(UpdateDoctorSpecialtyRequest req, Long doctorId, Long specialtyId) {
        specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));

        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DoctorSpecialty doctorSpecialty = doctorSpecialtyRepository.findByDoctorIdAndSpecialtyId(doctorId, specialtyId);
        if (doctorSpecialty == null) {
            throw new RuntimeException("DoctorSpecialty not found");
        }

        doctorSpecialty.setLevel(req.level());
        doctorSpecialty.setConsultationFeeOverride(req.consultationFeeOverride());

        DoctorSpecialty update = doctorSpecialtyRepository.save(doctorSpecialty);

        return update;
    }

    /// Eliminar especialidad -- P8: Prueba el orphanRemoval = true. Solo debe borrarse la fila intermedia, no el doctor ni la especialidad.
    public void removeSpecialty(Long doctorId, Long specialtyId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + specialtyId));

        DoctorSpecialty doctorSpecialty = doctorSpecialtyRepository.findByDoctorIdAndSpecialtyId(doctorId, specialtyId);
        if (doctorSpecialty == null) {
            throw new RuntimeException("DoctorSpecialty not found");
        }

        doctor.removeSpecialty(doctorSpecialty);
        doctorRepository.delete(doctor);
    }
}
