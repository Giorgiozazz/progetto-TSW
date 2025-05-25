<!DOCTYPE html>
<html lang="it">
<head>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Registrazione</title>
</head>
<body>
	<%@ include file="/jsp/common/header.jsp" %>
  <main>
    <form action="<%= request.getContextPath() %>/UtenteControl" method="post">
      <input type="hidden" name="action" value="registrazione" />
      
      <label for="cf">Codice Fiscale</label>
      <input type="text" id="cf" name="cf" maxlength="16" required />

      <label for="nome">Nome</label>
      <input type="text" id="nome" name="nome" required />

      <label for="cognome">Cognome</label>
      <input type="text" id="cognome" name="cognome" required />

      <label for="email">Email</label>
      <input type="email" id="email" name="email" required />

      <label for="telefono">Telefono</label>
      <input type="text" id="telefono" name="telefono" maxlength="20" required />

      <label for="citta">Città</label>
      <input type="text" id="citta" name="citta" required />

      <label for="provincia">Provincia</label>
      <input type="text" id="provincia" name="provincia" maxlength="100" required />

      <label for="indirizzo">Indirizzo</label>
      <input type="text" id="indirizzo" name="indirizzo" required />

      <label for="numero_civico">Numero Civico</label>
      <input type="text" id="numero_civico" name="numero_civico" maxlength="10"/>

	  <label for="password">Password</label>
      <input type="password" id="password" name="password" required />
      
      <input type="submit" value="Registrati" />
      <input type="reset" value="Annulla" />
    </form>
    <div class="registrazione-container">
        	<p>Hai già un account?
        	<a href="${pageContext.request.contextPath}/jsp/utente/login.jsp" class="registrazione-link">Accedi qui</a></p>
	</div>
  </main>
  <%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
