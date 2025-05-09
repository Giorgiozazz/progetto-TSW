<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Errore</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="error-box">
        <h2>Si è verificato un errore</h2>

        <%
		    // Attributi standard per errori servlet
		    Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		    String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
		    String errorURI = (String) request.getAttribute("javax.servlet.error.request_uri");
		
		    // Attributo personalizzato che potresti settare nella servlet
		    String customError = (String) request.getAttribute("error");
		
		    // Imposta il messaggio di errore da visualizzare
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

        <a href="<%= request.getContextPath() %>/index.jsp" class="btn"> Torna al catalogo</a>
    </div>
</body>
</html>
