<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Errore</title>
</head>
<body>
    <h2 style="color: red;">Si è verificato un errore:</h2>
    <p><strong><%= request.getAttribute("error") %></strong></p>
    <br>
    <p>Ritorna al <a href="index.jsp">CATALOGO</a></p>
</body>
</html>
