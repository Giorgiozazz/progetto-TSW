<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giorgio Zazzerini - Info</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <%@ include file="/jsp/common/header.jsp" %>
	<main>
    <div class="about-us">
    	<h2>Giorgio Zazzerini</h2>
    	
        <img src="<%= request.getContextPath() %>/img/gz.jpeg" alt="Giorgio Zazzerini" class="creatore-foto">
        
        <p><strong>Email:</strong> g.zazzerini@studenti.unisa.it</p>
        <p><strong>Telefono:</strong> +39 392 218 4541</p>

        <div class="social-icons">
            <p><strong>Seguimi su:</strong></p>
            <a href="https://www.facebook.com/giorgio.zazzerini.9?locale=it_IT" target="_blank">Facebook</a> |
            <a href="https://www.instagram.com/_giorgio.zazz_/" target="_blank">Instagram</a> |
            <a href="https://www.tiktok.com/@tdgjgfb" target="_blank">TikTok</a>
        </div>
    </div>
	</main>
    <%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
