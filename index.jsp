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
<header id="main-header" class="hidden">
  <%@ include file="/jsp/common/header.jsp" %>
</header>

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

<div id="search-container">
    <input type="text" id="search-bar" placeholder="Cerca auto..." autocomplete="off">
    <div id="suggestions"></div>
</div>
<h1 style="text-align: center;">ECCO ALCUNE NOSTRE SCELTE</h1>
<div class="auto-grid-container">
    <%
        List<ProductBean> autoList = (List<ProductBean>) request.getAttribute("autoList");
        if (autoList != null && !autoList.isEmpty()) {
            for (ProductBean auto : autoList) {
    %>
       <div class="auto-card">
		    <a href="<%= request.getContextPath() %>/product?action=read&vin=<%= auto.getVin() %>&from=home">
		        <img class="auto-image" src="<%= request.getContextPath() + "/" + auto.getImmagine() %>" alt="Immagine <%= auto.getModello() %>" />
		    </a>
		    <h3>
		        <a href="<%= request.getContextPath() %>/product?action=read&vin=<%= auto.getVin() %>&from=home">
		            <%= auto.getMarca() %> <%= auto.getModello() %>
		        </a>
		    </h3>
		    <p>Prezzo: € <%= new java.text.DecimalFormat("#,###").format(auto.getPrezzo()) %></p>
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
<div class="about-us">
        <h2>PERCHÈ SCEGLIERCI?</h2>

        <p class="paragrafo-giustificato">Mettiamo la passione per le auto al centro di tutto ciò che facciamo. 
        Che tu sia un appassionato di motori, un professionista del settore o semplicemente in cerca del meglio per il tuo veicolo,
        ecco cosa ci distingue:</p>

        <h3>• Spedizione veloce e tracciata su tutto il catalogo, per non lasciarti mai in attesa.</h3>

        <h3>• Assistenza specializzata per guidarti nella scelta del prodotto più adatto al tuo veicolo.</h3>
        
        <h3>• Ricambi e accessori di alta qualità, selezionati tra i migliori marchi sul mercato.</h3>

        <h3>• Pagamenti sicuri e massima protezione dei tuoi dati.</h3>
        
        <h3>• Resi semplici e rapidi, perché può capitare di cambiare idea.</h3>

        <h3>• Fiducia di migliaia di automobilisti, che ci scelgono ogni giorno per la cura della loro auto.</h3>
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
  function scrollDown() {
    // scrolla il 70% dell’altezza della finestra
    const offset = window.innerHeight * 0.92;
    window.scrollBy({ top: offset, behavior: 'smooth' });
  }

  document.getElementById('scroll-left-btn')
          .addEventListener('click', scrollDown);

  document.getElementById('scroll-right-btn')
          .addEventListener('click', scrollDown);
</script>
<script>
window.onload = () => {
	  const header = document.getElementById("main-header");
	  // Nascondi inizialmente
	  header.classList.add("hidden");
	  // Mostra l'header appena scrolli anche 1px
	  window.addEventListener("scroll", () => {
	    console.log("ScrollY:", window.scrollY);
	    if (window.scrollY > 50) {
	      header.classList.remove("hidden");
	    } else {
	      header.classList.add("hidden");
	    }
	  });
	};
</script>

<script>
  const contextPath = '<%= request.getContextPath() %>';
</script>
<script src="<%= request.getContextPath() %>/js/search.js"></script>
<script src="<%= request.getContextPath() %>/js/script.js"></script>
</body>
</html>