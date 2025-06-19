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
  <input name="anno" type="number" min=1950 max=2025 required><br>

  <label>Chilometraggio:</label>
  <input name="kilometri" type="number" min=0 required><br>

  <input name="quantita" type="hidden" value="1">

  <label>Tipo carburante:</label>
  <select name="tipoCarburante" required>
        <option value="Benzina">Benzina</option>
        <option value="Diesel">Diesel</option>
        <option value="Elettrico">Elettrico</option>
        <option value="GPL">GPL</option>
        <option value="Ibrido">Ibrido</option>
        <option value="Metano">Metano</option>
  </select><br>
  
  <label>Potenza Elettrica:</label>
  <input name="potenzaElettrica" type="number" value="0" readonly>


  <label>Ruote motrici:</label>
  <select name="ruoteMotrici">
		<option value="anteriore">Anteriore</option>
		<option value="posteriore">Posteriore</option>
		<option value="integrale">Integrale</option>
  </select><br>
  
  <label>Trazione:</label>
  <select name="trazione">
		<option value="anteriore">Anteriore</option>
		<option value="posteriore">Posteriore</option>
		<option value="integrale">Integrale</option>
  </select><br>
  
  <label>Potenza (CV):</label>
  <input id="potenza" name="potenza" type="number" value="0" readonly><br>
  
  <label>Cilindrata:</label>
  <input id="cilindrata" name="cilindrata" type="number"  value="0" min=0 readonly><br>
  
  <label>Porte:</label>
  <input name="porte" type="number" min=3 max=5><br>

  <label>Tipo:</label>
  <select name="tipo" required>
  		<option value="" disabled selected>Seleziona tipo</option>
		<option value="Berlina">Berlina</option>
		<option value="Cabriolet">Cabriolet</option>
		<option value="Coupé">Coupé</option>
		<option value="Monovolume">Monovolume</option>
		<option value="Station Wagon">Station Wagon</option>
		<option value="Sportiva">Sportiva</option>
		<option value="SUV">SUV</option>
		<option value="Utilitaria">Utilitaria</option>
		<option value="Van">Van</option>
  </select><br>

  <label>Posti:</label>
  <input name="posti" type="number" min=1 max=8><br>

  <label>Tipo cambio:</label>
  <select name="tipoCambio">
    <option value="manuale">Manuale</option>
    <option value="automatico">Automatico</option>
  </select><br>

  <label>Rapporti:</label>
  <input name="rapporti" type="number"><br>

  <label>Peso (kg):</label>
  <input name="peso" type="number" ><br>

  <label>Colore:</label>
  <input name="colore" type="text"><br>
  
  <label>Schermo integrato:</label>
  <select name="schermoIntegrato">
		<option value="true">Sì</option>
		<option value="false">No</option>
  </select><br>

  <label>Climatizzatore:</label>
  <select name="climatizzatore">
		<option value="true">Sì</option>
		<option value="false">No</option>
  </select><br>
  
  <label>Immagine:</label>
  <input type="file" name="immagine" accept="image/*" required><br>
  
  <input type="hidden" name="iva" value="22.0">
	
  <input type="submit" value="Inserisci">
  <input type="reset" value="Reset">
</form>
<%@ include file="/jsp/common/footer.jsp" %>
<script>
document.addEventListener("DOMContentLoaded", function() {
  const trazione = document.querySelector("select[name='trazione']");
  const ruoteMotrici = document.querySelector("select[name='ruoteMotrici']");

  function syncValues(source, target) {
    target.value = source.value;
  }

  trazione.addEventListener("change", () => syncValues(trazione, ruoteMotrici));
  ruoteMotrici.addEventListener("change", () => syncValues(ruoteMotrici, trazione));
});
</script>
<script>
document.addEventListener("DOMContentLoaded", function() {
	  const tipoCarburanteSelect = document.querySelector('select[name="tipoCarburante"]');
	  const potenzaElettricaInput = document.querySelector('input[name="potenzaElettrica"]');
	  const potenzaInput = document.getElementById('potenza');
	  const cilindrataInput = document.getElementById('cilindrata');

	  function aggiornaCampi() {
	    const tipo = tipoCarburanteSelect.value;

	    if (tipo === 'Elettrico') {
	      potenzaElettricaInput.readOnly = false;
	      potenzaElettricaInput.min = 2;
	      potenzaElettricaInput.step = 1;
	      if (!potenzaElettricaInput.value || parseInt(potenzaElettricaInput.value) < 2) {
	        potenzaElettricaInput.value = 2;
	      }

	      potenzaInput.readOnly = true;
	      potenzaInput.value = 0;

	      cilindrataInput.readOnly = true;
	      cilindrataInput.value = 0;

	    } else if (tipo === 'Ibrido') {
	      potenzaElettricaInput.readOnly = false;
	      potenzaElettricaInput.min = 2;
	      potenzaElettricaInput.step = 1;
	      if (!potenzaElettricaInput.value || parseInt(potenzaElettricaInput.value) < 2) {
	        potenzaElettricaInput.value = 2;
	      }

	      potenzaInput.readOnly = false;
	      cilindrataInput.readOnly = false;

	    } else {
	      potenzaElettricaInput.readOnly = true;
	      potenzaElettricaInput.value = 0;
	      potenzaElettricaInput.step = 1;

	      potenzaInput.readOnly = false;
	      cilindrataInput.readOnly = false;
	    }
	  }

	  tipoCarburanteSelect.addEventListener('change', aggiornaCampi);

	  aggiornaCampi();
	});

</script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>