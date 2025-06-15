<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unisa.model.UtenteBean, it.unisa.model.OrdineBean, it.unisa.OrdineModelDM" %>
<%@ page import="java.util.List" %>

<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");

    if (utente == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/utente/login.jsp");
        return;
    }

    OrdineModelDM ordineModel = new OrdineModelDM();
    List<OrdineBean> ordini = ordineModel.doRetrieveByCF(utente.getCf());
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>I tuoi Ordini</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ProductStyle.css">
</head>
<body>

<%@ include file="/jsp/common/header.jsp" %>
<main>
    <% if (ordini == null || ordini.isEmpty()) { %>
        <header>
    		<h1>Non ci sono ordini presenti da visualizzare</h1>
		</header>
    <% } else { %>
    <header>
    	<h1>Storico Ordini</h1>
	</header>
        <table>
            <tr>
                <th>ID Ordine</th>
                <th>Data</th>
                <th>Stato</th>
                <th>Totale (€)</th>
                <th>Tipo Consegna</th>
                <th>Spedizione</th>
            </tr>
            <% for (OrdineBean ordine : ordini) { %>
                <tr>
                    <td><%= ordine.getIdOrdine() %></td>
                    <td><%= ordine.getDataOrdine() %></td>
                    <td><%= ordine.getStatoOrdine() %></td>
                    <td><%= String.format("%.2f", ordine.getTotale()) %></td>
                    <td><%= ordine.getTipoConsegna() %></td>
                    <td>
					    <% if ("ritiro".equalsIgnoreCase(ordine.getTipoConsegna())) { %>
					        Ritiro
					    <% } else { %>
					        <%= ordine.getIndirizzoSpedizione() != null ? ordine.getIndirizzoSpedizione() : "" %>,
					        <%= ordine.getNumeroCivicoSpedizione() != null ? ordine.getNumeroCivicoSpedizione() : "" %>,
					        <%= ordine.getCittaSpedizione() != null ? ordine.getCittaSpedizione() : "" %>
					        (<%= ordine.getProvinciaSpedizione() != null ? ordine.getProvinciaSpedizione() : "" %>)
					    <% } %>
					</td>
                </tr>
            <% } %>
        </table>
    <% } %>

    <div class="torna-wrapper">
        <a href="<%=request.getContextPath()%>/jsp/utente/infoPersonali.jsp" class="torna-catalogo">Torna al profilo</a>
        <a href="<%=request.getContextPath()%>/index.jsp" class="torna-catalogo">Torna alla home</a>
    </div>
</main>

<%@ include file="/jsp/common/footer.jsp" %>

</body>
</html>
