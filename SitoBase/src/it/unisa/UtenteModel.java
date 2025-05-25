package it.unisa;

import java.sql.SQLException;

import it.unisa.model.UtenteBean;

public interface UtenteModel {
    public void doSave(UtenteBean utente) throws SQLException;
    public UtenteBean doRetrieveByKey(String cf) throws SQLException;
    public boolean doDelete(String cf) throws SQLException;
    public UtenteBean doRetrieveByEmailPassword(String email, String password) throws SQLException;
	boolean doUpdate(UtenteBean utente) throws SQLException;
}
