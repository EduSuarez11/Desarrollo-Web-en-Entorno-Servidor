<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.daw.jakarta.jdbcapp.model.Fabricante" %>
<%@ page import="es.daw.jakarta.jdbcapp.model.Producto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulario de Producto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .form-container {
            max-width: 600px;
            margin: 3rem auto;
            padding: 2rem;
            border-radius: 1rem;
            background: #fff;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

<div class="container">
    <div class="form-container">
        <%
            Producto producto = (Producto) request.getAttribute("producto");
            List<Fabricante> fabricantes = (List<Fabricante>) request.getAttribute("fabricantes");

            boolean esEdicion = producto != null;
            String titulo = esEdicion ? "✏️ Editar Producto" : "➕ Nuevo Producto";
            String accion = esEdicion ? "actualizar" : "crear";
        %>

        <h2 class="text-center text-primary mb-4"><%= titulo %></h2>

        <form action="<%= request.getContextPath() %>/productos/<%= accion %>" method="post">

            <!-- CÓDIGO DEL PRODUCTO -->
            <div class="mb-3">
                <label for="codigo" class="form-label">Código del producto</label>
                <input type="number" id="codigo" name="codigo" class="form-control"
                       required placeholder="Ej: 101"
                       value="<%= esEdicion ? producto.getCodigo() : "" %>"
                    <%= esEdicion ? "readonly" : "" %>>
                <div class="form-text text-muted">
                    El código identifica de forma única al producto en la base de datos.
                    <% if (esEdicion) { %>
                    (No se puede modificar)
                    <% } %>
                </div>
            </div>

            <!-- NOMBRE -->
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre del producto</label>
                <input type="text" id="nombre" name="nombre" class="form-control"
                       required placeholder="Ej: Portátil Lenovo"
                       value="<%= esEdicion ? producto.getNombre() : "" %>">
            </div>

            <!-- PRECIO -->
            <div class="mb-3">
                <label for="precio" class="form-label">Precio</label>
                <input type="number" step="0.01" id="precio" name="precio" class="form-control"
                       required placeholder="Ej: 599.99"
                       value="<%= esEdicion ? producto.getPrecio() : "" %>">
            </div>

            <!-- FABRICANTE -->
            <div class="mb-3">
                <label for="fabricante" class="form-label">Fabricante</label>
                <select id="fabricante" name="codigo_fabricante" class="form-select" required>
                    <option value="">-- Selecciona un fabricante --</option>
                    <%
                        if (fabricantes != null && !fabricantes.isEmpty()) {
                            for (Fabricante f : fabricantes) {
                                boolean seleccionado = esEdicion && f.getCodigo().equals(producto.getCodigo_fabricante());
                    %>
                    <option value="<%= f.getCodigo() %>" <%= seleccionado ? "selected" : "" %>>
                        <%= f.getNombre() %>
                    </option>
                    <%
                        }
                    } else {
                    %>
                    <option disabled>No hay fabricantes disponibles</option>
                    <% } %>
                </select>
            </div>

            <!-- BOTONES -->
            <div class="d-flex justify-content-between">
                <a href="<%= request.getContextPath() %>/productos/ver" class="btn btn-secondary">⬅️ Cancelar</a>
                <button type="submit" class="btn btn-success">
                    <%= esEdicion ? "💾 Actualizar" : "Guardar producto" %>
                </button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
