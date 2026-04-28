package es.daw.clinicaapi.service;

import es.daw.clinicaapi.dto.auth.user.request.UserRequestCreate;
import es.daw.clinicaapi.dto.auth.user.response.UserResponseUpdate;
import es.daw.clinicaapi.dto.auth.user.response.UserResponseCreate;
import es.daw.clinicaapi.dto.auth.user.response.UserRoleResponse;
import es.daw.clinicaapi.dto.auth.user.request.UserRequestUpdate;
import es.daw.clinicaapi.entity.AppUser;
import es.daw.clinicaapi.entity.Role;
import es.daw.clinicaapi.exception.BadRequestException;
import es.daw.clinicaapi.exception.NotFoundException;
import es.daw.clinicaapi.repository.AppUserRepository;
import es.daw.clinicaapi.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final AppUserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final RoleRepository roleRepository;

    /// Crear usuario
    @Transactional
    public UserResponseCreate createUser(UserRequestCreate request){
        Optional<AppUser> find = userRepository.findByUsername(request.username());
        Set<String> existentRoles = new HashSet<>();
        // Validar que el usuario no exista y que existan roles en el body
        if (find.isPresent()) throw new NotFoundException("Username already exists");
        if (request.roles().isEmpty()) throw new BadRequestException("The user must have at least one role");


        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(encoder.encode(request.password()));

        // Validar que existan los roles
        request.roles().forEach(role -> {
            Role rolExists = roleRepository.findByName(role)
                    .orElseThrow(() -> new NotFoundException("Role not found"));

            if (!existentRoles.add(role)) throw new BadRequestException("Duplicate role");
            user.addRole(rolExists);
        });

        AppUser saved = userRepository.save(user);

        return new UserResponseCreate(
                saved.getId(),
                saved.getUsername(),
                saved.getPassword(),
                saved.isEnabled(),
                saved.getRoles().stream().map(Role::getName).toList()
        );
    }

    /// Modificar usuario
    public UserResponseUpdate userUpdate(UserRequestUpdate update, Long id) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (user.getRoles().isEmpty()) throw new BadRequestException("The user must have at least one role");

        user.setUsername(update.username());
        user.setPassword(encoder.encode(update.password()));

        AppUser upde = userRepository.save(user);

        return new UserResponseUpdate(
                upde.getId(),
                upde.getUsername(),
                upde.getPassword(),
                upde.isEnabled()
        );
    }

    /// Eliminar usuario
    public void deleteUser(Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userRepository.delete(user);
    }

    /// Obtener un usuario por sus roles
    public List<UserResponseUpdate> getUsersByRoleName(String roleName) {
        List<UserResponseUpdate> users = userRepository.findByRoles_Name(roleName);
        if (users.isEmpty()) {
            log.info("No hay usuarios: " + users);
        }

        log.info("Usuarios: " + users);
        return users;
    }

    /// Obtener usuario y todos sus roles
    public UserRoleResponse getUser(Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        log.info("User: " + user);
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();

        return new UserRoleResponse(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                roles
        );
    }


}
