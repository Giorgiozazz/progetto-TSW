<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Recupero della collezione di prodotti
    Collection<ProductBean> products = (Collection<ProductBean>) request.getAttribute("products");

    // Verifica se la collezione è vuota o null e redirige
    if (products == null || products.isEmpty()) {
    	response.sendRedirect(request.getContextPath() + "/product"); 
        return;
    }

    // Recupero di un singolo prodotto, se presente
    ProductBean product = (ProductBean) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.unisa.model.ProductBean"%>

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Catalogo Auto</title>
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>

<body>
    <%@ include file="/jsp/common/header.jsp" %>
    <br><br><br><br><br>
    <main>
    <h2><b>CATALOGO AUTO</b></h2>

    <table class="dettagli-auto">
        <tr>
            <th class="col-foto">FOTO</th>
            <th>Marca <a href="product?sort=marca" class="ordina-link">Ordina</a></th>
            <th>Modello <a href="product?sort=modello" class="ordina-link">Ordina</a></th>
            <th>Targa <a href="product?sort=targa" class="ordina-link">Ordina</a></th>
            <th>Prezzo <a href="product?sort=prezzo" class="ordina-link">Ordina</a></th>
            <th class="col-foto">Info</th>
            <th class="col-foto">Compra</th>
        </tr>

        <%
            // Ciclo attraverso i prodotti per visualizzarli nella tabella
            for (ProductBean bean : products) {
        %>
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
                <td data-label="Prezzo"><%= bean.getPrezzo() %> €</td>
                <td data-label="Info">
                	<a href="product?action=read&vin=<%= bean.getVin() %><%= request.getQueryString() != null ? "&" + request.getQueryString() : "" %>">Dettagli</a>
                </td>
                <td data-label="Compra">
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
	</main>
    <%@ include file="/jsp/common/footer.jsp" %>
    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
