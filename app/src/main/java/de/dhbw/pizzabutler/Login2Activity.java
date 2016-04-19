package de.dhbw.pizzabutler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import de.dhbw.pizzabutler_entities.Bestellung;
import de.dhbw.pizzabutler_entities.User;
import de.dhbw.pizzabutler_entities.Zusatzbelag;

public class Login2Activity extends BaseActivity {
    //Variablen für das Auslesen der Usereingaben
    private EditText eingabeUser;
    private EditText eingabePasswort;


    private String userID;
    private String email;
    private String strasse;
    private String hausnummer;
    private String plz;
    private String ort;
    private boolean lieferung;
    private double rechnungsbetrag;
    private String restaurantID;


    //Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private Bestellung bestellung;
    private Intent nutzerDatenAnzeigen;
    private String zahlungsart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences session = getSharedPreferences("id", MODE_PRIVATE);

        if (!(session.getString("id", "")).equals("")) {
            new UserThroughBackend(session.getString("id", "")).execute();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_login_2);

        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);
        restaurantID = getIntent().getStringExtra("restaurantID");
        bestellung = (Bestellung) getIntent().getSerializableExtra("bestellung");
        zahlungsart = getIntent().getStringExtra("zahlungsart");
        lieferung = getIntent().getBooleanExtra("lieferung", false);

        //Übergabe der Eingabefelder in Variablen
        eingabeUser = (EditText) findViewById(R.id.benutzername);
        eingabePasswort = (EditText) findViewById(R.id.passwort_login);
    }

    /**
     * Ueberpruefung des LogIn und entsprechende Reaktion
     *
     * @param v Standard View
     */
    public void logIn(View v) {
        if (eingabeUser.getText().toString().contains("@") && eingabeUser.getText().toString().contains(".") && eingabePasswort.getText().toString().length() < 13 && eingabePasswort.getText().toString().length() > 7) {
            //Ueberpruefung der Internetverbindung
            if(!isOnline()){
                Toast.makeText(this, "Keine Internetverbindung möglich", Toast.LENGTH_SHORT).show();
            }
            else{
                String passwort = eingabePasswort.getText().toString();
                String email = eingabeUser.getText().toString();
                Toast.makeText(this, "Login wird durchgeführt", Toast.LENGTH_SHORT).show();
                new LoginThroughBackend(passwort, email).execute();
            }
        } else {
            Toast failure = Toast.makeText(this, getString(R.string.rückgabewert_minus1), Toast.LENGTH_SHORT);
            failure.show();
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
                obj.addProperty("userID", userID);
                obj.addProperty("gastID", 0);
                obj.addProperty("strasse", strasse);
                obj.addProperty("email", email);
                obj.addProperty("hausnummer", hausnummer);
                obj.addProperty("plz", plz);
                obj.addProperty("ort", ort);
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

    class LoginThroughBackend extends AsyncTask<Void, Void, Void> {

        ResponseEntity<User> response;
        String passwort;
        String email;

        public LoginThroughBackend(String p, String e) {
            passwort = p;
            email = e;
        }


        @Override
        protected Void doInBackground(Void... params) {

            try {
                //Definition einer URL
                String url = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/user/login/";

                JsonObject obj = new JsonObject();
                obj.addProperty("email", email);
                obj.addProperty("passwort", passwort);

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                response = restTemplate.postForEntity(url, obj, User.class);

                //LoginID zum Abgreifen der Daten nutzen
                String id = response.getBody().getId();
                url = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/user/";
                url += id;

                response = restTemplate.getForEntity(url, User.class);


                //Ausgabe des Mock-Wertes
                System.out.println(response.getStatusCode());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(response.getStatusCode().value() == 200) {
                //Setzen der User-ID (verhält sich ähnlich einer Session)
                SharedPreferences session = getSharedPreferences("id", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = session.edit();
                editor.putString("id", response.getBody().getId());
                editor.commit();

                //Starten einer neuen Activity: NutzerDatenAnzeigen
                nutzerDatenAnzeigen = new Intent(Login2Activity.this, BestaetigungActivity.class);


                email = response.getBody().getId();
                userID = response.getBody().getId();
                strasse = response.getBody().getStrasse();
                hausnummer = response.getBody().getHausnr();
                plz = response.getBody().getPlz();
                ort = response.getBody().getOrt();

                nutzerDatenAnzeigen.putExtra("bestellung" , bestellung);
                nutzerDatenAnzeigen.putExtra("id", response.getBody().getId());
                nutzerDatenAnzeigen.putExtra("anrede", response.getBody().getAnrede());
                nutzerDatenAnzeigen.putExtra("vorname", response.getBody().getVorname());
                nutzerDatenAnzeigen.putExtra("nachname", response.getBody().getNachname());
                nutzerDatenAnzeigen.putExtra("strasse", response.getBody().getStrasse());
                nutzerDatenAnzeigen.putExtra("hausnummer", response.getBody().getHausnr());
                nutzerDatenAnzeigen.putExtra("plz", response.getBody().getPlz());
                nutzerDatenAnzeigen.putExtra("ort", response.getBody().getOrt());
                nutzerDatenAnzeigen.putExtra("telefonnummer", response.getBody().getTelefonnummer());
                nutzerDatenAnzeigen.putExtra("passwort", response.getBody().getPasswort());
                nutzerDatenAnzeigen.putExtra("email", response.getBody().getEmail());

                sendBestellung();
            }
            else{
                Toast.makeText(getApplicationContext(), "Username oder Passwort falsch." , Toast.LENGTH_SHORT).show();
            }
        }
    }

    class UserThroughBackend extends AsyncTask<Void, Void, Void> {

        ResponseEntity<User> response;
        String id;

        public UserThroughBackend(String mId) {
            id = mId;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                //Definition einer URL
                String url = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/user/";

                url += id;

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                response = restTemplate.getForEntity(url, User.class);

                //Ausgabe des Mock-Wertes
                System.out.println(response.getStatusCode());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Befüllen des Intents mit Nutzerdaten

            nutzerDatenAnzeigen = new Intent(Login2Activity.this, BestaetigungActivity.class);

            email = response.getBody().getId();
            userID = response.getBody().getId();
            strasse = response.getBody().getStrasse();
            hausnummer = response.getBody().getHausnr();
            plz = response.getBody().getPlz();
            ort = response.getBody().getOrt();

            nutzerDatenAnzeigen.putExtra("id", response.getBody().getId());
            nutzerDatenAnzeigen.putExtra("anrede", response.getBody().getAnrede());
            nutzerDatenAnzeigen.putExtra("vorname", response.getBody().getVorname());
            nutzerDatenAnzeigen.putExtra("nachname", response.getBody().getNachname());
            nutzerDatenAnzeigen.putExtra("strasse", response.getBody().getStrasse());
            nutzerDatenAnzeigen.putExtra("hausnummer", response.getBody().getHausnr());
            nutzerDatenAnzeigen.putExtra("plz", response.getBody().getPlz());
            nutzerDatenAnzeigen.putExtra("ort", response.getBody().getOrt());
            nutzerDatenAnzeigen.putExtra("telefonnummer", response.getBody().getTelefonnummer());
            nutzerDatenAnzeigen.putExtra("passwort", response.getBody().getPasswort());
            nutzerDatenAnzeigen.putExtra("email", response.getBody().getEmail());

            sendBestellung();
        }
    }
}