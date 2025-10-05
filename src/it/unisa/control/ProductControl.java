package it.unisa.control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.http.HttpSession;

import it.unisa.ProductModel;
import it.unisa.ProductModelDM;
import it.unisa.ProductModelDS;
import it.unisa.model.ProductBean;
import it.unisa.model.UtenteBean;

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
    	System.out.println("ProductControl doGet called, action=" + request.getParameter("action") + ", search=" + request.getParameter("search"));

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        UtenteBean utente = (session != null) ? (UtenteBean) session.getAttribute("utenteLoggato") : null;
        String sort = request.getParameter("sort");
        String from = request.getParameter("from");
        request.setAttribute("from", from);

        try {
            if (action != null) {
            	if (action.equalsIgnoreCase("read")) {
            	    String vin = request.getParameter("vin");
            	    request.removeAttribute("product");
            	    request.setAttribute("product", model.doRetrieveByKey(vin));

            	    // Ricostruisce la URL per tornare al catalogo mantenendo i filtri/sort
            	    String queryString = request.getQueryString(); 
            	    String backUrl = "product";

            	    if (queryString != null && queryString.contains("&")) {
            	        // Rimuove "action=read" e mantiene solo sort/search
            	        backUrl += "?" + queryString.replaceFirst("action=read&?", "").replaceFirst("vin=[^&]*&?", "");
            	        backUrl = backUrl.replaceAll("&$", ""); // pulizia finale se finisce con &
            	    }

            	    request.setAttribute("backUrl", backUrl);

            	    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/auto/dettagli.jsp");
            	    dispatcher.forward(request, response);
            	    return;
            	} else if (action.equalsIgnoreCase("edit")) {
                    String vin = request.getParameter("vin");
                    ProductBean product = model.doRetrieveByKey(vin);

                    if (product != null) {
                        request.setAttribute("product", product);
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/admin/dettagliadmin.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        request.setAttribute("error", "Prodotto non trovato.");
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
                        dispatcher.forward(request, response);
                    }
                    return;
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
                        int cilindrata = request.getParameter("cilindrata").isEmpty() ? 0 : Integer.parseInt(request.getParameter("cilindrata"));
                        int posti = Integer.parseInt(request.getParameter("posti"));
                        int kilometri = Integer.parseInt(request.getParameter("kilometri"));
                        String tipoCambio = request.getParameter("tipoCambio");
                        int rapporti = Integer.parseInt(request.getParameter("rapporti"));
                        int potenzaElettrica = request.getParameter("potenzaElettrica").isEmpty() ? 0 : Integer.parseInt(request.getParameter("potenzaElettrica"));
                        String trazione = request.getParameter("trazione");
                        int peso = Integer.parseInt(request.getParameter("peso"));
                        String colore = request.getParameter("colore");
                        int potenza = Integer.parseInt(request.getParameter("potenza"));
                        Part filePart = request.getPart("immagine");
                        String imagePath = saveImage(filePart);
                        if (filePart == null) {
                            System.out.println("Nessun file caricato!");
                        } else {
                            System.out.println("File caricato: " + filePart.getSubmittedFileName());
                        }
                        double iva = Double.parseDouble(request.getParameter("iva"));

                        if (utente == null) {
                            request.setAttribute("error", "Devi essere loggato per inserire un annuncio.");
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
                            dispatcher.forward(request, response);
                            return;
                        }

                        String CF = utente.getCf();

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
                        bean.setImmagine(imagePath);
                        bean.setCf(CF);
                        bean.setIva(iva);

                        model.doSave(bean);

                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Errore di formato nei numeri. Controlla i valori inseriti.");
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                }
            }

            // Ricerca o visualizzazione catalogo
            String search = request.getParameter("search");
            Collection<ProductBean> productList;

            if (search != null && !search.trim().isEmpty()) {
                if (utente != null && utente.isAdmin()) {
                    productList = model.doSearchByKeywordAdmin(search.trim());
                } else {
                    productList = model.doSearchByKeyword(search.trim());
                }
                System.out.println("Ricerca con keyword='" + search + "' trovati " + productList.size() + " prodotti.");
            } else {
                if (utente != null && utente.isAdmin()) {
                    productList = model.doRetrieveAllAdmin(sort);
                } else {
                    productList = model.doRetrieveAll(sort);
                }
            }

            request.setAttribute("products", productList);
            getServletContext().setAttribute("productList", productList);

            String pagina = (utente != null && utente.isAdmin()) ? "/jsp/admin/catalogo.jsp" : "/jsp/auto/catalogo.jsp";

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pagina);
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Errore SQL: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private String saveImage(Part filePart) throws IOException {
        String imageDir = getServletContext().getRealPath("/img");
        File dir = new File(imageDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String fileName = filePart.getSubmittedFileName();
        if (fileName != null && !fileName.isEmpty()) {
            String filePath = imageDir + File.separator + fileName;
            filePart.write(filePath);
            return "/img/" + fileName;
        }
        return null;
    }
}
