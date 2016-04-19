package de.dhbw.pizzabutler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Pattern;

import de.dhbw.pizzabutler_api.EditTextExtended;
import de.dhbw.pizzabutler_entities.Bestellung;
import de.dhbw.pizzabutler_entities.Gast;
import de.dhbw.pizzabutler_entities.User;
import de.dhbw.pizzabutler_entities.Zusatzbelag;

public class RegisterGastActivity extends AppCompatActivity {

    private Spinner anredeSp;
    private EditText vornameET;
    private EditText nachnameET;
    private EditText strasseET;
    private EditText hausnummerET;
    private EditText plzET;
    private EditText ortET;
    private EditText emailET;
    private EditText telefonnummerET;
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
    private boolean jHilf = false;

    private String json_email;
    private String json_ID;
    private String json_strasse;
    private String json_hausnummer;
    private String json_plz;
    private String json_ort;

    private String zahlungsart;
    private String restaurantID;
    private boolean lieferung;

    private Intent nutzerDatenAnzeigen;


    private Bestellung bestellung;

    //Error Icon Object
    Drawable errorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren_gast);

        anredeSp = (Spinner) findViewById(R.id.spinner_anrede);
        vornameET = (EditTextExtended) findViewById(R.id.vorname);
        nachnameET = (EditTextExtended) findViewById(R.id.nachname);
        strasseET = (EditTextExtended) findViewById(R.id.strasse);
        hausnummerET = (EditTextExtended) findViewById(R.id.hausnummer);
        plzET = (EditTextExtended) findViewById(R.id.plz);
        ortET = (EditTextExtended) findViewById(R.id.ort);
        telefonnummerET = (EditTextExtended) findViewById(R.id.telefonnummer);
        emailET = (EditTextExtended) findViewById(R.id.email);
        agb_check_CB = (CheckBox) findViewById(R.id.agb_check);
        accept_button = (Button) findViewById(R.id.weiter_button);

        bestellung = (Bestellung) getIntent().getSerializableExtra("bestellung");

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

        bestellung = (Bestellung) getIntent().getSerializableExtra("bestellung");
        zahlungsart = getIntent().getStringExtra("zahlungsart");
        lieferung = getIntent().getBooleanExtra("lieferung", false);
        restaurantID = getIntent().getStringExtra("restaurantID");

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

        //Validierung des Feldes Telefonnummer nach erfolgter Eingabe
        telefonnummerET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
                String telNr = telefonnummerET.getText().toString();
                if (telNr.length() < 9) {
                    telefonnummerET.setError("", errorIcon);
                    hHilf = false;
                } else {
                    telefonnummerET.setError(null);
                    hHilf = true;
                }
                changeButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String telNr = telefonnummerET.getText().toString();
                if (telNr.length() < 9) {
                    telefonnummerET.setError("", errorIcon);
                    hHilf = false;
                } else {
                    telefonnummerET.setError(null);
                    hHilf = true;
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

    /**
     * Weiterleitung zur Aktivität NutzerdatenActivity
     *
     * @param v Standard View
     */
    public void registrierungAbschliessen(View v) {

        //Pruefung der Internetverbindung
        if(!isOnline()){
            Toast.makeText(this, "Keine Internetverbindung möglich", Toast.LENGTH_SHORT).show();
        }
        else {
            //Uebergabe der Daten in die innere Klasse RegisterThroughBackend
            new RegisterThroughBackend(anredeSp.getSelectedItem().toString(), vornameET.getText().toString(),
                    nachnameET.getText().toString(), strasseET.getText().toString(),
                    hausnummerET.getText().toString(), ortET.getText().toString(), plzET.getText().toString(),
                    telefonnummerET.getText().toString(), emailET.getText().toString()).execute();
        }
    }

    //enable or disable button
    private void changeButton() {

        //Wenn keine Fehler vorhanden sind, aktiviere den Button
        if (aHilf && bHilf && cHilf && dHilf && eHilf && fHilf && gHilf && hHilf && jHilf) {
            accept_button.setEnabled(true);
        }

        //Wenn Fehler vorhanden sind, deaktiviere den Button
        else {
            accept_button.setEnabled(false);
        }
    }

    //Funktion zur Ueberpruefung der Internetverbindung
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(null != netInfo && (netInfo.getState()==NetworkInfo.State.CONNECTED || netInfo.getState()==NetworkInfo.State.CONNECTING)){
            return true;
        }
        else{
            return false;
        }
    }

    public void sendBestellung() {
        new BestellungThroughBackend().execute();
    }

    private class RegisterThroughBackend extends AsyncTask<Void, Void, Void> {

        ResponseEntity<Gast> response;
        String anrede;
        String vorname;
        String nachname;
        String strasse;
        String hausnummer;
        String plz;
        String ort;
        String telefonnummer;
        String email;

        public RegisterThroughBackend(String mAnrede, String mVorname, String mNachname, String mStrasse, String mHausnummer,
                                      String mOrt, String mPlz, String mTelefonnummer, String mEmail) {
            anrede = mAnrede;
            vorname = mVorname;
            nachname = mNachname;
            strasse = mStrasse;
            hausnummer = mHausnummer;
            ort = mOrt;
            plz = mPlz;
            telefonnummer = mTelefonnummer;
            email = mEmail;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Das zu versendende JSONObjekt
                JsonObject obj = new JsonObject();
                obj.addProperty("anrede", anrede);
                obj.addProperty("vorname", vorname);
                obj.addProperty("nachname", nachname);
                obj.addProperty("strasse", strasse);
                obj.addProperty("hausnummer", hausnummer);
                obj.addProperty("plz", plz);
                obj.addProperty("ort", ort);
                obj.addProperty("telefonnummer", telefonnummer);
                obj.addProperty("email", email);

                //Definition einer URL
                final String url = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/gast";

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

                response = restTemplate.postForEntity(url, obj, Gast.class);

                //Ausgabe des Statuscodes
                System.out.println(response.getStatusCode());

            } catch (Exception e) {
                Log.e("RegistrierenActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //Starten einer neuen Activity: NutzerDatenAnzeigen
            nutzerDatenAnzeigen = new Intent(RegisterGastActivity.this, BestaetigungActivity.class);

            json_email = response.getBody().getEmail();
            json_ID = response.getBody().getGastID();
            json_strasse = response.getBody().getStrasse();
            json_hausnummer = response.getBody().getHausnummer();
            json_plz = response.getBody().getPlz();
            json_ort = response.getBody().getOrt();


            nutzerDatenAnzeigen.putExtra("bestellung" , bestellung);
            nutzerDatenAnzeigen.putExtra("id", response.getBody().getGastID());
            nutzerDatenAnzeigen.putExtra("anrede", response.getBody().getAnrede());
            nutzerDatenAnzeigen.putExtra("vorname", response.getBody().getVorname());
            nutzerDatenAnzeigen.putExtra("nachname", response.getBody().getNachname());
            nutzerDatenAnzeigen.putExtra("strasse", response.getBody().getStrasse());
            nutzerDatenAnzeigen.putExtra("hausnummer", response.getBody().getHausnummer());
            nutzerDatenAnzeigen.putExtra("plz", response.getBody().getPlz());
            nutzerDatenAnzeigen.putExtra("ort", response.getBody().getOrt());
            nutzerDatenAnzeigen.putExtra("telefonnummer", response.getBody().getTelefonnummer());
            nutzerDatenAnzeigen.putExtra("email", response.getBody().getEmail());

            sendBestellung();
        }
    }

    class BestellungThroughBackend extends AsyncTask<Void, Void, Void> {

        public BestellungThroughBackend(Void... params) {

        }

        @Override
        protected Void doInBackground(Void... params) {

            ResponseEntity<String> response;

            try {
                //Definition einer URL
                final String url = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/bestellung/send";

                int variantenID = 0;

                JsonObject obj = new JsonObject();
                obj.addProperty("userID", 0);
                obj.addProperty("gastID", json_ID);
                obj.addProperty("strasse", json_strasse);
                obj.addProperty("email", json_email);
                obj.addProperty("hausnummer", json_hausnummer);
                obj.addProperty("plz", json_plz);
                obj.addProperty("ort", json_ort);
                obj.addProperty("restaurantID", restaurantID);
                obj.addProperty("bestellzeitpunkt", "");
                obj.addProperty("bestellinfo", "");
                obj.addProperty("rechnungsbetrag", bestellung.getRechnungsbeitrag());
                obj.addProperty("lieferung", lieferung);

                JsonArray jArray = new JsonArray();

                for(int i = 0; i<bestellung.getBestellpositionen().length; i++){
                    JsonObject object = new JsonObject();
                    object.addProperty("anzahl", bestellung.getBestellpositionen()[i].getAnzahl());
                    object.addProperty("preis", bestellung.getBestellpositionen()[i].getPreis());

                    if(bestellung.getBestellpositionen()[i].getVariantenbezeichnung().equals("klein")){
                        variantenID = 1;
                    }
                    else if(bestellung.getBestellpositionen()[i].getVariantenbezeichnung().equals("mittel")){
                        variantenID = 2;
                    }
                    else if(bestellung.getBestellpositionen()[i].getVariantenbezeichnung().equals("groß")){
                        variantenID = 3;
                    }

                    JsonObject produktObject = new JsonObject();
                    produktObject.addProperty("bezeichnung", bestellung.getBestellpositionen()[i].getProduktbezeichnung());
                    produktObject.addProperty("beschreibung", "");
                    produktObject.addProperty("produktID", 0);

                    JsonArray vArray = new JsonArray();
                    JsonObject vObject_1 = new JsonObject();
                    vObject_1.addProperty("varianteID", 0);
                    vObject_1.addProperty("preis", "");
                    JsonObject vObject_2 = new JsonObject();
                    vObject_2.addProperty("varianteID", 0);
                    vObject_2.addProperty("preis", "");

                    produktObject.add("varianten", vArray);

                    object.add("produkt", produktObject);
                    JsonObject variantenObject = new JsonObject();
                    variantenObject.addProperty("varianteID", variantenID);
                    variantenObject.addProperty("bezeichnung", "");
                    object.add("variante", variantenObject);

                    JsonArray zArray = new JsonArray();
                    Zusatzbelag[] belaege = bestellung.getBestellpositionen()[i].getZusatzbelag();
                    if(belaege != null) {
                        for (int a = 0; a < belaege.length; a++) {
                            JsonObject zObject = new JsonObject();
                            zObject.addProperty("zusatzbelagID", 0);
                            zObject.addProperty("name", bestellung.getBestellpositionen()[i].getZusatzbelag()[a].getName());
                            zObject.addProperty("preis", bestellung.getBestellpositionen()[i].getZusatzbelag()[a].getPreis());
                            zArray.add(zObject);
                        }
                    }
                    object.add("zusatzbelag", zArray);
                    jArray.add(object);
                }

                obj.add("bestellpositionen" , jArray);
                System.out.println(obj.toString());

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                response = restTemplate.postForEntity(url, obj, String.class);

                //Ausgabe des Mock-Wertes
                System.out.println(response.getStatusCode());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Starten einer neuen Activity: NutzerDatenAnzeigen
            nutzerDatenAnzeigen.putExtra("zahlungsart", zahlungsart);
            startActivity(nutzerDatenAnzeigen);
        }
    }
}