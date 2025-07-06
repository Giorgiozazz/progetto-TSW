package it.unisa;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unisa.model.UtenteBean;
import it.unisa.utils.PasswordUtils;

public class UtenteModelDM implements UtenteModel {

    private static final String TABLE_NAME = "utente";

    @Override
    public synchronized void doSave(UtenteBean utente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String checkCFSQL = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE CF = ?";
        String checkEmailSQL = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE EMAIL = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();

            // Controllo Codice Fiscale già presente
            preparedStatement = connection.prepareStatement(checkCFSQL);
            preparedStatement.setString(1, utente.getCf());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new SQLException("Codice Fiscale già presente nel database.");
            }
            resultSet.close();
            preparedStatement.close();

            // Controllo Email già presente
            preparedStatement = connection.prepareStatement(checkEmailSQL);
            preparedStatement.setString(1, utente.getEmail());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new SQLException("Email già presente nel database.");
            }
            resultSet.close();
            preparedStatement.close();

            // Hash della password prima di salvarla
            String hashedPassword = null;
            try {
                hashedPassword = PasswordUtils.toHash(utente.getPassword());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new SQLException("Errore durante l'hashing della password.");
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
            preparedStatement.setString(10, hashedPassword);
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
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
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

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?";

        try {
            System.out.println("Tentativo di login:");
            System.out.println("Email inserita: " + email);

            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String dbEmail = rs.getString("EMAIL");
                String dbPassword = rs.getString("PASSWORD");

                // Verifica se la password hashata corrisponde a quella inserita
                if (dbEmail != null && dbPassword != null) {
                    try {
                        if (PasswordUtils.checkPassword(password, dbPassword)) {
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
                        }
                    } catch (NoSuchAlgorithmException e) {
                        System.out.println("Errore nell'hashing della password: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Credenziali errate o password non valida.");
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
                "NOME = ?, COGNOME = ?, EMAIL = ?, TELEFONO = ?, CITTA = ?, PROVINCIA = ?, " +
                "INDIRIZZO = ?, NUMERO_CIVICO = ?, IS_ADMIN = ? " +
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

    
    public synchronized UtenteBean doRetrieveByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UtenteBean utente = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, email);

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
 // Salva o aggiorna il token di reset e la sua scadenza per l'utente con l'email data
    public synchronized void saveResetToken(String email, String token) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE " + TABLE_NAME + " SET RESET_TOKEN = ?, TOKEN_SCADENZA = DATE_ADD(NOW(), INTERVAL 1 HOUR) WHERE EMAIL = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, email);

            int updatedRows = preparedStatement.executeUpdate();
            connection.commit();

            if (updatedRows == 0) {
                throw new SQLException("Nessun utente trovato con l'email: " + email);
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }
    }

    // Recupera l'utente tramite il token di reset (solo se token valido e non scaduto)
    public synchronized UtenteBean doRetrieveByResetToken(String token) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UtenteBean utente = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE RESET_TOKEN = ? AND TOKEN_SCADENZA > NOW()";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, token);

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
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }

        return utente;
    }

    // Aggiorna la password usando il token (e hash della nuova password)
    public synchronized void updatePasswordByToken(String token, String newPassword) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE " + TABLE_NAME + " SET PASSWORD = ?, RESET_TOKEN = NULL, TOKEN_SCADENZA = NULL WHERE reset_token = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();

            // Hash della nuova password
            String hashedPassword;
            try {
                hashedPassword = PasswordUtils.toHash(newPassword);
            } catch (NoSuchAlgorithmException e) {
                throw new SQLException("Errore durante l'hashing della password.");
            }

            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, token);

            int updatedRows = preparedStatement.executeUpdate();
            connection.commit();

            if (updatedRows == 0) {
                throw new SQLException("Token non valido o già usato.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }
    }

    // Opzionale: invalidare token manualmente (qui annullo il reset_token e token_expiry)
    public synchronized void invalidateResetToken(String token) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE " + TABLE_NAME + " SET RESET_TOKEN = NULL, TOKEN_SCADENZA = NULL WHERE reset_token = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, token);

            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            DriverManagerConnectionPool.releaseConnection(connection);
        }
    }

}