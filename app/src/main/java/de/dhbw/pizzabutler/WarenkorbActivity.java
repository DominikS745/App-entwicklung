package de.dhbw.pizzabutler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import de.dhbw.pizzabutler_adapter.WarenkorbListAdapter;
import de.dhbw.pizzabutler_entities.Bestellposition;
import de.dhbw.pizzabutler_entities.Bestellung;
import de.dhbw.pizzabutler_entities.WarenkorbItem;
import de.dhbw.pizzabutler_entities.Zusatzbelag;

/**
 * Created by Marvin on 16.03.16.
 */
public class WarenkorbActivity extends BaseActivity {

    private WarenkorbListAdapter listAdapter;
    private ListView listView;

    //Custom Code
    private ArrayList<WarenkorbItem> data;

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private Bestellung bestellung;
    private Bestellposition[] bestellpositions;
    private double mindestbestellwert;
    private double bestellwert;
    private double lieferkosten;
    private double gesamtpreis;

    //Listener und Preferences Element (Vorsorge gegen Garbage Collection)
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences preferences;

    private boolean checkZahlung;

    //GUI-Elemente
    private TextView gesamtpreisView;
    private TextView lieferkostenView;
    private TextView bestellwertView;
    private CheckBox paypalCB;
    private CheckBox ecCB;
    private CheckBox barCB;
    private CheckBox abholungCB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_warenkorb);

        bestellung = (Bestellung) getIntent().getSerializableExtra("warenkorb");
        lieferkosten = getIntent().getDoubleExtra("lieferkosten", 0);
        mindestbestellwert = getIntent().getDoubleExtra("mindestbestellwert", 0);
        Zusatzbelag[] zusatzbelage = (Zusatzbelag[]) getIntent().getSerializableExtra("zusatzbelage");
        bestellpositions = new Bestellposition[bestellung.getBestellpositionen().length];

        for(int i = 0; i<bestellung.getBestellpositionen().length; i++){
            bestellpositions[i] = bestellung.getBestellpositionen()[i];
        }

        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

        //Startangabe der Preisberechnung
        Bestellposition[] positionen = bestellung.getBestellpositionen();

        bestellwert = 0;
        gesamtpreis = 0;

        checkZahlung = false;

        for(int i = 0; i<positionen.length; i++) {
            bestellwert = bestellwert + positionen[i].getPreis();
        }

        gesamtpreis = bestellwert + lieferkosten;

        //Füllen der Bestellung mit entsprechenden Werten
        bestellung.setRechnungsbeitrag(((float) gesamtpreis));

        //Definition und Befüllen der Kostenfelder im Warenkorb
        bestellwertView = (TextView) findViewById(R.id.bestellwert);
        bestellwertView.setText(String.valueOf(bestellwert));
        lieferkostenView = (TextView) findViewById(R.id.lieferkosten);
        lieferkostenView.setText(String.valueOf(lieferkosten));
        gesamtpreisView = (TextView) findViewById(R.id.gesamtkosten);
        gesamtpreisView.setText(String.valueOf(gesamtpreis));

        //Definition der Checkboxen
        paypalCB = (CheckBox) findViewById(R.id.checkbox_paypal);
        barCB = (CheckBox) findViewById(R.id.checkbox_bar);
        ecCB = (CheckBox) findViewById(R.id.checkbox_EC);
        abholungCB = (CheckBox) findViewById(R.id.checkbox_abholung);

        //Definition der Listener für die Checkboxen
        paypalCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

           @Override
           public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

               if(paypalCB.isChecked()) {
                   Toast.makeText(getApplicationContext(), "Sie bezahlen mit Paypal." , Toast.LENGTH_SHORT).show();
                   barCB.setClickable(false);
                   ecCB.setClickable(false);
                   checkZahlung = true;
               }
               else {
                   barCB.setClickable(true);
                   ecCB.setClickable(true);
                   checkZahlung = false;
               }
           }
           }
        );
        ecCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

           @Override
           public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

               if(ecCB.isChecked()) {
                   Toast.makeText(getApplicationContext(), "Sie bezahlen mit EC-Karte." , Toast.LENGTH_SHORT).show();
                   barCB.setClickable(false);
                   paypalCB.setClickable(false);
                   checkZahlung = true;
               }
               else {
                   barCB.setClickable(true);
                   paypalCB.setClickable(true);
                   checkZahlung = false;
               }
           }
       }
        );
        barCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

           @Override
           public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

               if(barCB.isChecked()) {
                   Toast.makeText(getApplicationContext(), "Sie bezahlen mit Bargeld." , Toast.LENGTH_SHORT).show();
                   paypalCB.setClickable(false);
                   ecCB.setClickable(false);
                   checkZahlung = true;
               }
               else {
                   paypalCB.setClickable(true);
                   ecCB.setClickable(true);
                   checkZahlung = false;
               }
           }
       }
        );
        abholungCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                      if (abholungCB.isChecked()) {
                                                          Toast.makeText(getApplicationContext(), "Sie holen Ihre Bestellung selbst ab.", Toast.LENGTH_SHORT).show();
                                                          lieferkosten = 0;
                                                          lieferkostenView.setText(String.valueOf(lieferkosten));
                                                          berechneKostenAnzeige();
                                                      } else {
                                                          lieferkosten = getIntent().getDoubleExtra("lieferkosten", 0);
                                                          lieferkosten = Math.floor(lieferkosten * 100) / 100;
                                                          lieferkostenView.setText(String.valueOf(lieferkosten));
                                                          berechneKostenAnzeige();
                                                      }
                                                  }
                                              }
        );

        // get the listview
        listView = (ListView) findViewById(R.id.listview_warenkorb);

        prepareListData();

        listAdapter = new WarenkorbListAdapter(WarenkorbActivity.this, data, zusatzbelage, bestellpositions);

        // setting list adapter
        listView.setAdapter(listAdapter);

        preferences = getSharedPreferences("bestellwert", Context.MODE_PRIVATE);

        //Register SharedPreferences Listener
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                float ergebnis = prefs.getFloat(key, 0);
                bestellwert = ergebnis;
                bestellwert = Math.floor(bestellwert * 100) / 100;
                bestellwertView.setText(String.valueOf(bestellwert));
                berechneKostenAnzeige();
            }
        };

        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    //Befüllen der Daten für die Liste
    public void prepareListData(){
        data = new ArrayList<WarenkorbItem>();

        Bestellposition[] bestellpositionen = bestellung.getBestellpositionen();

        for(int i = 0; i<bestellpositionen.length; i++){
            WarenkorbItem warenkorbItem = new WarenkorbItem();
            warenkorbItem.setBezeichnung(bestellpositionen[i].getProdukt().getName());
            warenkorbItem.setPreis(bestellpositionen[i].getPreis());
            warenkorbItem.setVariante(bestellpositionen[i].getVariante().getBezeichnung());
            warenkorbItem.setAnzahl(1);

            data.add(i, warenkorbItem);
        }
    }

    //OnClick für Weiter Button
    public void OnClickWeiter(View v){


        if(listAdapter.getWarenliste() == null) {
            Toast.makeText(this, "Bitte bestellen Sie etwas." ,Toast.LENGTH_SHORT).show();
        }
        else{

            if(checkZahlung == false){
                Toast.makeText(this, "Bitte wählen Sie eine Zahlungsmethode aus." , Toast.LENGTH_SHORT).show();
            }
            else {
                ArrayList<WarenkorbItem> warenliste = listAdapter.getWarenliste();
                if (bestellwert < mindestbestellwert) {
                    Toast.makeText(this, "Bitte bestellen Sie etwas über dem Mindestbestellwert von " + mindestbestellwert + " €.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, RolleActivity.class);
                    intent.putExtra("Bestellung", bestellung);
                    startActivity(intent);
                }
            }
        }
    }

    public void berechneKostenAnzeige(){
        gesamtpreis = lieferkosten + bestellwert;
        gesamtpreis = Math.floor(gesamtpreis * 100) / 100;
        gesamtpreisView.setText(String.valueOf(gesamtpreis));
        bestellung.setRechnungsbeitrag((float) gesamtpreis);
    }
}
