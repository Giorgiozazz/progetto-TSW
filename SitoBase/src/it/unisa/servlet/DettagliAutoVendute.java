package it.unisa.servlet;

import it.unisa.AutomobiliVenduteModelDM;
import it.unisa.model.ProductBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class DettagliAutoVendute extends HttpServlet {

    private AutomobiliVenduteModelDM model;

    @Override
    public void init() {
        model = new AutomobiliVenduteModelDM();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vin = request.getParameter("vin");
        String idOrdine = request.getParameter("id");  // <-- aggiunto

        if (vin == null || vin.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {
            ProductBean product = model.doRetrieveByVin(vin);

            if (product == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            request.setAttribute("product", product);

            if (idOrdine != null && !idOrdine.isEmpty()) {
                request.setAttribute("id", idOrdine);  // passo l'id ordine alla JSP
            }

            request.getRequestDispatcher("/jsp/auto/dettagliVendute.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }


    // Se vuoi gestire anche POST, reindirizza a GET
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
