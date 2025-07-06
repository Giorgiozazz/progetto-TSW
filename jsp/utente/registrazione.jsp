<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
  <title>Registrazione</title>
  <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
  <%@ include file="/jsp/common/header.jsp" %>
  <br><br><br><br><br>
  <main>
    <form action="<%= request.getContextPath() %>/UtenteControl" method="post">
      <input type="hidden" name="action" value="registrazione" />
      
      <label for="cf">Codice Fiscale *</label>
      <input type="text" id="cf" name="cf" maxlength="16" required />
      <span id="cf-check" style="font-size: 0.9em; display: block; height: 1.2em; margin-top: 2px;"></span>

      <label for="nome">Nome *</label>
      <input type="text" id="nome" name="nome" required />

      <label for="cognome">Cognome *</label>
      <input type="text" id="cognome" name="cognome" required />

      <label for="email">Email *</label>
      <input type="email" id="email" name="email" required />
      <span id="email-check" style="font-size: 0.9em; display: block; height: 1.2em; margin-top: 2px;"></span>

      <label for="telefono">Telefono *</label>
      <input type="text" id="telefono" name="telefono" maxlength="20" required />

      <label for="provincia">Provincia *</label>
      <select id="provincia" name="provincia" required>
        <option value="">-- Seleziona Provincia --</option>
      </select>
      
      <label for="citta">Città *</label>
      <select id="citta" name="citta" required>
        <option value="">-- Seleziona Città --</option>
      </select>

      <label for="indirizzo">Indirizzo *</label>
      <input type="text" id="indirizzo" name="indirizzo" required />

      <label for="numero_civico">Numero Civico</label>
      <input type="text" id="numero_civico" name="numero_civico" maxlength="10"/>

      <label for="password">Password *</label>
		<div class="password-wrapper">
		  <input type="password" id="password" name="password" required />
		  <span class="toggle-password" onclick="togglePassword()">🙈</span>
		</div>
		<p>(*) CAMPI OBBLIGATORI</p>
      <input type="submit" value="Registrati" />
      <input type="reset" value="Annulla" />
    </form>
    
    <div class="registrazione-container">
      <p>Hai già un account?
      <a href="${pageContext.request.contextPath}/jsp/utente/login.jsp" class="registrazione-link">Accedi qui</a></p>
    </div>
  </main>

  <%@ include file="/jsp/common/footer.jsp" %>

<script type="text/javascript">
  var selectProvincia = document.getElementById('provincia');
  var selectCitta = document.getElementById('citta');
  var dati = [];

  function popolaCitta(provinciaSelezionata) {
    selectCitta.innerHTML = '<option value="">-- Seleziona Città --</option>';

    if (!provinciaSelezionata) return;

    var cittaFiltrate = dati.filter(function(item) {
      return item["Sigla automobilistica"] === provinciaSelezionata;
    });

    var cittaUniche = Array.from(new Set(cittaFiltrate.map(item => item["Denominazione in italiano"]))).sort();

    cittaUniche.forEach(function(citta) {
      var option = document.createElement('option');
      option.value = citta;
      option.textContent = citta;
      selectCitta.appendChild(option);
    });
  }

  fetch('<%= request.getContextPath() %>/data/paesi_province.json')
    .then(response => {
      if (!response.ok) throw new Error('Errore nel caricamento del file JSON');
      return response.json();
    })
    .then(json => {
      dati = json;
      var province = Array.from(new Set(dati.map(item => item["Sigla automobilistica"]))).sort();

      province.forEach(function(provincia) {
        var option = document.createElement('option');
        option.value = provincia;
        option.textContent = provincia;
        selectProvincia.appendChild(option);
      });

      selectProvincia.addEventListener('change', function() {
        popolaCitta(this.value);
      });
    })
    .catch(error => {
      console.error('Errore caricamento JSON:', error);
    });
</script>

<script>
  let debounceTimeout;
  const emailInput = document.getElementById('email');
  const emailCheckSpan = document.getElementById('email-check');

  emailInput.addEventListener('input', function () {
    clearTimeout(debounceTimeout);
    const email = this.value.trim();

    // Regex semplice per controllo base email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

    if (email.length === 0) {
      emailCheckSpan.textContent = '';
      return;
    }

    if (!emailRegex.test(email)) {
      emailCheckSpan.textContent = 'Email non valida';
      emailCheckSpan.style.color = 'red';
      return;  // Non fare chiamata al server
    }

    // Se email valida, aspetta un attimo prima di chiamare il server
    debounceTimeout = setTimeout(() => {
      fetch('<%= request.getContextPath() %>/CheckEmail', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'email=' + encodeURIComponent(email),
      })
      .then(response => response.text())
      .then(data => {
        if (data === 'exists') {
          emailCheckSpan.textContent = 'Email già registrata';
          emailCheckSpan.style.color = 'red';
        } else {
          emailCheckSpan.textContent = 'Email disponibile';
          emailCheckSpan.style.color = 'green';
        }
      })
      .catch(() => {
        emailCheckSpan.textContent = 'Errore controllo email';
        emailCheckSpan.style.color = 'orange';
      });
    }, 300);
  });
</script>
<script>
const cfInput = document.getElementById('cf');
const cfCheckSpan = document.getElementById('cf-check');

cfInput.addEventListener('input', () => {
  cfInput.value = cfInput.value.toUpperCase();
});

let cfDebounceTimeout;
cfInput.addEventListener('input', function() {
  clearTimeout(cfDebounceTimeout);
  const cf = this.value.trim().toUpperCase();
  cfInput.value = cf;

  const cfRegex = /^[A-Z0-9]{16}$/;

  if (cf.length === 0) {
    cfCheckSpan.textContent = '';
    return;
  }

  if (!cfRegex.test(cf)) {
    cfCheckSpan.textContent = 'Codice Fiscale non valido';
    cfCheckSpan.style.color = 'red';
    return;
  }

  cfDebounceTimeout = setTimeout(() => {
    console.log("Controllo CF: ", cf);
    fetch('<%= request.getContextPath() %>/CheckCF', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: 'cf=' + encodeURIComponent(cf),
    })
    .then(response => response.text())
    .then(data => {
      console.log("Risposta CheckCF:", data);
      if (data === 'exists') {
        cfCheckSpan.textContent = 'Codice Fiscale già presente';
        cfCheckSpan.style.color = 'red';
      } else {
        cfCheckSpan.textContent = 'Codice Fiscale disponibile';
        cfCheckSpan.style.color = 'green';
      }
    })
    .catch(() => {
      cfCheckSpan.textContent = 'Errore controllo Codice Fiscale';
      cfCheckSpan.style.color = 'orange';
    });
  }, 300);
});

</script>
<script>
function togglePassword() {
    var pwInput = document.getElementById('password');
    var toggleIcon = document.querySelector('.toggle-password');
    if (pwInput.type === 'password') {
        pwInput.type = 'text';
        toggleIcon.textContent = '👁️'; // occhio aperto = password visibile
    } else {
        pwInput.type = 'password';
        toggleIcon.textContent = '🙈'; // scimmietta occhi chiusi = password nascosta
    }
}

// Inizializza l'icona corretta al caricamento della pagina
window.onload = function() {
    var pwInput = document.getElementById('password');
    var toggleIcon = document.querySelector('.toggle-password');
    if (pwInput.type === 'password') {
        toggleIcon.textContent = '🙈';
    } else {
        toggleIcon.textContent = '👁️';
    }
};
</script>

<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>