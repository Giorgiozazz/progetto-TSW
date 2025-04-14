package it.unisa;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static boolean isDataSource = true;
    static ProductModel model;

    static {
        if (isDataSource) {
            model = new ProductModelDS();
        } else {
            model = new ProductModelDM();
        }
    }

    public ProductControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("read")) {
                    String vin = request.getParameter("vin");
                    request.removeAttribute("product");
                    request.setAttribute("product", model.doRetrieveByKey(vin));

                } else if (action.equalsIgnoreCase("delete")) {
                    String vin = request.getParameter("vin");
                    model.doDelete(vin);

                } else if (action.equalsIgnoreCase("insert")) {
                    try {
                        String vin = request.getParameter("vin");
                        String marca = request.getParameter("marca");
                        String modello = request.getParameter("modello");
                        String targa = request.getParameter("targa");
                        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
                        int anno = Integer.parseInt(request.getParameter("anno"));
                        String tipoCarburante = request.getParameter("tipoCarburante");
                        int porte = Integer.parseInt(request.getParameter("porte"));
                        String tipo = request.getParameter("tipo");
                        boolean climatizzatore = Boolean.parseBoolean(request.getParameter("climatizzatore"));
                        String ruoteMotrici = request.getParameter("ruoteMotrici");
                        boolean schermoIntegrato = Boolean.parseBoolean(request.getParameter("schermoIntegrato"));

                        // Se il parametro è vuoto, assegniamo 0
                        int cilindrata = request.getParameter("cilindrata").isEmpty() ? 0 : Integer.parseInt(request.getParameter("cilindrata"));
                        int posti = Integer.parseInt(request.getParameter("posti"));
                        int kilometri = Integer.parseInt(request.getParameter("kilometri"));
                        String tipoCambio = request.getParameter("tipoCambio");
                        int rapporti = Integer.parseInt(request.getParameter("rapporti"));

                        // Se il parametro è vuoto, assegniamo 0
                        int potenzaElettrica = request.getParameter("potenzaElettrica").isEmpty() ? 0 : Integer.parseInt(request.getParameter("potenzaElettrica"));

                        String trazione = request.getParameter("trazione");
                        int peso = Integer.parseInt(request.getParameter("peso"));
                        String colore = request.getParameter("colore");
                        int potenza = Integer.parseInt(request.getParameter("potenza"));

                        // Creazione del bean
                        ProductBean bean = new ProductBean();
                        bean.setVin(vin);
                        bean.setMarca(marca);
                        bean.setModello(modello);
                        bean.setTarga(targa);
                        bean.setPrezzo(prezzo);
                        bean.setAnno(anno);
                        bean.setKilometri(kilometri);
                        bean.setTipoCarburante(tipoCarburante);
                        bean.setPorte(porte);
                        bean.setTipo(tipo);
                        bean.setClimatizzatore(climatizzatore);
                        bean.setRuoteMotrici(ruoteMotrici);
                        bean.setSchermoIntegrato(schermoIntegrato);
                        bean.setCilindrata(cilindrata);
                        bean.setPosti(posti);
                        bean.setTipoCambio(tipoCambio);
                        bean.setRapporti(rapporti);
                        bean.setPotenzaElettrica(potenzaElettrica);
                        bean.setTrazione(trazione);
                        bean.setPeso(peso);
                        bean.setColore(colore);
                        bean.setPotenza(potenza);

                        model.doSave(bean);

                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Errore di formato nei numeri. Controlla i valori inseriti.");
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Errore SQL: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String sort = request.getParameter("sort");

        try {
            request.removeAttribute("products");
            request.setAttribute("products", model.doRetrieveAll(sort));
        } catch (SQLException e) {
            request.setAttribute("error", "Errore SQL durante il recupero delle automobili: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
