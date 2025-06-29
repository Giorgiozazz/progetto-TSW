package it.unisa.model;

import java.util.Date;

public class OrdineBean {
    private int idOrdine;
    private String cf;
    private Date dataOrdine;
    private String statoOrdine;
    private double totale;
    private String tipoConsegna;
    private String indirizzoSpedizione;
    private String numeroCivicoSpedizione;
    private String cittaSpedizione;
    private String provinciaSpedizione;


    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public String getStatoOrdine() {
        return statoOrdine;
    }

    public void setStatoOrdine(String statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public String getTipoConsegna() {
        return tipoConsegna;
    }

    public void setTipoConsegna(String tipoConsegna) {
        this.tipoConsegna = tipoConsegna;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public void setIndirizzoSpedizione(String indirizzoSpedizione) {
        this.indirizzoSpedizione = indirizzoSpedizione;
    }

    public String getNumeroCivicoSpedizione() {
        return numeroCivicoSpedizione;
    }

    public void setNumeroCivicoSpedizione(String numeroCivicoSpedizione) {
        this.numeroCivicoSpedizione = numeroCivicoSpedizione;
    }

    public String getCittaSpedizione() {
        return cittaSpedizione;
    }

    public void setCittaSpedizione(String cittaSpedizione) {
        this.cittaSpedizione = cittaSpedizione;
    }

    public String getProvinciaSpedizione() {
        return provinciaSpedizione;
    }

    public void setProvinciaSpedizione(String provinciaSpedizione) {
        this.provinciaSpedizione = provinciaSpedizione;
    }
}
