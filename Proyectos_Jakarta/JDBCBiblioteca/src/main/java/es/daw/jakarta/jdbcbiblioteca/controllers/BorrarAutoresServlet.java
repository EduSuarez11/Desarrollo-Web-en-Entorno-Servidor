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
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/autores/borrar")
public class BorrarAutoresServlet extends HttpServlet {

    private GenericDAO<Autor, Integer> daoA;
    private static final Logger log = Logger.getLogger(CrearLibroServlet.class.getName());
    private List<Autor> autores;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            daoA = new AutorDAO();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("cod");
            daoA.delete(Integer.parseInt(id));
            resp.sendRedirect(req.getContextPath() + "/autores/ver");
        } catch (SQLException ex) {
            log.info("Error: " + ex.getMessage());
        }
    }

}
