<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recupero Password</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
    <%@ include file="/jsp/common/header.jsp" %>
    <br><br><br><br>
    <main>
        <h2>Recupera la tua password</h2>
        <h2>Inserisci l'indirizzo email associato al tuo account. Riceverai un link per reimpostare la password.</h2>
        <form action="<%= request.getContextPath() %>/UtenteControl" method="post">
            <input type="hidden" name="action" value="richiediResetPassword">

            <label for="emailReset">Email:</label><br>
			<input type="email" id="emailReset" name="emailReset" required>
			<span id="emailReset-check" style="font-size: 0.9em; display: block; height: 1.2em; margin-top: 2px;"></span>

            <input type="submit" value="Invia link di reset">
            <input type="reset" value="Annulla" onclick="window.history.back();">
        </form>
    </main>
    <%@ include file="/jsp/common/footer.jsp" %>
    <script>
	  let debounceTimeoutReset;
	  const emailResetInput = document.getElementById('emailReset');
	  const emailResetCheckSpan = document.getElementById('emailReset-check');
	
	  emailResetInput.addEventListener('input', function () {
	    clearTimeout(debounceTimeoutReset);
	    const email = this.value.trim();
	
	    // Regex semplice per controllo base email
	    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
	
	    if (email.length === 0) {
	      emailResetCheckSpan.textContent = '';
	      return;
	    }
	
	    if (!emailRegex.test(email)) {
	      emailResetCheckSpan.textContent = 'Email non valida';
	      emailResetCheckSpan.style.color = 'red';
	      return;
	    }
	
	    debounceTimeoutReset = setTimeout(() => {
	      fetch('<%= request.getContextPath() %>/CheckEmail', {
	        method: 'POST',
	        headers: {
	          'Content-Type': 'application/x-www-form-urlencoded',
	        },
	        body: 'email=' + encodeURIComponent(email),
	      })
	      .then(response => response.text())
	      .then(data => {
	        if (data === 'exists') {
	          emailResetCheckSpan.textContent = 'Email trovata nel sistema';
	          emailResetCheckSpan.style.color = 'green';
	        } else {
	          emailResetCheckSpan.textContent = 'Email non registrata';
	          emailResetCheckSpan.style.color = 'red';
	        }
	      })
	      .catch(() => {
	        emailResetCheckSpan.textContent = 'Errore controllo email';
	        emailResetCheckSpan.style.color = 'orange';
	      });
	    }, 300);
	  });
	</script>
    
    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
