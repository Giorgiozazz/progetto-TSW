package it.unisa;

import java.sql.SQLException;
import it.unisa.model.UtenteBean;

public interface UtenteModel {
    public void doSave(UtenteBean utente) throws SQLException;
    public UtenteBean doRetrieveByKey(String cf) throws SQLException;
    public boolean doDelete(String cf) throws SQLException;
    public UtenteBean doRetrieveByEmailPassword(String email, String password) throws SQLException;
    public UtenteBean doRetrieveByEmail(String email) throws SQLException;
    public boolean doUpdate(UtenteBean utente) throws SQLException;

    // Metodi aggiunti per il reset della password
    public void saveResetToken(String email, String token) throws SQLException;
    public UtenteBean doRetrieveByResetToken(String token) throws SQLException;
    public void updatePasswordByToken(String token, String newPassword) throws SQLException;
    public void invalidateResetToken(String token) throws SQLException; // opzionale ma utile
}
