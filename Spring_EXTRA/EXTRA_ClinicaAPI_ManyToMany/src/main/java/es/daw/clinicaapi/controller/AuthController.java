package es.daw.clinicaapi.controller;

import es.daw.clinicaapi.dto.auth.AuthRequest;
import es.daw.clinicaapi.dto.auth.AuthResponse;
import es.daw.clinicaapi.dto.auth.user.request.UserRequestCreate;
import es.daw.clinicaapi.dto.auth.user.response.UserResponseUpdate;
import es.daw.clinicaapi.dto.auth.user.response.UserResponseCreate;
import es.daw.clinicaapi.dto.auth.user.response.UserRoleResponse;
import es.daw.clinicaapi.dto.auth.user.request.UserRequestUpdate;
import es.daw.clinicaapi.security.JwtService;
import es.daw.clinicaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token) );
    }


    /**
     * Endpoint para dar de alta a usuario: 201 - Usuario creado correctamente
     * @param request
     * @return
     */
    @PostMapping("/user/create")
    public ResponseEntity<UserResponseCreate> addUser(
            @RequestBody UserRequestCreate request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body((userService.createUser(request)));
    }

    /**
     * Endpoint para modificar usuario: 200 - Usuario modificado correctamente
     * @return
     */
    @PatchMapping("/user/update/{id}")
    public ResponseEntity<UserResponseUpdate> updateUser(
            @PathVariable("id") Long userId,
            @RequestBody UserRequestUpdate userRequestUpdate
            ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.userUpdate(userRequestUpdate, userId));
    }


    /**
     * Endpoint para eliminar usuario: 204 - Usuario eliminado correctamente
     * @param userId
     * @return
     */
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    /**
     * Endpoint para listar usuarios por rol: 200 - Usuarios obtenidos correctamente
     * @param roleName
     * @return
     */
    @GetMapping("/get/users/{id}")
    public ResponseEntity<List<UserResponseUpdate>> getUsersByRoleName(
            @PathVariable("id") String roleName
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersByRoleName(roleName));
    }


    /**
     * Endpoint para obtener info del usuario (con roles) por id: 200 - Usuario obtenido correctamente
     * @param userId
     * @return
     */
    @GetMapping("/get/user/{id}")
    public ResponseEntity<UserRoleResponse> getUser(
            @PathVariable("id") Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

}
