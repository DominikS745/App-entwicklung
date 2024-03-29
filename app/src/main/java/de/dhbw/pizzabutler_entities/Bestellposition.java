package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 01.04.2016.
 */
public class Bestellposition implements Serializable{
    private int bestellpositionID;
    private int anzahl;
    private float preis;
    private Produkt produkt;
    private Variante variante;
    private Zusatzbelag[] zusatzbelag;
    private String produktbezeichnung;
    private String variantenbezeichnung;

    public int getBestellpositionID() {
        return bestellpositionID;
    }

    public void setBestellpositionID(int bestellpositionID) {
        this.bestellpositionID = bestellpositionID;
    }

    public float getPreis() {
        return preis;
    }

    public void setPreis(float preis) {
        this.preis = preis;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }

    public Variante getVariante() {
        return variante;
    }

    public void setVariante(Variante variante) {
        this.variante = variante;
    }

    public Zusatzbelag[] getZusatzbelag() {
        return zusatzbelag;
    }

    public void setZusatzbelag(Zusatzbelag[] zusatzbelag) {
        this.zusatzbelag = zusatzbelag;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public String getProduktbezeichnung() {
        return produktbezeichnung;
    }

    public void setProduktbezeichnung(String produktbezeichnung) {
        this.produktbezeichnung = produktbezeichnung;
    }

    public String getVariantenbezeichnung() {
        return variantenbezeichnung;
    }

    public void setVariantenbezeichnung(String variantenbezeichnung) {
        this.variantenbezeichnung = variantenbezeichnung;
    }
}
