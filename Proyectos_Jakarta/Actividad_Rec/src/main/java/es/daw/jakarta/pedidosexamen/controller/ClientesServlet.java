package es.daw.jakarta.pedidosexamen.controller;

import es.daw.jakarta.pedidosexamen.model.Cliente;
import es.daw.jakarta.pedidosexamen.repository.ClienteDAO;
import es.daw.jakarta.pedidosexamen.repository.GenericDAO;
import es.daw.jakarta.pedidosexamen.repository.PedidoDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/clientes/listar")
public class ClientesServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(ListarPedidosServlet.class.getName());

    private GenericDAO<Cliente, Long> clienteDAO = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            clienteDAO = new ClienteDAO();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            log.severe(e.getMessage());
            throw new ServletException("Error inicializando DAOs", e);
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Cliente> clientes = clienteDAO.findAll();
            request.setAttribute("clientes", clientes);
            getServletContext()
                    .getRequestDispatcher("/clientes.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            // 5️⃣ Si ocurre error SQL, reenviar a página de error
            e.printStackTrace();
            request.setAttribute("mensajeError",
                    "Error al recuperar los clientes: " + e.getMessage());

            getServletContext()
                    .getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {




    }
}