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
	<br><br><br><br><br><br>
    <section class="carrello-messaggio successo">
        <h2>Ordine Confermato!</h2>
        <p>Grazie per aver effettuato il tuo acquisto. Il tuo ordine è stato ricevuto con successo.</p>
    </section>
    
    <div class="ordina-link-container">
    	<a href="<%= request.getContextPath() %>/jsp/utente/ordini.jsp" class="ordina-link">Vai ai tuoi ordini</a>
        <a href="<%= request.getContextPath() %>/index.jsp" class="ordina-link">Torna al catalogo</a>
    </div>
</main>
<%@ include file="/jsp/common/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
