<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, it.unisa.bean.ProductBean" %>

<%
    // Recupero il carrello dalla sessione
    Collection<ProductBean> carrello = (Collection<ProductBean>) session.getAttribute("carrello");
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Carrello</title>
</head>

<body>
    <%@ include file="/jsp/common/header.jsp" %>
<main>
    <h2><b>IL TUO CARRELLO</b></h2>
    
    <table class="dettagli-auto">
        <tr>
            <th>FOTO</th>
            <th>VIN</th>
            <th>Marca</th>
            <th>Modello</th>
            <th>Targa</th>
            <th>Prezzo</th>
            <th>Rimuovi</th>
        </tr>

        <%
            // Controllo se il carrello contiene prodotti
            if (carrello != null && !carrello.isEmpty()) {
                // Ciclo attraverso gli oggetti nel carrello
                for (ProductBean bean : carrello) {
        %>
            <tr>
                <td>
                    <% if (bean.getImmagine() != null && !bean.getImmagine().isEmpty()) { %>
                        <img src="<%= request.getContextPath() + bean.getImmagine() %>" style="width: 100%; height: 100%; object-fit: cover;" />
                    <% } else { %>
                        <img src="<%= request.getContextPath() %>/images/placeholder.jpg" style="width: 100px; height: 100px; object-fit: cover;" />
                    <% } %>
                </td>
                <td><%= bean.getVin() %></td>
                <td><%= bean.getMarca() %></td>
                <td><%= bean.getModello() %></td>
                <td><%= bean.getTarga() != null ? bean.getTarga() : "N/A" %></td>
                <td><%= bean.getPrezzo() %> €</td>
                <td>
                    <a href="<%= request.getContextPath() %>/RemoveFromCart?vin=<%= bean.getVin() %>">Rimuovi</a>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="7">Il carrello è vuoto.</td>
            </tr>
        <%
            }
        %>
    </table>
	<div class="ordina-link-container">
    <a href="<%= request.getContextPath() %>/index.jsp" class="ordina-link">Torna al Catalogo</a>
    </div>
</main>
    <%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
