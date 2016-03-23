package de.dhbw.pizzabutler_entities;

import java.io.Serializable;

/**
 * Created by Schagrat on 02.03.2016.
 */
public class Pizzeria implements Serializable{

    String restaurantID;
    String name;
    String beschreibung;
    Oeffnungszeiten[] oeffnungszeiten;
    Double mindestbestellwert;
    String strasse;
    String hausnummer;
    Double lieferkosten;
    String plz;
    String ort;
    String email;
    String bild;

    public String getId() {
        return restaurantID;
    }

    public void setId(String id) {
        this.restaurantID = id;
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

    public Oeffnungszeiten[] getOeffnungszeiten() {
        return oeffnungszeiten;
    }

    public void setOeffnungszeiten(Oeffnungszeiten[] oeffnungszeiten) {
        this.oeffnungszeiten = oeffnungszeiten;
    }

    public Double getMindestbestellwert() {
        return mindestbestellwert;
    }

    public void setMindestbestellwert(Double mindestbestellwert) {
        this.mindestbestellwert = mindestbestellwert;
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

    public void setHausnummer(String hausnr) {
        this.hausnummer = hausnummer;
    }

    public Double getLieferkosten() {
        return lieferkosten;
    }

    public void setLieferkosten(Double lieferkosten) {
        this.lieferkosten = lieferkosten;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBild() {
        return bild;
    }

    public void setBild(String bild) {
        this.bild = bild;
    }
}
