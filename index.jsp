<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*,it.unisa.model.ProductBean,it.unisa.model.UtenteBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>RivenditAuto</title>
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<main>
<div id="search-container" style="position: relative; width: 300px; margin: 20px auto;">
    <input type="text" id="search-bar" placeholder="Cerca auto..." autocomplete="off">
    <div id="suggestions"></div>
</div>
<div id="banner-image">
    <img src="<%= request.getContextPath() %>/img/Banner1.jpg" alt="Banner promozionale">
</div>
<div class="auto-grid-container">
    <%
        List<ProductBean> autoList = (List<ProductBean>) request.getAttribute("autoList");
        if (autoList != null && !autoList.isEmpty()) {
            for (ProductBean auto : autoList) {
    %>
        <div class="auto-card">
            <img src="<%= request.getContextPath() + "/" + auto.getImmagine() %>" alt="Immagine <%= auto.getModello() %>" />
            <h3>
                <a href="<%= request.getContextPath() %>/product?action=read&vin=<%= auto.getVin() %>">
                    <%= auto.getMarca() %> <%= auto.getModello() %>
                </a>
            </h3>
            <p>Prezzo: € <%= String.format("%.2f", auto.getPrezzo()) %></p>
        </div>
    <%
            }
        } else {
    %>
        <p>Nessuna auto disponibile al momento.</p>
    <%
        }
    %>
</div>

</main>
<%@ include file="/jsp/common/footer.jsp" %>
<script>
  const contextPath = '<%= request.getContextPath() %>';
</script>
<script src="<%= request.getContextPath() %>/js/search.js"></script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>