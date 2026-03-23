<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.model.Autor" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.util.Utils" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de autores</title>
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

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary mb-0">📦 Autores disponibles</h2>
        <a href="<%=request.getContextPath()%>/autores/crear" class="btn btn-success">➕ Añadir autor</a>
    </div>

    <%

        List<Autor> autores = (List<Autor>)request.getAttribute("autores");

        if (autores != null && !autores.isEmpty()) {
    %>
    <div class="table-responsive">
        <table class="table table-bordered table-custom">
            <thead class="table-dark">
            <tr>
                <th scope="col">Código</th>
                <th scope="col">Nombre</th>
                <th scope="col">Editar</th>
                <th scope="col">Eliminar</th>

            </tr>
            </thead>
            <tbody>
            <%
                for (Autor a : autores) {

            %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getNombre() %></td>
                <td>
                    <form action="<%=request.getContextPath()%>/autores/modificar" method="get">
                        <input type="hidden" name="code" value="<%=a.getId()%>">
                        <button type="submit" class="btn btn-warning btn-sm">✏️</button>
                    </form>
                </td>
                <td>
                    <form action="<%=request.getContextPath()%>/autores/borrar" method="post" onsubmit="return confirm('¿Seguro que deseas borrar este producto?');">
                        <input type="hidden" name="cod" value="<%=a.getId()%>">
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
        ⚠️ No hay autores disponibles
    </div>
    <% } %>

    <div class="mt-4">
        <a href="<%= request.getContextPath() %>/" class="btn btn-secondary">⬅️ Volver al inicio</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>