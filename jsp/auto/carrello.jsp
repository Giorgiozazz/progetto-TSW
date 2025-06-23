<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.model.ProductBean" %>

<%
    Collection<ProductBean> carrello = (Collection<ProductBean>) session.getAttribute("carrello");
    String added = request.getParameter("added");
    String already = request.getParameter("already");
    boolean isCarrelloVuoto = carrello == null || carrello.isEmpty();
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Carrello</title>
</head>

<body>
    <%@ include file="/jsp/common/header.jsp" %>
<main>
    <% if ("true".equals(added)) { %>
        <div class="carrello-messaggio successo" id="messaggio-successo">✅ Prodotto aggiunto al carrello con successo!</div>
    <% } else if ("true".equals(already)) { %>
        <div class="carrello-messaggio avviso" id="messaggio-avviso">⚠️ Il prodotto è già presente nel carrello.</div>
    <% } %>

    <h2><b>IL TUO CARRELLO</b></h2>

    <form method="post" action="<%= request.getContextPath() %>/jsp/auto/checkout.jsp" class="form-simple">
        <table class="dettagli-auto">
            <tr>
                <th class="col-foto">FOTO</th>
                <th class="col-foto">Marca</th>
                <th class="col-foto">Modello</th>
                <th class="col-foto">Targa</th>
                <th class="col-foto">Prezzo</th>
                <!--  <th>Quantità</th> --> 
                <th class="col-foto">Rimuovi</th>
            </tr>
            <% 
                if (carrello != null && !carrello.isEmpty()) {
                    for (ProductBean bean : carrello) {
            %>
                <tr>
                    <td data-label="Foto">
                            <% if (bean.getImmagine() != null && !bean.getImmagine().isEmpty()) { %>
                                <img src="<%= request.getContextPath() + bean.getImmagine() %>" alt="Immagine prodotto" />
                            <% } else { %>
                                <img src="<%= request.getContextPath() %>/images/placeholder.jpg" alt="Immagine non disponibile" />
                            <% } %>
                        
                    </td>
                    <td data-label="Marca"><%= bean.getMarca() %></td>
                    <td data-label="Modello"><%= bean.getModello() %></td>
                    <td data-label="Targa"><%= bean.getTarga() != null ? bean.getTarga() : "N/A" %></td>
                    <td data-label="Prezzo"><%= bean.getPrezzo() %> €</td>
                    <!-- <td>
                        <input type="hidden" name="vin" value="<%= bean.getVin() %>" />
                        <button type="button" onclick="modificaQuantita(this, -1)">-</button>
                        <input type="text" name="quantita_<%= bean.getVin() %>" value="<%= request.getAttribute("quantita_" + bean.getVin()) != null ? request.getAttribute("quantita_" + bean.getVin()) : 1 %>" readonly style="width: 40px; text-align: center;" />
                        <button type="button" onclick="modificaQuantita(this, 1)">+</button>
                    </td> -->
                    <td data-label="Rimuovi">
                        <a href="<%= request.getContextPath() %>/RemoveFromCart?vin=<%= bean.getVin() %>">Rimuovi</a>
                    </td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="6">Il carrello è vuoto.</td>
                </tr>
            <% } %>
        </table>

        <div class="ordina-link-container">
            <!-- Pulsante di conferma ordine solo se il carrello non è vuoto -->
            <% if (!isCarrelloVuoto) { %>
                <input type="submit" value="Conferma Ordine" class="conferma-ordine" id="conferma-ordine">
            <% } %>
        </div>
    </form>

    <div class="ordina-link-container">
        <a href="<%= request.getContextPath() %>/jsp/auto/catalogo.jsp" class="ordina-link">Torna al Catalogo</a>
    </div>
</main>
<br><br>
<%@ include file="/jsp/common/footer.jsp" %>

<script>
setTimeout(() => {
    const successo = document.getElementById("messaggio-successo");
    const avviso = document.getElementById("messaggio-avviso");
    const avvisoLogin = document.getElementById("messaggio-avviso-login");

    if (successo) {
        successo.style.transition = "opacity 1s ease";
        successo.style.opacity = 0;
        setTimeout(() => {
            successo.style.display = "none";
        }, 1000);
    }

    if (avviso) {
        avviso.style.transition = "opacity 1s ease";
        avviso.style.opacity = 0;
        setTimeout(() => {
            avviso.style.display = "none";
        }, 1000);
    }

    if (avvisoLogin) {
        avvisoLogin.style.transition = "opacity 1s ease";
        avvisoLogin.style.opacity = 0;
        setTimeout(() => {
            avvisoLogin.style.display = "none";
        }, 1000);
    }
}, 5000);

</script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
