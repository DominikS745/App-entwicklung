package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 01.04.2016.
 */
public class Bestellung implements Serializable{

    private int bestellungID;
    private int pizzeriaID;
    private String bestellzeitpunkt;
    private float rechnungsbeitrag;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String ort;
    private Bestellposition[] bestellpositionen;

    public int getBestellungID() {
        return bestellungID;
    }

    public void setBestellungID(int bestellungID) {
        this.bestellungID = bestellungID;
    }

    public int getPizzeriaID() {
        return pizzeriaID;
    }

    public void setPizzeriaID(int pizzeriaID) {
        this.pizzeriaID = pizzeriaID;
    }

    public String getBestellzeitpunkt() {
        return bestellzeitpunkt;
    }

    public void setBestellzeitpunkt(String bestellzeitpunkt) {
        this.bestellzeitpunkt = bestellzeitpunkt;
    }

    public float getRechnungsbeitrag() {
        return rechnungsbeitrag;
    }

    public void setRechnungsbeitrag(float rechnungsbeitrag) {
        this.rechnungsbeitrag = rechnungsbeitrag;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Bestellposition[] getBestellpositionen() {
        return bestellpositionen;
    }

    public void setBestellpositionen(Bestellposition[] bestellpositionen) {
        this.bestellpositionen = bestellpositionen;
    }
}
