package it.unisa.servlet;

import it.unisa.bean.ProductBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class AddToCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vin = request.getParameter("vin");

        // Verifica se il VIN è valido
        if (vin == null || vin.trim().isEmpty()) {
            response.sendRedirect("catalogo.jsp?error=VIN invalido"); // Aggiungi un messaggio di errore
            return;
        }

        // Recupera la lista dei prodotti dal contesto
        List<ProductBean> listaProdotti = (List<ProductBean>) getServletContext().getAttribute("productList");

        if (listaProdotti == null || listaProdotti.isEmpty()) {
            response.sendRedirect("product?error=no-products"); // Informa l'utente che non ci sono prodotti
            return;
        }

        // Cerca il prodotto dal VIN
        ProductBean prodotto = null;
        for (ProductBean p : listaProdotti) {
            if (p.getVin().equals(vin)) {
                prodotto = p;
                break;
            }
        }

        // Se il prodotto è trovato, aggiungilo al carrello
        if (prodotto != null) {
            HttpSession session = request.getSession();
            List<ProductBean> carrello = (List<ProductBean>) session.getAttribute("carrello");

            // Se il carrello non esiste, crealo
            if (carrello == null) {
                carrello = new ArrayList<>();
            }

            carrello.add(prodotto);
            session.setAttribute("carrello", carrello);
            response.sendRedirect(request.getContextPath() + "/jsp/auto/carrello.jsp"); // Redirige l'utente al carrello dopo aver aggiunto il prodotto
        } else {
            response.sendRedirect("catalogo.jsp?error=prodotto-non-trovato"); // Messaggio nel caso il prodotto non venga trovato
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // Gestisce anche la richiesta GET come una POST
    }
}
