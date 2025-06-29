<%@ page import="java.util.*, java.sql.*" %>
<%@ page import="java.sql.Date" %>
<%@ page import="it.unisa.DriverManagerConnectionPool" %>
<%@ page import="it.unisa.model.UtenteBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
    if (utente == null || !utente.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }

    String cfFiltro = request.getParameter("cfFiltro");
    String dataDa = request.getParameter("dataDa");
    String dataA = request.getParameter("dataA");

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
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
<main>
<header>
    <h1>Visualizzazione ordini generali</h1>
</header>
    <form method="get">
        <label for="cfFiltro">Codice Fiscale Cliente:</label>
        <input type="text" id="cfFiltro" name="cfFiltro" value="<%= (cfFiltro != null) ? cfFiltro : "" %>" />
		<span id="cfFiltro-check" style="font-size: 0.9em; display: block; height: 1.2em; margin-top: 2px;"></span>

        <label for="dataDa">Data da:</label>
        <input type="date" id="dataDa" name="dataDa" value="<%= (dataDa != null) ? dataDa : "" %>" />

        <label for="dataA">Data a:</label>
        <input type="date" id="dataA" name="dataA" value="<%= (dataA != null) ? dataA : "" %>" />

        <input type="submit" value="Filtra" />
    </form>

    <table class="dettagli-auto">
        <tr>
            <th class="col-foto col-id">ID Ordine</th>
			<th class="col-foto col-cf">Codice Fiscale</th>
            <th class="col-foto">Data Ordine</th>
            <th class="col-foto">Stato</th>
            <th class="col-foto">Totale</th>
            <th class="col-foto">Tipo Consegna</th>
        </tr>
<%
    try {
        conn = DriverManagerConnectionPool.getConnection();

        String sql = "SELECT * FROM ORDINE WHERE 1=1";
        List<Object> parametri = new ArrayList<>();

        if (cfFiltro != null && !cfFiltro.trim().isEmpty()) {
            sql += " AND CF = ?";
            parametri.add(cfFiltro.trim());
        }
        if (dataDa != null && !dataDa.trim().isEmpty()) {
            sql += " AND DATA_ORDINE >= ?";
            parametri.add(Date.valueOf(dataDa));
        }
        if (dataA != null && !dataA.trim().isEmpty()) {
            sql += " AND DATA_ORDINE <= ?";
            parametri.add(Date.valueOf(dataA));
        }

        sql += " ORDER BY DATA_ORDINE DESC";

        ps = conn.prepareStatement(sql);
        for (int i = 0; i < parametri.size(); i++) {
            ps.setObject(i + 1, parametri.get(i));
        }

        rs = ps.executeQuery();
        while (rs.next()) {
%>
        <tr>
            <td class="col-id" data-label="ID ORDINE"><%= rs.getInt("ID_ORDINE") %></td>
            <td class="col-cf" data-label="CF"><%= rs.getString("CF") %></td>
            <td data-label="DATA ORDINE"><%= rs.getTimestamp("DATA_ORDINE") %></td>
            <td data-label="STATO ORDINE"><%= rs.getString("STATO_ORDINE") %></td>
            <td data-label="TOTALE"><%= rs.getBigDecimal("TOTALE") %> €</td>
            <td data-label="TIPO CONSEGNA"><%= rs.getString("TIPO_CONSEGNA") %></td>
        </tr>
<%
        }
    } catch (Exception e) {
%>
        <tr><td colspan="6">Errore: <%= e.getMessage() %></td></tr>
<%
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (conn != null) DriverManagerConnectionPool.releaseConnection(conn);
    }
%>
    </table>
    <div class="torna-wrapper">
	    <a href="<%=request.getContextPath()%>/jsp/utente/infoPersonali.jsp" class="torna-catalogo">Torna alle INFO</a>
	    <a href="<%=request.getContextPath()%>/home" class="torna-catalogo">Torna alla home</a>
    </div>
    <br>
</main>
<%@ include file="/jsp/common/footer.jsp" %>
<script>
  const cfFiltroInput = document.getElementById('cfFiltro');
  cfFiltroInput.addEventListener('input', () => {
    cfFiltroInput.value = cfFiltroInput.value.toUpperCase();
  });
</script>
<script>
  let cfFiltroDebounce;
  const cfFiltroCheckSpan = document.getElementById('cfFiltro-check');

  cfFiltroInput.addEventListener('input', function() {
    clearTimeout(cfFiltroDebounce);
    const cf = this.value.trim().toUpperCase();
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

    cfFiltroDebounce = setTimeout(() => {
      fetch('<%= request.getContextPath() %>/CheckCF', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'cf=' + encodeURIComponent(cf),
      })
      .then(res => res.text())
      .then(data => {
        if (data === 'exists') {
          cfFiltroCheckSpan.textContent = 'Codice Fiscale trovato';
          cfFiltroCheckSpan.style.color = 'green';
        } else {
          cfFiltroCheckSpan.textContent = 'Codice Fiscale non trovato';
          cfFiltroCheckSpan.style.color = 'red';
        }
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

    const dataDaInput = document.getElementById('dataDa');
    const dataAInput = document.getElementById('dataA');

    if (dataDaInput) {
      dataDaInput.setAttribute('min', minDate);
    }

    if (dataAInput) {
      dataAInput.setAttribute('min', minDate);
    }
  });
</script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
