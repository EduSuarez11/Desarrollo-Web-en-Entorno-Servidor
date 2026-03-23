<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.daw.jakarta.jdbcapp.model.Fabricante" %>
<%@ page import="es.daw.jakarta.jdbcapp.model.Producto" %>
<%@ page import="es.daw.jakarta.jdbcapp.util.Utils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="es.daw.jakarta.jdbcapp.controllers.MostrarProductoPreferenciaServlet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Fabricantes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
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
<%String seleccionado = "";
    String opcion = "";

    if (request.getCookies() != null) {
        for (Cookie c: request.getCookies()) {
            if (c.getName().equals("preferencia")) {
                opcion = c.getValue();
            }
        }
    }

    if (opcion.equals("no")) {
        seleccionado = "selected";
    }

    Logger log = Logger.getLogger(MostrarProductoPreferenciaServlet.class.getName());
    log.info("opcion es: " + opcion);
%>

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary mb-0">🏭 Fabricantes registrados</h2>

        <form action="<%= request.getContextPath() %>/preferencias" method="post" class="text-center mb-4">
            <label for="mostrar" class="form-label fw-bold">Mostrar productos por fabricante:</label>
            <select name="mostrar" id="mostrar" class="form-select d-inline-block w-auto">
                <option value="si">Sí</option>
                <option value="no" <%=seleccionado%>>No</option>
            </select>
            <button type="submit" class="btn btn-outline-primary ms-2">Guardar</button>
        </form>

        <a href="<%= request.getContextPath() %>/fabricantes/crear" class="btn btn-success">➕ Añadir fabricante</a>
    </div>

    <%
        List<Fabricante> fabricantes = (List<Fabricante>) request.getAttribute("fabricantes");
        List<Producto> productos = (List<Producto>) request.getAttribute("productos");

        if (fabricantes != null && !fabricantes.isEmpty()) {
    %>
    <div class="table-responsive">
        <table class="table table-bordered table-custom">
            <thead class="table-dark">
            <tr>
                <th scope="col">Código</th>
                <th scope="col">Nombre</th>
                <%if (opcion.equals("si")) {%>

                    <th scope="col">Productos</th>
                <%}%>
                <th scope="col">Editar</th>
                <th scope="col">Borrar</th>
            </tr>
            </thead>
            <tbody>
            <%

                for (Fabricante f : fabricantes) {
            %>
            <tr>
                <td><%= f.getCodigo() %>
                </td>
                <td><%= f.getNombre() %>
                </td>

                <% if (opcion.equals("si")) { %>
                <td>
                        <%for (Producto p : productos) {%>

                        <%
                            if (f.getCodigo().equals(p.getCodigo_fabricante())) {

                        %>
                    <ul>
                        <li><%=p.getNombre()%></li>
                    </ul>
                            <%}%>
                        <%}%>
                </td>
                <%}%>

                <td>
                    <form action="<%= request.getContextPath() %>/fabricantes/editar" method="get">
                        <input type="hidden" name="codigo" value="<%= f.getCodigo() %>">
                        <button type="submit" class="btn btn-warning btn-sm">✏️</button>
                    </form>
                </td>

                <% if (!Utils.existeNombreProducto(productos, f.getCodigo())) {%>
                <td>
                    <form action="<%= request.getContextPath() %>/fabricantes/borrar" method="post"
                          onsubmit="return confirm('¿Seguro que deseas borrar este fabricante?');">
                        <input type="hidden" name="codigo" value="<%= f.getCodigo() %>">
                        <button type="submit" class="btn btn-danger btn-sm">🗑️</button>
                    </form>
                </td>
                <% } else { %>
                <td>
                    <form action="<%= request.getContextPath() %>/fabricantes/borrar" method="post"
                          onsubmit="return confirm('¿Seguro que deseas borrar este fabricante?');">
                        <input type="hidden" name="codigo" value="<%= f.getCodigo() %>">
                        <i>No se puede borrar</i>
                    </form>
                </td>
                <%}%>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <% } else { %>
    <div class="alert alert-warning" role="alert">
        ⚠️ No hay fabricantes registrados
    </div>
    <% } %>

    <div class="mt-4">
        <a href="<%= request.getContextPath() %>/" class="btn btn-secondary">⬅️ Volver al inicio</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
