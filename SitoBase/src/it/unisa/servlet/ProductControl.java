package it.unisa.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import it.unisa.ProductModel;
import it.unisa.ProductModelDM;
import it.unisa.ProductModelDS;
import it.unisa.bean.ProductBean;

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

                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/auto/dettagli.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                else if (action.equalsIgnoreCase("delete")) {
                    String vin = request.getParameter("vin");
                    model.doDelete(vin);
                }

                else if (action.equalsIgnoreCase("insert")) {
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
                        int quantita=Integer.parseInt(request.getParameter("quantita"));

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
                        bean.setQuantita(quantita);
                        
                        model.doSave(bean);

                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Errore di formato nei numeri. Controlla i valori inseriti.");
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
                        dispatcher.forward(request, response);
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Errore SQL: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Recupera lista prodotti solo se NON si è fatto il forward prima
        String sort = request.getParameter("sort");

        try {
            request.removeAttribute("products");
            Collection<ProductBean> productList = model.doRetrieveAll(sort);  // Collection<ProductBean>
            request.setAttribute("products", productList);

            // Rende disponibile la lista anche ad altre servlet (es: AddToCart)
            getServletContext().setAttribute("productList", productList);

        } catch (SQLException e) {
            request.setAttribute("error", "Errore SQL durante il recupero delle automobili: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/common/error.jsp");
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
