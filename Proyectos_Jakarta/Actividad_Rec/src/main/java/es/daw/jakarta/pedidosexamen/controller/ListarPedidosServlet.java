package es.daw.jakarta.pedidosexamen.controller;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import es.daw.jakarta.pedidosexamen.model.Cliente;
import es.daw.jakarta.pedidosexamen.model.Pedido;
import es.daw.jakarta.pedidosexamen.repository.ClienteDAO;
import es.daw.jakarta.pedidosexamen.repository.GenericDAO;
import es.daw.jakarta.pedidosexamen.repository.PedidoDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ListarPedidosServlet", value = "/pedidos/listar")
public class ListarPedidosServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(ListarPedidosServlet.class.getName());

    private GenericDAO<Pedido, Long> pedidoDAO = null;
    private GenericDAO<Cliente, Long> clienteDAO = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            pedidoDAO = new PedidoDAO();
            clienteDAO = new ClienteDAO();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            log.severe(e.getMessage());
            throw new ServletException("Error inicializando DAOs", e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        List<Cliente> clientes = new ArrayList<>();
        String id_clienteString = request.getParameter("clienteId") != null ? request.getParameter("clienteId") : null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("pageSize")) {
                request.setAttribute("pageSize", cookie.getValue());
            }
        }
        try {
            if (id_clienteString != null) {
                log.info("Pasando por filtro id_clienteString: " + id_clienteString);
                Long id_cliente = Long.parseLong(id_clienteString);
                List<Pedido> pedidosId = pedidoDAO.findByIdCliente(id_cliente);

                request.setAttribute("pedidos", pedidosId);
                request.setAttribute("clientes", clienteDAO.findAll());
                request.setAttribute("id_cliente", id_cliente);
                getServletContext().getRequestDispatcher("/pedidos.jsp").forward(request, response);
                return;
            }

            pedidos = pedidoDAO.findAll();
            clientes = clienteDAO.findAll();

            request.setAttribute("pedidos", pedidos);
            request.setAttribute("clientes", clientes);
            getServletContext().getRequestDispatcher("/pedidos.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("mensajeError",
                    "Error al acceder a la base de datos: " + e.getMessage());

            // Redirigir con ruta absoluta dentro del contexto
            getServletContext()
                    .getRequestDispatcher("/error.jsp")
                    .forward(request, response);
            return;
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

}