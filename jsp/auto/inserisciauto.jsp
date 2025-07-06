<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Inserisci Nuova Auto</title>
  <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
  <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
	<%@ include file="/jsp/common/header.jsp" %>
	<br><br><br><br><br>
	<h2><b>INSERISCI UNA NUOVA AUTO</b></h2>
<form method="post" action="<%= request.getContextPath() %>/product" enctype="multipart/form-data">
  <input type="hidden" name="action" value="insert">

  <label>VIN:</label>
  <input id="vin" name="vin" type="text" maxlength="17" required style="border: 2px solid #ccc;">
  <span id="vin-check" style="font-size: 0.9em; display: block; height: 1.2em; margin-top: 2px;"></span><br>


  <label>Marca:</label>
  <input name="marca" type="text" required><br>

  <label>Modello:</label>
  <input name="modello" type="text" required><br>

  <label>Targa:</label>
  <input name="targa" type="text" id="targa" required>
  <span id="targa-check" style="font-size: 0.9em; display: block; height: 1.2em; margin-top: 2px;"></span>

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
  <input name="rapporti" type="number" min=5 max=9><br>

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
<script>
document.addEventListener("DOMContentLoaded", () => {
  const targaInput = document.getElementById('targa');
  const targaCheck = document.getElementById('targa-check');
  const form = document.querySelector('form');

  const regexTarga = /^[A-Z]{2}[0-9]{3}[A-Z]{2}$/;

  let targaDebounceTimeout;

  function validaTarga() {
    const targa = targaInput.value.toUpperCase();
    targaInput.value = targa;

    if (!targa) {
      targaCheck.textContent = '';
      return true;
    }

    if (!regexTarga.test(targa)) {
      targaCheck.textContent = 'Formato non valido (es. AB123CD)';
      targaCheck.style.color = 'red';
      return false;
    }

    // Se formato valido, esegui controllo AJAX
    clearTimeout(targaDebounceTimeout);
    targaDebounceTimeout = setTimeout(() => {
      fetch('<%= request.getContextPath() %>/CheckTarga', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'targa=' + encodeURIComponent(targa)
      })
      .then(response => response.text())
      .then(data => {
        if (data === 'exists') {
          targaCheck.textContent = 'Targa già presente nel catalogo';
          targaCheck.style.color = 'red';
        } else {
          targaCheck.textContent = 'Targa disponibile';
          targaCheck.style.color = 'green';
        }
      })
      .catch(() => {
        targaCheck.textContent = 'Errore nel controllo targa';
        targaCheck.style.color = 'orange';
      });
    }, 300);

    return true;
  }

  targaInput.addEventListener('input', validaTarga);

  form.addEventListener('submit', e => {
    if (!regexTarga.test(targaInput.value.toUpperCase())) {
      e.preventDefault();
      targaCheck.textContent = 'Formato non valido (es. AB123CD)';
      targaCheck.style.color = 'red';
      targaInput.focus();
    }
  });
});
</script>

<script>
  const vinInput = document.getElementById('vin');
  const vinCheckSpan = document.getElementById('vin-check');

  vinInput.addEventListener('input', () => {
    vinInput.value = vinInput.value.toUpperCase();
  });

  let vinDebounceTimeout;
  vinInput.addEventListener('input', function() {
    clearTimeout(vinDebounceTimeout);
    const vin = this.value.trim().toUpperCase();

    // Controllo base formato VIN: 17 caratteri alfanumerici (senza I, O, Q)
    const vinRegex = /^[A-HJ-NPR-Z0-9]{17}$/;

    if (vin.length === 0) {
      vinCheckSpan.textContent = '';
      return;
    }

    if (!vinRegex.test(vin)) {
      vinCheckSpan.textContent = 'Formato VIN non valido. Esempio di VIN: 1ABCD12345A123456';
      vinCheckSpan.style.color = 'red';
      return;
    }

    vinDebounceTimeout = setTimeout(() => {
      fetch('<%= request.getContextPath() %>/CheckVIN', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'vin=' + encodeURIComponent(vin),
      })
      .then(response => response.text())
      .then(data => {
        if (data === 'exists') {
          vinCheckSpan.textContent = 'VIN già presente nel catalogo';
          vinCheckSpan.style.color = 'red';
        } else {
          vinCheckSpan.textContent = 'VIN disponibile';
          vinCheckSpan.style.color = 'green';
        }
      })
      .catch(() => {
        vinCheckSpan.textContent = 'Errore nel controllo VIN';
        vinCheckSpan.style.color = 'orange';
      });
    }, 300);
  });
</script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>