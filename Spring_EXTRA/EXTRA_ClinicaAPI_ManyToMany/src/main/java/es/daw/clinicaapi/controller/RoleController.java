package es.daw.clinicaapi.controller;

import es.daw.clinicaapi.dto.auth.role.request.RoleRequestCreate;
import es.daw.clinicaapi.dto.auth.role.request.RoleRequestId;
import es.daw.clinicaapi.dto.auth.role.response.RoleResponse;
import es.daw.clinicaapi.dto.auth.role.response.RoleUserResponse;
import es.daw.clinicaapi.entity.Role;
import es.daw.clinicaapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;


    /**
     * Endpoint para crear un rol: 201 - Rol creado correctamente
     * @param request
     * @return
     */
    @PostMapping("/role/create")
    public ResponseEntity<Role> createRole(
            @RequestBody RoleRequestCreate request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request));
    }


    /**
     * Endpoint para actualizar un rol: 200 - Rol actualizado correctamente
     * @param roleId
     * @param name
     * @return
     */
    @PutMapping("/role/update/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @RequestParam("id") Long roleId,
            @RequestBody String name
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(name, roleId));
    }


    /**
     * Endpoint para eliminar un rol: 204 - Rol eliminado correctamente
     * @param roleId
     * @return
     */
    @DeleteMapping("/role/delete/{id}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable("id") Long roleId
    ) {
        roleService.deleteRole(roleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    /**
     * Endpoint para asignar un rol a un usuario: 201 - Rol asignado correctamente
     * @param userId
     * @return
     */
    @PostMapping("/role/add/{id}")
    public ResponseEntity<RoleUserResponse> addRole(
            @PathVariable("id") Long userId,
            @RequestBody RoleRequestId request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.addRoleToUser(request, userId));
    }


    /**
     * Endpoint para quitar un rol de un usuario: 204 - Rol quitado correctamente
     * @param userId
     * @param request
     * @return
     */
    @PostMapping("/role/remove/{id}")
    public ResponseEntity<RoleUserResponse> removeRole(
            @PathVariable("id") Long userId,
            @RequestBody RoleRequestId request
    ) {
        roleService.removeRoleFromUser(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    /**
     * Endpoint para obtener roles de un usuario: 200 - Roles obtenidos correctamente
     * @param userId
     * @return
     */
    @GetMapping("/get/role/{id}")
    public ResponseEntity<List<RoleResponse>> getRolesByUser(
            @PathVariable("id") Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRolesByUser(userId));
    }


    /**
     * Endpoint para reemplazar roles de un usuario: 200 - Roles reemplazados correctamente
     * @param userId
     * @param rolesId
     * @return
     */
    @PatchMapping("/replace/roles/{id}")
    public ResponseEntity<RoleUserResponse> replaceRoles(
            @PathVariable("id") Long userId,
            @RequestBody RoleRequestId rolesId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.replaceRolesByNewRoles(rolesId, userId));
    }

}
