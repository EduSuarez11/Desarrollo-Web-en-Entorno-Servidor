package es.daw.extra_estudiantes_mvc.repository;

import es.daw.extra_estudiantes_mvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
