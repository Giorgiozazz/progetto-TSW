<%@ page import="java.util.List" %>
<%@ page import="it.unisa.model.OrdineBean" %>
<%@ page import="it.unisa.model.UtenteBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null || !utente.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }

    String cfFiltro = (String) request.getAttribute("cfFiltro");
    String dataDa = (String) request.getAttribute("dataDa");
    String dataA = (String) request.getAttribute("dataA");

    List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("ordini");
    String errore = (String) request.getAttribute("errore");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Ordini Generali</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ProductStyle.css">
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<br><br><br><br>
<main>
    <h2><b>VISUALIZZAZIONE ORDINI GENERALI</b></h2>

    <form method="get" action="<%= request.getContextPath() %>/OrdiniGenerali">
        <label for="cfFiltro">Codice Fiscale Cliente:</label>
        <input type="text" id="cfFiltro" name="cfFiltro" value="<%= (cfFiltro != null) ? cfFiltro : "" %>" />
        <span id="cfFiltro-check" style="font-size: 0.9em; display: block; height: 1.2em; margin-top: 2px;"></span>

        <label for="dataDa">Data da:</label>
        <input type="date" id="dataDa" name="dataDa" value="<%= (dataDa != null) ? dataDa : "" %>" />

        <label for="dataA">Data a:</label>
        <input type="date" id="dataA" name="dataA" value="<%= (dataA != null) ? dataA : "" %>" />

        <input type="submit" value="Filtra" />
    </form>

    <% if (errore != null) { %>
        <p style="color: red;"><%= errore %></p>
    <% } else if (ordini == null || ordini.isEmpty()) { %>
        <p style="text-align: center;">Nessun ordine trovato.</p>
    <% } else { %>
        <table class="dettagli-auto">
            <tr>
                <th class="col-foto col-id">ID Ordine</th>
                <th class="col-foto col-cf">Codice Fiscale</th>
                <th class="col-foto">Data Ordine</th>
                <th class="col-foto">Stato</th>
                <th class="col-foto">Totale</th>
                <th class="col-foto">Info</th>
            </tr>
            <% for (OrdineBean ordine : ordini) { %>
                <tr>
                    <td class="col-id" data-label="ID ORDINE"><%= ordine.getIdOrdine() %></td>
                    <td class="col-cf" data-label="CF"><%= ordine.getCf() %></td>
                    <td data-label="DATA ORDINE"><%= ordine.getDataOrdine() %></td>
                    <td data-label="STATO ORDINE"><%= ordine.getStatoOrdine() %></td>
                    <td data-label="TOTALE"><%= ordine.getTotale() %> €</td>
                    <td data-label="INFO"><a href="<%= request.getContextPath() %>/jsp/admin/DettagliOrdineAdmin.jsp?id=<%= ordine.getIdOrdine() %>">
                            Vedi Dettagli
                        </a></td>
                </tr>
            <% } %>
        </table>
    <% } %>

    <div class="torna-wrapper">
        <a href="<%=request.getContextPath()%>/jsp/utente/infoPersonali.jsp" class="torna-catalogo">Torna alle INFO</a>
        <a href="<%=request.getContextPath()%>/home" class="torna-catalogo">Torna alla home</a>
    </div>
    <br>
</main>

<%@ include file="/jsp/common/footer.jsp" %>

<!-- Script: validazione CF, date, fetch CheckCF -->
<script>
  const cfFiltroInput = document.getElementById('cfFiltro');
  const cfFiltroCheckSpan = document.getElementById('cfFiltro-check');

  cfFiltroInput.addEventListener('input', () => {
    clearTimeout(window.cfFiltroDebounce);
    const cf = cfFiltroInput.value.toUpperCase().trim();
    cfFiltroInput.value = cf;

    if (cf.length === 0) {
      cfFiltroCheckSpan.textContent = '';
      return;
    }

    const cfRegex = /^[A-Z0-9]{16}$/;
    if (!cfRegex.test(cf)) {
      cfFiltroCheckSpan.textContent = 'Codice Fiscale non valido';
      cfFiltroCheckSpan.style.color = 'red';
      return;
    }

    window.cfFiltroDebounce = setTimeout(() => {
      fetch('<%= request.getContextPath() %>/CheckCF', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'cf=' + encodeURIComponent(cf),
      })
      .then(res => res.text())
      .then(data => {
        cfFiltroCheckSpan.textContent = data === 'exists' ? 'Codice Fiscale trovato' : 'Codice Fiscale non trovato';
        cfFiltroCheckSpan.style.color = data === 'exists' ? 'green' : 'red';
      })
      .catch(() => {
        cfFiltroCheckSpan.textContent = 'Errore verifica CF';
        cfFiltroCheckSpan.style.color = 'orange';
      });
    }, 300);
  });
</script>

<script>
  document.addEventListener('DOMContentLoaded', () => {
    const minDate = '1950-01-01';
    document.getElementById('dataDa')?.setAttribute('min', minDate);
    document.getElementById('dataA')?.setAttribute('min', minDate);
  });
</script>

<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
