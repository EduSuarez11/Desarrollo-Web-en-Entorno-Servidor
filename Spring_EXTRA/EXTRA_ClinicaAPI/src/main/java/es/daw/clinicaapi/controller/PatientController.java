package es.daw.clinicaapi.controller;

import es.daw.clinicaapi.dto.patient.PatientResponse;
import es.daw.clinicaapi.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @GetMapping("/HasInsurance")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PatientResponse>> getAllPatients(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {
        List<PatientResponse> patients = patientService.listAllPatient(start, end);

        return ResponseEntity.status(HttpStatus.OK).body(patients);
    }
}
