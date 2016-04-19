package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 23.03.2016.
 */
public class Produkt implements Serializable {
    private int produktID;
    private String name;
    private Preis[] preise;

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

    public Preis[] getPreis() {
        return preise;
    }

    public void setPreis(Preis[] preis) {
        this.preise = preise;
    }
}
