package it.unisa.control;

import it.unisa.UtenteModelDM;
import it.unisa.model.UtenteBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class ModificaUtente extends HttpServlet {

    private UtenteModelDM utenteModelDM = new UtenteModelDM(); 

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ottiene l'utente loggato dalla sessione
        HttpSession session = request.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");

        if (utente == null) {
            // Se l'utente non è loggato, reindirizza al login
            response.sendRedirect(request.getContextPath() + "/jsp/utente/login.jsp");
            return;
        }

        // Recupera i parametri del form di modifica
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String indirizzo = request.getParameter("indirizzo");
        String numeroCivico = request.getParameter("numeroCivico");

        // Controllo base della validità dei dati
        if (email == null || email.isEmpty()) {
            request.setAttribute("error", "Email non valida.");
            request.getRequestDispatcher("/jsp/utente/infoPersonali.jsp").forward(request, response);
            return;
        }

        // Aggiorna i dati dell'utente
        utente.setEmail(email);
        utente.setTelefono(telefono);
        utente.setCitta(citta);
        utente.setProvincia(provincia);
        utente.setIndirizzo(indirizzo);
        utente.setNumeroCivico(numeroCivico);

        try {
            boolean success = utenteModelDM.doUpdate(utente);

            if (success) {
                session.setAttribute("utenteLoggato", utente);
                response.sendRedirect(request.getContextPath() + "/jsp/utente/infoPersonali.jsp");
            } else {
                request.setAttribute("error", "Errore nell'aggiornamento del profilo.");
                request.getRequestDispatcher("/jsp/utente/infoPersonali.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore nel database. Riprova più tardi.");
            request.getRequestDispatcher("/jsp/utente/infoPersonali.jsp").forward(request, response);
        }
    }
}
