package es.daw.jakarta.formpost;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;

/**
 * Segunda versión del formulario
 * Asociado a index2.jsp
 * Recibe los datos del formulario y los guarda en un ArrayList
 * Al recibir errores, guardará los datos que eran correctos
 * */

@WebServlet("/registro2")
public class ParamsFormServlet2 extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ParamsFormServlet2.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String username = req.getParameter("username");
        logger.info("username: " + username);

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

        Map<String, String> errores = new HashMap<>();

        req.setAttribute("errores", errores);

        if (username.isBlank()) {
            errores.put("username", "El usuario es obligatorio");
        }
        if (password.isBlank()) {
            errores.put("password", "El password es obligatorio");
        }

        if (email.isBlank() || !email.contains("@")) {
            errores.put("email", "El email debe contener el formato correcto");
        }

        if (lenguajes == null) {
            errores.put("lenguajes", "Debes seleccionar al menos un lenguaje");
        }

        if (roles == null) {
            errores.put("roles", "Debes seleccionar al menos un rol");
        }

        if (pais == null) {
            errores.put("pais", "El pais no puede quedar vacio");
        }

        if (idioma == null) {
            errores.put("idioma", "El idioma no puede quedar vacio");
        }

        getServletContext().getRequestDispatcher("/index2.jsp").forward(req, resp);
    }

    public void destroy() {
    }
}