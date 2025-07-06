<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.model.ProductBean" %>

<%
    Collection<ProductBean> carrello = (Collection<ProductBean>) session.getAttribute("carrello");
    if (carrello == null || carrello.isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/jsp/auto/carrello.jsp?empty=true");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<br><br><br><br>
<main>
    <h2>Conferma Ordine</h2>
    
    <form method="post" action="<%= request.getContextPath() %>/ConfermaOrdine">
        <fieldset>
            <legend>Metodo di consegna</legend>
            <label>
                <input type="radio" name="tipo_consegna" value="Ritiro" checked> Ritiro in sede
            </label><br>
            <label>
                <input type="radio" name="tipo_consegna" value="spedizione"> Spedizione a domicilio
            </label>
        </fieldset>

        <div id="dati-spedizione">
            <fieldset>
            	<label for="provincia">Provincia *</label>
                <select id="provincia" name="provincia">
                    <option value="">-- Seleziona Provincia --</option>
                </select><br>
                
                <legend>Dati per la spedizione</legend>
                <label for="paese">Paese *</label>
				<select id="paese" name="paese">
				  <option value="">-- Seleziona Paese --</option>
				</select><br>
                
                <div class="form-row">
			        <label for="indirizzo">Indirizzo *</label>
			        <input class="input-largo" type="text" name="indirizzo" >
			    </div>
			    <div class="form-row">
			        <label for="numero_civico">Numero Civico</label>
			        <input class="input-largo" type="text" name="numero_civico" >
			    </div>
            </fieldset>
        </div>

      <fieldset>
		    <legend>Metodo di pagamento</legend>
		
		    <label>
		        <input type="radio" name="pagamento" value="carta" checked> Carta di credito
		    </label><br>
		    <label>
		        <input type="radio" name="pagamento" value="paypal"> PayPal
		    </label>
		
		    <!-- Sezione dati Carta di Credito -->
			<div id="dati-carta">
			    <div class="form-row">
			        <label for="numero_carta">Numero Carta *</label>
			        <input class="input-largo" type="text" name="numero_carta"
			               maxlength="19" inputmode="numeric" pattern="\d{4}(?:\s\d{4}){3}"  placeholder="1234 5678 9012 3456" required>
			    </div>
			
			    <div class="form-row">
			        <label for="nome_carta">Nome Intestatario *</label>
			        <input class="input-largo" type="text" name="nome_carta" required>
			    </div>
			
			    <div class="form-row">
			        <label for="scadenza_carta">Data Scadenza (MM/YY) *</label>
			        <input class="input-largo" type="text" name="scadenza_carta"
			               pattern="\d{2}/\d{2}" placeholder="MM/YY" required>
	                <span class="error-message" id="scadenza-error" style="color: red; font-size: 0.9em; display: none;"></span>
			    </div>
			
			    <div class="form-row">
			        <label for="cvv_carta">CVV *</label>
			        <input class="input-largo" type="text" name="cvv_carta"
			               maxlength="3" pattern="\d{3}" required>
			    </div>
			</div>

		    <!-- Sezione dati PayPal -->
		    <div id="dati-paypal" style="display: none;">
		        <div class="form-row">
			        <label for="nome_propietario">Nome Intestatario *</label>
			        <input class="input-largo" type="text" name="nome_propietario" required>
			    </div>
		    </div>
		</fieldset>

        <fieldset>
            <legend>Dettagli del pagamento</legend>
            <div class="form-row">
		        <label for="anticipo">Anticipo (€)</label>
		        <input class="input-largo" type="text" name="anticipo">
		    </div>
        
            <label>Numero rate * 
                <select name="num_rate" id="num_rate">
                    <option value="0">Pagamento completo</option>
                    <option value="6">6 rate</option>
                    <option value="12">12 rate</option>
                    <option value="24">24 rate</option>
                    <option value="36">36 rate</option>
                </select>
            </label>
        </fieldset>
		<p>(*) CAMPI OBBLIGATORI</p>
        <input type="submit" value="Finalizza Ordine" />
    </form>
    <div class="ordina-link-container">
        <a href="<%= request.getContextPath() %>/jsp/auto/carrello.jsp" class="ordina-link">Torna al Carrello</a>
        <a href="<%= request.getContextPath() %>/jsp/auto/catalogo.jsp" class="ordina-link">Torna al Catalogo</a>
    </div>
    <br>
</main>
<script>
	const tipoConsegna = document.querySelectorAll('input[name="tipo_consegna"]');
	const sezioneSpedizione = document.getElementById('dati-spedizione');
	const indirizzoInput = document.querySelector('input[name="indirizzo"]');
	const numCivicoInput = document.querySelector('input[name="numero_civico"]');
	
	tipoConsegna.forEach(radio => {
	    radio.addEventListener('change', () => {
	        if (radio.value === 'spedizione' && radio.checked) {
	            sezioneSpedizione.style.display = 'block';
	            indirizzoInput.required = true;
	            numCivicoInput.required = false;
	        } else {
	            sezioneSpedizione.style.display = 'none';
	            indirizzoInput.required = false;
	            numCivicoInput.required = false;
	        }
	    });
	});
	
	// Inizializza correttamente lo stato all'avvio
	if (document.querySelector('input[name="tipo_consegna"]:checked').value === 'spedizione') {
	    sezioneSpedizione.style.display = 'block';
	    indirizzoInput.required = true;
	    numCivicoInput.required = false;
	} else {
	    sezioneSpedizione.style.display = 'none';
	    indirizzoInput.required = false;
	    numCivicoInput.required = false;
	}

    // Disabilita campo anticipo se selezionato pagamento completo (num_rate == 0)
    const numRateSelect = document.getElementById('num_rate');
    const anticipoInput = document.querySelector('input[name="anticipo"]');

    function toggleAnticipo() {
        if (numRateSelect.value === '0') {
            anticipoInput.value = '';
            anticipoInput.disabled = true;
        } else {
            anticipoInput.disabled = false;
        }
    }

    toggleAnticipo();
    numRateSelect.addEventListener('change', toggleAnticipo);
</script>

<script>
var selectProvincia = document.getElementById('provincia');
var selectPaese = document.getElementById('paese');
var dati = []; 

// Funzione per popolare select paese con dati filtrati
function popolaPaesi(provinciaSelezionata) {
    // Svuota la select paese
    selectPaese.innerHTML = '<option value="">-- Seleziona Paese --</option>';

    if (!provinciaSelezionata) return; // se non c'è provincia selezionata, non fa nulla

    // Filtra i dati per provincia
    var paesiFiltrati = dati.filter(function(item) {
        return item["Sigla automobilistica"] === provinciaSelezionata;
    });

    // Estrai i paesi unici dalla lista filtrata
    var paesiUnici = Array.from(new Set(paesiFiltrati.map(item => item["Denominazione in italiano"]))).sort();

    // Popola la select paese con i paesi filtrati
    paesiUnici.forEach(function(paese) {
        var option = document.createElement('option');
        option.value = paese;
        option.innerHTML = paese
            .replace(/à/g, '&agrave;')
            .replace(/è/g, '&egrave;')
            .replace(/é/g, '&eacute;')
            .replace(/ì/g, '&igrave;')
            .replace(/ò/g, '&ograve;')
            .replace(/ù/g, '&ugrave;');
        selectPaese.appendChild(option);
    });
}

fetch('<%= request.getContextPath() %>/data/paesi_province.json')
    .then(function(response) {
      if (!response.ok) throw new Error('Errore nel caricamento del file JSON');
      return response.json();
    })
    .then(function(json) {
      dati = json;

      // Popola province 
      var province = Array.from(new Set(dati.map(function(item) {
        return item["Sigla automobilistica"];
      }))).sort();

      province.forEach(function(provincia) {
        var option = document.createElement('option');
        option.value = provincia;
        option.textContent = provincia;
        selectProvincia.appendChild(option);
      });

      // Aggiungi listener sulla select provincia per aggiornare i paesi
      selectProvincia.addEventListener('change', function() {
          var provinciaSelezionata = this.value;
          popolaPaesi(provinciaSelezionata);
      });
    })
    .catch(function(error) {
      console.error('Errore caricamento JSON:', error);
    });

</script>
<script>
    const pagamentoRadios = document.querySelectorAll('input[name="pagamento"]');
    const datiCarta = document.getElementById('dati-carta');
    const datiPaypal = document.getElementById('dati-paypal');

    function toggleCampiPagamento() {
        const metodo = document.querySelector('input[name="pagamento"]:checked').value;
        if (metodo === 'carta') {
            datiCarta.style.display = 'block';
            datiPaypal.style.display = 'none';

            datiCarta.querySelectorAll('input').forEach(input => {
                input.required = true;
                input.disabled = false;  // abilita il campo così viene validato
            });
            datiPaypal.querySelectorAll('input').forEach(input => {
                input.required = false;
                input.disabled = true;   // disabilita i campi PayPal
            });
        } else if (metodo === 'paypal') {
            datiCarta.style.display = 'none';
            datiPaypal.style.display = 'block';

            datiCarta.querySelectorAll('input').forEach(input => {
                input.required = false;
                input.disabled = true;  // **disabilita i campi carta, incluso scadenza**
            });
            datiPaypal.querySelectorAll('input').forEach(input => {
                input.required = true;
                input.disabled = false;  // abilita i campi PayPal
            });
        }
    }
    pagamentoRadios.forEach(radio => {
        radio.addEventListener('change', toggleCampiPagamento);
    });

    // Mostra i campi corretti all'avvio
    toggleCampiPagamento();
</script>
<script>
// Permetti solo numeri nei campi specifici
document.querySelectorAll('input[name="numero_carta"], input[name="cvv_carta"], input[name="num_civico"]').forEach(input => {
    input.addEventListener('input', () => {
        input.value = input.value.replace(/\D/g, ''); // Rimuove tutto ciò che non è cifra
    });
});
</script>
<script>
document.addEventListener('DOMContentLoaded', () => {
  const scadenzaInput  = document.querySelector('input[name="scadenza_carta"]');
  const scadenzaError  = document.getElementById('scadenza-error');
  const form           = document.querySelector('form');

  if (!scadenzaInput || !scadenzaError || !form) return;  

  scadenzaInput.addEventListener('input', () => {
      let v = scadenzaInput.value.replace(/\D/g, '');
      if (v.length > 4) v = v.slice(0, 4);
      scadenzaInput.value = v.length >= 3 ? v.slice(0,2) + '/' + v.slice(2) : v;
      scadenzaError.style.display = 'none';                 
  });

  form.addEventListener('submit', e => {
	    const metodoPagamento = document.querySelector('input[name="pagamento"]:checked').value;

	    if (metodoPagamento === 'carta') {
	        const v = scadenzaInput.value;
	        let msg = '';

	        if (!/^\d{2}\/\d{2}$/.test(v)) {
	            msg = 'Formato corretto: MM/YY';
	        } else {
	            const [mStr, yStr] = v.split('/');
	            const mese = +mStr;
	            const anno = 2000 + +yStr;

	            const minAnno = 2025, minMese = 7;   // 07/25
	            const maxAnno = 2031, maxMese = 12;  // 12/31

	            if (mese < 1 || mese > 12)
	                msg = 'Mese tra 01 e 12';
	            else if (anno < minAnno || (anno === minAnno && mese < minMese))
	                msg = 'Data ≥ 07/25';
	            else if (anno > maxAnno || (anno === maxAnno && mese > maxMese))
	                msg = 'Data ≤ 12/31';
	        }

	        if (msg) {
	            e.preventDefault();
	            scadenzaError.textContent = msg;
	            scadenzaError.style.display = 'inline';
	            scadenzaInput.focus();
	        } else {
	            scadenzaError.style.display = 'none';
	        }
	    } else {
	        // Metodo di pagamento diverso da carta: ignora validazione scadenza carta
	        scadenzaError.style.display = 'none';
	    }
	});

});
</script>


<script>
// Maschera input per numero carta: spazio ogni 4 cifre
const numeroCartaInput = document.querySelector('input[name="numero_carta"]');

numeroCartaInput.addEventListener('input', function(e) {
    let value = this.value.replace(/\D/g, ''); // Rimuove tutti i caratteri non numerici
    value = value.slice(0, 16); // Limita a massimo 16 cifre

    // Inserisce spazio ogni 4 cifre
    let formattedValue = value.match(/.{1,4}/g)?.join(' ') || '';
    this.value = formattedValue;
});
</script>

<%@ include file="/jsp/common/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
