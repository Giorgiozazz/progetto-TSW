package it.unisa.servlet;

import it.unisa.model.ProductBean;
import it.unisa.ProductModelDM;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@MultipartConfig
public class UpdateProduct extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupero dei parametri dal form
        String vin = request.getParameter("vin");
        String marca = request.getParameter("marca");
        String modello = request.getParameter("modello");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String targa = request.getParameter("targa");
        int porte = Integer.parseInt(request.getParameter("porte"));
        String tipo = request.getParameter("tipo");
        boolean climatizzatore = request.getParameter("climatizzatore") != null;
        String ruoteMotrici = request.getParameter("ruoteMotrici");
        boolean schermoIntegrato = request.getParameter("schermoIntegrato") != null;
        int anno = Integer.parseInt(request.getParameter("anno"));
        int cilindrata = Integer.parseInt(request.getParameter("cilindrata"));
        int posti = Integer.parseInt(request.getParameter("posti"));
        int chilometri = Integer.parseInt(request.getParameter("kilometri"));
        String tipoCambio = request.getParameter("tipoCambio");
        int rapporti = Integer.parseInt(request.getParameter("rapporti"));
        int potenzaElettrica = Integer.parseInt(request.getParameter("potenzaElettrica"));
        String tipoCarburante = request.getParameter("tipoCarburante");
        String colore = request.getParameter("colore");
        int potenza = Integer.parseInt(request.getParameter("potenza"));
        String trazione = request.getParameter("trazione");
        int peso = Integer.parseInt(request.getParameter("peso"));
        String cf = request.getParameter("cf");

        // Gestione file immagine
        Part filePart = request.getPart("immagine");
        String imagePath = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadDirPath = getServletContext().getRealPath("") + File.separator + "img"; // Cartella di destinazione
            File uploadDir = new File(uploadDirPath);
            
            // Crea la directory se non esiste
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadDirPath + File.separator + fileName;
            
            try {
                // Scrive il file
                filePart.write(filePath);
                imagePath = "img/" + fileName; // Percorso relativo dell'immagine
            } catch (IOException e) {
                e.printStackTrace();
                // Mostra errore se il file non viene caricato correttamente
                request.setAttribute("error", "Errore durante il caricamento dell'immagine: " + e.getMessage());
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
                return;
            }
        }

        // Crea un nuovo ProductBean con i dati ricevuti
        ProductBean product = new ProductBean();
        product.setVin(vin);
        product.setMarca(marca);
        product.setModello(modello);
        product.setPrezzo(prezzo);
        product.setTarga(targa);
        product.setPorte(porte);
        product.setTipo(tipo);
        product.setClimatizzatore(climatizzatore);
        product.setRuoteMotrici(ruoteMotrici);
        product.setSchermoIntegrato(schermoIntegrato);
        product.setAnno(anno);
        product.setCilindrata(cilindrata);
        product.setPosti(posti);
        product.setKilometri(chilometri);
        product.setTipoCambio(tipoCambio);
        product.setRapporti(rapporti);
        product.setPotenzaElettrica(potenzaElettrica);
        product.setTipoCarburante(tipoCarburante);
        product.setColore(colore);
        product.setPotenza(potenza);
        product.setTrazione(trazione);
        product.setPeso(peso);
        product.setCf(cf);
        
        // Imposta il percorso dell'immagine, se presente
        if (imagePath != null) {
            product.setImmagine(imagePath);
        }

        // Aggiorna il prodotto nel database
        ProductModelDM productModel = new ProductModelDM();
        try {
            productModel.updateProduct(product); // Metodo che effettua l'aggiornamento nel DB
            response.sendRedirect(request.getContextPath() + "/jsp/admin/catalogo.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            // Mostra errore in caso di fallimento dell'aggiornamento
            request.setAttribute("error", "Errore durante l'aggiornamento del prodotto: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
}
