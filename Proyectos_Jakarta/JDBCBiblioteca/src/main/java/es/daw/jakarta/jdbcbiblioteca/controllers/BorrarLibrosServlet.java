package es.daw.jakarta.jdbcbiblioteca.controllers;

import es.daw.jakarta.jdbcbiblioteca.model.Libro;
import es.daw.jakarta.jdbcbiblioteca.repository.GenericDAO;
import es.daw.jakarta.jdbcbiblioteca.repository.LibroDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet("/libros/borrar")
public class BorrarLibrosServlet extends HttpServlet {

    private GenericDAO<Libro, Integer> daoL;
    private static final Logger log = Logger.getLogger(CrearLibroServlet.class.getName());


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            daoL = new LibroDAO();
        } catch (SQLException ex) {
            log.info("Error: " + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("cod");
            daoL.delete(Integer.parseInt(id));
            resp.sendRedirect(req.getContextPath() + "/libros/ver");

        } catch (SQLException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + ex.getMessage());
        }



    }


}
