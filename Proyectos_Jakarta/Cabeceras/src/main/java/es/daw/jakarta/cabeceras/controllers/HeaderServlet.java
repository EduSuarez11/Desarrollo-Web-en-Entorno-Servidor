package es.daw.jakarta.cabeceras.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cabeceras-request")
public class HeaderServlet extends HttpServlet {
        public void init() {
        }

        public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/html;charset=UTF-8");

            // Información de la solicitud
            String metodoHttp = req.getMethod();
            String requestUri = req.getRequestURI();
            String requestUrl = req.getRequestURL().toString();
            String contextPath = req.getContextPath();
            String servletPath = req.getServletPath();
            String ipCliente = req.getRemoteAddr();
            String ipLocal = req.getLocalAddr();
            int port = req.getLocalPort();
            String scheme = req.getScheme();
            String host = req.getHeader("host");
            String url = scheme + "://" + host + contextPath + servletPath;
            String url2 = scheme + "://" + ipLocal + ":" + port + contextPath + servletPath;

            try (PrintWriter out = resp.getWriter()) {

                // Encabezado HTML usando literal template
                out.println("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Cabeceras HTTP Request</title>
            </head>
            <body>
                <h1>Cabeceras HTTP Request</h1>
                <ul>
            """);

                // Lista de información básica de la solicitud
                String infoHtml = """
            <li>Metodo HTTP: %s</li>
            <li>Request URI: %s</li>
            <li>Request URL: %s</li>
            <li>Context Path: %s</li>
            <li>Servlet Path: %s</li>
            <li>IP local: %s</li>
            <li>IP cliente: %s</li>
            <li>Puerto local: %d</li>
            <li>Scheme: %s</li>
            <li>Host: %s</li>
            <li>URL: %s</li>
            <li>URL2: %s</li>
            """.formatted(metodoHttp, requestUri, requestUrl, contextPath, servletPath,
                        ipLocal, ipCliente, port, scheme, host, url, url2);

                out.println(infoHtml);

                // Headers HTTP usando streams para mayor legibilidad
                out.println(
                        java.util.Collections.list(req.getHeaderNames()).stream()
                                .map(name -> "<li>" + name + ": " + req.getHeader(name) + "</li>")
                                .reduce("", String::concat)
                );

                // Cierre del HTML
                out.println("""
                </ul>
                <a href="/Cabeceras_war">Volver al inicio</a>
            </body>
            </html>
            """);
            }
        }

        public void destroy() {
        }

}
