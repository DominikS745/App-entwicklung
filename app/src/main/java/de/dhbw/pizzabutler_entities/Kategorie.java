package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 23.03.2016.
 */
public class Kategorie implements Serializable {

    int kategorieID;
    String name;
    Variante[] variante;
    Produkt[] produkte;
    Zusatzbelag[] zusatzbelaege;

    public int getKategorieID() {
        return kategorieID;
    }

    public void setKategorieID(int kategorieID) {
        this.kategorieID = kategorieID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Variante[] getVariante() {
        return variante;
    }

    public void setVariante(Variante[] variante) {
        this.variante = variante;
    }

    public Produkt[] getProdukte() {
        return produkte;
    }

    public void setProdukte(Produkt[] produkte) {
        this.produkte = produkte;
    }

    public Zusatzbelag[] getZusatzbelaege() {
        return zusatzbelaege;
    }

    public void setZusatzbelaege(Zusatzbelag[] zusatzbelaege) {
        this.zusatzbelaege = zusatzbelaege;
    }
}
