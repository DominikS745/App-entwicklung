package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 19.04.2016.
 */
public class Preis implements Serializable{
    float preise;
    int varianteID;

    public float getPreis() {
        return preise;
    }

    public void setPreis(float preise) {
        this.preise = preise;
    }

    public int getVarianteID() {
        return varianteID;
    }

    public void setVarianteID(int varianteID) {
        this.varianteID = varianteID;
    }
}
