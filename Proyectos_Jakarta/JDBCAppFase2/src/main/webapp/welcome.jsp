<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LogísticaApp - Panel principal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #e9f2fa, #f8f9fa);
            font-family: 'Segoe UI', sans-serif;
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
    </style>
</head>
<body>
<div class="container mt-5">

    <section class="hero">
        <h1>LogísticaApp</h1>
        <p class="lead text-secondary">Gestión sencilla de productos y fabricantes</p>
    </section>

    <div class="card-container">
        <!-- Card de productos -->
        <div class="card text-center">
            <div class="card-body">
                <h4 class="card-title text-primary">Productos</h4>
                <p class="card-text text-muted">Consulta, edita o añade nuevos productos del almacén.</p>
                <a href="productos/ver" class="btn btn-primary btn-custom">Gestionar productos</a>
            </div>
        </div>

        <!-- Card de fabricantes -->
        <div class="card text-center">
            <div class="card-body">
                <h4 class="card-title text-success">Fabricantes</h4>
                <p class="card-text text-muted">Administra la lista de fabricantes disponibles.</p>
                <a href="fabricantes/ver" class="btn btn-success btn-custom">Gestionar fabricantes</a>
            </div>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>