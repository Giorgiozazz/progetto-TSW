<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*,it.unisa.model.ProductBean,it.unisa.model.UtenteBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>RivenditAuto</title>
    <link rel="icon" href="<%= request.getContextPath() %>/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<%@ include file="/jsp/common/header.jsp" %>
<main>
<div class="slider-container">
    <div class="slider">
      <img src="<%= request.getContextPath() %>/img/slide1.jpg" alt="Immagine 1" class="slide active">
      <img src="<%= request.getContextPath() %>/img/slide3.jpg" alt="Immagine 2" class="slide">
      <img src="<%= request.getContextPath() %>/img/slide2.jpg" alt="Immagine 3" class="slide">
    </div>
    <button class="prev">❮</button>
    <button class="next">❯</button>
    <button id="scroll-left-btn" aria-label="Scorri in basso">
	  <img src="<%= request.getContextPath() %>/img/freccia-giu.gif" alt="Scorri a sinistra" style="width: 40px; height: auto;">
	</button>
	
	<button id="scroll-right-btn" aria-label="Scorri in basso">
	  <img src="<%= request.getContextPath() %>/img/freccia-giu.gif" alt="Scorri a destra" style="width: 40px; height: auto;">
	</button>

  </div>

<div id="search-container" style="position: relative; width: 300px; margin: 20px auto 40px auto;">
    <input type="text" id="search-bar" placeholder="Cerca auto..." autocomplete="off">
    <div id="suggestions"></div>
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
const slides = document.querySelectorAll(".slide");
const prevBtn = document.querySelector(".prev");
const nextBtn = document.querySelector(".next");

let currentIndex = 0;
function showSlide(index) {
  slides.forEach((slide) => slide.classList.remove("active"));
  slides[index].classList.add("active");
}

showSlide(currentIndex);
prevBtn.addEventListener("click", () => {
  currentIndex = (currentIndex - 1 + slides.length) % slides.length;
  showSlide(currentIndex);
});

nextBtn.addEventListener("click", () => {
  currentIndex = (currentIndex + 1) % slides.length;
  showSlide(currentIndex);
});
setInterval(() => {
  currentIndex = (currentIndex + 1) % slides.length;
  showSlide(currentIndex);
}, 5000);
</script>
<script>
function scrollToNextSection() {
	  // Cambia 'content' con l'id della sezione dove vuoi scrollare
	  const nextSection = document.getElementById('search-container'); 
	  if (nextSection) {
	    nextSection.scrollIntoView({ behavior: 'smooth' });
	  }
	}

	document.getElementById('scroll-left-btn')
	        .addEventListener('click', scrollToNextSection);

	document.getElementById('scroll-right-btn')
	        .addEventListener('click', scrollToNextSection);

</script>

<script>
  const contextPath = '<%= request.getContextPath() %>';
</script>
<script src="<%= request.getContextPath() %>/js/search.js"></script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>