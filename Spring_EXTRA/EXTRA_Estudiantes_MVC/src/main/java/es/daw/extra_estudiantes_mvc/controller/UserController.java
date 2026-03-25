package es.daw.extra_estudiantes_mvc.controller;

import es.daw.extra_estudiantes_mvc.entity.User;
import es.daw.extra_estudiantes_mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String viewLogin(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        return "login";
    }


    @GetMapping("/register")
    public String viewRegistro(Model model) {
        model.addAttribute("user", new User());
        return "estudiantes/registro";
    }


    @PostMapping("register")
    public String register(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

}
