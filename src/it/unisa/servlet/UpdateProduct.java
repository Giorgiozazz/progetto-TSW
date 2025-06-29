package it.unisa.servlet;

import it.unisa.model.ProductBean;
import it.unisa.ProductModelDM;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
                 maxFileSize = 1024 * 1024 * 10,      // 10 MB
                 maxRequestSize = 1024 * 1024 * 15)   // 15 MB
public class UpdateProduct extends HttpServlet {

    private static final String UPLOAD_DIR = "img";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8");

            ProductBean product = new ProductBean();

            product.setVin(request.getParameter("vin"));
            product.setMarca(request.getParameter("marca"));
            product.setModello(request.getParameter("modello"));
            product.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
            product.setTarga(request.getParameter("targa"));
            product.setAnno(Integer.parseInt(request.getParameter("anno")));
            product.setKilometri(Integer.parseInt(request.getParameter("kilometri")));
            product.setTipoCarburante(request.getParameter("tipoCarburante"));
            product.setPorte(Integer.parseInt(request.getParameter("porte")));
            product.setTipo(request.getParameter("tipo"));
            product.setClimatizzatore(Boolean.parseBoolean(request.getParameter("climatizzatore")));
            product.setRuoteMotrici(request.getParameter("ruoteMotrici"));
            product.setSchermoIntegrato(Boolean.parseBoolean(request.getParameter("schermoIntegrato")));
            product.setCilindrata(Integer.parseInt(request.getParameter("cilindrata")));
            product.setPosti(Integer.parseInt(request.getParameter("posti")));
            product.setTipoCambio(request.getParameter("tipoCambio"));
            product.setRapporti(Integer.parseInt(request.getParameter("rapporti")));
            product.setPotenzaElettrica(Integer.parseInt(request.getParameter("potenzaElettrica")));
            product.setTrazione(request.getParameter("trazione"));
            product.setPeso(Integer.parseInt(request.getParameter("peso")));
            product.setColore(request.getParameter("colore"));
            product.setPotenza(Integer.parseInt(request.getParameter("potenza")));
            product.setCf(request.getParameter("cf"));
            product.setVenduta(Boolean.parseBoolean(request.getParameter("venduta")));
            product.setIva(Double.parseDouble(request.getParameter("iva")));
            String idOrdineParam = request.getParameter("id_ordine");
            if (idOrdineParam != null && !idOrdineParam.isEmpty()) {
                try {
                    int idOrdine = Integer.parseInt(idOrdineParam);
                    product.setIdOrdine(idOrdine);
                } catch (NumberFormatException e) {
                    product.setIdOrdine(0); // oppure gestisci diversamente se 0 non è valido
                }
            }

            // Gestione immagine
            Part filePart = request.getPart("immagine");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = new File(filePart.getSubmittedFileName()).getName();
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                String filePath = uploadPath + File.separator + fileName;
                filePart.write(filePath);

                String relativePath = "/" + UPLOAD_DIR + "/" + fileName;
                product.setImmagine(relativePath); // usa il metodo corretto
            }

            // Se non viene caricata una nuova immagine, mantieni quella vecchia
            if (product.getImmagine() == null || product.getImmagine().isEmpty()) {
                ProductModelDM model = new ProductModelDM();
                ProductBean old = model.doRetrieveByKey(product.getVin());
                if (old != null) {
                    product.setImmagine(old.getImmagine());
                }
            }

            // Aggiornamento del prodotto nel database
            ProductModelDM model = new ProductModelDM();
            model.doUpdate(product);

            // Redirigi al catalogo
            response.sendRedirect(request.getContextPath() + "/jsp/admin/catalogo.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore durante l'aggiornamento del prodotto: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
}
