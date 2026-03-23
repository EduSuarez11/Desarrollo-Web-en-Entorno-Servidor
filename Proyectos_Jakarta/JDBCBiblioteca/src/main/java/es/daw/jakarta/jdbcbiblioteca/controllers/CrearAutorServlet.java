package es.daw.jakarta.jdbcbiblioteca.controllers;


import es.daw.jakarta.jdbcbiblioteca.model.Autor;
import es.daw.jakarta.jdbcbiblioteca.repository.AutorDAO;
import es.daw.jakarta.jdbcbiblioteca.repository.GenericDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet("/autores/crear")
public class CrearAutorServlet extends HttpServlet {

    private GenericDAO<Autor, Integer> daoA;
    private static final Logger log = Logger.getLogger(CrearLibroServlet.class.getName());


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            daoA = new AutorDAO();
        } catch (Exception ex) {
            log.info("Error: " + ex.getMessage());
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher("/Autores/formularioAutor.jsp").forward(req, resp);
        } catch (Exception ex) {
            log.info("Error: " + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");
            String nombre = req.getParameter("nombre");
            Autor autor = new Autor(Integer.parseInt(id), nombre);

            daoA.save(autor);
            resp.sendRedirect(req.getContextPath() + "/autores/ver");
        } catch (SQLException ex) {
            log.info("Error: " + ex.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + ex.getMessage());
        }
    }
}
