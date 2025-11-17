<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>

<h1>Login correcto!</h1>
<p>Hola <%= request.getAttribute("login") %> has iniciado sesión con éxito!
</p>
<br/>
<a href="index.html">Volver</a>
</body>
</html>