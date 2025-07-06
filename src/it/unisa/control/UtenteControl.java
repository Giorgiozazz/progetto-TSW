package it.unisa.control;

import it.unisa.UtenteModel;
import it.unisa.UtenteModelDM;
import it.unisa.model.UtenteBean;
import it.unisa.utils.EmailUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class UtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final UtenteModel model = new UtenteModelDM();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //String action = request.getParameter("action");
        System.out.println("---- IN doPost ----");
        String action = request.getParameter("action");
        System.out.println("Azione ricevuta: " + action);
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

                model.doSave(utente); // (l'hashing è qui)

                response.sendRedirect(request.getContextPath() + "/jsp/utente/login.jsp");

            } catch (SQLException e) {
                request.setAttribute("error", "Errore durante la registrazione: " + e.getMessage());
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

                    // Redirect in base al ruolo
                    String destinazione = utente.isAdmin() ? "/home" : "/home";
                    response.sendRedirect(request.getContextPath() + destinazione);
                } else {
                    request.setAttribute("error", "Email o password errati.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/common/error.jsp");
                    dispatcher.forward(request, response);
                }

            } catch (SQLException e) {
                request.setAttribute("error", "Errore SQL durante il login: " + e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/common/error.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", "Errore durante il login: " + e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/common/error.jsp");
                dispatcher.forward(request, response);
            }
        } else if ("richiediResetPassword".equalsIgnoreCase(action)) {
            String email = request.getParameter("emailReset");
            System.out.println("Email inserita: " + email);

            try {
                UtenteBean utente = model.doRetrieveByEmail(email);

                if (utente != null) {
                    String token = java.util.UUID.randomUUID().toString();
                    model.saveResetToken(email, token);

                    String resetLink = request.getRequestURL().toString()
                            .replace(request.getServletPath(), "")
                            + "/jsp/utente/resetPassword.jsp?token=" + token;

                    
                    System.out.println("Invio mail a: " + email + " con token: " + token);
                    EmailUtils.sendResetPasswordEmail(email, token);

                    request.setAttribute("message", "Ti è stata inviata una mail per reimpostare la password.");
                    request.getRequestDispatcher("/jsp/utente/passwordDimenticata.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Email non registrata.");
                    request.getRequestDispatcher("/jsp/utente/passwordDimenticata.jsp").forward(request, response);
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Errore durante la richiesta di reset password: " + e.getMessage());
                request.getRequestDispatcher("/jsp/utente/passwordDimenticata.jsp").forward(request, response);
            }

        } else if ("resetPassword".equalsIgnoreCase(action)) {
            String token = request.getParameter("token");
            
            //controlla che la nuova password coincide con la ripetizione della stessa
            String nuovaPassword = request.getParameter("newPassword"); 
            String confermaPassword = request.getParameter("confirmPassword");

            if (nuovaPassword == null || !nuovaPassword.equals(confermaPassword)) {
                request.setAttribute("error", "Le password non coincidono.");
                request.getRequestDispatcher("/jsp/utente/resetPassword.jsp?token=" + token).forward(request, response);
                return;
            }

            try {
                UtenteBean utente = model.doRetrieveByResetToken(token);

                if (utente != null) {
                    model.updatePasswordByToken(token, nuovaPassword);

                    model.invalidateResetToken(token);

                    request.setAttribute("message", "Password aggiornata con successo. Effettua il login.");
                    request.getRequestDispatcher("/jsp/utente/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Token di reset non valido o scaduto.");
                    request.getRequestDispatcher("/jsp/utente/resetPassword.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Errore durante l'aggiornamento della password: " + e.getMessage());
                request.getRequestDispatcher("/jsp/utente/resetPassword.jsp").forward(request, response);
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Azione non valida.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Usa POST per accedere o registrarti.");
    }
}
