package it.unisa.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import it.unisa.model.ProductBean;

import java.io.IOException;
import java.util.*;

public class AddToCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vin = request.getParameter("vin");

        // Verifica se il VIN Ë valido
        if (vin == null || vin.trim().isEmpty()) {
            response.sendRedirect("catalogo.jsp?error=VIN invalido");
            return;
        }

        // Recupera la lista dei prodotti dal contesto
        List<ProductBean> listaProdotti = (List<ProductBean>) getServletContext().getAttribute("productList");

        if (listaProdotti == null || listaProdotti.isEmpty()) {
            response.sendRedirect("product?error=no-products");
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

        if (prodotto != null) {
            HttpSession session = request.getSession();
            List<ProductBean> carrello = (List<ProductBean>) session.getAttribute("carrello");

            if (carrello == null) {
                carrello = new ArrayList<>();
            }

            boolean gi‡NelCarrello = false;
            for (ProductBean item : carrello) {
                if (item.getVin().equals(prodotto.getVin())) {
                    gi‡NelCarrello = true;
                    break;
                }
            }

            if (!gi‡NelCarrello) {
                carrello.add(prodotto);
                session.setAttribute("carrello", carrello);
                response.sendRedirect(request.getContextPath() + "/jsp/auto/carrello.jsp?added=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/jsp/auto/carrello.jsp?already=true");
            }

        } else {
            response.sendRedirect("catalogo.jsp?error=prodotto-non-trovato");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
