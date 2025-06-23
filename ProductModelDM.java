
package it.unisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import it.unisa.model.ProductBean;

public class ProductModelDM implements ProductModel {

    private static final String TABLE_NAME = "automobili";

    public synchronized void doSave(ProductBean automobile) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (VIN, MARCA, MODELLO, TARGA, PREZZO, ANNO, KILOMETRI, TIPO_CARBURANTE, PORTE, TIPO, " +
                "CLIMATIZZATORE, RUOTEMOTRICI, SCHERMO_INTEGRATO, CILINDRATA, POSTI, " +
                "TIPO_CAMBIO, RAPPORTI, POTENZAELETTRICA, TRAZIONE, PESO, COLORE, POTENZA, IMAGEPATH, CF, VENDUTA, ID_ORDINE, IVA) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, automobile.getVin());
            preparedStatement.setString(2, automobile.getMarca());
            preparedStatement.setString(3, automobile.getModello());
            preparedStatement.setString(4, automobile.getTarga());
            preparedStatement.setDouble(5, automobile.getPrezzo());
            preparedStatement.setInt(6, automobile.getAnno());
            preparedStatement.setInt(7, automobile.getKilometri());
            preparedStatement.setString(8, automobile.getTipoCarburante());
            preparedStatement.setInt(9, automobile.getPorte());
            preparedStatement.setString(10, automobile.getTipo());
            preparedStatement.setBoolean(11, automobile.isClimatizzatore());
            preparedStatement.setString(12, automobile.getRuoteMotrici());
            preparedStatement.setBoolean(13, automobile.isSchermoIntegrato());
            preparedStatement.setInt(14, automobile.getCilindrata());
            preparedStatement.setInt(15, automobile.getPosti());
            preparedStatement.setString(16, automobile.getTipoCambio());
            preparedStatement.setInt(17, automobile.getRapporti());
            preparedStatement.setInt(18, automobile.getPotenzaElettrica());
            preparedStatement.setString(19, automobile.getTrazione());
            preparedStatement.setInt(20, automobile.getPeso());
            preparedStatement.setString(21, automobile.getColore());
            preparedStatement.setInt(22, automobile.getPotenza());
            preparedStatement.setString(23, automobile.getImmagine());
            preparedStatement.setString(24, automobile.getCf());
            preparedStatement.setBoolean(25, false);
            if (automobile.getIdOrdine() != null) {
                preparedStatement.setInt(26, automobile.getIdOrdine());
            } else {
                preparedStatement.setNull(26, java.sql.Types.INTEGER);
            }
            preparedStatement.setDouble(27, automobile.getIva());

            preparedStatement.executeUpdate();
            System.out.println("Auto salvata con successo: " + automobile.getVin());

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public synchronized ProductBean doRetrieveByKey(String vin) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ProductBean automobile = new ProductBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE VIN = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, vin);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                automobile.setVin(rs.getString("VIN"));
                automobile.setMarca(rs.getString("MARCA"));
                automobile.setModello(rs.getString("MODELLO"));
                automobile.setTarga(rs.getString("TARGA"));
                automobile.setPrezzo(rs.getDouble("PREZZO"));
                automobile.setAnno(rs.getInt("ANNO"));
                automobile.setPorte(rs.getInt("PORTE"));
                automobile.setTipo(rs.getString("TIPO"));
                automobile.setClimatizzatore(rs.getBoolean("CLIMATIZZATORE"));
                automobile.setRuoteMotrici(rs.getString("RUOTEMOTRICI"));
                automobile.setSchermoIntegrato(rs.getBoolean("SCHERMO_INTEGRATO"));
                automobile.setCilindrata(rs.getInt("CILINDRATA"));
                automobile.setPosti(rs.getInt("POSTI"));
                automobile.setKilometri(rs.getInt("KILOMETRI"));
                automobile.setTipoCambio(rs.getString("TIPO_CAMBIO"));
                automobile.setRapporti(rs.getInt("RAPPORTI"));

