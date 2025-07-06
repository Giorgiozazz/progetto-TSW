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
    <title>Aggiorna Profilo</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ProductStyle.css">
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<br><br><br><br><br>
<main>
<h2><b>MODIFICA LE TUE INFORMAZIONI</b></h2>
    <form action="<%= request.getContextPath() %>/ModificaUtente" method="post" class="form-profilo">
        <table>
            <tr>
                <th>Campo</th>
                <th>Valore</th>
            </tr>
            <tr>
                <td>Nome</td>
                <td><input type="text" value="<%= utente.getNome() %>" disabled></td>
            </tr>

            <tr>
                <td>Cognome</td>
                <td><input type="text" value="<%= utente.getCognome() %>" disabled></td>
            </tr>

            <tr>
                <td>Codice Fiscale</td>
                <td><input type="text" value="<%= utente.getCf() %>" disabled></td>
            </tr>

            <!-- Campi modificabili -->
            <tr>
                <td>Email</td>
                <td><input type="email" name="email" value="<%= utente.getEmail() %>" required></td>
            </tr>
            <tr>
                <td>Telefono</td>
                <td><input type="text" name="telefono" value="<%= utente.getTelefono() %>"></td>
            </tr>
            <tr>
                <td>Città</td>
                <td><input type="text" name="citta" value="<%= utente.getCitta() %>"></td>
            </tr>
            <tr>
                <td>Provincia</td>
                <td><input type="text" name="provincia" value="<%= utente.getProvincia() %>"></td>
            </tr>
            <tr>
                <td>Indirizzo</td>
                <td><input type="text" name="indirizzo" value="<%= utente.getIndirizzo() %>"></td>
            </tr>
            <tr>
                <td>Numero Civico</td>
                <td><input type="text" name="numeroCivico" value="<%= utente.getNumeroCivico() %>"></td>
            </tr>
        </table>

        <div class="torna-wrapper">
            <button type="submit" class="torna-catalogo">Salva modifiche</button>
        </div>
        <div class="torna-wrapper">
            <a href="<%=request.getContextPath()%>/jsp/utente/infoPersonali.jsp" class="torna-catalogo">Annulla</a>
        </div>
    </form>
</main>
<br/>
<%@ include file="/jsp/common/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
