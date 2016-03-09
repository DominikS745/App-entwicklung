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

    //Aufruf der Pizzaria Liste mit Location-Abfrage
    public void onClickButton(View v) {
        Intent findLocation = new Intent(this, LocationActivity.class);
        startActivityForResult(findLocation, 400);
        System.out.println("TEST");
    }

    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        System.out.println("------------------------------------------");
        String plz = getIntent().getStringExtra("plz");
        System.out.println(plz);
        System.out.println("------------------------------------------");
        Intent listPizzerien = new Intent(this, ListPizzariaActivity.class);
        listPizzerien.putExtra("plz", plz);
        startActivity(listPizzerien);
    }
}
