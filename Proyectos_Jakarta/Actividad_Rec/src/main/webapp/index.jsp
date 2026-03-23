<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestión de Pedidos - Inicio</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #e3f2fd, #ffffff);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        header {
            background-color: #0d6efd;
            color: white;
            padding: 2rem 0;
            text-align: center;
            box-shadow: 0 2px 8px rgba(0,0,0,0.15);
        }
        .main-content {
            margin-top: 3rem;
        }
        .card {
            border: none;
            border-radius: 1rem;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            transition: transform 0.2s ease;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        footer {
            text-align: center;
            margin-top: 4rem;
            padding: 1rem;
            font-size: 0.9rem;
            color: #6c757d;
        }
        .form-select {
            width: auto;
            display: inline-block;
        }
    </style>
</head>

<body>
<header>
    <h1>📦 Aplicación de Gestión de Pedidos</h1>
    <p class="lead mb-0">Consulta y administración de clientes y sus pedidos</p>
</header>

<div class="container main-content">

    <div class="row text-center mb-5">
        <div class="col-md-6 mb-3">
            <div class="card h-100">
                <div class="card-body">
                    <h4 class="card-title text-primary">📑 Consultar Pedidos</h4>
                    <p class="card-text">Explora todos los pedidos registrados y filtra por cliente para obtener información detallada.</p>
                    <a href="<%= request.getContextPath() %>/pedidos/listar" class="btn btn-primary">Ir a pedidos</a>
                </div>
            </div>
        </div>
        <div class="col-md-6 mb-3">
            <div class="card h-100">
                <div class="card-body">
                    <h4 class="card-title text-success">👥 Administración de Clientes</h4>
                    <p class="card-text">Gestiona los datos de tus clientes: creación, actualización o eliminación.</p>
                    <a href="<%= request.getContextPath() %>/clientes/listar" class="btn btn-success">Ir a clientes</a>
                </div>
            </div>
        </div>
    </div>

    <div class="row justify-content-center mt-5">
        <div class="col-md-6">
            <div class="card p-4">
                <h5 class="text-center text-secondary mb-4">⚙️ Preferencias de visualización</h5>
                <form action="preferencias" method="post" class="text-center">
                    <div class="mb-3">
                        <label for="pageSize" class="form-label fw-bold">Pedidos por página:</label>
                        <select name="pageSize" id="pageSize" class="form-select d-inline-block">
                            <option value="0">--- sin límite ---</option>
                            <option value="3">3</option>
                            <option value="5">5</option>
                            <option value="8">8</option>
                            <option value="10">10</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-outline-primary">Guardar</button>
                </form>
            </div>
        </div>
    </div>
</div>

<footer>
    <p>Proyecto JAKARTAEE · Gestión de Pedidos · © 2025</p>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
