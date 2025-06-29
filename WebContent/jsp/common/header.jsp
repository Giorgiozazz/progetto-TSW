<%@ page import="it.unisa.model.UtenteBean" %>

<header id="main-header" style="background-color: #000080; color: white; padding: 15px;">
  <div style="display: flex; justify-content: space-between; align-items: center;">
    <div style="display: flex; align-items: center; gap: 10px;">
      <a href="<%= request.getContextPath() %>/home" style="display: flex; align-items: center; text-decoration: none; color: white;">
        <img src="<%= request.getContextPath() %>/img/Logo.jpg" alt="Logo AutoStore" style="height: 40px; width: auto;">
        <span style="font-size: 1.5em; font-weight: bold; margin-left: 8px;">RivenditAuto</span>
      </a>
    </div>
    
    <!-- Bottone hamburger -->
    <button id="menu-toggle" aria-label="Toggle menu">
      &#9776;
    </button>
    
    <nav>
      <ul id="menu" class="menu-list">
        <li><a href="<%= request.getContextPath() %>/home" style="color: white;">Home</a></li>
        <li><a href="<%= request.getContextPath() %>/product" style="color: white;">Catalogo</a></li>
        <li><a href="<%= request.getContextPath() %>/jsp/auto/inserisciauto.jsp" style="color: white;">Vendi</a></li>
        <li><a href="<%= request.getContextPath() %>/jsp/auto/carrello.jsp" style="color: white;">Carrello</a></li>
        
        <% 
          // Verifica se l'utente è loggato
          UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("utenteLoggato");
          if (utenteLoggato != null) { // Se l'utente è loggato
        %>
            <!-- Link per visualizzare le informazioni personali -->
            <li><a href="<%= request.getContextPath() %>/jsp/utente/infoPersonali.jsp" style="color: white;">Le tue informazioni</a></li>
            <li><a href="<%= request.getContextPath() %>/logout" style="color: white;">Logout</a></li>
        <% 
          } else { // Se l'utente non è loggato
        %>
            <!-- Link per il login -->
            <li><a href="<%= request.getContextPath() %>/jsp/utente/login.jsp" style="color: white;">Login</a></li>
        <% 
          } 
        %>
      </ul>
    </nav>
  </div>
</header>
