<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Map" %>
<%@ page import="java.util.Arrays" %>
<%
    Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");

    String usernameVal = request.getParameter("username") != null? request.getParameter("username"):"";
    String passwordVal = request.getParameter("password") != null? request.getParameter("password"):"";
    String[] lenguajesVal = request.getParameterValues("lenguajes");


    String[] roles = request.getParameterValues("roles");
    // No necesito hacerlo si recojo el valor directamente con ${param.pais}
    //String paisVal = request.getParameter("pais") != null? request.getParameter("pais"):"";

%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Formulario de usuarios</title>
    <%--    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<h3>Formulario de usuarios JSP y BS</h3>

<h2><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></h2>



<%
    // FORMA 1
    if (errores != null && errores.size() > 0) {
%>
<ul class="alert alert-danger mx-5 px-5">
    <% for (String error : errores.values()) {%>
    <li><%=error%>
    </li>
    <%}%>
</ul>
<%}%>


<%--<%--%>
<%--// FORMA 2--%>
<%--if(errores != null && errores.size()>0){--%>
<%--  out.println("<ul class=\"alert alert-danger mx-5 px-5\">");--%>
<%--    for(String error: errores.values()){--%>
<%--      out.println("<li>"+error+"</li>");--%>
<%--    }--%>
<%--  out.println("</ul>");--%>
<%--  --%>
<%--}--%>

<%--%>--%>


<div class="px-5">
    <form action="registro2" method="post">

        <div class="row mb-3">
            <label for="username" class="col-form-label col-sm-2">Usuario</label>
            <div class="col-sm-4">
                <input type="text" name="username" id="username" class="form-control" value="<%=usernameVal%>">
            </div>
            <%
                if (errores != null && errores.containsKey("username")) {
                    out.println("<small class='alert alert-danger col-sm-4' style='color: red;'>" + errores.get("username") + "</small>");
                }
            %>
        </div>

        <div class="row mb-3">
            <label for="password" class="col-form-label col-sm-2">Password</label>
            <div class="col-sm-4"><input type="password" name="password" id="password" class="form-control" value="${param.password}"></div>
            <%
                if (errores != null && errores.containsKey("password")) {%>
            <small class='alert alert-danger col-sm-4' style='color: red;'><%=errores.get("password")%>
            </small>
            <%}%>

        </div>

        <div class="row mb-3">
            <label for="email" class="col-form-label col-sm-2">Email</label>
            <div class="col-sm-4"><input type="text" name="email" id="email" class="form-control"
                                         value="${param.email}"></div>
            <%
                if (errores != null && errores.containsKey("email")) {
                    out.println("<small class='alert alert-danger col-sm-4' style='color: red;'>" + errores.get("email") + "</small>");
                }
            %>
        </div>
        <div class="row mb-3">
            <label for="pais" class="col-form-label col-sm-2">País</label>
            <div class="col-sm-4">
                <select name="pais" id="pais" class="form-select">
                    <option value="">-- seleccionar --</option>
                    <option value="ES" ${param.pais.equals("ES")?"selected":""}>España</option>
                    <option value="MX" ${param.pais.equals("MX")?"selected":""}>México</option>
                    <option value="CL" ${param.pais.equals("CL")?"selected":""}>Chile</option>
                    <option value="AR" ${param.pais.equals("AR")?"selected":""}>Argentina</option>
                    <option value="PE" ${param.pais.equals("PE")?"selected":""}>Perú</option>
                    <option value="CO" ${param.pais.equals("CO")?"selected":""}>Colombia</option>
                    <option value="VE" ${param.pais.equals("VE")?"SELECTED":""}>Venezuela</option>
                </select>
            </div>
            <%
                if (errores != null && errores.containsKey("pais")) {
                    out.println("<small class='alert alert-danger col-sm-4' style='color: red;'>" + errores.get("pais") + "</small>");
                }
            %>
        </div>

        <div class="row mb-3">
            <label for="lenguajes" class="col-form-label col-sm-2">Lenguajes de programación</label>
            <div class="col-sm-4">
                <select name="lenguajes" id="lenguajes" multiple>
                    <option value="java" <%= lenguajesVal != null && Arrays.asList(lenguajesVal).contains("java")?"selected":""%> >Java SE</option>
                    <option value="jakartaee" <%= lenguajesVal != null && Arrays.asList(lenguajesVal).contains("jakartaee")?"selected":""%>>Jakarta EE9</option>
                    <option value="spring" <%= lenguajesVal != null && Arrays.asList(lenguajesVal).contains("spring")?"selected":""%>>Spring Boot</option>
                    <option value="js" <%= lenguajesVal != null && Arrays.asList(lenguajesVal).contains("js")?"selected":""%>>JavaScript</option>
                    <option value="angular" <%= lenguajesVal != null && Arrays.asList(lenguajesVal).contains("angular")?"selected":""%>>Angular</option>
                    <option value="react" <%= lenguajesVal != null && Arrays.asList(lenguajesVal).contains("react")?"selected":""%>>React</option>
                </select>
            </div>
        </div>

        <div class="row mb-3">
            <label class="col-form-label col-sm-2">Roles</label>
            <div class="form-check col-sm-2">
                <input type="checkbox" name="roles" value="ROLE_ADMIN" class="form-check-input" <%= roles != null && Arrays.asList(roles).contains("ROLE_ADMIN") ? "checked": "" %>>
                <label class="form-check-label">Administrador</label>
            </div>
            <div class="form-check col-sm-2">
                <input type="checkbox" name="roles" value="ROLE_USER" class="form-check-input" <%= roles != null && Arrays.asList(roles).contains("ROLE_USER") ? "checked": "" %>>
                <label class="form-check-label">Usuario</label>
            </div>
            <div class="form-check col-sm-2">
                <input type="checkbox" name="roles" value="ROLE_MODERATOR" class="form-check-input" <%= roles != null && Arrays.asList(roles).contains("ROLE_MODERATOR") ? "checked": "" %>>
                <label class="form-check-label">Moderador</label>
            </div>

        </div>
        <div class="row mb-3">
            <label class="col-form-label col-sm-2">Idiomas</label>
            <div class="form-check col-sm-2">
                <input type="radio" name="idioma" value="es" class="form-check-input" ${param.idioma.equals("es") ? "checked" : ""}>
                <label class="form-check-label">Español</label>
            </div>
            <div class="form-check col-sm-2">
                <input type="radio" name="idioma" value="en" class="form-check-input" ${param.idioma.equals("en") ? "checked" : ""}>
                <label class="form-check-label">Inglés</label>
            </div>
            <div class="form-check col-sm-2">
                <input type="radio" name="idioma" value="fr" class="form-check-input">
                <label class="form-check-label">Frances</label>
            </div>

        </div>
        <div class="row mb-3">
            <label for="habilitar" class="col-form-label col-sm-2">Habilitar</label>
            <div class="form-check col-sm-2">
                <input type="checkbox" name="habilitar" id="habilitar" class="form-check-input">
            </div>
        </div>
        <div class="row mb-3">
            <div>
                <input type="submit" value="Enviar" class="btn btn-primary">
            </div>
        </div>
        <input type="hidden" name="secreto" value="12345">
    </form>
</div>
</body>
</html>
