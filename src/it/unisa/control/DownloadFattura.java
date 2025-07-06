package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import it.unisa.OrdineModelDM;
import it.unisa.AutomobiliVenduteModelDM;  // Importa il model corretto
import it.unisa.UtenteModelDM;
import it.unisa.model.OrdineBean;
import it.unisa.model.ProductBean;
import it.unisa.model.UtenteBean;

public class DownloadFattura extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID ordine mancante");
            return;
        }

        int idOrdine = Integer.parseInt(idStr);
        OrdineBean ordine;
        List<ProductBean> prodotti;
        UtenteBean utente;

        try {
            OrdineModelDM ordineModel = new OrdineModelDM();
            AutomobiliVenduteModelDM automobiliVenduteModel = new AutomobiliVenduteModelDM(); // MODEL MODIFICATO
            UtenteModelDM utenteModel = new UtenteModelDM();

            ordine = ordineModel.doRetrieveById(idOrdine);
            prodotti = automobiliVenduteModel.doRetrieveByOrder(idOrdine);  // Ritiro auto vendute aggiornate
            utente = utenteModel.doRetrieveByKey(ordine.getCf());

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante il recupero dei dati.");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"fattura_" + idOrdine + ".pdf\"");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Intestazione ordine
            document.add(new Paragraph("Fattura Ordine #" + idOrdine, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Data: " + ordine.getDataOrdine()));
            document.add(Chunk.NEWLINE);

            // Dati cliente
            if (utente != null) {
                document.add(new Paragraph("Cliente: " + utente.getNome() + " " + utente.getCognome()));
                document.add(new Paragraph("Email: " + utente.getEmail()));
                document.add(new Paragraph("Telefono: " + utente.getTelefono()));
                document.add(new Paragraph("Indirizzo: " + utente.getIndirizzo() + ", " + utente.getNumeroCivico()));
                document.add(new Paragraph("Città: " + utente.getCitta() + " (" + utente.getProvincia() + ")"));
                document.add(Chunk.NEWLINE);
            } else {
                document.add(new Paragraph("Cliente: Informazioni non disponibili"));
                document.add(Chunk.NEWLINE);
            }

            // Tabella Prodotti
            PdfPTable table = new PdfPTable(7); 
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Intestazioni
            Stream.of("Marca", "Modello", "Prezzo lordo", "Quantità", "Aliquota IVA", "Prezzo netto", "Importo IVA")
                  .forEach(col -> {
                      PdfPCell header = new PdfPCell(new Phrase(col));
                      header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                      header.setHorizontalAlignment(Element.ALIGN_CENTER);
                      table.addCell(header);
                  });

            // Dati prodotti
            for (ProductBean p : prodotti) {
                double aliquotaProdotto = p.getIva(); // Assumendo getIva() restituisce la percentuale IVA (es. 22.0)
                double prezzoLordoProd = p.getPrezzo();
                double prezzoNettoProd = prezzoLordoProd / (1 + aliquotaProdotto / 100);
                double ivaProd = prezzoLordoProd - prezzoNettoProd;

                table.addCell(p.getMarca());
                table.addCell(p.getModello());
                table.addCell(String.format("%.2f €", prezzoLordoProd));
                table.addCell("1"); // Se hai la quantità, sostituisci qui
                table.addCell(String.format("%.0f%%", aliquotaProdotto));
                table.addCell(String.format("%.2f €", prezzoNettoProd));
                table.addCell(String.format("%.2f €", ivaProd));
            }

            document.add(table);

            // Calcolo e stampa dettaglio IVA totale
            final double aliquotaIVA = 22.0; // Aliquota IVA fissa (MODIFICARE QUI IN CASO DI CAMBIAMENTO)
            double totaleLordo = ordine.getTotale();
            double prezzoNetto = totaleLordo / (1 + aliquotaIVA / 100);
            double importoIVA = totaleLordo - prezzoNetto;

            document.add(new Paragraph(String.format("RIEPILOGO TOTALE:")));
            document.add(new Paragraph(String.format("Prezzo netto: %.2f €", prezzoNetto)));
            document.add(new Paragraph(String.format("IVA (%.0f%%): %.2f €", aliquotaIVA, importoIVA)));
            document.add(new Paragraph(String.format("Totale (IVA inclusa): %.2f €", totaleLordo)));

            document.close();

        } catch (DocumentException e) {
            throw new IOException("Errore nella generazione PDF: " + e.getMessage());
        }
    }
}
