package es.daw.jakarta.jdbcapp.controllers;

import es.daw.jakarta.jdbcapp.model.Fabricante;
import es.daw.jakarta.jdbcapp.model.Producto;
import es.daw.jakarta.jdbcapp.repository.FabricanteDAO;
import es.daw.jakarta.jdbcapp.repository.GenericDAO;
import es.daw.jakarta.jdbcapp.repository.ProductoDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/fabricantes/ver")
public class ListarFabricanteServlet extends HttpServlet {

    private GenericDAO<Fabricante,Integer> daoF;
    private GenericDAO<Producto,Integer> daoP;
    private Logger log = Logger.getLogger(ListarFabricanteServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            daoF = new FabricanteDAO();
            daoP = new ProductoDAO();
        } catch (Exception e) {
            log.info("Error al inicializar el DAO de Fabricante: " + e.getMessage());
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Fabricante> fabricantes;
        List<Producto> productos;
        boolean existe;
        try {
            fabricantes = daoF.findAll();
            productos = daoP.findAll();

            Collections.sort(fabricantes);

            req.setAttribute("fabricantes", fabricantes);
            req.setAttribute("productos", productos);

            getServletContext().getRequestDispatcher("/fabricantes.jsp").forward(req, resp);
        } catch (SQLException ex) {
            resp.sendError(500, "Este es el error: " + ex.getMessage());
        }


    }
}
