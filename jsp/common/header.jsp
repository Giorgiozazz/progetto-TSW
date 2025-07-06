<%@ page import="it.unisa.model.UtenteBean" %>
<header id="main-header" style="background-color: #000080; color: white; padding: 15px;">
  <div style="display: flex; justify-content: space-between; align-items: center;">
    <div style="display: flex; align-items: center; gap: 10px;">
      <a href="<%= request.getContextPath() %>/home" style="display: flex; align-items: center; text-decoration: none; color: white;">
        <img id="logo-container" src="<%= request.getContextPath() %>/img/Logo.jpg" alt="Logo AutoStore" style="height: 40px; width: auto;">
        <span style="font-size: 1.5em; font-weight: bold; margin-left: 8px;">RivenditAuto</span>
      </a>
    </div>

    <!-- Barra di ricerca -->
    <div id="search-container" style="position: relative; width: 300px; margin: 0 20px;">
        <input type="text" id="search-bar" placeholder="Cerca auto..." autocomplete="off" style="width: 100%; padding: 6px 10px; border-radius: 4px; border: none;">
        <div id="suggestions" style="position: absolute; top: 100%; left: 0; right: 0; background: white; color: black; border: 1px solid #ccc; max-height: 150px; overflow-y: auto; display: none;"></div>
    </div>
    
    <!-- Bottone hamburger -->
    <button id="menu-toggle" aria-label="Toggle menu" style="color: white; background: transparent; border: none; font-size: 24px; cursor: pointer;">
      &#9776;
    </button>
    
    <nav>
      <ul id="menu" class="menu-list">
        <li><a href="<%= request.getContextPath() %>/home" style="color: white;">Home</a></li>
        <li><a href="<%= request.getContextPath() %>/product" style="color: white;">Catalogo</a></li>
        <li><a href="<%= request.getContextPath() %>/jsp/auto/inserisciauto.jsp" style="color: white;">Vendi</a></li>
        <li><a href="<%= request.getContextPath() %>/jsp/auto/carrello.jsp" style="color: white;">Carrello</a></li>
        
        <% 
          UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("utenteLoggato");
          if (utenteLoggato != null) { 
        %>
            <li><a href="<%= request.getContextPath() %>/jsp/utente/infoPersonali.jsp" style="color: white;">Profilo</a></li>
            <li><a href="<%= request.getContextPath() %>/logout" style="color: white;">Logout</a></li>
        <% 
          } else { 
        %>
            <li><a href="<%= request.getContextPath() %>/jsp/utente/login.jsp" style="color: white;">Login</a></li>
        <% 
          } 
        %>
      </ul>
    </nav>
  </div>
</header>
