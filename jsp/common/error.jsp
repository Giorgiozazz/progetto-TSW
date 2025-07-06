<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Errore</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
    <div class="error-box">
        <h2>Si è verificato un errore</h2>

        <%
            Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
            String errorURI = (String) request.getAttribute("javax.servlet.error.request_uri");
            String customError = (String) request.getAttribute("error");

            String errorDetails = "Errore sconosciuto.";

            if (customError != null) {
                errorDetails = customError;
            } else if (errorCode != null) {
                switch (errorCode) {
                    case 404:
                        errorDetails = "ERRORE 404<br><br>Pagina non trovata. Il percorso " + errorURI + " non esiste.";
                        break;
                    case 500:
                        errorDetails = "Errore interno del server. Si è verificato un problema durante l'elaborazione della richiesta.";
                        break;
                    default:
                        errorDetails = "Errore " + errorCode + ": " + (errorMessage != null ? errorMessage : "Descrizione non disponibile.");
                        break;
                }
            }
        %>

        <p><strong>Dettagli:</strong></p>
        <p><%= errorDetails %></p>

        <%
		    if (customError != null) {
		        if (customError.contains("Email o password errati")) {
		%>
		            <a href="<%= request.getContextPath() %>/jsp/utente/login.jsp" class="btn"> Torna al Login</a>
		<%
		        } else if (customError.contains("anticipo non può superare il 50%")) {
		%>
		            <a href="<%= request.getContextPath() %>/jsp/auto/checkout.jsp" class="btn"> Torna al Checkout</a>
		<%
		        } else if (customError.contains("Devi essere loggato per inserire un annuncio.")) {
		%>
		            <a href="<%= request.getContextPath() %>/jsp/utente/login.jsp" class="btn"> Torna al Login</a>
		<%
		        } else {
		%>
		            <a href="<%= request.getContextPath() %>/home" class="btn"> Torna alla Home</a>
		<%
		        }
		    } else {
		%>
		        <a href="<%= request.getContextPath() %>/home" class="btn"> Torna alla Home</a>
		<%
		    }
		%>
    </div>
    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
