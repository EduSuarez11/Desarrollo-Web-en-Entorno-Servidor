package es.daw.jakarta.jdbcbiblioteca.controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/idioma")
public class PreferenciaIdiomaServlet extends HttpServlet {


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idioma = req.getParameter("idioma") == null ? "es" : req.getParameter("idioma");

        if (idioma != null) {
            Cookie cookie = new Cookie("Idioma", idioma);
            cookie.setMaxAge(7*24*60*60);
            cookie.setPath("http://localhost:8080/JDBCBiblioteca_war_exploded/");
            resp.addCookie(cookie);
        }
        resp.sendRedirect(req.getContextPath());
    }
}
