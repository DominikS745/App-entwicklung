package de.dhbw.pizzabutler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NutzerDatenActivity extends BaseActivity {
    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_nutzerdaten);

        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

        String id = getIntent().getStringExtra("id");

        String anrede = getIntent().getStringExtra("anrede");
        String vorname = getIntent().getStringExtra("vorname");
        String nachname = getIntent().getStringExtra("nachname");

        String strasse = getIntent().getStringExtra("strasse");
        String hausnummer = getIntent().getStringExtra("hausnummer");

        String plz = getIntent().getStringExtra("plz");
        String ort = getIntent().getStringExtra("ort");

        String telefonnummer = getIntent().getStringExtra("telefonnummer");
        String email = getIntent().getStringExtra("email");

        String nameDisplayText = getString(R.string.name);
        String adresseDisplayText = getString(R.string.adresse);
        String emailDisplayText = getString(R.string.email);
        String agbCheckDisplayText = getString(R.string.zustimmungAGB);

        TextView nutzerdaten_id = (TextView) findViewById(R.id.nutzerdaten_ID);
        TextView nutzerdaten_name = (TextView) findViewById(R.id.nutzerdaten_Name);
        TextView nutzerdaten_adresse_1 = (TextView) findViewById(R.id.nutzerdaten_Adresse_1);
        TextView nutzerdaten_adresse_2 = (TextView) findViewById(R.id.nutzerdaten_Adresse_2);
        TextView nutzerdaten_telnr = (TextView) findViewById(R.id.nutzerdaten_telefonnummer);
        TextView nutzerdaten_email = (TextView) findViewById(R.id.nutzerdaten_email);

        nutzerdaten_id.setText("Ihre User-ID: " + id);
        nutzerdaten_name.setText("Ihr Name: " + anrede + " " + vorname + " " + nachname);
        nutzerdaten_adresse_1.setText("Ihre Adresse: " + strasse + " " + hausnummer);
        nutzerdaten_adresse_2.setText(plz + " " + ort);
        nutzerdaten_telnr.setText("Ihre Telefonnummer: " + telefonnummer);
        nutzerdaten_email.setText("Ihre Email-Adresse: " + email);
    }

    public void onClickLogout(View v) {
        Intent logout = new Intent(this, StartActivity.class);

        //Löschen der User-ID (verhält sich ähnlich einer Session)
        SharedPreferences session = getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();
        editor.clear();
        editor.commit();

        startActivity(logout);
    }

    public void onClickStart(View v) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }



}
