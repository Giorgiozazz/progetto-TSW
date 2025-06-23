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
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
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
            	<label for="provincia">Provincia:</label>
                <select id="provincia" name="provincia">
                    <option value="">-- Seleziona Provincia --</option>
                </select><br>
                
                <legend>Dati per la spedizione</legend>
                <label for="paese">Paese:</label>
				<select id="paese" name="paese">
				  <option value="">-- Seleziona Paese --</option>
				</select><br>
                
                <label>Indirizzo: <input type="text" name="indirizzo" /></label><br>
                <label>Numero Civico: <input type="text" name="numero_civico" /></label><br>
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
        </fieldset>
        
        <fieldset>
            <legend>Dettagli del pagamento</legend>
            <label>Anticipo (€): 
                <input type="number" name="anticipo" min="0" step="0.01" value="0">
            </label><br>
        
            <label>Numero rate: 
                <select name="num_rate" id="num_rate">
                    <option value="0">Pagamento completo</option>
                    <option value="6">6 rate</option>
                    <option value="12">12 rate</option>
                    <option value="24">24 rate</option>
                    <option value="36">36 rate</option>
                </select>
            </label>
        </fieldset>

        <input type="submit" value="Finalizza Ordine" />
    </form>
    <div class="ordina-link-container">
        <a href="<%= request.getContextPath() %>/jsp/auto/carrello.jsp" class="ordina-link">Torna al Carrello</a>
        <a href="<%= request.getContextPath() %>/index.jsp" class="ordina-link">Torna al Catalogo</a>
    </div>
    <br>
</main>
<script>
    // Mostra/nasconde i campi di spedizione in base al tipo di consegna
    const tipoConsegna = document.querySelectorAll('input[name="tipo_consegna"]');
    const sezioneSpedizione = document.getElementById('dati-spedizione');

    tipoConsegna.forEach(radio => {
        radio.addEventListener('change', () => {
            sezioneSpedizione.style.display = (radio.value === 'spedizione' && radio.checked) ? 'block' : 'none';
        });
    });

    // All'avvio nascondi se è selezionato "ritiro"
    if (document.querySelector('input[name="tipo_consegna"]:checked').value.toLowerCase() === 'ritiro') {
        sezioneSpedizione.style.display = 'none';
    } else {
        sezioneSpedizione.style.display = 'block';
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
var dati = []; // la tua variabile globale già definita

// Funzione per popolare select paese con dati filtrati
function popolaPaesi(provinciaSelezionata) {
    // Svuota la select paese
    selectPaese.innerHTML = '<option value="">-- Seleziona Paese --</option>';

    if (!provinciaSelezionata) return; // se non c'è provincia selezionata, non fare nulla

    // Filtra i dati per la provincia selezionata
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

// Quando carichi dati dal JSON, aggiungi l'event listener
fetch('<%= request.getContextPath() %>/data/paesi_province.json')
    .then(function(response) {
      if (!response.ok) throw new Error('Errore nel caricamento del file JSON');
      return response.json();
    })
    .then(function(json) {
      dati = json;

      // Popola province (come già fai)
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

<%@ include file="/jsp/common/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
