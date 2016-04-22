package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 23.03.2016.
 */
public class Speisekarte implements Serializable {

    private Kategorie[] kategorien;
    private Variante[] variante;

    public Kategorie[] getKategorien() {
        return kategorien;
    }

    public void setKategorien(Kategorie[] kategorien) {
        this.kategorien = kategorien;
    }

    public Variante[] getVariante() {
        return variante;
    }

    public void setVariante(Variante[] variante) {
        this.variante = variante;
    }
}
