<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%= request.getContextPath() %>/css/ProductStyle.css" rel="stylesheet" type="text/css">
    <title>About Us</title>
</head>
<body>
    <%@ include file="/jsp/common/header.jsp" %>
	<main>
    <div class="about-us">
        <h2>Chi Siamo</h2>

        <p class="paragrafo-giustificato">RivenditAuto è una piattaforma che offre una selezione di auto nuove e usate di alta qualità, 
        in modo semplice e sicuro. Siamo un team di appassionati di automobili e il nostro obiettivo è mettere a disposizione dei nostri 
        clienti le migliori offerte di auto a prezzi competitivi.</p>

        <h3>• Il Nostro Team</h3>
        <p class="paragrafo-giustificato">Il nostro team è formato da esperti nel settore automotive e da appassionati di tecnologia. 
        Lavoriamo ogni giorno per offrirvi un'esperienza d'acquisto unica, affidabile e piacevole.</p>

        <h3>• Missione</h3>
        <p class="paragrafo-giustificato">La nostra missione è quella di rendere facile per chiunque trovare l'auto dei propri sogni, 
        mettendo a disposizione un'esperienza d'acquisto semplice, sicura e trasparente. Siamo convinti che acquistare un'auto debba essere 
        un'esperienza positiva e senza stress.</p>

        <h3>• I Nostri Fondatori</h3>
        <p class="paragrafo-giustificato"><b>Francesco Zambrino</b> e <b>Giorgio Zazzerini</b> sono due studenti del Dipartimento di Informatica dell’Università degli Studi di Salerno. 
        La realizzazione di questo sito nasce dalla loro passione per il web development e si inserisce come progetto finale del corso Tecnologie 
        Software per il Web (Resto 1), tenuto dalla professoressa Rita Francese.
        Entrambi vantano già esperienze pregresse nella creazione di siti web maturate durante il loro percorso scolastico alle superiori. 
        Queste basi, unite all'entusiasmo per l’innovazione digitale, li hanno spinti ad approfondire le proprie competenze e a sviluppare una 
        piattaforma funzionale, curata nei dettagli e orientata all’utente.</p>

        <h3>• Contatti</h3>
        <p>Se hai domande o desideri maggiori informazioni, non esitare a contattarci :) .</p>
    </div>
	</main>
    <%@ include file="/jsp/common/footer.jsp" %>
</body>
</html>
