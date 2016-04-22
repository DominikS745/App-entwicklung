package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 01.04.2016.
 */
public class Variante implements Serializable{
    private int varianteID;
    private String groesse;

    public Variante(String mGroesse) {
        groesse = mGroesse;
    }

    public int getVarianteID() {
        return varianteID;
    }

    public void setVarianteID(int varianteID) {
        this.varianteID = varianteID;
    }

    public String getGroesse() {
        return groesse;
    }

    public void setGroesse(String groesse) {
        this.groesse = groesse;
    }
}
