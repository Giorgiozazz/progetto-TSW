<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="it.unisa.model.*" %>

<%
    ProductBean product = (ProductBean) request.getAttribute("product");
    if (product == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Dettagli Auto</title>
</head>
<body>
    <%@ include file="/jsp/common/header.jsp" %>

    <h2>Dettagli Auto</h2>

    <%
        if (product != null) {
    %>
        <!-- Visualizzazione immagine -->
        <div class="product-image">
            <% if (product.getImmagine() != null && !product.getImmagine().isEmpty()) { %>
                <img src="<%= request.getContextPath() + product.getImmagine() %>" alt="Immagine del prodotto" style="width: 300px; height: auto; object-fit: cover;">
            <% } else { %>
                <p>Immagine non disponibile</p>
            <% } %>
        </div>

        <!-- Tabella dei dettagli del prodotto -->
        <table border="1" class="dettagli-auto">
            <tr>
                <th class="col-foto">Marca</th>
                <th class="col-foto">Modello</th>
                <th class="col-foto">Tipo</th>
                <th class="col-foto">Targa</th>
                <th class="col-foto">Prezzo</th>
            </tr>
            <tr>
                <td data-label="Marca"><%=product.getMarca()%></td>
                <td data-label="Modello"><%=product.getModello()%></td>
                <td data-label="Tipo"><%=product.getTipo()%></td>
                <td data-label="Targa"><%=product.getTarga() != null ? product.getTarga() : "N/A"%></td>
                <td data-label="Prezzo"><%=product.getPrezzo()%> €</td>
            </tr>
        </table>

        <h3 align="center">Dettagli Aggiuntivi</h3>
        <table border="1" class="dettagli-auto">
            <tr>
                <th class="col-foto">Potenza</th>
                <th class="col-foto">Trazione</th>
                <th class="col-foto">Cilindrata</th>
                <!--  <th class="col-foto">Ruote Motrici</th>-->
                <th class="col-foto">Cambio</th>
                <th class="col-foto">Rapporti</th>
                <th class="col-foto">Peso</th>
                <th class="col-foto">Anno</th>
            </tr>
            <tr>
            	<td data-label="Potenza"><%=product.getPotenza()%> HP</td>
                <td data-label="Trazione"><%=product.getTrazione()%></td>
                <td data-label="Cilindrata"><%=product.getCilindrata()%></td>
                <td data-label="Cambio"><%=product.getTipoCambio()%></td>
                <td data-label="Rapporti"><%=product.getRapporti()%></td>
                <td data-label="Peso"><%=product.getPeso()%> KG</td>
                <!-- <td data-label="Ruote Motrici"><%=product.getRuoteMotrici()%></td>-->
                <td data-label="Anno"><%=product.getAnno()%></td>
            </tr>
        </table>

        <table border="1" class="dettagli-auto">
            <tr>
                <th class="col-foto">Carburante</th>
                <th class="col-foto">Kw</th>
                <th class="col-foto">kilometri</th>
                <th class="col-foto">Porte</th>
                <th class="col-foto">Posti</th>
                <th class="col-foto">Colore</th>
            </tr>
            <tr>
            	<td data-label="Carburante"><%=product.getTipoCarburante()%></td>
            	<td data-label="Potenza Elettrica"><%=product.getPotenzaElettrica()%></td>
                <td data-label="Kilometri"><%=product.getKilometri()%></td>
                <td data-label="Porte"><%=product.getPorte()%></td>
                <td data-label="Posti"><%=product.getPosti()%></td>
                <td data-label="Colore"><%=product.getColore()%></td>
            </tr>
        </table>

        <table border="1" class="dettagli-auto">
            <tr>
                <th class="col-foto">Schermo Integrato</th>
                <th class="col-foto">Climatizzatore</th>
                <th class="col-foto">VIN</th>
                <th class="col-foto">Proprietario</th>
            </tr>
            <tr>
                <td data-label="Schermo Integrato"><%=product.isSchermoIntegrato() ? "Sì" : "No"%></td>
                <td data-label="Climatizzatore"><%=product.isClimatizzatore() ? "Sì" : "No"%></td>
                <td data-label="VIN"><%=product.getVin()%></td>
                <td data-label="Cf"><%=product.getCf()%></td>
            </tr>
        </table>
    <%
        }
    %>
    <div class="torna-wrapper">
    	 <a href="<%= request.getContextPath() + "/" + request.getAttribute("backUrl") %>" class="torna-catalogo">TORNA AL CATALOGO</a>
	</div>
    <br/>
    <%@ include file="/jsp/common/footer.jsp" %>
    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
