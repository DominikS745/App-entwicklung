package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class StartActivity extends BaseActivity {

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_start);


        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

    }


    //Aufruf der Location-Abfrage (-Activity)
    public void onClickLocation(View v) {
        Intent findLocation = new Intent(this, LocationActivity.class);
        startActivity(findLocation);
    }

    //Aufruf der Pizzaria Liste
    public void onClickButton(View v) {
        Intent intent = new Intent(this, ListPizzariaActivity.class);
        startActivity(intent);
    }
}
