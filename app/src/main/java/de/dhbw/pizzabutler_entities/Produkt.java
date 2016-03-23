package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 23.03.2016.
 */
public class Produkt implements Serializable {
    private int produktID;
    private String name;
    private String beschreibung;
    private float[] preis;

    public int getProduktID() {
        return produktID;
    }

    public void setProduktID(int produktID) {
        this.produktID = produktID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public float[] getPreis() {
        return preis;
    }

    public void setPreis(float[] preis) {
        this.preis = preis;
    }
}
