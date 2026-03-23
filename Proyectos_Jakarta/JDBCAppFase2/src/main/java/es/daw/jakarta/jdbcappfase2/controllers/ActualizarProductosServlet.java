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
import java.util.Optional;

@WebServlet("/productos/actualizar")
public class ActualizarProductosServlet extends HttpServlet {


    private GenericDAO<Producto, Integer> daoP;
    private GenericDAO<Fabricante, Integer> daoF;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            daoP = new ProductoDAO();
            daoF = new FabricanteDAO();
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigo = request.getParameter("codigo");


        if (codigo == null && codigo.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parametro con el codigo");
        }

        try {
            Optional<Producto> productoOp = daoP.findById(Integer.parseInt(codigo));
            if (productoOp.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El producto no existe");
                return;
            }


            Producto producto = productoOp.get();
            request.setAttribute("producto", producto);
            getServletContext().getRequestDispatcher("/productos/formularioProducto.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int codigo = Integer.parseInt(request.getParameter("codigo"));
            String nombre = request.getParameter("nombre");
            BigDecimal precio = new BigDecimal(request.getParameter("precio"));
            int codigoFabricante = Integer.parseInt(request.getParameter("codigo_fabricante"));

            Producto productoNuevo = new Producto(codigo, nombre, precio, codigoFabricante);
            daoP.update(productoNuevo);

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "FORMATO INCORRECTO");
        }

        // Redirigir de nuevo a la lista actualizada
        response.sendRedirect(request.getContextPath() + "/productos/ver");
    }
}
