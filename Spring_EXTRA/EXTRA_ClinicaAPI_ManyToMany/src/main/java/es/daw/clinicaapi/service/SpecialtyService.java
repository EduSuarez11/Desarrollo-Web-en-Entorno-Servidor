package es.daw.clinicaapi.service;


import es.daw.clinicaapi.dto.specialty.SpecialtyRequest;

import es.daw.clinicaapi.entity.DoctorSpecialty;
import es.daw.clinicaapi.entity.Specialty;
import es.daw.clinicaapi.repository.DoctorSpecialtyRepository;
import es.daw.clinicaapi.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final DoctorSpecialtyRepository doctorSpecialtyRepository;

    public Specialty createSpecialty(SpecialtyRequest request) {
        Specialty specialty = new Specialty();
        specialty.setCode(request.getCode());
        specialty.setName(request.getName());

        Specialty saved = specialtyRepository.save(specialty);

        return saved;
    }



    public List<DoctorSpecialty> getDoctorsBySpecialty(Long specialtyId) {
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));

        List<DoctorSpecialty> doctorSpecialties = doctorSpecialtyRepository.findBySpecialtyId(specialtyId);

        return doctorSpecialties;
    }

}
