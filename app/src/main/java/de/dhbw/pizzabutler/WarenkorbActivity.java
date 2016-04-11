package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.dhbw.pizzabutler_adapter.WarenkorbListAdapter;
import de.dhbw.pizzabutler_entities.Bestellposition;
import de.dhbw.pizzabutler_entities.Bestellung;
import de.dhbw.pizzabutler_entities.Produkt;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_warenkorb);

        bestellung = (Bestellung) getIntent().getSerializableExtra("warenkorb");
        double lieferkosten = getIntent().getDoubleExtra("lieferkosten", 0);
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

        // get the listview
        listView = (ListView) findViewById(R.id.listview_warenkorb);

        prepareListData();

        listAdapter = new WarenkorbListAdapter(WarenkorbActivity.this, data, zusatzbelage, bestellpositions);

        // setting list adapter
        listView.setAdapter(listAdapter);

        //Testausgabe
        Bestellposition[] positionen = bestellung.getBestellpositionen();

        double bestellwert = 0;
        double gesamtpreis = 0;

        for(int i = 0; i<positionen.length; i++) {
            bestellwert = bestellwert + positionen[i].getPreis();
            //zusatzbelaege to Do
        }

        gesamtpreis = bestellwert + lieferkosten;

        //Füllen der Bestellung mit entsprechenden Werten
        bestellung.setRechnungsbeitrag(((float) gesamtpreis));

        //Definition und Befüllen der Kostenfelder im Warenkorb
        TextView bestellwertView = (TextView) findViewById(R.id.bestellwert);
        bestellwertView.setText(String.valueOf(bestellwert));
        TextView lieferkostenView = (TextView) findViewById(R.id.lieferkosten);
        lieferkostenView.setText(String.valueOf(lieferkosten));
        TextView gesamtpreisView = (TextView) findViewById(R.id.gesamtkosten);
        gesamtpreisView.setText(String.valueOf(gesamtpreis));

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
            ArrayList<WarenkorbItem> warenliste = listAdapter.getWarenliste();
            double preis = 0;
            for(int i = 0; i<warenliste.size(); i++){
                preis = preis + warenliste.get(i).getPreis();
            }
            if(preis < mindestbestellwert){
                Toast.makeText(this, "Bitte bestellen Sie etwas über dem Mindestbestellwert von " + mindestbestellwert + " €." ,Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(this, RolleActivity.class);
                intent.putExtra("Bestellung", bestellung);
                startActivity(intent);
            }
        }
    }
}
