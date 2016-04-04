package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.dhbw.pizzabutler_entities.Bestellposition;
import de.dhbw.pizzabutler_entities.Bestellung;
import de.dhbw.pizzabutler_entities.Produkt;
import de.dhbw.pizzabutler_entities.Zusatzbelag;

/**
 * Created by Marvin on 16.03.16.
 */
public class WarenkorbActivity extends BaseActivity {

    private WarenkorbListAdapter listAdapter;
    private ListView listView;

    //Custom Code
    private ArrayList<String> data;

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private Bestellung bestellung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_warenkorb);

        bestellung = (Bestellung) getIntent().getSerializableExtra("warenkorb");
        double lieferkosten = getIntent().getDoubleExtra("lieferkosten", 0);

        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

        // get the listview
        listView = (ListView) findViewById(R.id.listview_warenkorb);

        // preparing list data
        String produkt = "Test Produkt";
        data = new ArrayList<String>();
        data.add(produkt);
        //prepareListData();

        listAdapter = new WarenkorbListAdapter(this, data);

        // setting list adapter
        listView.setAdapter(listAdapter);

        //Testausgabe
        Bestellposition[] positionen = bestellung.getBestellpositionen();

        double bestellwert = 0;
        double gesamtpreis = 0;

        for(int i = 0; i<positionen.length; i++) {
            System.out.println("---------------");
            System.out.println(positionen[i].getPreis());
            System.out.println(positionen[i].getProdukt().getName());
            System.out.println(positionen[i].getVariante().getBezeichnung());
            bestellwert = bestellwert + positionen[i].getPreis();
            //zusatzbelaege to To
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
    /*
    public void prepareListData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.clear();
        listDataChild.clear();

        Bestellposition[] bestellpositionen = bestellung.getBestellpositionen();

        for(int i = 0; i<bestellpositionen.length; i++){
            List<String> data = new ArrayList<String>();
            System.out.println(bestellpositionen[i].getProdukt().getName());
            listDataHeader.add(bestellpositionen[i].getProdukt().getName());
            data.add(0, String.valueOf(bestellpositionen[i].getPreis()));
            data.add(1, bestellpositionen[i].getVariante().getBezeichnung());

            //Zusatzbelag[] zusatzbelage = bestellpositionen[i].getZusatzbelag();
            //for(int a = 0; a<zusatzbelage.length; a++){
            //    data.add(a+2, String.valueOf(bestellpositionen[i].getZusatzbelag()[a]));
            //}
            listDataChild.put(listDataHeader.get(i), data);
        }
    }
*/
    //OnClick für Weiter Button
    public void OnClickWeiter(View v){
        Intent intent = new Intent(this, RolleActivity.class);
        //intent.putExtra("Bestellung", bestellung);
        startActivity(intent);
    }
}
