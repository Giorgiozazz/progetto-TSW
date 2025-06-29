package it.unisa.servlet;

import it.unisa.UtenteModel;
import it.unisa.UtenteModelDM;
import it.unisa.model.UtenteBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class CheckEmail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static UtenteModel model = new UtenteModelDM();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        boolean emailEsiste = false;

        if (email != null && !email.trim().isEmpty()) {
            try {
                UtenteBean utente = model.doRetrieveByEmail(email);
                emailEsiste = (utente != null);
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("error");
                return;
            }
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(emailEsiste ? "exists" : "available");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Evita l'uso via GET
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Usa POST per questa richiesta.");
    }
}
