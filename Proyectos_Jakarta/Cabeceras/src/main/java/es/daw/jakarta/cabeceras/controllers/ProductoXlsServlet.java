package es.daw.jakarta.cabeceras.controllers;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

import es.daw.jakarta.cabeceras.model.Producto;
import es.daw.jakarta.cabeceras.service.ProductoService;
import es.daw.jakarta.cabeceras.service.ProductoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet({"/productos.xls", "/productos.html"})
public class ProductoXlsServlet extends HttpServlet {


    private static final Logger log = Logger.getLogger(ProductoXlsServlet.class.getName());

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ProductoService service = new ProductoServiceImpl();



        // Obtener la lista de productos
        List<Producto> productos = service.findAll();
        request.setAttribute("productos", productos);

        log.info("- Request URI: " + request.getRequestURI());
        log.info("- Servlet path: " + request.getServletPath());

        if (request.getServletPath().endsWith(".xls")) {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition","attachment;filename=productos.xls");
        }
        getServletContext().getRequestDispatcher("/productos.jsp").forward(request, response);
    }

    public void destroy() {
    }
}