package es.daw.extra_estudiantes_mvc.service;

import es.daw.extra_estudiantes_mvc.entity.Role;
import es.daw.extra_estudiantes_mvc.entity.User;
import es.daw.extra_estudiantes_mvc.repository.RoleRepository;
import es.daw.extra_estudiantes_mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public void registerUser(User user) {
        // 1. Codificar contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2. Buscar rol, si no error
        Role role = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 3. Asignar rol al usuario
        user.addRole(role);

        // 4. Guardar usuario en BD
        userRepository.save(user);
    }
}
