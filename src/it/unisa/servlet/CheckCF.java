package it.unisa.servlet;

import it.unisa.DriverManagerConnectionPool;
import it.unisa.UtenteModel;
import it.unisa.UtenteModelDM;
import it.unisa.model.UtenteBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckCF extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cf = request.getParameter("cf");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (cf == null || cf.trim().length() != 16) {
            response.getWriter().write("not_exists");  // formato errato o mancante, considera non esistente
            return;
        }

        cf = cf.toUpperCase();

        boolean exists = false;

        try (Connection conn = DriverManagerConnectionPool.getConnection()) {
            String query = "SELECT COUNT(*) FROM Utente WHERE CF = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, cf);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        exists = rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In caso di errore, puoi rispondere come preferisci
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

