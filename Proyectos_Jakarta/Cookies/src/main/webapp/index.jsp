<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cookie</title>
</head>

<%

    // PENDIENTE CAMBIAR EL CÓDIGO PARA ENCONTRAR LA COOKIE DE DIFERENTES FORMAS!!
    // WHILE...
    // LIST CONTAINS...
    String color = "#ffffff";
    if (request.getCookies() != null) {
        // El nombre de mi cookie será colorFondo
        for(Cookie c : request.getCookies()) {
            if (c.getName().equals("colorFondo")) {
                color = c.getValue();
                break;
            }
        }
    }
%>

<body style="background-color: <%=color%>">

<h1><%= "Bienvenido" %></h1>
<p>Color de fondo actual <strong><%=color%></strong></p>
<p>¿Quieres cambiar el color de fondo?</p>

<br/>
<ul>
    <li><a href="color.jsp">Si, cambiar color</a></li>
    <li><a href="preferencias?accion=borrar">Borrar la preferencia</a></li>

</ul>

</body>
</html>