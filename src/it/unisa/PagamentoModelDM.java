package it.unisa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.unisa.model.PagamentoBean;

public class PagamentoModelDM {

    private static final String TABLE_NAME = "pagamento";

    public synchronized void doSave(PagamentoBean pagamento, Connection connection) throws SQLException {
    PreparedStatement preparedStatement = null;

    String insertSQL = "INSERT INTO " + TABLE_NAME +
            " (ID_ORDINE, METODO_PAGAMENTO, ANTICIPO, NUM_RATE, IMPORTO_RATA, DATA_PAGAMENTO, IVA) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try {
        preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

        System.out.println("[DEBUG] Salvataggio pagamento con ID ordine: " + pagamento.getIdOrdine());

        preparedStatement.setInt(1, pagamento.getIdOrdine());
        preparedStatement.setString(2, pagamento.getMetodoPagamento());
        preparedStatement.setDouble(3, pagamento.getAnticipo());
        preparedStatement.setInt(4, pagamento.getNumRate());
        preparedStatement.setDouble(5, pagamento.getImportoRata());
        preparedStatement.setTimestamp(6, new Timestamp(pagamento.getDataPagamento().getTime()));
        preparedStatement.setDouble(7, pagamento.getIva());

        int result = preparedStatement.executeUpdate();
        System.out.println("[DEBUG] Righe inserite in pagamento: " + result);

        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            int generatedId = rs.getInt(1);
            pagamento.setIdPagamento(generatedId);
            System.out.println("[DEBUG] Pagamento inserito con ID: " + generatedId);
        } else {
            System.out.println("[DEBUG] Nessuna chiave generata per il pagamento.");
        }

    } finally {
        if (preparedStatement != null) preparedStatement.close();
        // niente commit o chiusura della connessione qui
    }
}

    public synchronized PagamentoBean doRetrieveById(int idPagamento) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PagamentoBean pagamento = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_PAGAMENTO = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idPagamento);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                pagamento = new PagamentoBean();
                pagamento.setIdPagamento(rs.getInt("ID_PAGAMENTO"));
                pagamento.setIdOrdine(rs.getInt("ID_ORDINE"));
                pagamento.setMetodoPagamento(rs.getString("METODO_PAGAMENTO"));
                pagamento.setAnticipo(rs.getDouble("ANTICIPO"));
                pagamento.setNumRate(rs.getInt("NUM_RATE"));
                pagamento.setImportoRata(rs.getDouble("IMPORTO_RATA"));
                pagamento.setDataPagamento(rs.getTimestamp("DATA_PAGAMENTO"));
                pagamento.setIva(rs.getDouble("IVA"));
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }

        return pagamento;
    }

    public synchronized List<PagamentoBean> doRetrieveByOrdine(int idOrdine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<PagamentoBean> pagamenti = new ArrayList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_ORDINE = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idOrdine);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                PagamentoBean pagamento = new PagamentoBean();
                pagamento.setIdPagamento(rs.getInt("ID_PAGAMENTO"));
                pagamento.setIdOrdine(rs.getInt("ID_ORDINE"));
                pagamento.setMetodoPagamento(rs.getString("METODO_PAGAMENTO"));
                pagamento.setAnticipo(rs.getDouble("ANTICIPO"));
                pagamento.setNumRate(rs.getInt("NUM_RATE"));
                pagamento.setImportoRata(rs.getDouble("IMPORTO_RATA"));
                pagamento.setDataPagamento(rs.getTimestamp("DATA_PAGAMENTO"));
                pagamento.setIva(rs.getDouble("IVA"));

                pagamenti.add(pagamento);
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }

        return pagamenti;
    }

    public synchronized boolean doDelete(int idPagamento) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE ID_PAGAMENTO = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idPagamento);

            result = preparedStatement.executeUpdate();
            connection.commit();

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }

        return result != 0;
    }
}