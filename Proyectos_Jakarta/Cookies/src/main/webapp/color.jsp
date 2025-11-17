<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Color</title>
</head>
<body>

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


<h1>Elige tu color de fondo</h1>

<form action="preferencias" method="post">
    <label for="color">Color</label>
    <input type="color" name="color" id="color" value="<%=color%>"/>
    <button type="submit">Guardar</button>
</form>

<p>
    <a href="index.jsp">Volver</a>
    <a href="preferencias?accion=borrar">Borrar preferencias</a>
</p>

</body>
</html>
