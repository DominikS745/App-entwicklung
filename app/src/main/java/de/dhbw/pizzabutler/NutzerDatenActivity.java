package de.dhbw.pizzabutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NutzerDatenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutzerdaten);

        String anrede = getIntent().getStringExtra("anrede");
        String vorname = getIntent().getStringExtra("vorname");
        String nachname = getIntent().getStringExtra("nachname");
        String strasse = getIntent().getStringExtra("strasse");
        String hausnummer = getIntent().getStringExtra("hausnummer");
        String plz = getIntent().getStringExtra("plz");
        String ort = getIntent().getStringExtra("ort");
        String email = getIntent().getStringExtra("email");
        String passwort = getIntent().getStringExtra("passwort");
        boolean agb_check = getIntent().getBooleanExtra("agb_check", false);

        String nameDisplayText = getString(R.string.name);
        String adresseDisplayText = getString(R.string.adresse);
        String emailDisplayText = getString(R.string.email);
        String agbCheckDisplayText = getString(R.string.zustimmungAGB);

        TextView nutzerDaten = (TextView) findViewById(R.id.nutzerdaten);
        nutzerDaten.setText(nameDisplayText + anrede + " " + vorname + " " + nachname + "\n" + adresseDisplayText + strasse + " " + hausnummer + " " + plz + " " + ort + "\n" + emailDisplayText + email + "\n" + agbCheckDisplayText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickLogout(View v) {
        Intent logout = new Intent(this, StartActivity.class);
        startActivity(logout);
    }

}
