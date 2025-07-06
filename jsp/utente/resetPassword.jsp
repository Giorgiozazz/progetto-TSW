<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<br><br><br><br>
<main>
    <h2>Reimposta la tua password</h2>
    <form action="<%= request.getContextPath() %>/UtenteControl" method="post">
        <input type="hidden" name="action" value="resetPassword">
        <input type="hidden" name="token" value="<%= request.getParameter("token") %>">

        <label for="newPassword">Nuova Password:</label><br>
        <div class="password-wrapper">
          <input type="password" id="newPassword" name="newPassword" required>
          <span class="toggle-password" onclick="togglePasswords()">🙈</span>
        </div>
        <br><br>

        <label for="confirmPassword">Conferma Password:</label><br>
        <div class="password-wrapper">
          <input type="password" id="confirmPassword" name="confirmPassword" required>
          <span class="toggle-password" onclick="togglePasswords()">🙈</span>
        </div>
        <br><br>

        <input type="submit" value="Resetta Password">
    </form>
</main>
    <%@ include file="/jsp/common/footer.jsp" %>
    <script>
	  function togglePasswords() {
	    const newPasswordInput = document.getElementById('newPassword');
	    const confirmPasswordInput = document.getElementById('confirmPassword');
	    const toggleIcons = document.querySelectorAll('.toggle-password');
	
	    if (newPasswordInput.type === 'password') {
	      newPasswordInput.type = 'text';
	      confirmPasswordInput.type = 'text';
	      toggleIcons.forEach(icon => icon.textContent = '👁️');
	    } else {
	      newPasswordInput.type = 'password';
	      confirmPasswordInput.type = 'password';
	      toggleIcons.forEach(icon => icon.textContent = '🙈');
	    }
	  }
	
	  window.onload = function() {
	    const newPasswordInput = document.getElementById('newPassword');
	    const toggleIcons = document.querySelectorAll('.toggle-password');
	    if (newPasswordInput.type === 'password') {
	      toggleIcons.forEach(icon => icon.textContent = '🙈');
	    } else {
	      toggleIcons.forEach(icon => icon.textContent = '👁️');
	    }
	  };
	</script>
    <script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>
