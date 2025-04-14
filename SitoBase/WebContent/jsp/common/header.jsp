<header style="background-color: #000080; color: white; padding: 15px;">
  <div style="display: flex; justify-content: space-between; align-items: center;">  
    <div style="display: flex; align-items: center; gap: 10px;">
      <a href="<%= request.getContextPath() %>/index.jsp" style="display: flex; align-items: center; text-decoration: none; color: white;">
        <img src="<%= request.getContextPath() %>/img/Logo.jpg" alt="Logo AutoStore" style="height: 40px; width: auto;">
        <span style="font-size: 1.5em; font-weight: bold; margin-left: 8px;">RivenditAuto</span>
      </a>
    </div>
    
    <nav>
      <ul style="display: flex; list-style: none; gap: 20px; margin: 0; padding: 0;">
        <li><a href="" style="color: white;">Home</a></li>
        <li><a href="<%= request.getContextPath() %>/index.jsp" style="color: white;">Catalogo</a></li>
        <li><a href="<%= request.getContextPath() %>/jsp/auto/inserisciauto.jsp" style="color: white;">Vendi</a></li>
        <li><a href="<%= request.getContextPath() %>/jsp/auto/carrello.jsp" style="color: white;">Carrello</a></li>
      </ul>
    </nav>
  </div>
</header>