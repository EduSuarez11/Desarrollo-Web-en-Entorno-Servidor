package es.daw.clinicaapi.controller;

import es.daw.clinicaapi.dto.specialty.SpecialtyRequest;
import es.daw.clinicaapi.entity.DoctorSpecialty;
import es.daw.clinicaapi.entity.Specialty;
import es.daw.clinicaapi.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    /**
     * PREREQUISITO 2
     * @param request
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Specialty> createSpecialty(
            @RequestBody SpecialtyRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyService.createSpecialty(request));
    }

    /**
     * Prueba 6: Listar los doctores de una especialidad 200
     * @param specialtyId
     * @return
     */
    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<DoctorSpecialty>> getDoctors(
            @PathVariable("id") Long specialtyId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(specialtyService.getDoctorsBySpecialty(specialtyId));
    }
}
