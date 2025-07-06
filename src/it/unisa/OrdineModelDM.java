package it.unisa;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.time.LocalDate;

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
        } finally {
            if (preparedStatement != null) preparedStatement.close();
        }
    }
    
    public List<OrdineBean> doRetrieveAll() throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String sql = "SELECT * FROM ORDINE ORDER BY DATA_ORDINE DESC";

        try (Connection conn = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrdineBean ordine = new OrdineBean();
                ordine.setIdOrdine(rs.getInt("ID_ORDINE"));
                ordine.setCf(rs.getString("CF"));
                ordine.setDataOrdine(rs.getTimestamp("DATA_ORDINE"));
                ordine.setStatoOrdine(rs.getString("STATO_ORDINE"));
                ordine.setTotale(rs.getDouble("TOTALE"));
                ordine.setTipoConsegna(rs.getString("TIPO_CONSEGNA"));
                ordini.add(ordine);
            }
        }

        return ordini;
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
   
    public List<OrdineBean> findByFilters(String cfFiltro, String dataDa, String dataA) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();

            String sql = "SELECT * FROM ORDINE WHERE 1=1";
            List<Object> parametri = new ArrayList<>();

            if (cfFiltro != null && !cfFiltro.trim().isEmpty()) {
                sql += " AND CF = ?";
                parametri.add(cfFiltro.trim());
            }
            if (dataDa != null && !dataDa.trim().isEmpty()) {
                sql += " AND DATA_ORDINE >= ?";
                parametri.add(Date.valueOf(dataDa.trim()));
            }
            if (dataA != null && !dataA.trim().isEmpty()) {
                LocalDate dataA_local = LocalDate.parse(dataA.trim());
                LocalDate dataA_plus1 = dataA_local.plusDays(1);
                
                sql += " AND DATA_ORDINE < ?";
                parametri.add(Date.valueOf(dataA_plus1));
            }

            sql += " ORDER BY DATA_ORDINE DESC";

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                OrdineBean ordine = new OrdineBean();
                ordine.setIdOrdine(rs.getInt("ID_ORDINE"));
                ordine.setCf(rs.getString("CF"));
                ordine.setDataOrdine(rs.getTimestamp("DATA_ORDINE"));
                ordine.setStatoOrdine(rs.getString("STATO_ORDINE"));
                ordine.setTotale(rs.getDouble("TOTALE"));
                ordine.setTipoConsegna(rs.getString("TIPO_CONSEGNA"));
                ordini.add(ordine);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) DriverManagerConnectionPool.releaseConnection(conn);
        }
        return ordini;
    }
}