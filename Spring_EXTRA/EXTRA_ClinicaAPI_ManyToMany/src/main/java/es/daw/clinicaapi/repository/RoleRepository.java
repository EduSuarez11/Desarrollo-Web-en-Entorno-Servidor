package es.daw.clinicaapi.repository;

import es.daw.clinicaapi.dto.auth.role.response.RoleResponse;
import es.daw.clinicaapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);


    @Query("""
        SELECT new es.daw.clinicaapi.dto.auth.role.response.RoleResponse(
            r.id,
            r.name
        ) FROM Role r
                JOIN r.users u
                    WHERE u.id = :id
    """)
    List<RoleResponse> findByUsers_Id(@Param("id") Long userId);
}