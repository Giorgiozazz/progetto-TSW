<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Recupero della collezione di prodotti
    Collection<ProductBean> products = (Collection<ProductBean>) request.getAttribute("products");

    // Verifica se la collezione è vuota o null e redirige
    if (products == null || products.isEmpty()) {
        response.sendRedirect("./product");
        return;
    }

    // Recupero di un singolo prodotto, se presente
    ProductBean product = (ProductBean) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*, it.unisa.bean.ProductBean"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Catalogo Auto</title>
</head>

<body>
    <%@ include file="/jsp/common/header.jsp" %>
    <h2><b>CATALOGO AUTO</b></h2>

    <table class="dettagli-auto">
        <tr>
            <th>FOTO</th>
            <th>VIN <br/><a href="product?sort=vin" class="ordina-link">Ordina</a></th>
            <th>Marca <a href="product?sort=marca" class="ordina-link">Ordina</a></th>
            <th>Modello <a href="product?sort=modello" class="ordina-link">Ordina</a></th>
            <th>Targa <a href="product?sort=targa" class="ordina-link">Ordina</a></th>
            <th>Prezzo <a href="product?sort=prezzo" class="ordina-link">Ordina</a></th>
            <th>Info</th>
            <th>Compra</th>
        </tr>

        <%
            // Ciclo attraverso i prodotti per visualizzarli nella tabella
            for (ProductBean bean : products) {
        %>
            <tr>
                <td>
                    <% if (bean.getImmagine() != null && !bean.getImmagine().isEmpty()) { %>
                        <img src="<%= request.getContextPath() + bean.getImmagine() %>" style="width: 100%; height: 100%; object-fit: cover;" />
                    <% } else { %>
                        N/A
                    <% } %>
                </td>
                <td><%= bean.getVin() %></td>
                <td><%= bean.getMarca() %></td>
                <td><%= bean.getModello() %></td>
                <td><%= bean.getTarga() != null ? bean.getTarga() : "N/A" %></td>
                <td><%= bean.getPrezzo() %> €</td>
                <td>
                    <a href="product?action=read&vin=<%= bean.getVin() %>">Dettagli</a>
                </td>
                <td>
                    <form method="get" action="<%= request.getContextPath() %>/AddToCart" class="carrello-form">
                        <input type="hidden" name="vin" value="<%= bean.getVin() %>"/>
                        <input type="submit" value="Aggiungi al carrello" />
                    </form>
                </td>
            </tr>
        <%
            }
        %>

        <%
            // Se non ci sono prodotti, mostra il messaggio "Nessuna auto disponibile"
            if (products.isEmpty()) {
        %>
            <tr>
                <td colspan="8">Nessuna auto disponibile</td>
            </tr>
        <%
            }
        %>
    </table>

    <%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
