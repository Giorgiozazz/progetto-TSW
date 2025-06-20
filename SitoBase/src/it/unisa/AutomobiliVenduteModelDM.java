package it.unisa;

import it.unisa.model.ProductBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutomobiliVenduteModelDM {
	private static final String TABLE_NAME = "automobili_vendute";

	public void doSave(ProductBean prodotto, Connection connection) throws SQLException {
	    String sql = "INSERT INTO automobili_vendute (VIN, MARCA, MODELLO, TARGA, PREZZO, ANNO, KILOMETRI, TIPO_CARBURANTE, PORTE, TIPO, " +
	                 "CLIMATIZZATORE, RUOTEMOTRICI, SCHERMO_INTEGRATO, CILINDRATA, POSTI, TIPO_CAMBIO, RAPPORTI, POTENZAELETTRICA, TRAZIONE, PESO, " +
	                 "COLORE, POTENZA, IMAGEPATH, CF, VENDUTA, ID_ORDINE, IVA) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, prodotto.getVin());
	        ps.setString(2, prodotto.getMarca());
	        ps.setString(3, prodotto.getModello());
	        ps.setString(4, prodotto.getTarga());
	        ps.setDouble(5, prodotto.getPrezzo());
	        ps.setInt(6, prodotto.getAnno());
	        ps.setInt(7, prodotto.getKilometri());
	        ps.setString(8, prodotto.getTipoCarburante());
	        ps.setInt(9, prodotto.getPorte());
	        ps.setString(10, prodotto.getTipo());
	        ps.setBoolean(11, prodotto.isClimatizzatore());
	        ps.setString(12, prodotto.getRuoteMotrici());
	        ps.setBoolean(13, prodotto.isSchermoIntegrato());
	        ps.setInt(14, prodotto.getCilindrata());
	        ps.setInt(15, prodotto.getPosti());
	        ps.setString(16, prodotto.getTipoCambio());
	        ps.setInt(17, prodotto.getRapporti());
	        ps.setInt(18, prodotto.getPotenzaElettrica());
	        ps.setString(19, prodotto.getTrazione());
	        ps.setInt(20, prodotto.getPeso());
	        ps.setString(21, prodotto.getColore());
	        ps.setInt(22, prodotto.getPotenza());
	        ps.setString(23, prodotto.getImmagine());
	        ps.setString(24, prodotto.getCf());
	        ps.setBoolean(25, prodotto.isVenduta());
	        ps.setInt(26, prodotto.getIdOrdine());
	        ps.setDouble(27, prodotto.getIva());

	        ps.executeUpdate();
	    }
	}
	public List<ProductBean> doRetrieveByOrder(int idOrdine) throws SQLException {
	    List<ProductBean> prodotti = new ArrayList<>();

	    String sql = "SELECT * FROM "+TABLE_NAME+" WHERE ID_ORDINE = ?";

	    try (Connection connection = DriverManagerConnectionPool.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setInt(1, idOrdine);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                ProductBean prodotto = new ProductBean();
	                prodotto.setVin(rs.getString("VIN"));
	                prodotto.setMarca(rs.getString("MARCA"));
	                prodotto.setModello(rs.getString("MODELLO"));
	                prodotto.setTarga(rs.getString("TARGA"));
	                prodotto.setPrezzo(rs.getDouble("PREZZO"));
	                prodotto.setAnno(rs.getInt("ANNO"));
	                prodotto.setKilometri(rs.getInt("KILOMETRI"));
	                prodotto.setTipoCarburante(rs.getString("TIPO_CARBURANTE"));
	                prodotto.setPorte(rs.getInt("PORTE"));
	                prodotto.setTipo(rs.getString("TIPO"));
	                prodotto.setClimatizzatore(rs.getBoolean("CLIMATIZZATORE"));
	                prodotto.setRuoteMotrici(rs.getString("RUOTEMOTRICI"));
	                prodotto.setSchermoIntegrato(rs.getBoolean("SCHERMO_INTEGRATO"));
	                prodotto.setCilindrata(rs.getInt("CILINDRATA"));
	                prodotto.setPosti(rs.getInt("POSTI"));
	                prodotto.setTipoCambio(rs.getString("TIPO_CAMBIO"));
	                prodotto.setRapporti(rs.getInt("RAPPORTI"));
	                prodotto.setPotenzaElettrica(rs.getInt("POTENZAELETTRICA"));
	                prodotto.setTrazione(rs.getString("TRAZIONE"));
	                prodotto.setPeso(rs.getInt("PESO"));
	                prodotto.setColore(rs.getString("COLORE"));
	                prodotto.setPotenza(rs.getInt("POTENZA"));
	                prodotto.setImmagine(rs.getString("IMAGEPATH"));
	                prodotto.setCf(rs.getString("CF"));
	                prodotto.setVenduta(rs.getBoolean("VENDUTA"));
	                prodotto.setIdOrdine(rs.getInt("ID_ORDINE"));
	                prodotto.setIva(rs.getDouble("IVA"));

	                prodotti.add(prodotto);
	            }
	        }
	    }

	    return prodotti;
	}
	
	public synchronized ProductBean doRetrieveByVin(String vin) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    ProductBean automobile = null;

	    String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE VIN = ?";

	    try {
	        connection = DriverManagerConnectionPool.getConnection();
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
	            automobile.setCf(rs.getString("CF"));
	            automobile.setVenduta(rs.getBoolean("VENDUTA"));
	            automobile.setIva(rs.getDouble("IVA"));

	            int idOrdine = rs.getInt("ID_ORDINE");
	            automobile.setIdOrdine(rs.wasNull() ? null : idOrdine);
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


}
