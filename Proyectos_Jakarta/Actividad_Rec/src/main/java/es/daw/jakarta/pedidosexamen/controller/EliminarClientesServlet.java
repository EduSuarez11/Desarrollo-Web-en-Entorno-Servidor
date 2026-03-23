package es.daw.jakarta.pedidosexamen.controller;

import es.daw.jakarta.pedidosexamen.repository.ClienteDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;


@WebServlet("/clientes/eliminar")
public class EliminarClientesServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(EliminarClientesServlet.class.getName());
    private ClienteDAO clienteDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            clienteDAO = new ClienteDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = (String) request.getParameter("idClient");
        log.info("Eliminando cliente con id: " + id);
        Long idCliente = Long.parseLong(id);
        log.info("Cliente Long con id: " + idCliente);
        try {
            clienteDAO.delete(idCliente);

        } catch (SQLException e) {
            request.setAttribute("mensajeError", "Error al eliminar el cliente: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        response.sendRedirect(request.getContextPath() + "/clientes/listar");
    }
}
