package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.dhbw.pizzabutler_entities.Bestellposition;
import de.dhbw.pizzabutler_entities.Bestellung;

/**
 * Created by Marvin on 16.03.16.
 */
public class WarenkorbActivity extends BaseActivity {

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

    //OnClick für Weiter Button
    public void OnClickWeiter(View v){
        Intent intent = new Intent(this, RolleActivity.class);
        intent.putExtra("Bestellung", bestellung);
        startActivity(intent);
    }
}
