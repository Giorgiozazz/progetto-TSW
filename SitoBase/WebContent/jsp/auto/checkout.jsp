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
                <legend>Dati per la spedizione</legend>
                <label>Indirizzo: <input type="text" name="indirizzo" /></label><br>
                <label>Numero Civico: <input type="text" name="numero_civico" /></label><br>
                <label>Città: <input type="text" name="citta" /></label><br>
                <label>Provincia: <input type="text" name="provincia" /></label><br>
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
		        <input type="number" name="anticipo" min="0" step="0.01" value="0" required>
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

    // Esegui al caricamento della pagina
    toggleAnticipo();

    // Esegui ogni volta che cambia num_rate
    numRateSelect.addEventListener('change', toggleAnticipo);
</script>

<%@ include file="/jsp/common/footer.jsp" %>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
