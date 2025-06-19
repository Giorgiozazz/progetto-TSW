<%@ page import="it.unisa.model.ProductBean, it.unisa.ProductModelDM" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    int idOrdine = Integer.parseInt(request.getParameter("id"));
    ProductModelDM model = new ProductModelDM();
    List<ProductBean> prodotti = model.doRetrieveByOrder(idOrdine);
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dettagli Ordine</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ProductStyle.css">
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<main>
<h2>Dettagli Ordine #<%= idOrdine %></h2>

<% if (prodotti == null || prodotti.isEmpty()) { %>
    <p>Nessun prodotto trovato per questo ordine.</p>
<% } else { %>

    <table class="dettagli-auto">
        <tr>
            <th class="col-foto">FOTO</th>
            <th class="col-foto">Marca</th>
            <th class="col-foto">Modello</th>
            <th class="col-foto">Targa</th>
            <th class="col-foto">Prezzo</th>
            <th class="col-foto">Dettagli</th>
        </tr>

        <% for (ProductBean bean : prodotti) { %>
            <tr>
                <td data-label="FOTO">
                    <% if (bean.getImmagine() != null && !bean.getImmagine().isEmpty()) { %>
                        <img src="<%= request.getContextPath() + bean.getImmagine() %>" style="object-fit: cover;" />
                    <% } else { %>
                        N/A
                    <% } %>
                </td>
                <td data-label="Marca"><%= bean.getMarca() %></td>
                <td data-label="Modello"><%= bean.getModello() %></td>
                <td data-label="Targa"><%= bean.getTarga() != null ? bean.getTarga() : "N/A" %></td>
                <td data-label="Prezzo"><%= String.format("%.2f", bean.getPrezzo()) %> €</td>
                <td data-label="Info">
                    <a href="<%= request.getContextPath() %>/product?action=read&vin=<%= bean.getVin() %>">Dettagli</a>
                </td>
            </tr>
        <% } %>
    </table>
<% } %>

<div class="torna-wrapper">
    <a href="<%= request.getContextPath() %>/jsp/utente/ordini.jsp" class="torna-catalogo">Torna agli ordini</a>
</div>
<br>
</main>
<br><br>
<%@ include file="/jsp/common/footer.jsp" %>

<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
