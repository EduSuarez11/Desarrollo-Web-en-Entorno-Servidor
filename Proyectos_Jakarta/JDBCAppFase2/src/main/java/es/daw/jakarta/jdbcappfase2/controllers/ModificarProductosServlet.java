package es.daw.jakarta.jdbcappfase2.controllers;

import es.daw.jakarta.jdbcappfase2.model.Fabricante;
import es.daw.jakarta.jdbcappfase2.model.Producto;
import es.daw.jakarta.jdbcappfase2.repository.FabricanteDAO;
import es.daw.jakarta.jdbcappfase2.repository.GenericDAO;
import es.daw.jakarta.jdbcappfase2.repository.ProductoDAO;
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

@WebServlet(name = "ModificarProductosServlet", value = "/productos/modificar")
public class ModificarProductosServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(ModificarProductosServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Pensar que este método va a recibir diferentes operaciones:
        // insert
        // update
        // delete...

        // Recoger los datos de entrada del request!!!!
        // HACER LAS COMPROBACIONES OPORTUNAS!!!
        // Si es nulo, especios en blanco o cosas no deseadas...
        String codigo = request.getParameter("codigo") == null || request.getParameter("codigo").isBlank()? "" : request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String codigo_fabricante = request.getParameter("codigo_fabricante");
        String precio = request.getParameter("precio");

        // insert, delete, update
        String operacion = request.getParameter("operacion");

        String codigoBorrar = request.getParameter("codigoBorrar");

        // Manejar con try catch y NumberFormatException
        //BigDecimal precioDecimal = new BigDecimal(precio);
        List<Producto> productos = new ArrayList<>();
        List<Fabricante> fabricantes = new ArrayList<>();
        // ------------------------------------------------------
        try {
            GenericDAO<Producto,Integer> daoP = new ProductoDAO();
            GenericDAO<Fabricante,Integer> daoF = new FabricanteDAO();

            Producto productoNuevo = new Producto(
                    Integer.parseInt(codigo),
                    nombre,
                    new BigDecimal(precio),
                    Integer.parseInt(codigo_fabricante)
            );

            switch (operacion) {
                case "insert" -> daoP.save(productoNuevo);
                case "update" -> {
                    // PENDIENTE!!!! SI NO EXISTE EL CÓDIGO A ACTUALIZAR QUE REDIRIJA A INDEX.JSP CON EL MENSAJE ADECUADO...
                    // forma 1: findById .. devuelve un optional vacío si no lo encuentra... (ifPresent)
                    // forma 2: findAll y busco en la lista el producto... recorrer y buscar el producto por propiedad
                    // ooooooooooooo usar contains!!! tengo que crear un objeto prod auxiliar con el código y .... debe
                    // estar implementado el equals para usar el contains.
                    daoP.update(productoNuevo);
                }
                case "delete" -> daoP.delete(Integer.parseInt(codigoBorrar));
            }

            // Independientemente de la operación realizada
            productos = daoP.findAll();
            fabricantes = daoF.findAll();

        } catch (SQLException e) {
            log.severe(e.getMessage());
            request.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
            return;
        } catch (NumberFormatException e) {
            //request.setAttribute("error", e.getMessage());
            request.setAttribute("error", "Formato numérico no correcto. El precio admite decimales con puntooooo majete");
            getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
            return;
        }

        System.out.println("************ CONTINÚA DESPUÉS DEL FORWARD *************");

        // SALIDA
        productos.sort((p1, p2) -> p2.getCodigo().compareTo(p1.getCodigo())); // desc
        //productos.sort((p1, p2) -> p1.getPrecio().compareTo(p2.getPrecio())); // asc
        request.setAttribute("productos", productos);
        request.setAttribute("fabricantes", fabricantes);
        request.setAttribute("message","Operación &lt;"+operacion+"&gt; realizada con éxito!");
        getServletContext().getRequestDispatcher("/informe.jsp").forward(request,response);

//        response.setContentType("text/html;charset=UTF-8");
//
//        try(PrintWriter out = response.getWriter()) {
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ModificarProductosServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            // PENDIENTE!! Este mensaje depende de la operación realizado!!!
//            out.println("<h1>Operación con producto realizada correctamente!!!</h1>");
//            out.println("</body>");
//        }

    }

}
