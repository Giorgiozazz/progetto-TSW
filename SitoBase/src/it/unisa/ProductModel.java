package it.unisa;

import java.sql.SQLException;
import java.util.Collection;

import it.unisa.bean.ProductBean;

public interface ProductModel {
    
    // Salva una nuova automobile nel database
    public void doSave(ProductBean automobile) throws SQLException;

    // Elimina un'auto tramite il VIN
    public boolean doDelete(String vin) throws SQLException;

    // Recupera un'auto tramite il VIN
    public ProductBean doRetrieveByKey(String vin) throws SQLException;
    
    // Recupera tutte le automobili, con un possibile ordinamento
    public Collection<ProductBean> doRetrieveAll(String order) throws SQLException;
}
