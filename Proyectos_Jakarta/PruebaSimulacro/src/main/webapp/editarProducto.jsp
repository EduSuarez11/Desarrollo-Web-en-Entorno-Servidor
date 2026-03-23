<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.daw.jakarta.jdbcapp.model.Producto" %>
<%@ page import="es.daw.jakarta.jdbcapp.model.Fabricante" %>
<%@ page import="java.util.List" %>

<%
    Producto producto = (Producto) request.getAttribute("producto");
    List<Fabricante> fabricantes = (List<Fabricante>) request.getAttribute("fabricantes");
%>

<html>
<head>
    <title>Editar Producto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-primary mb-4">✏️ Editar Producto</h2>

    <form action="actualizar" method="post">
        <input type="hidden" name="codigo" value="<%= producto.getCodigo() %>">

        <div class="mb-3">
            <label class="form-label">Nombre:</label>
            <input type="text" name="nombre" class="form-control" value="<%= producto.getNombre() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Precio:</label>
            <input type="number" step="0.01" name="precio" class="form-control" value="<%= producto.getPrecio() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Fabricante:</label>
            <select name="codigo_fabricante" class="form-select" required>
                <% for (Fabricante f : fabricantes) { %>
                <option value="<%=f.getCodigo()%>" <%= f.getCodigo().equals(producto.getCodigo_fabricante()) ? "selected" : "" %>>
                    <%=f.getNombre()%>
                </option>
                <% } %>
            </select>
        </div>

        <button type="submit" class="btn btn-success">💾 Guardar cambios</button>
        <a href="<%= request.getContextPath() %>/productos/listar" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>

