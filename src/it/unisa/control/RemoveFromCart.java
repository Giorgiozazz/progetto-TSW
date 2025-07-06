package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.unisa.ProductModel;
import it.unisa.ProductModelDM;
import it.unisa.ProductModelDS;
import it.unisa.model.ProductBean;

public class RemoveFromCart extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<ProductBean> carrello = (List<ProductBean>) session.getAttribute("carrello");

        if (carrello != null) {
            String vin = request.getParameter("vin");
            carrello.removeIf(p -> p.getVin().equals(vin));
        }

        response.sendRedirect(request.getContextPath() + "/jsp/auto/carrello.jsp");
    }
}
