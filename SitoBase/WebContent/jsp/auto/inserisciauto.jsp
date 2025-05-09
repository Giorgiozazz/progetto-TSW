<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Inserisci Nuova Auto</title>
  <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="/jsp/common/header.jsp" %>
	<h2>Inserisci Nuovo Prodotto</h2>
<form method="post" action="<%= request.getContextPath() %>/product" enctype="multipart/form-data">
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
  <input name="prezzo" type="number" min=0 step="0.01" required><br>

  <label>Anno:</label>
  <input name="anno" type="number" min=1950 required><br>

<label>Chilometraggio:</label>
<input name="kilometri" type="number" min=0 required><br>

  <label>Quantità:</label>
  <input name="quantita" type="number" min=1 required><br>

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
  <input name="porte" type="number" min=3 max=5><br>

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
  <input name="cilindrata" type="number" min=0><br>

  <label>Posti:</label>
  <input name="posti" type="number" min=1 max=8><br>

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
  <input name="peso" type="number" ><br>

  <label>Colore:</label>
  <input name="colore" type="text"><br>

  <label>Potenza (CV):</label>
  <input name="potenza" type="number"><br>
  
  <label>Immagine:</label>
  <input type="file" name="immagine" accept="image/*" required><br>

  <input type="submit" value="Inserisci">
  <input type="reset" value="Reset">
</form>
<%@ include file="/jsp/common/footer.jsp" %>
</body>