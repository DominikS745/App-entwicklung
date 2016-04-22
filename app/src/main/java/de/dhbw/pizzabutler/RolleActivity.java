package de.dhbw.pizzabutler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.dhbw.pizzabutler_entities.Bestellung;

public class RolleActivity extends BaseActivity {

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private Bestellung bestellung;
    private String restaurantID;
    private String zahlungsart;
    private boolean lieferung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_rolle_bestellung);

        bestellung = (Bestellung) getIntent().getSerializableExtra("Bestellung");
        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
        restaurantID = getIntent().getStringExtra("restaurantID");
        zahlungsart = getIntent().getStringExtra("zahlungsweise");
        lieferung = getIntent().getBooleanExtra("lieferung", false);
    }

    //On Click Methoden für die Buttons

    public void OnClickLogin(View v){
        Intent intent = new Intent(this, Login2Activity.class);
        intent.putExtra("bestellung", bestellung);
        intent.putExtra("restaurantID", restaurantID);
        intent.putExtra("zahlungsart", zahlungsart);
        intent.putExtra("lieferung", lieferung);
        startActivity(intent);

    }

    public void OnClickGast(View v){
        Intent intent = new Intent(this, RegisterGastActivity.class);
        intent.putExtra("bestellung", bestellung);
        intent.putExtra("restaurantID", restaurantID);
        intent.putExtra("zahlungsart", zahlungsart);
        intent.putExtra("lieferung", lieferung);
        startActivity(intent);
    }
}
