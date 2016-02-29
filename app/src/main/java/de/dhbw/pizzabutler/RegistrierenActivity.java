package de.dhbw.pizzabutler;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import de.dhbw.pizzabutler_api.EditTextExtended;

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
    private Button accept_button;

    //Hilfsvariabeln zur Buttonaktivierung / -deaktivierung
    private boolean aHilf = false;
    private boolean bHilf = false;
    private boolean cHilf = false;
    private boolean dHilf = false;
    private boolean eHilf = false;
    private boolean fHilf = false;
    private boolean gHilf = false;
    private boolean hHilf = false;
    private boolean iHilf = false;
    private boolean jHilf = false;

    //Error Icon Object
    Drawable errorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren);

        anredeSp = (Spinner) findViewById(R.id.spinner_anrede);
        vornameET = (EditTextExtended) findViewById(R.id.vorname);
        nachnameET = (EditTextExtended) findViewById(R.id.nachname);
        strasseET = (EditTextExtended) findViewById(R.id.strasse);
        hausnummerET = (EditTextExtended) findViewById(R.id.hausnummer);
        plzET = (EditTextExtended) findViewById(R.id.plz);
        ortET = (EditTextExtended) findViewById(R.id.ort);
        emailET = (EditTextExtended) findViewById(R.id.email);
        passwortET = (EditText) findViewById(R.id.passwort_register);
        passwortCheckET = (EditText) findViewById(R.id.passwort_match);
        agb_check_CB = (CheckBox) findViewById(R.id.agb_check);
        accept_button = (Button) findViewById(R.id.weiter_button);


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

        /**
         * Instantiierung des Drawable ErrorIcon
         * Veralteter Aufruf um dem min. SDK von 16 zu entsprechen
         */

        //noinspection deprecation
        errorIcon = getResources().getDrawable(R.drawable.pizzabutler_erroricon_old);
        errorIcon.setBounds(new Rect(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight()));

        //Validierung des Feldes Vorname nach erfolgter Eingabe
        vornameET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String vorname = vornameET.getText().toString();
                if ((!Pattern.matches(regexName, vorname) && !Pattern.matches(regexDName, vorname)) || vorname.length() < 2) {
                    vornameET.setError("", errorIcon);
                    aHilf = false;
                } else {
                    vornameET.setError(null);
                    aHilf = true;
                }
                changeButton();
            }
        });

        //Validierung des Feldes Nachname nach erfolgter Eingabe
        nachnameET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String nachname = nachnameET.getText().toString();
                if ((!Pattern.matches(regexName, nachname) && !Pattern.matches(regexDName, nachname)) || nachname.length() < 4) {
                    nachnameET.setError("", errorIcon);
                    bHilf = false;
                } else {
                    nachnameET.setError(null);
                    bHilf = true;
                }
                changeButton();
            }
        });

        //Validierung des Feldes Straße nach erfolgter Eingabe
        strasseET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
                String strasse = strasseET.getText().toString();
                if (strasse.isEmpty()) {
                    strasseET.setError("", errorIcon);
                    cHilf = false;
                } else {
                    strasseET.setError(null);
                    cHilf = true;
                }
                changeButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String strasse = strasseET.getText().toString();
                if (strasse.isEmpty()) {
                    strasseET.setError("", errorIcon);
                    cHilf = false;
                } else {
                    strasseET.setError(null);
                    cHilf = true;
                }
                changeButton();
            }
        });

        //Validierung des Feldes Hausnummer nach erfolgter Eingabe
        hausnummerET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String hausnummer = hausnummerET.getText().toString();
                if (!Pattern.matches(regexHnr, hausnummer) || hausnummer.length() > 5) {
                    hausnummerET.setError("", errorIcon);
                    dHilf = false;
                } else {
                    hausnummerET.setError(null);
                    dHilf = true;
                }
                changeButton();
            }
        });

        //Validierung des Feldes PLZ nach erfolgter Eingabe
        plzET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String plz = plzET.getText().toString();
                if (!Pattern.matches(regexPlz, plz)) {
                    plzET.setError("", errorIcon);
                    eHilf = false;
                } else {
                    plzET.setError(null);
                    eHilf = true;
                }
                changeButton();
            }
        });

        //Validierung des Feldes Ort nach erfolgter Eingabe
        ortET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String ort = ortET.getText().toString();
                if ((!Pattern.matches(regexName, ort) && !Pattern.matches(regexDName, ort)) || ort.length() < 3 || ort.length() > 32) {
                    ortET.setError("", errorIcon);
                    fHilf = false;
                } else {
                    ortET.setError(null);
                    fHilf = true;
                }
                changeButton();
            }
        });

        //Validierung des Feldes Email nach erfolgter Eingabe
        emailET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = emailET.getText().toString();
                if (!(email.contains("@") && email.contains("."))) {
                    emailET.setError("", errorIcon);
                    gHilf = false;
                } else {
                    emailET.setError(null);
                    gHilf = true;
                }
                changeButton();
            }
        });

        //Validierung des Feldes Passwort nach erfolgter Eingabe
        passwortET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwort = passwortET.getText().toString();
                String passwortCheck = passwortCheckET.getText().toString();
                if (passwort.length() < 8 || passwort.length() > 12) {
                    passwortET.setError(getString(R.string.passwort_fehler), errorIcon);
                    hHilf = false;
                } else {
                    hHilf = true;
                    passwortET.setError(null);
                }
                if (!passwort.equals(passwortCheck)) {
                    passwortCheckET.setError(getString(R.string.passwort_check_fehler), errorIcon);
                    iHilf = false;
                } else {
                    iHilf = true;
                    passwortCheckET.setError(null);
                }
                changeButton();
            }
        });

        //Validierung des Feldes PasswortCheck nach erfolgter Eingabe
        passwortCheckET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwort = passwortET.getText().toString();
                String passwortCheck = passwortCheckET.getText().toString();
                if (!passwort.equals(passwortCheck)) {
                    passwortCheckET.setError(getString(R.string.passwort_check_fehler), errorIcon);
                    iHilf = false;
                } else {
                    iHilf = true;
                }
                changeButton();
            }
        });

        //Überprüfung der Checkbox (clicked or not clicked)
        agb_check_CB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!agb_check_CB.isChecked()) {
                    agb_check_CB.setError(getString(R.string.agb_fehler), errorIcon);
                    jHilf = false;
                } else {
                    agb_check_CB.setError(null); // EditText überschreiben setError(...,null)
                    jHilf = true;
                }
                changeButton();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        // return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * Weiterleitung zur Aktivität NutzerdatenActivity
     *
     * @param v Standard View
     */
    public void registrierungAbschliessen(View v) {
        //Uebergabe der Daten in die innere Klasse RegisterThroughBackend

        new RegisterThroughBackend(anredeSp.getSelectedItem().toString(),vornameET.getText().toString(),
                nachnameET.getText().toString(), strasseET.getText().toString(),
                hausnummerET.getText().toString(), plzET.getText().toString(), ortET.getText().toString(),
                passwortET.getText().toString(), emailET.getText().toString()).execute();

        Intent nutzerDatenAnzeigen = new Intent(this, NutzerDatenActivity.class);

        nutzerDatenAnzeigen.putExtra("vorname", vornameET.getText().toString());
        nutzerDatenAnzeigen.putExtra("nachname", nachnameET.getText().toString());
        nutzerDatenAnzeigen.putExtra("anrede", anredeSp.getSelectedItem().toString());
        nutzerDatenAnzeigen.putExtra("email", emailET.getText().toString());
        nutzerDatenAnzeigen.putExtra("passwort", passwortET.getText().toString());
        nutzerDatenAnzeigen.putExtra("strasse", strasseET.getText().toString());
        nutzerDatenAnzeigen.putExtra("hausnummer", hausnummerET.getText().toString());
        nutzerDatenAnzeigen.putExtra("plz", plzET.getText().toString());
        nutzerDatenAnzeigen.putExtra("ort", ortET.getText().toString());

        startActivity(nutzerDatenAnzeigen);
    }

    //enable or disable button
    private void changeButton() {

        //Wenn keine Fehler vorhanden sind, aktiviere den Button
        if (aHilf && bHilf && cHilf && dHilf && eHilf && fHilf && gHilf && hHilf && iHilf && jHilf) {
            accept_button.setEnabled(true);
        }

        //Wenn Fehler vorhanden sind, deaktiviere den Button
        else {
            accept_button.setEnabled(false);
        }
    }

    private class RegisterThroughBackend extends AsyncTask<Void, Void, Void> {

        String anrede;
        String vorname;
        String nachname;
        String strasse;
        String hausnummer;
        String plz;
        String ort;
        String passwort;
        String email;

        public RegisterThroughBackend(String mAnrede, String mVorname, String mNachname, String mStrasse, String mHausnummer,
                                      String mOrt, String mPlz, String mPasswort, String mEmail) {
            anrede = mAnrede;
            vorname = mVorname;
            nachname = mNachname;
            strasse = mStrasse;
            hausnummer = mHausnummer;
            ort = mOrt;
            plz = mPlz;
            email = mEmail;
            passwort = mPasswort;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Das zu versendende JSONObjekt
                JSONObject obj = new JSONObject();
                obj.put("anrede", anrede);
                obj.put("vorname", vorname);
                obj.put("nachname", nachname);
                obj.put("strasse", strasse);
                obj.put("hausnr", hausnummer);
                obj.put("plz", plz);
                obj.put("ort", ort);
                obj.put("passwort", passwort);
                obj.put("email", email);

                //Definition einer URL
                final String url = "http://pizzaButlerBackend.krihi.com/user";

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                String response = restTemplate.postForObject(url, obj, String.class);

                //Ausgabe des Mock-Wertes
                System.out.println(response);

            } catch (Exception e) {
                Log.e("RegistrierenActivity", e.getMessage(), e);
            }

            return null;
        }
    }
}
