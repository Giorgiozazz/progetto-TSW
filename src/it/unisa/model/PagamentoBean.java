package it.unisa.model;

import java.util.Date;

public class PagamentoBean {
    private int idPagamento;
    private int idOrdine;
    private String metodoPagamento;
    private double anticipo;
    private int numRate;
    private double importoRata;
    private Date dataPagamento;
    private double iva;
    
    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(double anticipo) {
        this.anticipo = anticipo;
    }

    public int getNumRate() {
        return numRate;
    }

    public void setNumRate(int numRate) {
        this.numRate = numRate;
    }

    public double getImportoRata() {
        return importoRata;
    }

    public void setImportoRata(double importoRata) {
        this.importoRata = importoRata;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
