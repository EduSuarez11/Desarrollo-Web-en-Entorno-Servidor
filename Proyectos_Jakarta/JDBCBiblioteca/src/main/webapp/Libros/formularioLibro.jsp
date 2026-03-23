<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.model.Autor" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.model.Libro" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.IOException" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nuevo Libro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.2/dist/materia/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
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
<%
    List<Autor> autores = (List<Autor>) request.getAttribute("autores");
    Libro libro = (Libro) request.getAttribute("libro");
    boolean esEdicion = libro != null;

    String titulo = esEdicion ? "Editar Libro" : "Nuevo Libro";
    String accion = esEdicion ? "modificar" : "crear";


    Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");


%>

<div class="container">
    <div class="form-container">
        <h2 class="text-center text-primary mb-4"><%= titulo %></h2>

        <form action="<%=request.getContextPath()%>/libros/<%=accion%>" method="post">

            <ul>
            <% if (errores != null) {
                for (String e: errores.values()) {%>
                 <li style="color:red;"><%=e%></li>
            <%}
            }%>
            </ul>
            <!-- ID del Libro -->
            <div class="mb-3">
                <label for="id" class="form-label">ID del Libro</label>
                <input type="text" id="id" name="id" class="form-control"
                       placeholder="Ej: 3"
                       value="<%= esEdicion ? libro.getId() : "" %>"
                    <%= esEdicion ? "readonly" : "" %>>
                <div class="form-text text-muted">
                    Identificador único del libro.
                </div>

            </div>

            <!-- Nombre del Libro -->
            <div class="mb-3">
                <label for="titulo" class="form-label">Título del Libro</label>
                <input type="text" id="titulo" name="titulo" class="form-control"
                       placeholder="Ej: Cien años de soledad"
                       value="<%= esEdicion ? libro.getTitulo() : "" %>">
            </div>

            <!-- Fecha de Publicación -->
            <div class="mb-3">
                <label for="fecha_publicacion" class="form-label">Fecha de Publicación</label>
                <input type="date" id="fecha_publicacion" name="fecha_publicacion" class="form-control"
                       value="<%= esEdicion ? libro.getFecha_publicacion() : "" %>">
            </div>


            <!-- Autor -->
            <div class="mb-3">
                <label for="autor" class="form-label">Autor</label>
                <select id="autor" name="autor" class="form-select">
                    <option value="">-- Selecciona un autor --</option>
                    <%
                        if (autores != null) {
                            for (Autor a : autores) {
                                boolean seleccionado = esEdicion && libro.getId() == a.getId();
                    %>
                    <option value="<%= a.getId() %>" <%= seleccionado ? "selected" : "" %>>
                        <%= a.getNombre() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <!-- Botones -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary px-4">
                    <%= esEdicion ? "Modificar" : "Guardar" %>
                </button>
                <a href="<%=request.getContextPath()%>/libros/ver" class="btn btn-secondary px-4">Cancelar</a>
            </div>

        </form>
    </div>
</div>

</body>
</html>