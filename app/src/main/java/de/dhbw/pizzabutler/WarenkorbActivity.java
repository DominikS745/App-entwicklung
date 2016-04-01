package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import de.dhbw.pizzabutler_entities.Bestellposition;
import de.dhbw.pizzabutler_entities.Bestellung;

/**
 * Created by Marvin on 16.03.16.
 */
public class WarenkorbActivity extends BaseActivity {

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_warenkorb);

        Bestellung bestellung = (Bestellung) getIntent().getSerializableExtra("warenkorb");

        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

        //Testausgabe
        Bestellposition[] positionen = bestellung.getBestellpositionen();

        for(int i = 0; i<positionen.length; i++) {
            System.out.println("---------------");
            System.out.println(positionen[i].getPreis());
            System.out.println(positionen[i].getProdukt().getName());
            System.out.println(positionen[i].getVariante().getBezeichnung());

            //Zusatzbelaege to do
        }

    }

    //OnClick für Weiter Button
    public void OnClickWeiter(View v){
        Intent intent = new Intent(this, RolleActivity.class);
        startActivity(intent);
    }
}
