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
    <title>Modifica Auto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/ProductStyle.css">
</head>
<body>
    <%@ include file="/jsp/common/header.jsp" %>
    <h2><b>Modifica Auto (ADMIN)</b></h2>

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
            <th>Targa</th>
            <td><input type="text" name="targa" value="<%= product.getTarga() != null ? product.getTarga() : "" %>"></td>
        </tr>
        <tr>
            <th>Prezzo</th>
            <td><input type="number" name="prezzo" value="<%= product.getPrezzo() %>" min="0" step="0.01" required></td>
        </tr>
        <tr>
            <th>Anno</th>
            <td><input type="number" name="anno" value="<%= product.getAnno() %>" min="1950" max="2025" required></td>
        </tr>
        <tr>
            <th>Kilometri</th>
            <td><input type="number" name="kilometri" value="<%= product.getKilometri() %>" min="0" required></td>
        </tr>
        <tr>
            <th>Tipo Carburante</th>
            <td>
                <select name="tipoCarburante" required>
                    <option value="Benzina" <%= "Benzina".equalsIgnoreCase(product.getTipoCarburante()) ? "selected" : "" %>>Benzina</option>
                    <option value="Diesel" <%= "Diesel".equalsIgnoreCase(product.getTipoCarburante()) ? "selected" : "" %>>Diesel</option>
                    <option value="GPL" <%= "GPL".equalsIgnoreCase(product.getTipoCarburante()) ? "selected" : "" %>>GPL</option>
                    <option value="Metano" <%= "Metano".equalsIgnoreCase(product.getTipoCarburante()) ? "selected" : "" %>>Metano</option>
                    <option value="Elettrico" <%= "Elettrico".equalsIgnoreCase(product.getTipoCarburante()) ? "selected" : "" %>>Elettrico</option>
                    <option value="Ibrido" <%= "Ibrido".equalsIgnoreCase(product.getTipoCarburante()) ? "selected" : "" %>>Ibrido</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>Potenza Elettrica</th>
            <td><input type="number" name="potenzaElettrica" value="<%= product.getPotenzaElettrica() %>"></td>
        </tr>
        <tr>
            <th>Ruote Motrici</th>
            <td>
                <select name="ruoteMotrici">
                    <option value="anteriore" <%= "anteriore".equalsIgnoreCase(product.getRuoteMotrici()) ? "selected" : "" %>>Anteriore</option>
                    <option value="posteriore" <%= "posteriore".equalsIgnoreCase(product.getRuoteMotrici()) ? "selected" : "" %>>Posteriore</option>
                    <option value="integrale" <%= "integrale".equalsIgnoreCase(product.getRuoteMotrici()) ? "selected" : "" %>>Integrale</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>Trazione</th>
            <td>
                <select name="trazione">
                    <option value="anteriore" <%= "anteriore".equalsIgnoreCase(product.getTrazione()) ? "selected" : "" %>>Anteriore</option>
                    <option value="posteriore" <%= "posteriore".equalsIgnoreCase(product.getTrazione()) ? "selected" : "" %>>Posteriore</option>
                    <option value="integrale" <%= "integrale".equalsIgnoreCase(product.getTrazione()) ? "selected" : "" %>>Integrale</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>Potenza</th>
            <td><input type="number" name="potenza" value="<%= product.getPotenza() %>"></td>
        </tr>
        <tr>
            <th>Cilindrata</th>
            <td><input type="number" name="cilindrata" value="<%= product.getCilindrata() %>" min="0"></td>
        </tr>
        <tr>
            <th>Porte</th>
            <td><input type="number" name="porte" value="<%= product.getPorte() %>" min="3" max="5"></td>
        </tr>
        <tr>
		    <th>Tipo</th>
		    <td>
		        <select name="tipo" required>
		            <option value="" disabled>Seleziona tipo</option>
		            <option value="Berlina" <%= "Berlina".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>Berlina</option>
		            <option value="Cabriolet" <%= "Cabriolet".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>Cabriolet</option>
		            <option value="Coupé" <%= "Coupé".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>Coupé</option>
		            <option value="Monovolume" <%= "Monovolume".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>Monovolume</option>
		            <option value="Station Wagon" <%= "Station Wagon".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>Station Wagon</option>
		            <option value="Sportiva" <%= "Sportiva".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>Sportiva</option>
		            <option value="SUV" <%= "SUV".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>SUV</option>
		            <option value="Utilitaria" <%= "Utilitaria".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>Utilitaria</option>
		            <option value="Van" <%= "Van".equalsIgnoreCase(product.getTipo()) ? "selected" : "" %>>Van</option>
		        </select>
		    </td>
		</tr>
        <tr>
            <th>Posti</th>
            <td><input type="number" name="posti" value="<%= product.getPosti() %>" min="1" max="8"></td>
        </tr>
        <tr>
            <th>Tipo Cambio</th>
            <td>
                <select name="tipoCambio">
                    <option value="manuale" <%= "manuale".equalsIgnoreCase(product.getTipoCambio()) ? "selected" : "" %>>Manuale</option>
                    <option value="automatico" <%= "automatico".equalsIgnoreCase(product.getTipoCambio()) ? "selected" : "" %>>Automatico</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>Rapporti</th>
            <td><input type="number" name="rapporti" value="<%= product.getRapporti() %>"></td>
        </tr>
        <tr>
            <th>Peso</th>
            <td><input type="number" name="peso" value="<%= product.getPeso() %>"></td>
        </tr>
        <tr>
            <th>Colore</th>
            <td><input type="text" name="colore" value="<%= product.getColore() %>"></td>
        </tr>
        <tr>
            <th>Schermo Integrato</th>
            <td>
                <select name="schermoIntegrato">
                    <option value="true" <%= product.isSchermoIntegrato() ? "selected" : "" %>>Sì</option>
                    <option value="false" <%= !product.isSchermoIntegrato() ? "selected" : "" %>>No</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>Climatizzatore</th>
            <td>
                <select name="climatizzatore">
                    <option value="true" <%= product.isClimatizzatore() ? "selected" : "" %>>Sì</option>
                    <option value="false" <%= !product.isClimatizzatore() ? "selected" : "" %>>No</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>Proprietario</th>
            <td><input type="text" name="cf" value="<%= product.getCf() %>" required></td>
        </tr>
        <tr>
            <th>Venduta</th>
            <td>
                <select name="venduta">
                    <option value="true" <%= product.isVenduta() ? "selected" : "" %>>Sì</option>
                    <option value="false" <%= !product.isVenduta() ? "selected" : "" %>>No</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>Id Ordine</th>
            <td><input type="number" name="id_ordine" value="<%= product.getIdOrdine() %>"></td>
        </tr>
        <tr>
		    <th>IVA (%)</th>
		    <td><input type="number" name="iva" value="<%= product.getIva() %>"></td>
		</tr>
        <tr>
            <th>Immagine</th>
            <td><input type="file" name="immagine" accept="image/*"></td>
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
        <a href="<%= request.getContextPath() %>/jsp/admin/catalogo.jsp" class="torna-catalogo">Torna al catalogo</a>
    </div>
	<br>
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
  const potenzaInput = document.querySelector('input[name="potenza"]');
  const cilindrataInput = document.querySelector('input[name="cilindrata"]');

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

  // Inizializza stato al caricamento pagina
  aggiornaCampi();
});
</script>

<script> src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
