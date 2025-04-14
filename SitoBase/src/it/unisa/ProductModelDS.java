package it.unisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import it.unisa.bean.ProductBean;

public class ProductModelDS implements ProductModel {

    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/catalogo_auto");
        } catch (NamingException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private static final String TABLE_NAME = "automobili";

    @Override
    public synchronized void doSave(ProductBean automobile) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (VIN, MARCA, MODELLO, TARGA, PREZZO, ANNO, KILOMETRI, TIPO_CARBURANTE, PORTE, " +
                "TIPO, CLIMATIZZATORE, RUOTEMOTRICI, SCHERMO_INTEGRATO, CILINDRATA, POSTI, " +
                "TIPO_CAMBIO, RAPPORTI, POTENZAELETTRICA, TRAZIONE, PESO, COLORE, POTENZA, IMAGEPATH,QUANTITA) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
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
            preparedStatement.setInt(24, automobile.getQuantita());

            preparedStatement.executeUpdate();
            //connection.commit();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
    }


    @Override
    public synchronized ProductBean doRetrieveByKey(String vin) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ProductBean automobile = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE VIN = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, vin);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                automobile = new ProductBean();
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
                automobile.setQuantita(rs.getInt("QUANTITA"));
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                if (connection != null) connection.close();
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
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, vin);

            result = preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
        return (result != 0);
    }

    @Override
    public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> automobiles = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = ds.getConnection();
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
                automobile.setQuantita(rs.getInt("QUANTITA"));

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

}
