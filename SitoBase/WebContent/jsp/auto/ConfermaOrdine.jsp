<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ordine Confermato</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<main>
    <h1>Grazie per il tuo ordine!</h1>
    <p><%= request.getAttribute("messaggio") != null ? request.getAttribute("messaggio") : "Ordine completato con successo." %></p>
    
    <div class="ordina-link-container">
        <a href="<%= request.getContextPath() %>/index.jsp" class="ordina-link">Torna al catalogo</a>
    </div>
</main>
<%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
