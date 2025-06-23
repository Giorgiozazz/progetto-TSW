package it.unisa;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.model.ProductBean;

public interface ProductModel {
    
    // Salva una nuova automobile nel database
    public void doSave(ProductBean automobile) throws SQLException;

    // Elimina un'auto tramite il VIN
    public boolean doDelete(String vin) throws SQLException;

    // Recupera un'auto tramite il VIN
    public ProductBean doRetrieveByKey(String vin) throws SQLException;
    
    // Recupera tutte le automobili, con un possibile ordinamento
    public Collection<ProductBean> doRetrieveAll(String order) throws SQLException;
    
    // Recupera tutte le automobili (incluse vendute), accessibile solo per admin
    public Collection<ProductBean> doRetrieveAllAdmin(String order) throws SQLException;

	public Collection<ProductBean> doSearchByKeyword(String trim) throws SQLException ;
	
	public Collection<ProductBean> doSearchByKeywordAdmin(String trim) throws SQLException ;
}
