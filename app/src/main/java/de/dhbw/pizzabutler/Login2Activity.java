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

import com.google.gson.JsonObject;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import de.dhbw.pizzabutler_entities.User;

public class Login2Activity extends BaseActivity {
    //Variablen für das Auslesen der Usereingaben
    private EditText eingabeUser;
    private EditText eingabePasswort;

    //Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

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
                String url = "http://pizzabutlerbackend.krihi.com/user/";

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
            //Starten einer neuen Activity: NutzerDatenAnzeigen
            Intent nutzerDatenAnzeigen = new Intent(Login2Activity.this, NutzerDatenActivity.class);

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
                final String url = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/user/login/";

                JsonObject obj = new JsonObject();
                obj.addProperty("email", email);
                obj.addProperty("passwort", passwort);

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                response = restTemplate.postForEntity(url, obj, User.class);

                //Ausgabe des Mock-Wertes
                System.out.println(response.getStatusCode());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //Setzen der User-ID (verhält sich ähnlich einer Session)
            SharedPreferences session = getSharedPreferences("id", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = session.edit();
            editor.putString("id", response.getBody().getId());
            editor.commit();

            //Starten einer neuen Activity: NutzerDatenAnzeigen
            Intent nutzerDatenAnzeigen = new Intent(Login2Activity.this, NutzerDatenActivity.class);

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

            startActivity(nutzerDatenAnzeigen);
        }
    }
}