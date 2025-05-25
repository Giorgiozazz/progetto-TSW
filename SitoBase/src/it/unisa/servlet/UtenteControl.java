package it.unisa.servlet;

import it.unisa.UtenteModel;
import it.unisa.UtenteModelDM;
import it.unisa.model.UtenteBean;
import it.unisa.utils.PasswordUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;

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

                // Hash della password
                String hashedPassword = PasswordUtils.toHash(password);
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
                utente.setPassword(hashedPassword); // Salva l'hash della password

                model.doSave(utente); // Salva l'utente nel database
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            } catch (SQLException | NoSuchAlgorithmException e) {
                request.setAttribute("error", "Errore durante la registrazione: " + e.getMessage());
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("login".equalsIgnoreCase(action)) {
            try {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                // Stampa dell'email per il debug (per il login non stampare la password in chiaro!)
                System.out.println("Email: " + email);

                // Hash della password inserita (come per la registrazione)
                String hashedPassword = PasswordUtils.toHash(password);

                // Recupera l'utente dal database usando l'email
                UtenteBean utente = model.doRetrieveByEmailPassword(email, hashedPassword);

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
                    request.setAttribute("error", "Email o password errati.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/common/error.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (SQLException e) {
                request.setAttribute("error", "Errore SQL durante il login: " + e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/common/error.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {  // Gestione generica per altri tipi di eccezione
                request.setAttribute("error", "Errore durante il login: " + e.getMessage());
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
