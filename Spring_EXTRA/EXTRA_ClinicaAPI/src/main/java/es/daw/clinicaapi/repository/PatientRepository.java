package es.daw.clinicaapi.repository;


import es.daw.clinicaapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {


}
