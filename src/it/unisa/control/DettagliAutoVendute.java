package it.unisa.control;

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
        String idOrdine = request.getParameter("id"); 

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
                request.setAttribute("id", idOrdine); 
            }

            request.getRequestDispatcher("/jsp/auto/dettagliVendute.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
