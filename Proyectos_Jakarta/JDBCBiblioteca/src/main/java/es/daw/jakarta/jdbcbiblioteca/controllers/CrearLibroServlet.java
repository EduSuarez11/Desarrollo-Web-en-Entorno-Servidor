package es.daw.jakarta.jdbcbiblioteca.controllers;

import es.daw.jakarta.jdbcbiblioteca.model.Libro;
import es.daw.jakarta.jdbcbiblioteca.model.Autor;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/libros/crear")
public class CrearLibroServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errores = new HashMap<>();

        try {
            String id = request.getParameter("id");
            String titulo = request.getParameter("titulo");
            String fecha = request.getParameter("fecha_publicacion");
            String autor = request.getParameter("autor");


            if (id.isBlank()) {
                errores.put("id", "El ID es obligatorio");
            }

            if (titulo.isBlank()) {
                errores.put("titulo", "El titulo es obligatorio");
            }

            if (fecha == null || fecha.isBlank()) {
                errores.put("fecha", "La fecha es obligatoria");
            }

            if (autor == null || autor.isBlank()) {
                errores.put("autor", "El autor es obligatorio");
            }

            if (!errores.isEmpty()) {
                request.setAttribute("errores", errores);
                getServletContext().getRequestDispatcher("/Libros/formularioLibro.jsp").forward(request, response);
                return;
            }

            Libro libro = new Libro(Integer.parseInt(id), titulo, Integer.parseInt(autor), Date.valueOf(fecha));
            daoL.save(libro);

        } catch (SQLException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + ex.getMessage());
        }
            response.sendRedirect(request.getContextPath() + "/libros/ver");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Autor> autores = new ArrayList<>();
        try {
            autores = daoA.findAll();
            req.setAttribute("autores", autores);
            // OBSERVACIÓN!!! AL SOBREESCRIBIR EL INIT SI NO LLAMO A SUPER PIERDO EL CONTEXTO
            getServletContext().getRequestDispatcher("/Libros/formularioLibro.jsp").forward(req,resp);
            //req.getRequestDispatcher("/productos/crear.jsp").forward(req,resp);

        } catch (SQLException e) {
            //throw new RuntimeException(e);
            log.severe(e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + e.getMessage());
        }

    }
}
