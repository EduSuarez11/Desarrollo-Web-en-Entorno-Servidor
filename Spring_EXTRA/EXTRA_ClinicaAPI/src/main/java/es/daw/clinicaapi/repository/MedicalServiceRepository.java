package es.daw.clinicaapi.repository;

import es.daw.clinicaapi.entity.MedicalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {
}

