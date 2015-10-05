package de.dhbw.pizzabutler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistrierenActivity extends AppCompatActivity {

    private Spinner anredeSp;
    private EditText vornameET;
    private EditText nachnameET;
    private EditText strasseET;
    private EditText hausnummerET;
    private EditText plzET;
    private EditText ortET;
    private EditText emailET;
    private EditText passwortET;
    private EditText passwortCheckET;
    private CheckBox agb_check_CB;
    private String fehler = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren);

        anredeSp = (Spinner) findViewById(R.id.spinner_anrede);
        vornameET = (EditText) findViewById(R.id.vorname);
        nachnameET = (EditText) findViewById(R.id.nachname);
        strasseET = (EditText) findViewById(R.id.strasse);
        hausnummerET = (EditText) findViewById(R.id.hausnummer);
        plzET = (EditText) findViewById(R.id.plz);
        ortET = (EditText) findViewById(R.id.ort);
        emailET = (EditText) findViewById(R.id.email);
        passwortET = (EditText) findViewById(R.id.passwort_register);
        passwortCheckET = (EditText) findViewById(R.id.passwort_match);
        agb_check_CB = (CheckBox) findViewById(R.id.agb_check);


        /**
         * Validierung der Eingabedaten;
         * überprüft die verschiedenen Eingabefelder nach erfolgter Eingabe mit Ausnahme der Straße und Checkbox
         * Straße wird vor erfolgter Eingabe überprüft, da sie nicht leer sein darf
         * Checkbox wird x überprüft
         *
         */

        final String regexName = "^[A-ZÖÜÄ]{1}[a-züäöß]+$";
        final String regexDName = "^[A-ZÖÜÄ]{1}[a-züäöß]+[\\s-]{1}[A-ZÖÜÄ]{1}[a-züäoß]+$";
        final String regexPlz = "^[0-9]{5}$";
        final String regexHnr = "^[0-9]+[a-z]?$";

        //Validierung des Feldes Vorname nach erfolgter Eingabe
        vornameET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String vorname = vornameET.getText().toString();
                if ((!Pattern.matches(regexName, vorname) && !Pattern.matches(regexDName,vorname)) || vorname.length() < 2) {
                    fehler += "Bitte geben Sie einen gültigen Vornamen ein.";
                    vornameET.setError(getString(R.string.vorname_fehler));
                }
            }
        });

        //Validierung des Feldes Nachname nach erfolgter Eingabe
        nachnameET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String nachname = nachnameET.getText().toString();
                if ((!Pattern.matches(regexName, nachname) && !Pattern.matches(regexDName,nachname)) || nachname.length() < 4) {
                    fehler += "Bitte geben Sie einen gültigen Nachnamen ein.";
                    nachnameET.setError(getString(R.string.nachname_fehler));
                }
            }
        });

        //Validierung des Feldes Straße nach erfolgter Eingabe
        strasseET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a)
            {
                String strasse = strasseET.getText().toString();
                if (strasse.isEmpty()) {
                    fehler += "Bitte geben Sie eine Straße ein.";
                    strasseET.setError(getString(R.string.strasse_fehler));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Validierung des Feldes Hausnummer nach erfolgter Eingabe
        hausnummerET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String hausnummer = hausnummerET.getText().toString();
                if (!Pattern.matches(regexHnr, hausnummer) || hausnummer.length() > 5) {
                    fehler += "Bitte geben Sie eine gültige Hausnummer ein.";
                    hausnummerET.setError(getString(R.string.hnr_fehler));
                }
            }
        });

        //Validierung des Feldes PLZ nach erfolgter Eingabe
        plzET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String plz = plzET.getText().toString();
                if (!Pattern.matches(regexPlz, plz)) {
                    fehler += "Bitte geben Sie eine gültige PLZ ein.";
                    plzET.setError(getString(R.string.plz_fehler));
                }
            }
        });

        //Validierung des Feldes Ort nach erfolgter Eingabe
        ortET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String ort = ortET.getText().toString();
                if ((!Pattern.matches(regexName, ort) && !Pattern.matches(regexDName, ort)) || ort.length()< 3 || ort.length() > 32) {
                    fehler += "Bitte geben Sie einen gültigen Wohnort ein.";
                    ortET.setError(getString(R.string.ort_fehler));
                }

            }
        });

        //Validierung des Feldes Email nach erfolgter Eingabe
        emailET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String email = emailET.getText().toString();
                if (!(email.contains("@") && email.contains("."))) {
                    fehler += "Bitte geben Sie eine gültige Email-Adresse ein.";
                    emailET.setError(getString(R.string.email_fehler));
                }

            }
        });

        //Validierung des Feldes Passwort nach erfolgter Eingabe
        passwortET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String passwort = passwortET.getText().toString();
                if (passwort.length() < 8 || passwort.length() > 12) {
                    fehler += "Bitte geben Sie ein Passwort ein.";
                    passwortET.setError(getString(R.string.passwort_fehler));
                }

            }
        });

        //Validierung des Feldes PasswortCheck nach erfolgter Eingabe
        passwortCheckET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String passwort = passwortET.getText().toString();
                String passwortCheck = passwortCheckET.getText().toString();
                if(!passwort.equals(passwortCheck)) {
                    fehler += "Bitte geben sie Ihr Passwort erneut ein.";
                    passwortCheckET.setError(getString(R.string.passwort_check_fehler));
                }

            }
        });


        if (!agb_check_CB.isChecked()) {
            fehler += "Bitte akzeptieren Sie die AGB's und Datenschutzrichtlinien.";
            agb_check_CB.setError(getString(R.string.agb_fehler));
        }
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


        if (!fehler.isEmpty()) {
            Toast failure = Toast.makeText(this, fehler, Toast.LENGTH_SHORT);
            failure.show();
        } else {
            startActivity(nutzerDatenAnzeigen);
        }
    }
}