                Integer potenzaElettrica = rs.getInt("POTENZAELETTRICA");
                automobile.setPotenzaElettrica(rs.wasNull() ? null : potenzaElettrica);

                automobile.setTipoCarburante(rs.getString("TIPO_CARBURANTE"));
                automobile.setColore(rs.getString("COLORE"));
                automobile.setPotenza(rs.getInt("POTENZA"));
                automobile.setTrazione(rs.getString("TRAZIONE"));
                automobile.setPeso(rs.getInt("PESO"));
                automobile.setImmagine(rs.getString("IMAGEPATH"));
                automobile.setCf(rs.getString("CF"));  // Recuperiamo il CF
                automobile.setVenduta(rs.getBoolean("VENDUTA"));
                automobile.setIva(rs.getDouble("IVA"));
                int idOrdine = rs.getInt("ID_ORDINE");
                if (!rs.wasNull()) {
                    automobile.setIdOrdine(idOrdine);
                } else {
                    automobile.setIdOrdine(null);
                }
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return automobile;
    }

    @Override
    public synchronized boolean doDelete(String vin) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE VIN = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, vin);

            result = preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return (result != 0);
    }

    @Override
    public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> automobiles = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE VENDUTA = FALSE";

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductBean automobile = new ProductBean();
                automobile.setVin(rs.getString("VIN"));
                automobile.setMarca(rs.getString("MARCA"));
                automobile.setModello(rs.getString("MODELLO"));
                automobile.setTarga(rs.getString("TARGA"));
                automobile.setPrezzo(rs.getDouble("PREZZO"));
                automobile.setAnno(rs.getInt("ANNO"));
                automobile.setKilometri(rs.getInt("KILOMETRI"));
                automobile.setTipoCarburante(rs.getString("TIPO_CARBURANTE"));
                automobile.setPorte(rs.getInt("PORTE"));
                automobile.setTipo(rs.getString("TIPO"));
                automobile.setClimatizzatore(rs.getBoolean("CLIMATIZZATORE"));
                automobile.setRuoteMotrici(rs.getString("RUOTEMOTRICI"));
                automobile.setSchermoIntegrato(rs.getBoolean("SCHERMO_INTEGRATO"));
                automobile.setCilindrata(rs.getInt("CILINDRATA"));
                automobile.setPosti(rs.getInt("POSTI"));
                automobile.setTipoCambio(rs.getString("TIPO_CAMBIO"));
                automobile.setRapporti(rs.getInt("RAPPORTI"));
                automobile.setPotenzaElettrica(rs.getInt("POTENZAELETTRICA"));
                automobile.setTrazione(rs.getString("TRAZIONE"));
                automobile.setPeso(rs.getInt("PESO"));
                automobile.setColore(rs.getString("COLORE"));
                automobile.setPotenza(rs.getInt("POTENZA"));
                automobile.setImmagine(rs.getString("IMAGEPATH"));
                automobile.setCf(rs.getString("CF"));
                automobile.setVenduta(rs.getBoolean("VENDUTA")); // Assicurati che esista anche nel ProductBean
                automobile.setIva(rs.getDouble("IVA"));
                

                automobiles.add(automobile);
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
        return automobiles;
    }
    
    public synchronized Collection<ProductBean> doRetrieveAllAdmin(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> automobiles = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductBean automobile = new ProductBean();
                automobile.setVin(rs.getString("VIN"));
                automobile.setMarca(rs.getString("MARCA"));
                automobile.setModello(rs.getString("MODELLO"));
                automobile.setTarga(rs.getString("TARGA"));
                automobile.setPrezzo(rs.getDouble("PREZZO"));
                automobile.setAnno(rs.getInt("ANNO"));
                automobile.setKilometri(rs.getInt("KILOMETRI"));
                automobile.setTipoCarburante(rs.getString("TIPO_CARBURANTE"));
                automobile.setPorte(rs.getInt("PORTE"));
                automobile.setTipo(rs.getString("TIPO"));
                automobile.setClimatizzatore(rs.getBoolean("CLIMATIZZATORE"));
                automobile.setRuoteMotrici(rs.getString("RUOTEMOTRICI"));
                automobile.setSchermoIntegrato(rs.getBoolean("SCHERMO_INTEGRATO"));
                automobile.setCilindrata(rs.getInt("CILINDRATA"));
                automobile.setPosti(rs.getInt("POSTI"));
                automobile.setTipoCambio(rs.getString("TIPO_CAMBIO"));
                automobile.setRapporti(rs.getInt("RAPPORTI"));
                automobile.setPotenzaElettrica(rs.getInt("POTENZAELETTRICA"));
                automobile.setTrazione(rs.getString("TRAZIONE"));
                automobile.setPeso(rs.getInt("PESO"));
                automobile.setColore(rs.getString("COLORE"));
                automobile.setPotenza(rs.getInt("POTENZA"));
                automobile.setImmagine(rs.getString("IMAGEPATH"));
                automobile.setCf(rs.getString("CF"));
                automobile.setVenduta(rs.getBoolean("VENDUTA"));
                int idOrdine = rs.getInt("ID_ORDINE");
                if (!rs.wasNull()) {
                    automobile.setIdOrdine(idOrdine);
                } else {
                    automobile.setIdOrdine(null);
                }
                automobile.setIva(rs.getDouble("IVA"));

                automobiles.add(automobile);
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
        return automobiles;
    }

    public void doUpdate(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE " + TABLE_NAME + " SET "
                + "MARCA=?, MODELLO=?, TARGA=?, PREZZO=?, ANNO=?, KILOMETRI=?, TIPO_CARBURANTE=?, "
                + "PORTE=?, TIPO=?, CLIMATIZZATORE=?, RUOTEMOTRICI=?, SCHERMO_INTEGRATO=?, CILINDRATA=?, POSTI=?, "
                + "TIPO_CAMBIO=?, RAPPORTI=?, POTENZAELETTRICA=?, TRAZIONE=?, PESO=?, COLORE=?, POTENZA=?, "
                + "IMAGEPATH=?, CF=?, VENDUTA=?, ID_ORDINE=?, IVA=? "
                + "WHERE VIN=?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, product.getMarca());
            preparedStatement.setString(2, product.getModello());
            preparedStatement.setString(3, product.getTarga());
            preparedStatement.setDouble(4, product.getPrezzo());
            preparedStatement.setInt(5, product.getAnno());
            preparedStatement.setInt(6, product.getKilometri());
            preparedStatement.setString(7, product.getTipoCarburante());
            preparedStatement.setInt(8, product.getPorte());
            preparedStatement.setString(9, product.getTipo());
            preparedStatement.setBoolean(10, product.isClimatizzatore());
            preparedStatement.setString(11, product.getRuoteMotrici());
            preparedStatement.setBoolean(12, product.isSchermoIntegrato());
            preparedStatement.setInt(13, product.getCilindrata());
            preparedStatement.setInt(14, product.getPosti());
            preparedStatement.setString(15, product.getTipoCambio());
            preparedStatement.setInt(16, product.getRapporti());
            preparedStatement.setInt(17, product.getPotenzaElettrica());
            preparedStatement.setString(18, product.getTrazione());
            preparedStatement.setInt(19, product.getPeso());
            preparedStatement.setString(20, product.getColore());
            preparedStatement.setInt(21, product.getPotenza());
            preparedStatement.setString(22, product.getImmagine());
            preparedStatement.setString(23, product.getCf());
            preparedStatement.setBoolean(24, product.isVenduta());
            if (product.getIdOrdine() != null && product.getIdOrdine() == 0) {
                preparedStatement.setNull(25, java.sql.Types.INTEGER);
            } else if (product.getIdOrdine() != null) {
                preparedStatement.setInt(25, product.getIdOrdine());
            } else {
                preparedStatement.setNull(25, java.sql.Types.INTEGER);
            }
            preparedStatement.setDouble(26, product.getIva());
            preparedStatement.setString(27, product.getVin());

            System.out.println("ID_ORDINE prima dell'update: " + product.getIdOrdine());
            preparedStatement.executeUpdate();
            connection.commit();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }
    
    public void doUpdate(ProductBean product, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE " + TABLE_NAME + " SET "
                + "MARCA=?, MODELLO=?, TARGA=?, PREZZO=?, ANNO=?, KILOMETRI=?, TIPO_CARBURANTE=?, "
                + "PORTE=?, TIPO=?, CLIMATIZZATORE=?, RUOTEMOTRICI=?, SCHERMO_INTEGRATO=?, CILINDRATA=?, POSTI=?, "
                + "TIPO_CAMBIO=?, RAPPORTI=?, POTENZAELETTRICA=?, TRAZIONE=?, PESO=?, COLORE=?, POTENZA=?, "
                + "IMAGEPATH=?, CF=?, VENDUTA=?, ID_ORDINE=?, IVA=? "
                + "WHERE VIN=?";

        try {
            preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, product.getMarca());
            preparedStatement.setString(2, product.getModello());
            preparedStatement.setString(3, product.getTarga());
            preparedStatement.setDouble(4, product.getPrezzo());
            preparedStatement.setInt(5, product.getAnno());
            preparedStatement.setInt(6, product.getKilometri());
            preparedStatement.setString(7, product.getTipoCarburante());
            preparedStatement.setInt(8, product.getPorte());
            preparedStatement.setString(9, product.getTipo());
            preparedStatement.setBoolean(10, product.isClimatizzatore());
            preparedStatement.setString(11, product.getRuoteMotrici());
            preparedStatement.setBoolean(12, product.isSchermoIntegrato());
            preparedStatement.setInt(13, product.getCilindrata());
            preparedStatement.setInt(14, product.getPosti());
            preparedStatement.setString(15, product.getTipoCambio());
            preparedStatement.setInt(16, product.getRapporti());
            preparedStatement.setInt(17, product.getPotenzaElettrica());
            preparedStatement.setString(18, product.getTrazione());
            preparedStatement.setInt(19, product.getPeso());
            preparedStatement.setString(20, product.getColore());
            preparedStatement.setInt(21, product.getPotenza());
            preparedStatement.setString(22, product.getImmagine());
            preparedStatement.setString(23, product.getCf());
            preparedStatement.setBoolean(24, product.isVenduta());
            if (product.getIdOrdine() != null && product.getIdOrdine() == 0) {
                preparedStatement.setNull(25, java.sql.Types.INTEGER);
            } else if (product.getIdOrdine() != null) {
                preparedStatement.setInt(25, product.getIdOrdine());
            } else {
                preparedStatement.setNull(25, java.sql.Types.INTEGER);
            }
            preparedStatement.setDouble(26, product.getIva());
            preparedStatement.setString(27, product.getVin());

            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
    public List<ProductBean> doRetrieveByOrder(int idOrdine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<ProductBean> prodotti = new ArrayList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_ORDINE = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idOrdine);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductBean bean = new ProductBean();

                bean.setVin(rs.getString("VIN"));
                bean.setMarca(rs.getString("MARCA"));
                bean.setModello(rs.getString("MODELLO"));
                bean.setPrezzo(rs.getDouble("PREZZO"));
                bean.setTarga(rs.getString("TARGA"));
                bean.setAnno(rs.getInt("ANNO"));
                bean.setKilometri(rs.getInt("KILOMETRI"));
                bean.setTipoCarburante(rs.getString("TIPO_CARBURANTE"));
                bean.setPorte(rs.getInt("PORTE"));
                bean.setTipo(rs.getString("TIPO"));
                bean.setClimatizzatore(rs.getBoolean("CLIMATIZZATORE"));
                bean.setRuoteMotrici(rs.getString("RUOTEMOTRICI"));
                bean.setSchermoIntegrato(rs.getBoolean("SCHERMO_INTEGRATO"));
                bean.setCilindrata(rs.getInt("CILINDRATA"));
                bean.setPosti(rs.getInt("POSTI"));
                bean.setTipoCambio(rs.getString("TIPO_CAMBIO"));
                bean.setRapporti(rs.getInt("RAPPORTI"));
                bean.setPotenzaElettrica(rs.getInt("POTENZAELETTRICA"));
                bean.setTrazione(rs.getString("TRAZIONE"));
                bean.setPeso(rs.getInt("PESO"));
                bean.setColore(rs.getString("COLORE"));
                bean.setPotenza(rs.getInt("POTENZA"));
                bean.setImmagine(rs.getString("IMAGEPATH"));
                bean.setCf(rs.getString("CF"));
                bean.setVenduta(rs.getBoolean("VENDUTA"));
                bean.setIdOrdine(rs.getInt("ID_ORDINE"));
                bean.setIva(rs.getDouble("IVA"));

                prodotti.add(bean);
            }

        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception ignored) {}
            DriverManagerConnectionPool.releaseConnection(connection);
        }

        return prodotti;
    }
    
    @Override
    public synchronized Collection<ProductBean> doSearchByKeyword(String keyword) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ProductBean> automobiles = new LinkedList<>();

        // Divido la keyword in singole parole, es: "Ford Puma" -> ["ford", "puma"]
        String[] keywords = keyword.toLowerCase().split("\\s+");

        // Costruisco dinamicamente la query con AND tra i gruppi di OR (solo MARCA e MODELLO)
        StringBuilder searchSQL = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE VENDUTA = FALSE");

        for (int i = 0; i < keywords.length; i++) {
            searchSQL.append(" AND (LOWER(MARCA) LIKE ? OR LOWER(MODELLO) LIKE ?)");
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(searchSQL.toString());

            int paramIndex = 1;
            for (String k : keywords) {
                String searchPattern = "%" + k + "%";
                preparedStatement.setString(paramIndex++, searchPattern);
                preparedStatement.setString(paramIndex++, searchPattern);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductBean automobile = new ProductBean();
                automobile.setImmagine(rs.getString("IMAGEPATH"));
                automobile.setVin(rs.getString("VIN"));
                automobile.setMarca(rs.getString("MARCA"));
                automobile.setModello(rs.getString("MODELLO"));
                automobile.setPrezzo(rs.getDouble("PREZZO"));
                automobile.setAnno(rs.getInt("ANNO"));
                // Se vuoi puoi aggiungere altri campi come nel tuo modello completo
                automobiles.add(automobile);
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return automobiles;
    }
    
    @Override
    public synchronized Collection<ProductBean> doSearchByKeywordAdmin(String keyword) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ProductBean> automobiles = new LinkedList<>();

        // Divido la keyword in singole parole
        String[] keywords = keyword.toLowerCase().split("\\s+");

        // Nessun filtro su VENDUTA
        StringBuilder searchSQL = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE 1=1");

        for (int i = 0; i < keywords.length; i++) {
            searchSQL.append(" AND (LOWER(MARCA) LIKE ? OR LOWER(MODELLO) LIKE ?)");
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(searchSQL.toString());

            int paramIndex = 1;
            for (String k : keywords) {
                String searchPattern = "%" + k + "%";
                preparedStatement.setString(paramIndex++, searchPattern);
                preparedStatement.setString(paramIndex++, searchPattern);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductBean automobile = new ProductBean();
                automobile.setImmagine(rs.getString("IMAGEPATH"));
                automobile.setVin(rs.getString("VIN"));
                automobile.setMarca(rs.getString("MARCA"));
                automobile.setModello(rs.getString("MODELLO"));
                automobile.setPrezzo(rs.getDouble("PREZZO"));
                automobile.setAnno(rs.getInt("ANNO"));
                // Se serve, aggiungi qui altri campi come nel metodo completo
                automobiles.add(automobile);
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return automobiles;
    }

}