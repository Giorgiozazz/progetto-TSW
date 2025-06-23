<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Francesco Zambrino - Info</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <%@ include file="/jsp/common/header.jsp" %>
	<main>
    <div class="about-us">
    	<h2>Francesco Zambrino</h2>
    	
        <img src="<%= request.getContextPath() %>/img/fz.jpeg" alt="Francesco Zambrino" class="creatore-foto">
        
        <p><strong>Email:</strong> f.zambrino@studenti.unisa.it</p>
        <p><strong>Telefono:</strong> +39 348 318 6111</p>

        <div class="social-icons">
            <p><strong>Seguimi su:</strong></p>
            <a href="https://www.facebook.com/francesco.zambrino.junior" target="_blank">Facebook</a> |
            <a href="https://www.instagram.com/_francesco_z/" target="_blank">Instagram</a> |
            <a href="https://www.tiktok.com/@francesco_zambrino" target="_blank">TikTok</a>
        </div>
    </div>
	</main>
    <%@ include file="/jsp/common/footer.jsp" %>
    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
