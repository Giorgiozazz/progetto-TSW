package it.unisa.control;

import it.unisa.AutomobiliVenduteModelDM;
import it.unisa.DriverManagerConnectionPool;
import it.unisa.OrdineModelDM;
import it.unisa.PagamentoModelDM;
import it.unisa.ProductModelDM;
import it.unisa.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collection;
import java.sql.Connection;

@WebServlet("/ConfermaOrdine")
public class ConfermaOrdineServlet extends HttpServlet {

    private OrdineModelDM ordineModel = new OrdineModelDM();
    private PagamentoModelDM pagamentoModel = new PagamentoModelDM();
    private ProductModelDM productModel = new ProductModelDM();
    private AutomobiliVenduteModelDM automobiliVenduteModel = new AutomobiliVenduteModelDM();

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
        String citta = request.getParameter("paese");
        String provincia = request.getParameter("provincia");

        double totale = 0.0;
        for (ProductBean prodotto : carrello) {
            totale += prodotto.getPrezzo();
        }

        int numRate = 0;
        try {
            numRate = Integer.parseInt(request.getParameter("num_rate"));
        } catch (NumberFormatException e) {
            numRate = 0; // default pagamento completo
        }

        double anticipo = 0.0;
        if (numRate == 0) {
            anticipo = totale;
        } else {
            try {
                String anticipoParam = request.getParameter("anticipo");
                if (anticipoParam != null && !anticipoParam.isEmpty()) {
                    anticipo = Double.parseDouble(anticipoParam);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Anticipo non valido.");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
                return;
            }
            double maxAnticipo = totale * 0.5;  // massimo 50% del totale

            if (anticipo > maxAnticipo) {
                request.setAttribute("error", "L'anticipo non può superare il 50% del totale dell'ordine (" + maxAnticipo + " €).");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
                return;
            }
        }

        double restante = totale - anticipo;

        double iva = 0.0;
        if (!carrello.isEmpty()) {
            iva = carrello.iterator().next().getIva();
        }

        UtenteBean utente = (UtenteBean) session.getAttribute("utenteLoggato");
        if (utente == null) {
            session.setAttribute("loginMessage", "⚠�? Devi effettuare il login per finalizzare l'ordine.");
            response.sendRedirect(request.getContextPath() + "/jsp/utente/login.jsp?loginRequired=true");
            return;
        }

        String cf = utente.getCf();

        Connection connection = null;

        try {
            connection = DriverManagerConnectionPool.getConnection();
            connection.setAutoCommit(false);

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

            ordineModel.doSave(ordine, connection);

            PagamentoBean pagamentoBean = new PagamentoBean();
            pagamentoBean.setIdOrdine(ordine.getIdOrdine());
            pagamentoBean.setMetodoPagamento(pagamento);
            pagamentoBean.setAnticipo(anticipo);
            pagamentoBean.setDataPagamento(new java.util.Date());
            pagamentoBean.setNumRate(numRate);
            pagamentoBean.setIva(iva);

            if (numRate > 0) {
                pagamentoBean.setImportoRata(restante / numRate);
            } else {
                pagamentoBean.setImportoRata(0);
            }

            pagamentoModel.doSave(pagamentoBean, connection);

            for (ProductBean prodotto : carrello) {
                prodotto.setVenduta(true);
                prodotto.setIdOrdine(ordine.getIdOrdine());

                // aggiorno lo stato nella tabella originale
                productModel.doUpdate(prodotto, connection);

                // salvataggio dell’auto venduta nella tabella apposita
                automobiliVenduteModel.doSave(prodotto, connection);
            }

            connection.commit();

            session.removeAttribute("carrello");

            request.setAttribute("messaggio", "Ordine completato con successo!");
            request.getRequestDispatcher("/jsp/auto/ConfermaOrdine.jsp").forward(request, response);

        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            request.setAttribute("error", "Errore durante il salvataggio dell'ordine: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    DriverManagerConnectionPool.releaseConnection(connection);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
