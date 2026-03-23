<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Error en la aplicación</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #f8d7da, #ffffff);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .error-box {
            max-width: 700px;
            margin: 6rem auto;
            background-color: #fff;
            border: 1px solid #f5c2c7;
            border-radius: 1rem;
            padding: 2.5rem;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 {
            color: #dc3545;
            font-weight: 700;
            margin-bottom: 1rem;
        }
        .alert {
            text-align: left;
        }
        footer {
            text-align: center;
            margin-top: 4rem;
            font-size: 0.9rem;
            color: #6c757d;
        }
    </style>
</head>
<body>

<div class="error-box">
    <h1>🚨 ¡Error en la aplicación!</h1>
    <p class="lead mb-4">Se ha producido un problema durante la ejecución.</p>

    <div class="alert alert-danger" role="alert">
        <strong>Detalle del error:</strong><br>
        <%
            String mensaje = (String) request.getAttribute("mensajeError");
            if (mensaje == null && exception != null) {
                mensaje = exception.getMessage();
            }
        %>
        <p class="mt-2"><%= mensaje != null ? mensaje : "Error desconocido." %></p>
    </div>

    <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-outline-danger mt-3">
        ⬅ Volver al inicio
    </a>
</div>

<footer>
    <p>Proyecto JAKARTAEE · Gestión de Pedidos · © 2025</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
