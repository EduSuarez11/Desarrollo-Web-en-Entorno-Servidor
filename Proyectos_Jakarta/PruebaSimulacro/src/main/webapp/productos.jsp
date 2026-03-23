<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.daw.jakarta.jdbcapp.model.Producto" %>
<%@ page import="es.daw.jakarta.jdbcapp.model.Fabricante" %>
<%@ page import="es.daw.jakarta.jdbcapp.util.Utils" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Gestión de Productos</title>
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

<div class="container mt-5">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="text-primary mb-0">📦 Productos disponibles</h2>
    <a href="<%= request.getContextPath() %>/productos/crear" class="btn btn-success">➕ Añadir producto</a>
  </div>

  <%
    List<Producto> productos = (List<Producto>)request.getAttribute("productos");
    List<Fabricante> fabricantes = (List<Fabricante>)request.getAttribute("fabricantes");

    if (productos != null && !productos.isEmpty()) {
  %>
  <div class="table-responsive">
    <table class="table table-bordered table-custom">
      <thead class="table-dark">
      <tr>
        <th scope="col">Código</th>
        <th scope="col">Nombre</th>
        <th scope="col">Precio</th>
        <th scope="col">Fabricante</th>
        <th scope="col">Editar</th>
        <th scope="col">Borrar</th>
      </tr>
      </thead>
      <tbody>
      <%
        for (Producto p : productos) {
          String nombreFab = Utils.obtenerNombreFabricante(fabricantes, p.getCodigo_fabricante());
      %>
        <tr>
          <td><%= p.getCodigo() %></td>
          <td><%= p.getNombre() %></td>
          <td><%= p.getPrecio() %></td>
          <td><%= nombreFab %></td>
          <td>
            <form action="<%= request.getContextPath() %>/productos/editar" method="get">
              <input type="hidden" name="codigo" value="<%= p.getCodigo() %>">
              <button type="submit" class="btn btn-warning btn-sm">✏️</button>
            </form>
          </td>
          <td>
            <form action="<%= request.getContextPath() %>/productos/borrar" method="post" onsubmit="return confirm('¿Seguro que deseas borrar este producto?');">
              <input type="hidden" name="codigo" value="<%= p.getCodigo() %>">
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
    ⚠️ No hay productos disponibles
  </div>
  <% } %>

  <div class="mt-4">
    <a href="<%= request.getContextPath() %>/" class="btn btn-secondary">⬅️ Volver al inicio</a>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
