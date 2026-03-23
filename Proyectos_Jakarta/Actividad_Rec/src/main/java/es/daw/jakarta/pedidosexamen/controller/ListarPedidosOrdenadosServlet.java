package es.daw.jakarta.pedidosexamen.controller;

import es.daw.jakarta.pedidosexamen.model.Cliente;
import es.daw.jakarta.pedidosexamen.model.Pedido;
import es.daw.jakarta.pedidosexamen.repository.ClienteDAO;
import es.daw.jakarta.pedidosexamen.repository.PedidoDAO;
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

@WebServlet(name = "ListarPedidosOrdenadosServlet", value = {"/pedidos/ascendente", "/pedidos/descendente"})
public class ListarPedidosOrdenadosServlet extends HttpServlet {

    private static final Logger log =  Logger.getLogger(ListarPedidosOrdenadosServlet.class.getName());
    private ClienteDAO clienteDAO;
    private PedidoDAO pedidoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            clienteDAO = new ClienteDAO();
            pedidoDAO = new PedidoDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Pedido> pedidos;
        List<Cliente> clientes;
        String id_clienteString = request.getParameter("clienteId") != null ? request.getParameter("clienteId") : null;
        Long id_cliente = Long.parseLong(id_clienteString != null ? id_clienteString : "-1");

        try {
            clientes = clienteDAO.findAll();
            log.info("id_cliente recibido desde order: " + id_cliente);
            if (id_clienteString == null) {
                log.info("No pasa por filtro, hacia el order completo");
                pedidos = pedidoDAO.findAll();
            } else {
                log.info("Filtrado para ordenar");
                pedidos = pedidoDAO.findByIdCliente(id_cliente);
            }

            if (request.getRequestURI().contains("ascendente")) {
                // Ascendente
                log.info("Ordenando ascendentemente, pasa por aqui");
                Collections.sort(pedidos);
            } else {
                // Descendente
                log.info("Ordenando descendentemente, pasa por aqui");
                Collections.sort(pedidos, Collections.reverseOrder());
            }

            request.setAttribute("pedidos", pedidos);
            request.setAttribute("clientes", clientes);
            request.setAttribute("id_cliente", id_cliente);
            getServletContext().getRequestDispatcher("/pedidos.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
