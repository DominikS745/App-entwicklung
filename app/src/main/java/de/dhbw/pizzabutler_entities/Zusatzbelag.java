package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 23.03.2016.
 */
public class Zusatzbelag implements Serializable{
    private int zusatzbelagID;
    private String name;
    private float preis;

    public int getZusatzbelagID() {
        return zusatzbelagID;
    }

    public void setZusatzbelagID(int zusatzbelagID) {
        this.zusatzbelagID = zusatzbelagID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPreis() {
        return preis;
    }

    public void setPreis(float preis) {
        this.preis = preis;
    }
}
