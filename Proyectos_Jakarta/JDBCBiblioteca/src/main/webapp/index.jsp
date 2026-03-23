<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Biblioteca JDBC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #f0f4f8, #e6ebef);
            font-family: 'Segoe UI', sans-serif;
        }
        header {
            background-color: #ffffff;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 0.75rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-radius: 0 0 1rem 1rem;
        }
        .brand {
            font-weight: 600;
            color: #0d6efd;
            font-size: 1.25rem;
        }
        .lang-select {
            width: 150px;
            border-radius: 20px;
        }
        .hero {
            text-align: center;
            padding: 3rem 1rem;
        }
        .hero h1 {
            font-weight: 700;
            color: #0d6efd;
        }
        .card-container {
            display: flex;
            justify-content: center;
            gap: 2rem;
            flex-wrap: wrap;
        }
        .card {
            width: 280px;
            border: none;
            border-radius: 1rem;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            transition: transform 0.2s ease-in-out;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .btn-custom {
            border-radius: 20px;
        }
        footer {
            text-align: center;
            color: #888;
            margin-top: 3rem;
        }
    </style>
</head>
<body>

<% String valor = request.getParameter("idioma") == null ? "es": request.getParameter("idioma");

if (request.getCookies() != null) {
    for (Cookie c : request.getCookies()) {
        if (c.getName().equals("Idioma")) {
            valor = c.getValue();
        }
    }
}

String seleccionado= "";
if (!valor.equals("es")) {
    seleccionado = "selected";
}
%>

<div class="container mt-3">

    <!-- CABECERA CON SELECTOR DE IDIOMA -->
    <header>
        <div class="brand">🌐 <%=valor.equals("es") ? "Biblioteca JDBC" : "JDBC Library" %></div>
        <form action="<%= request.getContextPath() %>/idioma" method="get" class="d-flex align-items-center">
            <select name="idioma" class="form-select lang-select" onchange="this.form.submit()">
                <option value="es" selected>Español 🇪🇸</option>
                <option value="en" <%=seleccionado%>>English 🇬🇧</option>
            </select>

<%--            <% String lenguaje = "";--%>
<%--                String idioma = request.getParameter("idioma");--%>

<%--                if (idioma.equals("es")) {--%>
<%--                    lenguaje = "Biblioteca JDBC";--%>
<%--                } else {--%>
<%--                    lenguaje = "JDBC Library";--%>
<%--                }--%>

<%--            %>--%>

        </form>
    </header>

    <section class="hero">
        <h1>📚 <%=valor.equals("es") ? "Biblioteca JDBC" : "JDBC Library"%> </h1>
        <p class="lead text-secondary">Gestión sencilla de autores y libros</p>
    </section>

    <div class="card-container">
        <!-- Card de Libros -->
        <div class="card text-center">
            <div class="card-body">
                <h4 class="card-title text-primary">📖 Libros</h4>
                <p class="card-text text-muted">Consulta, añade o edita libros de la biblioteca.</p>
                <a href="<%= request.getContextPath() %>/libros/ver" class="btn btn-primary btn-custom">Gestionar libros</a>
            </div>
        </div>

        <!-- Card de Autores -->
        <div class="card text-center">
            <div class="card-body">
                <h4 class="card-title text-success">✍️ Autores</h4>
                <p class="card-text text-muted">Administra el catálogo de autores registrados.</p>
                <a href="<%= request.getContextPath() %>/autores/ver" class="btn btn-success btn-custom">Gestionar autores</a>
            </div>
        </div>
    </div>

    <footer>
        <hr>
        <p class="small">Desarrollado con Servlets + JSP + JDBC</p>
    </footer>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
