<%@ page import="es.daw.jakarta.cabeceras.model.Producto" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado de productos</title>
    <style>
        table{
            border-collapse: collapse;
        }
        th, td{
            border: 5px solid fuchsia;
        }
    </style>
</head>
<body>
<h1>Listado de productos</h1>
<table>
    <thead>
    <th>ID</th>
    <th>NOMBRE</th>
    <th>TIPO</th>
    <th>PRECIO</th>
    </thead>
    <tbody>

<%-- PENDIENTE AÑADIR SCRIPTLET PARA MOSTRAR LA LISTA DE PRODUCTOS --%>
<% List<Producto> productos = (List<Producto>) request.getAttribute("productos"); %>
<%--  RECOJO DE REQUEST LA LISTA DE PRODUCTO, RECORRO LA LISTA, COJO PROD A PROD  --%>

    <% for (Producto prod : productos) {%>
        <tr>
        <td><%= prod.getId() %></td>
        <td><%= prod.getNombre() %></td>
        <td><%= prod.getTipo() %></td>
        <td><%= prod.getPrecio() %></td>
        </tr>
    <%}%>
    </tbody>
</table>
<a href="/Cabeceras_war">Volver al inicio</a>
</body>
</html>