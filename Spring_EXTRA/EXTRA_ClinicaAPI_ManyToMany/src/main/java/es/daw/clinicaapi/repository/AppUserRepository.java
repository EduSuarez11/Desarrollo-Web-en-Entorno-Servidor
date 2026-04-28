package es.daw.clinicaapi.repository;


import es.daw.clinicaapi.dto.auth.user.response.UserResponseUpdate;
import es.daw.clinicaapi.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);


    @Query("""
        SELECT new es.daw.clinicaapi.dto.auth.user.response.UserResponseUpdate(
            u.id,
            u.username,
            u.password,
            u.enabled
        )
            FROM AppUser u
                JOIN u.roles r
                    WHERE r.name = :name
    """)
    List<UserResponseUpdate> findByRoles_Name(@Param("name") String roleName);
}

