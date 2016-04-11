package de.dhbw.pizzabutler_entities;

/**
 * Created by Schagrat on 04.04.2016.
 */
public class WarenkorbItem {
    private String bezeichnung;
    private double preis;
    private String variante;
    private int anzahl;
    private Zusatzbelag[] zusatzbelage;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getVariante() {
        return variante;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public Zusatzbelag[] getZusatzbelage() {
        return zusatzbelage;
    }

    public void setZusatzbelage(Zusatzbelag[] zusatzbelage) {
        this.zusatzbelage = zusatzbelage;
    }
}
