<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.UtenteBean" %>
<%
    // Recupero i parametri della query per mostrare messaggi all'utente
    String loginError = request.getParameter("loginError");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Utente</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <%@ include file="/jsp/common/header.jsp" %>
    <main>
        <%
            if ("true".equals(loginError)) {
        %>
            <div class="carrello-messaggio errore">⚠️ Credenziali non valide, riprova.</div>
        <%
            }
        %>
        <h2><b>Login</b></h2>
        <form action="<%= request.getContextPath() %>/UtenteControl" method="post">
            <!-- Input nascosto per l'azione del form -->
            <input type="hidden" name="action" value="login">

            <label for="email">Email:</label><br>
            <input type="email" id="email" name="email" required><br><br>

            <label for="password">Password:</label><br>
            <input type="password" id="password" name="password" required><br><br>

            <input type="submit" value="Accedi">
            <input type="reset" value="Annulla">
        </form>
        <div class="registrazione-container">
        	<p>Non hai un account?
        	<a href="${pageContext.request.contextPath}/jsp/utente/registrazione.jsp" class="registrazione-link">Registrati qui</a></p>
		</div>
	</main>
    <%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
