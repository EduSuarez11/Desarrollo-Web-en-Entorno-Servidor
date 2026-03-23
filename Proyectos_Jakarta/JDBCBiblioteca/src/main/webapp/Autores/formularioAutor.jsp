<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.model.Autor" %>
<%@ page import="es.daw.jakarta.jdbcbiblioteca.model.Libro" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nuevo Autor</title>
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
    Autor autor = (Autor) request.getAttribute("libro");
    boolean esEdicion = autor != null;

    String titulo = esEdicion ? "Editar Autor" : "Nuevo Autor";
    String accion = esEdicion ? "modificar" : "crear";
%>

<div class="container">
    <div class="form-container">
        <h2 class="text-center text-primary mb-4"><%= titulo %></h2>

        <form action="<%=request.getContextPath()%>/autores/<%=accion%>" method="post">

            <!-- ID del Libro -->
            <div class="mb-3">
                <label for="id" class="form-label">ID del Autor</label>
                <input type="text" id="id" name="id" class="form-control"
                       required placeholder="Ej: 3"
                       value="<%= esEdicion ? autor.getId() : "" %>"
                    <%= esEdicion ? "readonly" : "" %>>
                <div class="form-text text-muted">
                    Identificador único del autor.
                </div>
            </div>

            <!-- Nombre del Libro -->
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre del Autor</label>
                <input type="text" id="nombre" name="nombre" class="form-control"
                       required placeholder="Ej: Fernando"
                       value="<%= esEdicion ? autor.getNombre() : "" %>">
            </div>


            <!-- Botones -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary px-4">
                    <%= esEdicion ? "Modificar" : "Guardar" %>
                </button>
                <a href="<%=request.getContextPath()%>/autores/ver" class="btn btn-secondary px-4">Cancelar</a>
            </div>

        </form>
    </div>
</div>

</body>
</html>