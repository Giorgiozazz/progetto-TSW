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



public class CheckTarga extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targa = request.getParameter("targa");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (targa == null || !targa.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$")) {
            response.getWriter().write("not_exists");  // formato errato o mancante
            return;
        }

        targa = targa.toUpperCase();
        boolean exists = false;

        try (Connection conn = DriverManagerConnectionPool.getConnection()) {
            String query = "SELECT COUNT(*) FROM automobili WHERE TARGA = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, targa);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        exists = rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("error");
            return;
        }

        response.getWriter().write(exists ? "exists" : "not_exists");
    }
}
