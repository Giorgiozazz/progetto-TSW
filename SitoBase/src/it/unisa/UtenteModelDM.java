package it.unisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.unisa.model.UtenteBean;

import java.sql.ResultSet;

public class UtenteModelDM implements UtenteModel {

    private static final String TABLE_NAME = "utente";

    @Override
    public synchronized void doSave(UtenteBean utente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String checkSQL = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE CF = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(checkSQL);
            preparedStatement.setString(1, utente.getCf());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new SQLException("Codice Fiscale già presente nel database.");
            }

            String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (CF, NOME, COGNOME, EMAIL, TELEFONO, CITTA, PROVINCIA, INDIRIZZO, NUMERO_CIVICO, PASSWORD, IS_ADMIN) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, utente.getCf());
            preparedStatement.setString(2, utente.getNome());
            preparedStatement.setString(3, utente.getCognome());
            preparedStatement.setString(4, utente.getEmail());
            preparedStatement.setString(5, utente.getTelefono());
            preparedStatement.setString(6, utente.getCitta());
            preparedStatement.setString(7, utente.getProvincia());
            preparedStatement.setString(8, utente.getIndirizzo());
            preparedStatement.setString(9, utente.getNumeroCivico());
            preparedStatement.setString(10, utente.getPassword());
            preparedStatement.setBoolean(11, utente.isAdmin());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Utente registrato correttamente nel database.");
            } else {
                System.out.println("Errore durante l'inserimento dell'utente.");
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DriverManagerConnectionPool.releaseConnection(connection);
        }
    }

    @Override
    public synchronized UtenteBean doRetrieveByKey(String cf) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UtenteBean utente = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE CF = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, cf);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                utente = new UtenteBean();
                utente.setCf(rs.getString("CF"));
                utente.setNome(rs.getString("NOME"));
                utente.setCognome(rs.getString("COGNOME"));
                utente.setEmail(rs.getString("EMAIL"));
                utente.setTelefono(rs.getString("TELEFONO"));
                utente.setCitta(rs.getString("CITTA"));
                utente.setProvincia(rs.getString("PROVINCIA"));
                utente.setIndirizzo(rs.getString("INDIRIZZO"));
                utente.setNumeroCivico(rs.getString("NUMERO_CIVICO"));
                utente.setPassword(rs.getString("PASSWORD"));
                utente.setAdmin(rs.getBoolean("IS_ADMIN"));
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return utente;
    }

    @Override
    public synchronized boolean doDelete(String cf) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE CF = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, cf);

            result = preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("Utente eliminato con successo.");

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }

        return result != 0;
    }

    @Override
    public synchronized UtenteBean doRetrieveByEmailPassword(String email, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UtenteBean utente = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ? AND EMAIL IS NOT NULL AND PASSWORD IS NOT NULL";

        try {
            System.out.println("Tentativo di login:");
            System.out.println("Email inserita: " + email);
            System.out.println("Password inserita: " + password);

            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String dbEmail = rs.getString("EMAIL");
                String dbPassword = rs.getString("PASSWORD");

                if (dbEmail != null && dbPassword != null && !dbEmail.isEmpty() && !dbPassword.isEmpty()) {
                    utente = new UtenteBean();
                    utente.setCf(rs.getString("CF"));
                    utente.setNome(rs.getString("NOME"));
                    utente.setCognome(rs.getString("COGNOME"));
                    utente.setEmail(dbEmail);
                    utente.setTelefono(rs.getString("TELEFONO"));
                    utente.setCitta(rs.getString("CITTA"));
                    utente.setProvincia(rs.getString("PROVINCIA"));
                    utente.setIndirizzo(rs.getString("INDIRIZZO"));
                    utente.setNumeroCivico(rs.getString("NUMERO_CIVICO"));
                    utente.setPassword(dbPassword);
                    utente.setAdmin(rs.getBoolean("IS_ADMIN"));
                } else {
                    System.out.println("Riga ignorata: campi null o vuoti.");
                }
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return utente;
    }

    @Override
    public synchronized boolean doUpdate(UtenteBean utente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        String updateSQL = "UPDATE " + TABLE_NAME + " SET " +
                "NOME = ?, COGNOME = ?, EMAIL = ?, TELEFONO = ?, CITTA = ?, PROVINCIA = ?, INDIRIZZO = ?, NUMERO_CIVICO = ?, IS_ADMIN = ? " +
                "WHERE CF = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getEmail());
            preparedStatement.setString(4, utente.getTelefono());
            preparedStatement.setString(5, utente.getCitta());
            preparedStatement.setString(6, utente.getProvincia());
            preparedStatement.setString(7, utente.getIndirizzo());
            preparedStatement.setString(8, utente.getNumeroCivico());
            preparedStatement.setBoolean(9, utente.isAdmin());
            preparedStatement.setString(10, utente.getCf());

            result = preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("Utente aggiornato con successo nel database.");

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return result > 0;
    }
}
