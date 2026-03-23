package es.daw.jakarta.jdbcappfase2.controllers;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import es.daw.jakarta.jdbcappfase2.model.Fabricante;
import es.daw.jakarta.jdbcappfase2.model.Producto;

import es.daw.jakarta.jdbcappfase2.repository.FabricanteDAO;
import es.daw.jakarta.jdbcappfase2.repository.GenericDAO;
import es.daw.jakarta.jdbcappfase2.repository.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ListarProductosServlet", value = "/productos/ver")
public class ListarProductosServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(ListarProductosServlet.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // PENDIENTE FILTAR POR ALGÚN CAMPO DE PRODUCTO...

        /// ------------- PRUEBA INICIAL PARA COMPROBAR QUE SE ESTABLECE LA CONEXIÓN -------------------
        // NO PUEDO CREAR OBJETOS DBCONNECTION PORQUE SU CONSTRUCTOR ES PRIVADO
        //DBConnection db = new DBConnection();
//        Connection con = null;
//        try {
//            con = DBConnection.getConnection();
//            log.info("* CONNECTION: " + con);
//        } catch (SQLException e) {
//            log.severe("* ERROR: " + e);
//
//            throw new RuntimeException(e);
//        }
        // ------------------------------------------------------
        List<Producto> productos = null;
        List<Fabricante> fabricantes = null;
        try {
            GenericDAO<Producto,Integer> daoP = new ProductoDAO();
            GenericDAO<Fabricante,Integer> daoF = new FabricanteDAO();

            productos = daoP.findAll();
            fabricantes = daoF.findAll();

            //productos.forEach(System.out::println);
            productos.forEach(p -> log.info(p.toString()));
            fabricantes.forEach(f -> log.info(f.toString()));

        } catch (SQLException e) {
            log.severe(e.getMessage());
            request.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
        }

        // SALIDA
        request.setAttribute("productos", productos);
        request.setAttribute("fabricantes", fabricantes);
        getServletContext().getRequestDispatcher("/productos/productos.jsp").forward(request,response);
        //request.getRequestDispatcher("/productos.jsp").forward(request,response); // rutas relativas...
    }

}