<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) {
		response.sendRedirect("./product");	
		return;
	}
	ProductBean product = (ProductBean) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,it.unisa.ProductBean"%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="ProductStyle.css" rel="stylesheet" type="text/css">
	<title>Catalogo Auto</title>
</head>

<body>
	<h2><b>CATALOGO AUTO</b></h2>
	<a href="product" style="display: block; text-align: center;">Lista</a>
	<table border="1">
		<tr>
			<th>VIN <br/><a href="product?sort=vin" class="ordina-link">Ordina</a></th>
			<th>Marca <a href="product?sort=marca" class="ordina-link">Ordina</a></th>
			<th>Modello <a href="product?sort=modello" class="ordina-link">Ordina</a></th>
			<th>Targa <a href="product?sort=targa" class="ordina-link">Ordina</a></th>
			<th>Prezzo <a href="product?sort=prezzo" class="ordina-link">Ordina</a></th>
			<th>Azione</th>
		</tr>
		<%
			if (products != null && products.size() != 0) {
				Iterator<?> it = products.iterator();
				while (it.hasNext()) {
					ProductBean bean = (ProductBean) it.next();
		%>
		<tr>
			<td><%=bean.getVin()%></td>
			<td><%=bean.getMarca()%></td>
			<td><%=bean.getModello()%></td>
			<td><%=bean.getTarga() != null ? bean.getTarga() : "N/A"%></td>
			<td><%=bean.getPrezzo()%> EUR</td>
			<td><a href="product?action=delete&vin=<%=bean.getVin()%>">Elimina</a><br>
				<a href="product?action=read&vin=<%=bean.getVin()%>">Dettagli</a></td>
		</tr>
		<%
				}
			} else {
		%>
		<tr>
			<td colspan="6">Nessuna auto disponibile</td>
		</tr>
		<%
			}
		%>
	</table>

	<h2>Dettagli Auto</h2>
	<%
		if (product != null) {
	%>
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
		</tr>
		<tr>
			<td><%=product.getPotenza()%></td>
			<td><%=product.getTrazione()%></td>
			<td><%=product.getPeso()%></td>
		</tr>
	</table>
	<%
		}
	%>

	<h2>Inserisci Nuovo Prodotto</h2>
<form method="post" action="product">
  <input type="hidden" name="action" value="insert">

  <label>VIN:</label>
  <input name="vin" type="text" maxlength="17" required><br>

  <label>Marca:</label>
  <input name="marca" type="text" required><br>

  <label>Modello:</label>
  <input name="modello" type="text" required><br>

  <label>Targa:</label>
  <input name="targa" type="text"><br>

  <label>Prezzo (€):</label>
  <input name="prezzo" type="number" step="0.01" required><br>

  <label>Anno:</label>
  <input name="anno" type="number" required><br>

<label>Chilometraggio:</label>
<input name="kilometri" type="number" required><br>

  <label>Tipo carburante:</label>
  <select name="tipoCarburante" required>
        <option value="Benzina">Benzina</option>
        <option value="Diesel">Diesel</option>
        <option value="GPL">GPL</option>
        <option value="Metano">Metano</option>
        <option value="Elettrico">Elettrico</option>
        <option value="Ibrido">Ibrido</option>
  </select><br>

  <label>Porte:</label>
  <input name="porte" type="number"><br>

  <label>Tipo:</label>
  <input name="tipo" type="text"><br>

  <label>Climatizzatore:</label>
  <select name="climatizzatore">
    <option value="true">Sì</option>
    <option value="false">No</option>
  </select><br>

  <label>Ruote motrici:</label>
  <select name="ruoteMotrici">
    <option value="anteriore">Anteriore</option>
    <option value="posteriore">Posteriore</option>
    <option value="integrale">Integrale</option>
  </select><br>

  <label>Schermo integrato:</label>
  <select name="schermoIntegrato">
    <option value="true">Sì</option>
    <option value="false">No</option>
  </select><br>

  <label>Cilindrata:</label>
  <input name="cilindrata" type="number"><br>

  <label>Posti:</label>
  <input name="posti" type="number"><br>

  <label>Tipo cambio:</label>
  <select name="tipoCambio">
    <option value="manuale">Manuale</option>
    <option value="automatico">Automatico</option>
  </select><br>

  <label>Rapporti:</label>
  <input name="rapporti" type="number"><br>

  <label>Potenza elettrica (kW):</label>
  <input name="potenzaElettrica" type="number"><br>

  <label>Trazione:</label>
  <select name="trazione">
    <option value="anteriore">Anteriore</option>
    <option value="posteriore">Posteriore</option>
    <option value="integrale">Integrale</option>
  </select><br>

  <label>Peso (kg):</label>
  <input name="peso" type="number"><br>

  <label>Colore:</label>
  <input name="colore" type="text"><br>

  <label>Potenza (CV):</label>
  <input name="potenza" type="number"><br>

  <input type="submit" value="Inserisci">
  <input type="reset" value="Reset">
</form>


</body>
</html>
