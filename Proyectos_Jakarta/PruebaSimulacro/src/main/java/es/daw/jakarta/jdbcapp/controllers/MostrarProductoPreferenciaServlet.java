package es.daw.jakarta.jdbcapp.controllers;

import es.daw.jakarta.jdbcapp.model.Fabricante;
import es.daw.jakarta.jdbcapp.model.Producto;
import es.daw.jakarta.jdbcapp.repository.FabricanteDAO;
import es.daw.jakarta.jdbcapp.repository.GenericDAO;
import es.daw.jakarta.jdbcapp.repository.ProductoDAO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


@WebServlet("/preferencias")
public class MostrarProductoPreferenciaServlet extends HttpServlet {

    private GenericDAO<Fabricante,Integer> daoF;
    private GenericDAO<Producto,Integer> daoP;
    private Logger log = Logger.getLogger(MostrarProductoPreferenciaServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            daoF = new FabricanteDAO();
            daoP = new ProductoDAO();
        } catch (Exception e) {
            log.info("Error al inicializar el DAO de Producto: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String opcion = req.getParameter("mostrar");

            if (opcion != null) {
                Cookie cookie = new Cookie("preferencia", opcion);
                cookie.setMaxAge(60*60*24*365);
                cookie.setPath(req.getContextPath());
                resp.addCookie(cookie);

                resp.sendRedirect(req.getContextPath() + "/fabricantes/ver");
            }
        } catch (Exception e) {
            resp.sendError(500, "Este es el error: " + e.getMessage());
        }
    }
}
