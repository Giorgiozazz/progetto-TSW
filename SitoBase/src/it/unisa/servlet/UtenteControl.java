package it.unisa.servlet;

import it.unisa.UtenteModel;
import it.unisa.UtenteModelDM;
import it.unisa.model.UtenteBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class UtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static UtenteModel model = new UtenteModelDM();

    public UtenteControl() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("registrazione".equalsIgnoreCase(action)) {
            try {
                String cf = request.getParameter("cf");
                String nome = request.getParameter("nome");
                String cognome = request.getParameter("cognome");
                String email = request.getParameter("email");
                String telefono = request.getParameter("telefono");
                String citta = request.getParameter("citta");
                String provincia = request.getParameter("provincia");
                String indirizzo = request.getParameter("indirizzo");
                String numeroCivico = request.getParameter("numero_civico");
                String password = request.getParameter("password");

                UtenteBean utente = new UtenteBean();
                utente.setCf(cf);
                utente.setNome(nome);
                utente.setCognome(cognome);
                utente.setEmail(email);
                utente.setTelefono(telefono);
                utente.setCitta(citta);
                utente.setProvincia(provincia);
                utente.setIndirizzo(indirizzo);
                utente.setNumeroCivico(numeroCivico);
                utente.setPassword(password);

                model.doSave(utente);
                response.sendRedirect(request.getContextPath() + "/jsp/utente/index.jsp");

            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Errore SQL durante la registrazione: " + e.getMessage());
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
                dispatcher.forward(request, response);
            }

        } else if ("login".equalsIgnoreCase(action)) {
            try {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                UtenteBean utente = model.doRetrieveByEmailPassword(email, password);

                if (utente != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("utenteLoggato", utente);

                    if (utente.isAdmin()) {
                        System.out.println("Admin loggato, reindirizzo a: " + request.getContextPath() + "/product?action=admin");
                        response.sendRedirect(request.getContextPath() + "/product?action=admin");
                    } else {
                        System.out.println("Utente normale, reindirizzo a: " + request.getContextPath() + "/product");
                        response.sendRedirect(request.getContextPath() + "/product");
                    }
                } else {
                    request.setAttribute("errorMessage", "Email o password errati.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/common/error.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Errore SQL durante il login: " + e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/common/error.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Usa POST per accedere o registrarti.");
    }
}
