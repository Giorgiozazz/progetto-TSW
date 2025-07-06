package it.unisa.control;

import it.unisa.ProductModelDM;
import it.unisa.model.ProductBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UpdateCart extends HttpServlet {

    private ProductModelDM productModel = new ProductModelDM();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vin = request.getParameter("vin");
        String modello = request.getParameter("modello");
        String marca = request.getParameter("marca");

        if (vin == null || vin.isEmpty()) {
            request.setAttribute("errorMessage", "Il prodotto non è valido.");
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            return;
        }

        // Recupera il carrello dalla sessione
        HttpSession session = request.getSession();
        List<ProductBean> carrello = (List<ProductBean>) session.getAttribute("carrello");

        if (carrello != null) {
            // Se la quantità è zero, rimuovi il prodotto dal carrello
            if (request.getParameter("quantita") == null || request.getParameter("quantita").equals("0")) {
                carrello.removeIf(p -> p.getVin().equals(vin));
                session.setAttribute("carrello", carrello);
            }

            response.sendRedirect("carrello.jsp");
        } else {
            // Se il carrello è vuoto
            request.setAttribute("errorMessage", "Il carrello è vuoto.");
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
}
