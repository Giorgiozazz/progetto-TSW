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
                <th>VIN</th>
                <th>Marca</th>
                <th>Modello</th>
                <th>Targa</th>
                <th>Prezzo</th>
            </tr>
            <tr>
                <td><%=product.getVin()%></td>
                <td><%=product.getMarca()%></td>
                <td><%=product.getModello()%></td>
                <td><%=product.getTarga() != null ? product.getTarga() : "N/A"%></td>
                <td><%=product.getPrezzo()%> EUR</td>
            </tr>
        </table>

        <h3 align="center">Dettagli Aggiuntivi</h3>
        <table border="1" class="dettagli-auto">
            <tr>
                <th>Porte</th>
                <th>Tipo</th>
                <th>Climatizzatore</th>
                <th>Ruote Motrici</th>
                <th>Schermo Integrato</th>
                <th>Anno</th>
                <th>Cilindrata</th>
            </tr>
            <tr>
                <td><%=product.getPorte()%></td>
                <td><%=product.getTipo()%></td>
                <td><%=product.isClimatizzatore() ? "Sì" : "No"%></td>
                <td><%=product.getRuoteMotrici()%></td>
                <td><%=product.isSchermoIntegrato() ? "Sì" : "No"%></td>
                <td><%=product.getAnno()%></td>
                <td><%=product.getCilindrata()%></td>
            </tr>
        </table>

        <table border="1" class="dettagli-auto">
            <tr>
                <th>Posti</th>
                <th>Kilometri</th>
                <th>Tipo Cambio</th>
                <th>Rapporti</th>
                <th>Potenza Elettrica</th>
                <th>Tipo Carburante</th>
                <th>Colore</th>
            </tr>
            <tr>
                <td><%=product.getPosti()%></td>
                <td><%=product.getKilometri()%></td>
                <td><%=product.getTipoCambio()%></td>
                <td><%=product.getRapporti()%></td>
                <td><%=product.getPotenzaElettrica()%></td>
                <td><%=product.getTipoCarburante()%></td>
                <td><%=product.getColore()%></td>
            </tr>
        </table>

        <table border="1" class="dettagli-auto">
            <tr>
                <th>Potenza</th>
                <th>Trazione</th>
                <th>Peso</th>
                <th>Proprietario</th>
            </tr>
            <tr>
                <td><%=product.getPotenza()%></td>
                <td><%=product.getTrazione()%></td>
                <td><%=product.getPeso()%></td>
                <td><%=product.getCf()%></td>
            </tr>
        </table>
    <%
        }
    %>

    <div class="torna-wrapper">
        <a href="index.jsp" class="torna-catalogo">TORNA AL CATALOGO</a>
    </div>
    <br/>
    <%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
