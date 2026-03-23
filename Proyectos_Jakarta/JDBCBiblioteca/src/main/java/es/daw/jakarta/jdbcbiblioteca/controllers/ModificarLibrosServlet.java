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
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/libros/modificar")
public class ModificarLibrosServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(CrearLibroServlet.class.getName());

    private GenericDAO<Libro, Integer> daoL;
    private GenericDAO<Autor, Integer> daoA;


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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String id = req.getParameter("id");
            String titulo = req.getParameter("titulo");
            String fecha = req.getParameter("fecha_publicacion");
            String autor = req.getParameter("autor");

            Libro libro = new Libro(Integer.parseInt(id), titulo, Integer.parseInt(autor), Date.valueOf(fecha));

            req.setAttribute("libro", libro);
            daoL.update(libro);

        } catch (SQLException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + ex.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/libros/ver");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Autor> autores;
        try {
            autores = daoA.findAll();
            String id = req.getParameter("code");
            Libro libro = daoL.findById(Integer.parseInt(id)).get();
            req.setAttribute("libro", libro);
            req.setAttribute("autores", autores);

            //autores = autores.stream().filter(b-> b.getId() == Integer.parseInt(id)).collect(Collectors.toList());

            getServletContext().getRequestDispatcher("/Libros/formularioLibro.jsp").forward(req, resp);

        } catch (SQLException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + ex.getMessage());
        }
    }



}
