<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unisa.model.UtenteBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");

    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/utente/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Informazioni Personali</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ProductStyle.css">
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<header>
    <h1>Il tuo profilo</h1>
</header>
<main>
    <h2>Informazioni personali</h2>

    <table>
        <tr>
            <th>Campo</th>
            <th>Valore</th>
        </tr>
        <tr><td>Nome</td><td><%= utente.getNome() %></td></tr>
        <tr><td>Cognome</td><td><%= utente.getCognome() %></td></tr>
        <tr><td>Email</td><td><%= utente.getEmail() %></td></tr>
        <tr><td>Telefono</td><td><%= utente.getTelefono() %></td></tr>
        <tr><td>Città</td><td><%= utente.getCitta() %></td></tr>
        <tr><td>Provincia</td><td><%= utente.getProvincia() %></td></tr>
        <tr><td>Indirizzo</td><td><%= utente.getIndirizzo() %> <%= utente.getNumeroCivico() %></td></tr>
        <tr><td>Codice Fiscale</td><td><%= utente.getCf() %></td></tr>
    </table>

    <div class="torna-wrapper">
    	<a href="<%=request.getContextPath()%>/jsp/utente/ordini.jsp" class="torna-catalogo">Visualizza i tuoi ordini</a>
        <a href="<%=request.getContextPath()%>/jsp/utente/aggiornaDati.jsp" class="torna-catalogo">Aggiorna i tuoi dati</a>
        <a href="<%=request.getContextPath()%>/index.jsp" class="torna-catalogo">Torna alla home</a>
    </div>
</main>
<br/>

<%@ include file="/jsp/common/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
