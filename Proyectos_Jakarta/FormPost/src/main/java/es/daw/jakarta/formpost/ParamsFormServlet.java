package es.daw.jakarta.formpost;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Primera versión del formulario
 * Asociado a index.jsp
 * Recibe los datos del formulario y los guarda en un ArrayList
 * Al recibir errores, no guarda los datos que eran correctos
 * */

@WebServlet("/registro")
public class ParamsFormServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    private static final Logger logger = Logger.getLogger(ParamsFormServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usuario = req.getParameter("username");
        logger.info("username: " + usuario);

        String password = req.getParameter("password");
        logger.info("password: " + password);

        String email = req.getParameter("email");
        logger.info("email: " + email);

        String pais = req.getParameter("pais");
        String[] lenguajes = req.getParameterValues("lenguajes");
        String[] roles = req.getParameterValues("roles");
        String idioma = req.getParameter("idioma");
        String habilitar = req.getParameter("habilitar");
        //String secreto = req.getParameter("secreto");



        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        ArrayList<String> errores = new ArrayList<String>();
        ArrayList<String> datos = new ArrayList<String>();
        List<String> campos = Arrays.asList("Username", "Password", "Email", "Pais", "Lenguajes", "Roles", "Idioma", "Habilitado");
        /*
         * PENDIENTE DE REALIZAR LAS VALIDACIONES
         * el username es requerido
         * el password no puede ser vacio
         * el email es requerido y debe tener un formato de correo
         * debe seleccionar un idioma
         *
         * */

        // VALIDACIONES
        if (usuario.isBlank()) {
            errores.add("El usuario es obligatorio");
        } else {
            datos.add(usuario);
        }

        if (password.isBlank()) {
            errores.add("La contraseña es obligatoria");
        } else {
            datos.add(password);
        }
        if (email.isBlank() || !email.contains("@")) {
            errores.add("El email esta en blanco o no tiene el formato correcto");
        } else {
            datos.add(email);
        }

        if (pais.isEmpty()) {
            errores.add("Selecciona al menos un pais");
        } else {
            datos.add(pais);
        }
        if (lenguajes == null) {
            errores.add("Debes seleccionar al menos un idioma");
        } else {
            datos.add(String.join(",", lenguajes));
        }
        if (roles == null) {
            errores.add("Debes seleccionar al menos un rol");
        } else {
            datos.add(String.join(",", roles));
        }
        if (idioma == null) {
            errores.add("Selecciona un idioma");
        } else {
            datos.add(idioma);
        }

        if (habilitar == null) {
            datos.add("false");
        } else {
            datos.add("true");
        }

        // OUTPUT RESPONSE
        out.println("""
                <html>
                <head>
                    <title>Formulario</title>
                </head>
                
                <body>
                    <ul>

                """);
        // UN <li> POR CADA MENSAJE DE ERROR
        // SI NO HAY ERRORES PINTO LA LISTA DE VALORES
        if (!errores.isEmpty()) {
            errores.forEach(err -> out.printf("<li>%s</li>", err));

        } else {
                String lenguajesHTML = "";
                    for (String lenguaje : lenguajes){
                        lenguajesHTML += "<li>" + lenguaje + "</li>";
                    }
            for (String lenguaje : lenguajes){
                lenguajesHTML += "<li>" + lenguaje + "</li>";
            }

        }
        out.println("""
                    </ul>
                    <a href='index.jsp'>Volver</a>;
                </body>
                
                </html>
                """);
    }

    public void destroy() {
    }
}