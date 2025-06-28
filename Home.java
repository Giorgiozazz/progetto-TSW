package it.unisa.servlet;

import it.unisa.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.util.List;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import it.unisa.model.ProductBean;


public class Home extends HttpServlet {

    private ProductModel productModel = new ProductModelDM();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<ProductBean> topCars = productModel.getTop6Cars();
            request.setAttribute("autoList", topCars);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore nel recupero delle auto");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
