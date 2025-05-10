package it.unisa;

import java.io.Serializable;

public class ProductBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String vin;
    private String modello;
    private String marca;
    private Double prezzo;  // Modificato da double a Double
    private int anno;
    private int kilometri;
    private String tipoCarburante;
    private String targa;
    private int porte;
    private String tipo;
    private boolean climatizzatore;
    private String ruoteMotrici;
    private boolean schermoIntegrato;
    private int cilindrata;
    private int posti;
    private String tipoCambio;
    private int rapporti;
    private Integer potenzaElettrica; // Modificato da int a Integer
    private String colore;
    private int potenza;
    private String trazione;
    private int peso;

    public ProductBean() {
        vin = "";
        modello = "";
        marca = "";
        prezzo = 0.0;
        anno = 0;
        kilometri = 0;
        tipoCarburante = "";
        targa = "";
        porte = 0;
        tipo = "";
        climatizzatore = false;
        ruoteMotrici = "";
        schermoIntegrato = false;
        cilindrata = 0;
        posti = 0;
        tipoCambio = "";
        rapporti = 0;
        potenzaElettrica = 0;
        colore = "";
        potenza = 0;
        trazione = "";
        peso = 0;
    }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getModello() { return modello; }
    public void setModello(String modello) { this.modello = modello; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public Double getPrezzo() { return prezzo; }  // Modificato da double a Double
    public void setPrezzo(Double prezzo) { this.prezzo = prezzo; }  // Modificato da double a Double

    public int getAnno() { return anno; }
    public void setAnno(int anno) { this.anno = anno; }

    public int getKilometri() { return kilometri; }
    public void setKilometri(int kilometri) { this.kilometri = kilometri; }

    public String getTipoCarburante() { return tipoCarburante; }
    public void setTipoCarburante(String tipoCarburante) { this.tipoCarburante = tipoCarburante; }

    public String getTarga() { return targa; }
    public void setTarga(String targa) { this.targa = targa; }

    public int getPorte() { return porte; }
    public void setPorte(int porte) { this.porte = porte; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public boolean isClimatizzatore() { return climatizzatore; }
    public void setClimatizzatore(boolean climatizzatore) { this.climatizzatore = climatizzatore; }

    public String getRuoteMotrici() { return ruoteMotrici; }
    public void setRuoteMotrici(String ruoteMotrici) { this.ruoteMotrici = ruoteMotrici; }

    public boolean isSchermoIntegrato() { return schermoIntegrato; }
    public void setSchermoIntegrato(boolean schermoIntegrato) { this.schermoIntegrato = schermoIntegrato; }

    public int getCilindrata() { return cilindrata; }
    public void setCilindrata(int cilindrata) { this.cilindrata = cilindrata; }

    public int getPosti() { return posti; }
    public void setPosti(int posti) { this.posti = posti; }

    public String getTipoCambio() { return tipoCambio; }
    public void setTipoCambio(String tipoCambio) { this.tipoCambio = tipoCambio; }

    public int getRapporti() { return rapporti; }
    public void setRapporti(int rapporti) { this.rapporti = rapporti; }

    public Integer getPotenzaElettrica() { return potenzaElettrica; }  // Modificato da int a Integer
    public void setPotenzaElettrica(Integer potenzaElettrica) { this.potenzaElettrica = potenzaElettrica; }  // Modificato da int a Integer

    public String getColore() { return colore; }
    public void setColore(String colore) { this.colore = colore; }

    public int getPotenza() { return potenza; }
    public void setPotenza(int potenza) { this.potenza = potenza; }

    public String getTrazione() { return trazione; }
    public void setTrazione(String trazione) { this.trazione = trazione; }

    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }

    @Override
    public String toString() {
        return marca + " " + modello + " (" + anno + "), " + prezzo + " EUR, " + kilometri + " km, " + tipoCarburante
                + ", Targa: " + (targa != null ? targa : "N/A");
    }
}
