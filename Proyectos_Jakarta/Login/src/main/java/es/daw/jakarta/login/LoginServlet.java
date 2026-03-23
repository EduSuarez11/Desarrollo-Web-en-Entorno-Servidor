package es.daw.jakarta.login;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/loginServ")
public class LoginServlet extends HttpServlet {
    public void init() {

    }

    final static String USUARIO  = "admin";
    final static String PASSWORD = "12345";


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login = request.getParameter("login");
        String password = request.getParameter("pwd");

        if (login.equals(USUARIO) && password.equals(PASSWORD)) {
            request.setAttribute("login", login);

//            try(PrintWriter out = response.getWriter()){
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Login correcto</title>");
//                out.println("<meta charset=\"UTF-8\">");
//                out.println("</head>");
//                out.println("   <body>");
//                out.println("       <h1>Login correcto!</h1>");
//                out.println("       <h3>Hola "+login+" has iniciado sesión con éxito!</h3>");
//                out.println("   </body>");
//                out.println("</html>");
//            }
            getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no esta autorizado para ingresar a esta página!");
        }
    }

    public void destroy() {
    }
}