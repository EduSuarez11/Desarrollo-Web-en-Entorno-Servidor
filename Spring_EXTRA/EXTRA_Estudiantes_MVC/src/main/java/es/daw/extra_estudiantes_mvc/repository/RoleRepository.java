package es.daw.extra_estudiantes_mvc.repository;

import es.daw.extra_estudiantes_mvc.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
