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
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<br><br><br><br><br>
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
        <%
		    String loginRequired = request.getParameter("loginRequired");
		    if ("true".equals(loginRequired)) {
		%>
		    <div class="carrello-messaggio avviso" id="messaggio-avviso">
		        ⚠️ Devi effettuare il login per poter confermare l'ordine.
		    </div>
		     <script>
		        // Dopo 5 secondi (5000 ms), nascondi il messaggio
		        setTimeout(function() {
		            var msg = document.getElementById('messaggio-avviso');
		            if (msg) {
		                // Opzionale: fai una dissolvenza graduale
		                msg.style.transition = "opacity 1s ease";
		                msg.style.opacity = 0;
		                setTimeout(function() {
		                    msg.style.display = "none";
		                }, 1000);
		            }
		        }, 5000);
		    </script>
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
			<div class="password-wrapper">
			    <input type="password" id="password" name="password" required>
			    <span class="toggle-password" onclick="togglePassword()">👁️</span>
			</div><br><br>
			
			<p>
			  <a href="<%= request.getContextPath() %>/jsp/utente/passwordDimenticata.jsp" class="password-dimenticata-link">
			    Password dimenticata?
			  </a>
			</p>

            <input type="submit" value="Accedi">
            <input type="reset" value="Annulla">
        </form>
        <div class="registrazione-container">
        	<p>Non hai un account?
        	<a href="${pageContext.request.contextPath}/jsp/utente/registrazione.jsp" class="registrazione-link">Registrati qui</a></p>
		</div>
	</main>
    <%@ include file="/jsp/common/footer.jsp" %>
    <script>
    function togglePassword() {
        var pwInput = document.getElementById('password');
        var toggleIcon = document.querySelector('.toggle-password');
        if (pwInput.type === 'password') {
            pwInput.type = 'text';
            toggleIcon.textContent = '👁️'; // occhio aperto = password visibile
        } else {
            pwInput.type = 'password';
            toggleIcon.textContent = '🙈'; // scimmietta occhi chiusi = password nascosta
        }
    }

    // Inizializza l'icona corretta al caricamento della pagina
    window.onload = function() {
        var pwInput = document.getElementById('password');
        var toggleIcon = document.querySelector('.toggle-password');
        if (pwInput.type === 'password') {
            toggleIcon.textContent = '🙈';
        } else {
            toggleIcon.textContent = '👁️';
        }
    };
</script>

    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
