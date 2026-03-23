<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.model.Libro" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.model.Autor" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.util.Utils" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.controllers.FiltrarPorFechaLibroServlet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de libros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.2/dist/materia/bootstrap.min.css" rel="stylesheet">
    <style>
        .table-custom tbody tr:nth-child(odd) {
            background-color: #f8f9fa;
        }

        .table-custom tbody tr:nth-child(even) {
            background-color: #e9f2fa;
        }

        .table-custom tbody tr:hover {
            background-color: #d0e7f9;
        }
    </style>
</head>
<body class="bg-light">

<%String seleccionado = (String) request.getAttribute("seleccionado") != null ? (String) request.getAttribute("seleccionado") : "";
    Logger log = Logger.getLogger(FiltrarPorFechaLibroServlet.class.getName());
    log.info("Sel: " + seleccionado);
%>


<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary mb-0">📦 Libros disponibles</h2>
        <form action="<%=request.getContextPath()%>/filtrar" method="get" class="d-flex align-items-center">
            <select name="id_autor" class="form-select" style="width: 200px;">
                <option value="">-- Mostrar todos los autores --</option>
                <option value="1">Gabriel García Márquez</option>
                <option value="2">Julio Cortázar</option>
                <option value="3">Jorge Luis Borges</option>
                <option value="4">Juan Rulfo</option>
                <option value="5">Ernesto Sabato</option>
                <option value="6">Isabel Allende</option>
                <option value="7">Mario Vargas Llosa</option>
                <option value="8">Roberto Bolaño</option>
            </select>
            <button type="submit" class="btn btn-success" style="width: 100px">Filtrar</button>
        </form>
<%-- --%>
        <form action="<%=request.getContextPath()%>/filtrarFecha" method="get" class="d-flex align-items-center">
            <select name="decada" id="decada" class="form-select" style="width: 200px;">
                <option value="">-- Mostrar todas las fechas --</option>
                <option value="1940" <%=seleccionado.equals("1940") ? "selected": ""%>>1940s - 1950s</option>
                <option value="1950" <%=seleccionado.equals("1950") ? "selected": ""%>>1950s - 1960s</option>
                <option value="1960" <%=seleccionado.equals("1960") ? "selected": ""%>>1960s - 1970s</option>
                <option value="1970" <%=seleccionado.equals("1970") ? "selected": ""%>>1970s - 1980s</option>
                <option value="1980" <%=seleccionado.equals("1980") ? "selected": ""%>>1980s - 1990s</option>
                <option value="1990" <%=seleccionado.equals("1990") ? "selected": ""%>>1990s - 2000s</option>
                <option value="2000" <%=seleccionado.equals("2000") ? "selected": ""%>>2000s - 2010s</option>
            </select>
            <button type="submit" class="btn btn-success" style="width: 100px">Filtrar</button>
        </form>

        <a href="<%=request.getContextPath()%>/libros/crear" class="btn btn-success">➕ Añadir producto</a>
    </div>


    <%
        List<Libro> libros = (List<Libro>) request.getAttribute("libros");
        List<Autor> autores = (List<Autor>) request.getAttribute("autores");

        log.info("Libros: " + libros);
        if (libros != null && !libros.isEmpty()) {
    %>
    <div class="table-responsive">
        <table class="table table-bordered table-custom">
            <thead class="table-dark">
            <tr>
                <th scope="col">Código</th>
                <th scope="col">Nombre</th>
                <th scope="col">Fecha de publicación</th>
                <th scope="col">Autor</th>
                <th scope="col">Editar</th>
                <th scope="col">Eliminar</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Libro l : libros) {
                    String autor = Utils.encontrarAutor(autores, l.getAutor_id());
            %>
            <tr>
                <td><%= l.getId() %>
                </td>
                <td><%= l.getTitulo() %>
                </td>
                <td><%= l.getFecha_publicacion() %>
                </td>
                <td><%= autor %>
                </td>
                <td>
                    <form action="<%=request.getContextPath()%>/libros/modificar" method="get">
                        <input type="hidden" name="code" value="<%=l.getId()%>">
                        <button type="submit" class="btn btn-warning btn-sm">✏️</button>
                    </form>
                </td>
                <td>
                    <form action="<%=request.getContextPath()%>/libros/borrar" method="post"
                          onsubmit="return confirm('¿Seguro que deseas borrar este producto?');">
                        <input type="hidden" name="cod" value="<%=l.getId()%>">
                        <button type="submit" class="btn btn-danger btn-sm">🗑️</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <% } else { %>
    <div class="alert alert-warning" role="alert">
        ⚠️ No hay libros disponibles
    </div>
    <% } %>

    <div class="mt-4">
        <a href="<%= request.getContextPath() %>/" class="btn btn-secondary">⬅️ Volver al inicio</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>