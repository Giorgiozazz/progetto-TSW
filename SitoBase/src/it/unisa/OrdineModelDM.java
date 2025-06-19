package it.unisa;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unisa.model.OrdineBean;

public class OrdineModelDM {

    private static final String TABLE_NAME = "ordine";

    public synchronized void doSave(OrdineBean ordine, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (CF, DATA_ORDINE, STATO_ORDINE, TOTALE, TIPO_CONSEGNA, INDIRIZZO_SPEDIZIONE, NUMERO_CIVICO_SPEDIZIONE, CITTA_SPEDIZIONE, PROVINCIA_SPEDIZIONE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, ordine.getCf());
            preparedStatement.setTimestamp(2, new Timestamp(ordine.getDataOrdine().getTime()));
            preparedStatement.setString(3, ordine.getStatoOrdine());
            preparedStatement.setDouble(4, ordine.getTotale());
            preparedStatement.setString(5, ordine.getTipoConsegna());
            preparedStatement.setString(6, ordine.getIndirizzoSpedizione());
            preparedStatement.setString(7, ordine.getNumeroCivicoSpedizione());
            preparedStatement.setString(8, ordine.getCittaSpedizione());
            preparedStatement.setString(9, ordine.getProvinciaSpedizione());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                ordine.setIdOrdine(rs.getInt(1));
            }

            //  NON fare: connection.commit();
            //  NON rilasciare la connessione

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            // NON chiudere la connessione qui
        }
    }


    public synchronized OrdineBean doRetrieveById(int idOrdine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        OrdineBean ordine = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_ORDINE = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idOrdine);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                ordine = new OrdineBean();
                ordine.setIdOrdine(rs.getInt("ID_ORDINE"));
                ordine.setCf(rs.getString("CF"));
                ordine.setDataOrdine(rs.getTimestamp("DATA_ORDINE"));
                ordine.setStatoOrdine(rs.getString("STATO_ORDINE"));
                ordine.setTotale(rs.getDouble("TOTALE"));
                ordine.setTipoConsegna(rs.getString("TIPO_CONSEGNA"));
                ordine.setIndirizzoSpedizione(rs.getString("INDIRIZZO_SPEDIZIONE"));
                ordine.setNumeroCivicoSpedizione(rs.getString("NUMERO_CIVICO_SPEDIZIONE"));
                ordine.setCittaSpedizione(rs.getString("CITTA_SPEDIZIONE"));
                ordine.setProvinciaSpedizione(rs.getString("PROVINCIA_SPEDIZIONE"));
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }

        return ordine;
    }
    
    public synchronized List<OrdineBean> doRetrieveByCF(String cf) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<OrdineBean> ordini = new ArrayList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE CF = ? ORDER BY DATA_ORDINE DESC";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, cf);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrdineBean ordine = new OrdineBean();
                ordine.setIdOrdine(rs.getInt("ID_ORDINE"));
                ordine.setCf(rs.getString("CF"));
                ordine.setDataOrdine(rs.getTimestamp("DATA_ORDINE"));
                ordine.setStatoOrdine(rs.getString("STATO_ORDINE"));
                ordine.setTotale(rs.getDouble("TOTALE"));
                ordine.setTipoConsegna(rs.getString("TIPO_CONSEGNA"));
                ordine.setIndirizzoSpedizione(rs.getString("INDIRIZZO_SPEDIZIONE"));
                ordine.setNumeroCivicoSpedizione(rs.getString("NUMERO_CIVICO_SPEDIZIONE"));
                ordine.setCittaSpedizione(rs.getString("CITTA_SPEDIZIONE"));
                ordine.setProvinciaSpedizione(rs.getString("PROVINCIA_SPEDIZIONE"));

                ordini.add(ordine);
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }

        return ordini;
    }

}
