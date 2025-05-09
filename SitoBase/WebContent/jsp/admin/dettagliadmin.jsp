<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.model.ProductBean" %>
<%@ page import="it.unisa.ProductModelDM" %>

<%
    // Recupera il vin del prodotto da modificare
    String vin = request.getParameter("vin");

    // Recupera il prodotto dal database usando il vin
    ProductModelDM productModel = new ProductModelDM();
    ProductBean product = productModel.doRetrieveByKey(vin);

    if (product == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/common/error.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Modifica Auto (ADMIN)</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/ProductStyle.css">
</head>
<body>
    <%@ include file="/jsp/common/header.jsp" %>
    <h2><b>Modifica Auto</b></h2>

    <form action="<%= request.getContextPath() %>/UpdateProduct" method="post" enctype="multipart/form-data" class="form-profilo-fix">
        <table>
            <tr>
                <th>Marca</th>
                <td><input type="text" name="marca" value="<%= product.getMarca() %>" required></td>
            </tr>
            <tr>
                <th>Modello</th>
                <td><input type="text" name="modello" value="<%= product.getModello() %>" required></td>
            </tr>
            <tr>
                <th>Prezzo</th>
                <td><input type="number" name="prezzo" value="<%= product.getPrezzo() %>" required></td>
            </tr>
            <tr>
                <th>Targa</th>
                <td><input type="text" name="targa" value="<%= product.getTarga() != null ? product.getTarga() : "" %>"></td>
            </tr>
            <tr>
                <th>Porte</th>
                <td><input type="number" name="porte" value="<%= product.getPorte() %>" required></td>
            </tr>
            <tr>
                <th>Tipo</th>
                <td><input type="text" name="tipo" value="<%= product.getTipo() %>" required></td>
            </tr>
            <tr>
                <th>Climatizzatore</th>
                <td><input type="checkbox" name="climatizzatore" <%= product.isClimatizzatore() ? "checked" : "" %>></td>
            </tr>
            <tr>
                <th>Ruote Motrici</th>
                <td><input type="text" name="ruoteMotrici" value="<%= product.getRuoteMotrici() %>" required></td>
            </tr>
            <tr>
                <th>Schermo Integrato</th>
                <td><input type="checkbox" name="schermoIntegrato" <%= product.isSchermoIntegrato() ? "checked" : "" %>></td>
            </tr>
            <tr>
                <th>Anno</th>
                <td><input type="number" name="anno" value="<%= product.getAnno() %>" required></td>
            </tr>
            <tr>
                <th>Cilindrata</th>
                <td><input type="number" name="cilindrata" value="<%= product.getCilindrata() %>" required></td>
            </tr>
            <tr>
                <th>Posti</th>
                <td><input type="number" name="posti" value="<%= product.getPosti() %>" required></td>
            </tr>
            <tr>
                <th>Kilometri</th>
                <td><input type="number" name="kilometri" value="<%= product.getKilometri() %>" required></td>
            </tr>
            <tr>
                <th>Tipo Cambio</th>
                <td><input type="text" name="tipoCambio" value="<%= product.getTipoCambio() %>" required></td>
            </tr>
            <tr>
                <th>Rapporti</th>
                <td><input type="number" name="rapporti" value="<%= product.getRapporti() %>" required></td>
            </tr>
            <tr>
                <th>Potenza Elettrica</th>
                <td><input type="number" name="potenzaElettrica" value="<%= product.getPotenzaElettrica() %>" required></td>
            </tr>
            <tr>
                <th>Tipo Carburante</th>
                <td><input type="text" name="tipoCarburante" value="<%= product.getTipoCarburante() %>" required></td>
            </tr>
            <tr>
                <th>Colore</th>
                <td><input type="text" name="colore" value="<%= product.getColore() %>" required></td>
            </tr>
            <tr>
                <th>Potenza</th>
                <td><input type="number" name="potenza" value="<%= product.getPotenza() %>" required></td>
            </tr>
            <tr>
                <th>Trazione</th>
                <td><input type="text" name="trazione" value="<%= product.getTrazione() %>" required></td>
            </tr>
            <tr>
                <th>Peso</th>
                <td><input type="number" name="peso" value="<%= product.getPeso() %>" required></td>
            </tr>
            <tr>
                <th>Proprietario</th>
                <td><input type="text" name="cf" value="<%= product.getCf() %>" required></td>
            </tr>
            <tr>
                <th>Immagine</th>
                <td><input type="file" name="immagine"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="hidden" name="vin" value="<%= product.getVin() %>">
                    <button type="submit" class="torna-catalogo">Salva Modifiche</button>
                </td>
            </tr>
        </table>
    </form>

    <div class="torna-wrapper">
        <a href="<%= request.getContextPath() %>/jsp/common/catalogo.jsp" class="torna-catalogo">Torna al catalogo</a>
    </div>
	<br>
    <%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
