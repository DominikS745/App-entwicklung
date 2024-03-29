package de.dhbw.pizzabutler;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import de.dhbw.pizzabutler_entities.Bestellung;

public class BestaetigungActivity extends BaseActivity {


    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_bestaetigung);

        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

        String zahlungsart = getIntent().getStringExtra("zahlungsart");

        TextView textView = (TextView) findViewById(R.id.text_bestaetigung);

        textView.setText("Vielen Dank für Ihre Bestellung. Eine Email wurde an die hinterlegte Adresse gesendet." +
                "Ihre Zahlungsart entspricht: " + zahlungsart);

    }

}
