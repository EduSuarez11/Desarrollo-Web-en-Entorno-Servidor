package es.daw.clinicaapi.service;


import es.daw.clinicaapi.dto.auth.role.request.RoleRequestCreate;
import es.daw.clinicaapi.dto.auth.role.request.RoleRequestId;
import es.daw.clinicaapi.dto.auth.role.response.RoleResponse;
import es.daw.clinicaapi.dto.auth.role.response.RoleUserResponse;
import es.daw.clinicaapi.entity.AppUser;
import es.daw.clinicaapi.entity.Role;
import es.daw.clinicaapi.exception.BadRequestException;
import es.daw.clinicaapi.repository.AppUserRepository;
import es.daw.clinicaapi.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final AppUserRepository userRepository;

    /// Crear rol
    public Role createRole(RoleRequestCreate req) {
        Role role = new Role();
        role.setName(req.name());

        Optional<Role> roleFind = roleRepository.findByName(req.name());

        if (roleFind.isPresent()) {
            throw new BadRequestException("Role already exists");
        }

        Role saved = roleRepository.save(role);

        return saved;
    }


    /// Modificar rol
    public RoleResponse updateRole(String name, Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        role.setName(name);

        Role update = roleRepository.save(role);
        return new RoleResponse(
                update.getId(),
                update.getName()
        );
    }

    /// Eliminar rol
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        roleRepository.delete(role);
    }


    /// Asignar un rol a un usuario
    public RoleUserResponse addRoleToUser(RoleRequestId req, Long userId) {
        List<String> rolesName = new ArrayList<>();
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Comprobar si rol existe y si ese usuario ya tiene ese rol asignado
        for (Long roleId : req.rolesId()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

            for (Role rolesUser : user.getRoles()) {
                if (rolesUser.getId().equals(roleId)) {
                    throw new BadRequestException("User already has this role");
                }
            }

            //role.addUser(user);
            user.addRole(role);
            //roleRepository.save(role);

            // Lista para mostrar el nombre de los roles asignados
            rolesName.add(role.getName());
        }

        return new RoleUserResponse(
                user.getId(),
                user.getUsername(),
                req.rolesId(),
                rolesName
        );
    }

    /// Eliminar un rol de un usuario
    public void removeRoleFromUser(Long userId, RoleRequestId req) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        for (Long roleId : req.rolesId()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

            for (Role rolesUser : user.getRoles()) {
                if (!rolesUser.getId().equals(roleId)) {
                    throw new BadRequestException("User does not have this role");
                }
            }

            role.removeUser(user);
            roleRepository.save(role);
        }
    }

    /// Listar roles por usuario
    public List<RoleResponse> getRolesByUser(Long userId) {
        List<RoleResponse> roles = roleRepository.findByUsers_Id(userId);
        if (roles.isEmpty()) {
            log.info("No hay roles: " + roles);
        }
        log.info("Roles: " + roles);
        return roles;
    }


    /// Reemplazar roles por nuevos roles
    public RoleUserResponse replaceRolesByNewRoles(RoleRequestId req, Long userId) {
        Set<Role> newRoles = new HashSet<>();
        List<String> rolesName = new ArrayList<>();
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        for (Long roleId : req.rolesId()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

            for (Role rolesUser : user.getRoles()) {
                if (rolesUser.getId().equals(roleId)) {
                    throw new BadRequestException("User already has this role");
                }
            }

            rolesName.add(role.getName());
            newRoles.add(role);
        }

        user.setRoles(newRoles);
        AppUser update = userRepository.save(user);
        return new RoleUserResponse(
                update.getId(),
                update.getUsername(),
                req.rolesId(),
                rolesName
        );
    }

}
