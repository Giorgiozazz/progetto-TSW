<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.model.ProductBean,it.unisa.model.UtenteBean" %>

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

    // Controllo se l'utente è admin
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    boolean isAdmin = utente != null && utente.isAdmin(); // Assicurati che esista il metodo isAdmin()
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Catalogo Auto</title>
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>

<body>
    <%@ include file="/jsp/common/header.jsp" %>
    <br><br><br><br>
    <main>
    <h2><b>CATALOGO AUTO (ADMIN)</b></h2>
	
    <table class="dettagli-auto">
        <tr>
            <th class="col-foto">FOTO</th>
            <th>Marca <a href="product?sort=marca" class="ordina-link">Ordina</a></th>
            <th>Modello <a href="product?sort=modello" class="ordina-link">Ordina</a></th>
            <th>Targa <a href="product?sort=targa" class="ordina-link">Ordina</a></th>
            <th>Prezzo <a href="product?sort=prezzo" class="ordina-link">Ordina</a></th>
            <th class="col-foto">Info</th>
            <% if (!isAdmin) { %>
			    <th>Compra</th>
			<% } %>
			<% if (isAdmin) { %>
			    <th class="col-foto">Elimina</th>
			<% } %>

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
                <td><%= bean.getMarca() %></td>
                <td><%= bean.getModello() %></td>
                <td><%= bean.getTarga() != null ? bean.getTarga() : "N/A" %></td>
                <td data-label="Prezzo"><%= java.text.NumberFormat.getNumberInstance(java.util.Locale.ITALY).format(bean.getPrezzo()) %> €</td>
                <td>
                     <a href="product?action=edit&vin=<%= bean.getVin() %>">Dettagli</a>
                </td>
                <% if (!isAdmin) { %>
			    <td>
			        <form method="get" action="<%= request.getContextPath() %>/AddToCart" class="carrello-form">
			            <input type="hidden" name="vin" value="<%= bean.getVin() %>"/>
			            <input type="submit" value="Aggiungi al carrello" />
			        </form>
			    </td>
				<% } %>
				<% if (isAdmin) { %>
				    <td>
				        <a href="product?action=delete&vin=<%= bean.getVin() %>" 
				           onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?');">Elimina</a>
				    </td>
				<% } %>
            </tr>
        <%
            }
        %>

        <% if (products.isEmpty()) { %>
            <tr>
                <td colspan="<%= isAdmin ? "8" : "7" %>">Nessuna auto disponibile</td>
            </tr>
        <% } %>
    </table>
	</main>
    <%@ include file="/jsp/common/footer.jsp" %>
    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
