package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 23.03.2016.
 */
public class Speisekarte implements Serializable {

    private Kategorie[] kategorien;

    public Kategorie[] getKategorien() {
        return kategorien;
    }

    public void setKategorien(Kategorie[] kategorien) {
        this.kategorien = kategorien;
    }
}
