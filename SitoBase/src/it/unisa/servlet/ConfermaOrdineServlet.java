package it.unisa.servlet;

import it.unisa.OrdineModelDM;
import it.unisa.PagamentoModelDM;
import it.unisa.ProductModelDM;
import it.unisa.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collection;
import java.sql.Date;

@WebServlet("/ConfermaOrdine")
public class ConfermaOrdineServlet extends HttpServlet {

    private OrdineModelDM ordineModel = new OrdineModelDM();
    private PagamentoModelDM pagamentoModel = new PagamentoModelDM();
    private ProductModelDM productModel = new ProductModelDM();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Collection<ProductBean> carrello = (Collection<ProductBean>) session.getAttribute("carrello");

        if (carrello == null || carrello.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?empty=true");
            return;
        }

        String tipoConsegna = request.getParameter("tipo_consegna");
        String pagamento = request.getParameter("pagamento");

        String indirizzo = request.getParameter("indirizzo");
        String numeroCivico = request.getParameter("numero_civico");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");

        double anticipo = 0.0;
        try {
            String anticipoParam = request.getParameter("anticipo");
            if (anticipoParam != null && !anticipoParam.isEmpty()) {
                anticipo = Double.parseDouble(anticipoParam);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errore", "Anticipo non valido.");
            request.getRequestDispatcher("/jsp/auto/checkout.jsp").forward(request, response);
            return;
        }

        double totale = 0.0;
        for (ProductBean prodotto : carrello) {
            totale += prodotto.getPrezzo();
        }

        if (anticipo > totale) {
            request.setAttribute("errore", "L'anticipo non può superare il totale dell'ordine (" + totale + " €).");
            request.getRequestDispatcher("/jsp/auto/checkout.jsp").forward(request, response);
            return;
        }

        double restante = totale - anticipo;

        // Recupera CF utente da sessione (assumi che ci sia)
        UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/utente/login.jsp");
            return;
        }
        String cf = utente.getCf();
        
        try {
            // Creo e salvo ordine
            OrdineBean ordine = new OrdineBean();
            ordine.setCf(cf);
            ordine.setDataOrdine(new java.util.Date());
            if (anticipo >= totale) {
                ordine.setStatoOrdine("Completato");
            } else {
                ordine.setStatoOrdine("In attesa");
            }
            ordine.setTotale(totale);
            ordine.setTipoConsegna(tipoConsegna);
            
            if ("spedizione".equalsIgnoreCase(tipoConsegna)) {
                ordine.setIndirizzoSpedizione(indirizzo);
                ordine.setNumeroCivicoSpedizione(numeroCivico);
                ordine.setCittaSpedizione(citta);
                ordine.setProvinciaSpedizione(provincia);
            }

            ordineModel.doSave(ordine);

         // Creo e salvo pagamento
            PagamentoBean pagamentoBean = new PagamentoBean();
            pagamentoBean.setIdOrdine(ordine.getIdOrdine());
            pagamentoBean.setMetodoPagamento(pagamento);
            pagamentoBean.setAnticipo(anticipo);
            pagamentoBean.setDataPagamento(new java.util.Date()); // ✅ AGGIUNTA QUESTA RIGA

            int numRate = Integer.parseInt(request.getParameter("num_rate"));
            pagamentoBean.setNumRate(numRate);

            if (numRate > 0) {
                pagamentoBean.setImportoRata(restante / numRate);
            } else {
                pagamentoBean.setImportoRata(0);
            }

            pagamentoModel.doSave(pagamentoBean);

            // *** Qui aggiorno lo stato dei prodotti a VENDUTA ***
            for (ProductBean prodotto : carrello) {
                prodotto.setVenduta(true);                           // Imposta venduta a true
                prodotto.setIdOrdine(ordine.getIdOrdine());          // Associa l'ID ordine
                productModel.doUpdate(prodotto);                     // Aggiorna il prodotto nel DB
            }

            
            // Svuoto carrello
            session.removeAttribute("carrello");

            request.setAttribute("messaggio", "✅ Ordine completato con successo!");
            request.getRequestDispatcher("/jsp/auto/ConfermaOrdine.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore durante il salvataggio dell'ordine: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
}