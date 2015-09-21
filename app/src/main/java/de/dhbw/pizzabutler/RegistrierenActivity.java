package de.dhbw.pizzabutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrierenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren);
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

    /**
     * Weiterleitung zur Aktivität NutzerdatenActivity
     *
     * @param v Standard View
     */
    public void registrierungAbschliessen(View v) {
        //Benutzereingaben abgreifen
        Spinner anredeSp = (Spinner) findViewById(R.id.spinner_anrede);
        EditText vornameET = (EditText) findViewById(R.id.vorname);
        EditText nachnameET = (EditText) findViewById(R.id.nachname);
        EditText strasseET = (EditText) findViewById(R.id.strasse);
        EditText hausnummerET = (EditText) findViewById(R.id.hausnummer);
        EditText plzET = (EditText) findViewById(R.id.plz);
        EditText ortET = (EditText) findViewById(R.id.ort);
        EditText emailET = (EditText) findViewById(R.id.email);
        EditText passwortET = (EditText) findViewById(R.id.passwort_register);
        CheckBox agb_check_CB = (CheckBox) findViewById(R.id.agb_check);

        //Uebergabe der Daten in die NutzerDaten Aktivitaet
        Intent nutzerDatenAnzeigen = new Intent(this, NutzerDatenActivity.class);
        nutzerDatenAnzeigen.putExtra("anrede", anredeSp.getSelectedItem().toString());
        nutzerDatenAnzeigen.putExtra("vorname", vornameET.getText().toString());
        nutzerDatenAnzeigen.putExtra("nachname", nachnameET.getText().toString());
        nutzerDatenAnzeigen.putExtra("strasse", strasseET.getText().toString());
        nutzerDatenAnzeigen.putExtra("hausnummer", hausnummerET.getText().toString());
        nutzerDatenAnzeigen.putExtra("plz", plzET.getText().toString());
        nutzerDatenAnzeigen.putExtra("ort", ortET.getText().toString());
        nutzerDatenAnzeigen.putExtra("email", emailET.getText().toString());
        nutzerDatenAnzeigen.putExtra("passwort", passwortET.getText().toString());
        nutzerDatenAnzeigen.putExtra("agb_check", agb_check_CB.isChecked());
        startActivity(nutzerDatenAnzeigen);
    }
}
