package es.daw.jakarta.jdbcappfase2.controllers;

import es.daw.jakarta.jdbcappfase2.model.Fabricante;
import es.daw.jakarta.jdbcappfase2.model.Producto;
import es.daw.jakarta.jdbcappfase2.repository.FabricanteDAO;
import es.daw.jakarta.jdbcappfase2.repository.GenericDAO;
import es.daw.jakarta.jdbcappfase2.repository.ProductoDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/productos/crear")
public class CrearProductosServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CrearProductosServlet.class.getName());

    private GenericDAO<Fabricante, Integer> fabricanteDAO;
    private GenericDAO<Producto, Integer> productoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            fabricanteDAO = new FabricanteDAO();
            productoDAO = new ProductoDAO();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            throw new ServletException("Error inicializando DAO",e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DAMOS POR HECHO QUE TODOS LOS PARÁMETROS VAN A VENIR FENOMENAL.. NO HAGO COMPROBACIONES
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String nombre = request.getParameter("nombre");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        int codigoFabricante = Integer.parseInt(request.getParameter("codigo_fabricante"));

        Producto nuevo = new Producto(codigo, nombre, precio, codigoFabricante);
        try {
            productoDAO.save(nuevo);
        } catch (SQLException e){
            logger.severe(e.getMessage());
            request.setAttribute("error", e.getMessage());
            //getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/productos/ver");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Fabricante> fabricantes = new ArrayList<>();
        try {
            fabricantes = fabricanteDAO.findAll();
            req.setAttribute("fabricantes",fabricantes);
            // OBSERVACIÓN!!! AL SOBREESCRIBIR EL INIT SI NO LLAMO A SUPER PIERDO EL CONTEXTO
            getServletContext().getRequestDispatcher("/productos/formularioProducto.jsp").forward(req,resp);
            //req.getRequestDispatcher("/productos/crear.jsp").forward(req,resp);

        } catch (SQLException e) {
            ///throw new RuntimeException(e);
            logger.severe(e.getMessage());
            req.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(req, resp);
        }


    }
}