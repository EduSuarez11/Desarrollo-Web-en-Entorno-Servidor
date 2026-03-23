package es.daw.jakarta.jdbcbiblioteca.controllers;

import es.daw.jakarta.jdbcbiblioteca.model.Autor;
import es.daw.jakarta.jdbcbiblioteca.model.Libro;
import es.daw.jakarta.jdbcbiblioteca.repository.AutorDAO;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "ListarLibrosServlet", value = "/libros/ver")
public class ListarLibrosServlet extends HttpServlet {

    private GenericDAO<Libro, Integer> daoL;
    private GenericDAO<Autor, Integer> daoA;
    private static final Logger log = Logger.getLogger(CrearLibroServlet.class.getName());


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            daoL = new LibroDAO();
            daoA = new AutorDAO();

        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Libro> libros;
        List<Autor> autores;
        try {
            autores = daoA.findAll();
            libros = daoL.findAll();


            //Collections.sort(libros);

            request.setAttribute("libros", libros);
            request.setAttribute("autores", autores);
            getServletContext().getRequestDispatcher("/Libros/libros.jsp").forward(request, response);
        }
        catch (SQLException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + ex.getMessage());
        }
    }
}
