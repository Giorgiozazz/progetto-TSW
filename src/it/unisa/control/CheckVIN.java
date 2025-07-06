package it.unisa.control;

import it.unisa.DriverManagerConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CheckVIN extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vin = request.getParameter("vin");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (vin == null || vin.trim().length() != 17) {
            response.getWriter().write("not_exists");
            return;
        }

        vin = vin.toUpperCase();

        boolean exists = false;

        try (Connection conn = DriverManagerConnectionPool.getConnection()) {
            String query = "SELECT COUNT(*) FROM automobili WHERE VIN = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, vin);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        exists = rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("not_exists");
            return;
        }

        if (exists) {
            response.getWriter().write("exists");
        } else {
            response.getWriter().write("not_exists");
        }
    }
}
