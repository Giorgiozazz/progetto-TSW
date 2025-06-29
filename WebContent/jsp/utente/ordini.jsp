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
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<br><br><br><br><br>
<%@ include file="/jsp/common/header.jsp" %>
<main>
    <% if (ordini == null || ordini.isEmpty()) { %>
        <h2><b>NON CI SONO ORDINI PRESENTI DA VISUALIZZARE</b></h2>
    <% } else { %>
        <h2><b>STORICO ORDINI</b></h2>
        <table class="dettagli-auto">
            <tr>
                <th class="col-foto">Data</th>
                <th class="col-foto">Stato</th>
                <th class="col-foto">Totale</th>
                <th class="col-foto">Spedizione</th>
                <th class="col-foto">Auto Acquistate</th>
                <th class="col-foto">Fattura</th>
            </tr>
            <% for (OrdineBean ordine : ordini) { %>
                <tr>
                    <td data-label="Data"><%= ordine.getDataOrdine() %></td>
                    <td data-label="Stato Ordine"><%= ordine.getStatoOrdine() %></td>
                    <td data-label="Totale"><%= String.format("%.2f", ordine.getTotale()) %> €</td>
                    <td data-label="Spedizione">
                        <% if ("ritiro".equalsIgnoreCase(ordine.getTipoConsegna())) { %>
                            Ritiro
                        <% } else { %>
                            <%= ordine.getIndirizzoSpedizione() != null ? ordine.getIndirizzoSpedizione() : "" %>,
                            <%= ordine.getNumeroCivicoSpedizione() != null ? ordine.getNumeroCivicoSpedizione() : "" %>,
                            <%= ordine.getCittaSpedizione() != null ? ordine.getCittaSpedizione() : "" %>
                            (<%= ordine.getProvinciaSpedizione() != null ? ordine.getProvinciaSpedizione() : "" %>)
                        <% } %>
                    </td>
                    <td data-label="Auto Acquistate">
                        <a href="<%= request.getContextPath() %>/jsp/utente/DettagliOrdine.jsp?id=<%= ordine.getIdOrdine() %>">
                            Vedi Dettagli
                        </a>
                    </td>
                    <td data-label="Fattura">
                        <a href="<%= request.getContextPath() %>/DownloadFattura?id=<%= ordine.getIdOrdine() %>" target="_blank" title="Scarica Fattura">
                            <img src="<%= request.getContextPath() %>/img/fattura.png" alt="Scarica Fattura PDF" style="width: 30%; height: auto; max-width: 32px;" />
                        </a>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } %>

    <div class="torna-wrapper">
        <a href="<%=request.getContextPath()%>/jsp/utente/infoPersonali.jsp" class="torna-catalogo">Torna al profilo</a>
        <a href="<%=request.getContextPath()%>/home" class="torna-catalogo">Torna alla home</a>
    </div>
    <br>
</main>

<%@ include file="/jsp/common/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
