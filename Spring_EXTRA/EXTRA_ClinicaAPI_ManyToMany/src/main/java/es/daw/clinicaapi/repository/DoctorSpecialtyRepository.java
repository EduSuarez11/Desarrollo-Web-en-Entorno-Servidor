package es.daw.clinicaapi.repository;

import es.daw.clinicaapi.entity.DoctorSpecialty;
import es.daw.clinicaapi.entity.DoctorSpecialtyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialty, DoctorSpecialtyId> {

    @Query("""
        SELECT ds
            FROM DoctorSpecialty ds
                WHERE ds.doctor.id = :doctorId
                    AND ds.specialty.id = :specialtyId
    """)
    DoctorSpecialty findByDoctorIdAndSpecialtyId(@Param("doctorId") Long doctorId, @Param("specialtyId") Long specialtyId);


    @Query("""
        SELECT ds
            FROM DoctorSpecialty ds
            JOIN FETCH ds.doctor s
                WHERE s.id = :specialtyId
    """)
    List<DoctorSpecialty> findBySpecialtyId(@Param("specialtyId") Long specialtyId);


    @Query("""
        SELECT ds
            FROM DoctorSpecialty ds
            JOIN FETCH ds.specialty s
                WHERE s.id = :doctorId
    """)
    List<DoctorSpecialty> findByDoctorId(@Param("doctorId") Long doctorId);



}
