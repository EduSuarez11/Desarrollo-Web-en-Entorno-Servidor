package es.daw.clinicaapi.controller;

import es.daw.clinicaapi.dto.doctor.DoctorRequest;
import es.daw.clinicaapi.dto.doctorspecialty.DoctorSpecialtyRequest;
import es.daw.clinicaapi.dto.doctorspecialty.UpdateDoctorSpecialtyRequest;
import es.daw.clinicaapi.entity.Doctor;
import es.daw.clinicaapi.entity.DoctorSpecialty;
import es.daw.clinicaapi.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    /**
     *
     * PREREQUISITO 1
     * @param doctorRequest
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Doctor> createDoctor(
            @RequestBody DoctorRequest doctorRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(doctorRequest));
    }

    /**
     * Prueba 1: Asignar una especialidad a un doctor 201
     * Prueba 2: Distintos doctores pueden compartir la misma especialidad 201
     * Prueba 3: Un doctor puede tener varias especialidades 201
     * Prueba 4: Un doctor NO puede repetir la misma especialidad 409
     * @param request
     * @param doctorId
     * @return
     */
    @PostMapping("/{id}/specialties")
    public ResponseEntity<DoctorSpecialty> addSpecialties(
            @RequestBody DoctorSpecialtyRequest request,
            @PathVariable("id") Long doctorId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.addSpecialty(request, doctorId));
    }


    /**
     * Prueba 5: Listar las especialidades de un doctor 200
     * @param doctorId
     * @return
     */
    @GetMapping("/{id}/specialties")
    public ResponseEntity<List<DoctorSpecialty>> getSpecialties(
            @PathVariable("id") Long doctorId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getSpecialtiesByDoctor(doctorId));
    }


    /**
     * Prueba 7: Actualizar atributos de la relación 200
     * @param doctorId
     * @param specialtyId
     * @param request
     * @return
     */
    @PatchMapping("/{doctorId}/specialties/{specialtyId}")
    public ResponseEntity<DoctorSpecialty> updateDoctorSpecialty(
            @PathVariable("doctorId") Long doctorId,
            @PathVariable("specialtyId") Long specialtyId,
            @RequestBody UpdateDoctorSpecialtyRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.updateDoctorSpecialty(request, doctorId, specialtyId));
    }


    /**
     * Prueba 8: Eliminar una especialidad de un doctor 204 (probar este método_)
     * @param doctorId
     * @param specialtyId
     * @return
     */
    @DeleteMapping("/{doctorId}/specialties/{specialtyId}")
    public ResponseEntity<Void> removeSpecialty(
            @PathVariable("doctorId") Long doctorId,
            @PathVariable("specialtyId") Long specialtyId
    ) {
        doctorService.removeSpecialty(doctorId, specialtyId);
        return ResponseEntity.noContent().build();
    }

}
