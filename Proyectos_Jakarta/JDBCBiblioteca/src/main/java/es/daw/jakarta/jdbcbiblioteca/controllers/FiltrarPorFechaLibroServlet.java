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
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/filtrarFecha")
public class FiltrarPorFechaLibroServlet extends HttpServlet {

    private GenericDAO<Libro, Integer> daoL;
    private GenericDAO<Autor, Integer> daoA;
    private Logger log = Logger.getLogger(FiltrarPorFechaLibroServlet.class.getName());

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
        String seleccionado = req.getParameter("decada");
        try {
            int fecha = 0;
            try {
                fecha = Integer.parseInt(req.getParameter("decada"));
            } catch (Exception ex) {
                libros = daoL.findAll();
                autores = daoA.findAll();
                req.setAttribute("libros", libros);
                req.setAttribute("autores", autores);
                getServletContext().getRequestDispatcher("/Libros/libros.jsp").forward(req, resp);
                return;
            }

            log.info("Decada: " + fecha);
            int fechaFinDecada = fecha + 9;

            //log.info("Fecha: " + fecha);
            String fechaInicioC = fecha + "-01-01";
            String fechaFinC = fechaFinDecada + "-12-31";

            Date fechaInicio = Date.valueOf(fechaInicioC);
            Date fechaFin = Date.valueOf(fechaFinC);

            log.info("Fecha inicio: " + fechaInicio);
            log.info("Fecha fin: " + fechaFin);
            libros = daoL.findByFecha(fechaInicio, fechaFin);
            autores = daoA.findAll();
            log.info("Libros encontrados: " + libros);


            req.setAttribute("libros", libros);
            req.setAttribute("autores", autores);
            req.setAttribute("seleccionado", seleccionado);
            getServletContext().getRequestDispatcher("/Libros/libros.jsp").forward(req, resp);

        } catch (Exception ex) {
            log.info("Error: " + ex.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ESTE ES EL ERROR: " + ex.getMessage());
        }
    }
}
