package es.daw.jakarta.cookies;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "/PreferenciasColorServlet", value = "/preferencias")
public class PreferenciasColorServlet extends HttpServlet {

    private static final String COOKIE_NAME = "colorFondo";

    /** Método GET en el que se elimina la cookie */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Eliminar la cookie elegida
        String accion = request.getParameter("accion");

        if (accion != null) {
            if (accion.equalsIgnoreCase("borrar")) {
                Cookie cookie = new Cookie(COOKIE_NAME, "");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    /** Método POST en el que se recibe el color seleccionado por el usuario */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Crear la cookie con el color elegido por el usuario o cambiar su valor...
        String color = request.getParameter("color");

        if (color != null && !color.isEmpty()) {
            // Crear una cookie
            Cookie cookie = new Cookie(COOKIE_NAME, color);

            // Establecer tiempo de expiración en 7 días
            cookie.setMaxAge(7 * 24 * 60 * 60); // Segundos!!!
            cookie.setPath("/"); // Cookie válida en toda la aplicación
            response.addCookie(cookie); // Enviamos la cookie al cliente para almacenarlo
        }

        // Redirigir a la página de inicio
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}