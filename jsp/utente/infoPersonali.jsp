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
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>


<br><br><br><br><br>
    <main>
    <h2><b>IL TUO PROFILO</b></h2>

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
		<%
		    if (utente.isAdmin()) {
		%>
		    <a href="<%=request.getContextPath()%>/OrdiniGenerali" class="torna-catalogo">Visualizza gli ordini</a>
		<%
		    } else {
		%>
		    <a href="<%=request.getContextPath()%>/jsp/utente/ordini.jsp" class="torna-catalogo">Visualizza i tuoi ordini</a>
		<%
		    }
		%>
        <a href="<%=request.getContextPath()%>/jsp/utente/aggiornaDati.jsp" class="torna-catalogo">Aggiorna i tuoi dati</a>
        <a href="<%=request.getContextPath()%>/home" class="torna-catalogo">Torna alla home</a>
    </div>
</main>
<br/>

<%@ include file="/jsp/common/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
