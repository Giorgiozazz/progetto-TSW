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

</main>
<%@ include file="/jsp/common/footer.jsp" %>
<script>
  const contextPath = '<%= request.getContextPath() %>';
</script>
<script src="<%= request.getContextPath() %>/js/search.js"></script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>