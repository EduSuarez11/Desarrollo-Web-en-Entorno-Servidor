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
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/filtrar")
public class FiltrarLibroServlet extends HttpServlet {

    private GenericDAO<Libro, Integer> daoL;
    private GenericDAO<Autor, Integer> daoA;
    private static final Logger log = Logger.getLogger(ListarLibrosServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            daoL = new LibroDAO();
            daoA = new AutorDAO();
        } catch (Exception ex) {
            log.info("Error: " + ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Libro> libros;
        List<Autor> autores;
        try {
            String id = req.getParameter("id_autor");

            if (id.isEmpty()) {
                libros = daoL.findAll();
                autores = daoA.findAll();
                req.setAttribute("libros", libros);
                req.setAttribute("autores", autores);
                getServletContext().getRequestDispatcher("/Libros/libros.jsp").forward(req, resp);
                return;
            }//

            //log.info("id: " + id);
            libros = daoL.findByAutor(Integer.parseInt(id));
            autores = daoA.findAll();
            req.setAttribute("libros", libros);
            req.setAttribute("autores", autores);
            //log.info("libros: " + libros);
            getServletContext().getRequestDispatcher("/Libros/libros.jsp").forward(req, resp);

        } catch (Exception ex) {
            log.info("Error: " + ex.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + ex.getMessage());
        }
    }

}
