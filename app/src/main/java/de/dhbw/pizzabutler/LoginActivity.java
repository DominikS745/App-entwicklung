package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.dhbw.pizzabutler_entities.User;

public class LoginActivity extends BaseActivity {
    //Variablen für das Auslesen der Usereingaben
    private EditText eingabeUser;
    private EditText eingabePasswort;

    //Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_login);

        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

        //Übergabe der Eingabefelder in Variablen
        eingabeUser = (EditText) findViewById(R.id.benutzername);
        eingabePasswort = (EditText) findViewById(R.id.passwort_login);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * Aufruf der Aktivitaet RegistrierenActivity
     *
     * @param v Standard View
     */
    public void registrieren(View v) {

        Intent registrieren = new Intent(this, RegistrierenActivity.class);
        startActivity(registrieren);
    }

    /**
     * Ueberpruefung des LogIn und entsprechende Reaktion
     *
     * @param v Standard View
     */
    public void logIn(View v) {
        if (eingabeUser.getText().toString().contains("@") && eingabeUser.getText().toString().contains(".") && eingabePasswort.getText().toString().length() < 13 && eingabePasswort.getText().toString().length() > 7) {
            String passwort = eingabePasswort.getText().toString();
            String email = eingabeUser.getText().toString();
            Toast.makeText(this, "Login wird durchgeführt", Toast.LENGTH_LONG).show();
            new LoginThroughBackend(passwort, email).execute();

            Intent nutzerDatenAnzeigen = new Intent(this, NutzerDatenActivity.class);
        } else {
            Toast failure = Toast.makeText(this, getString(R.string.rückgabewert_minus1), Toast.LENGTH_SHORT);
            failure.show();
        }
    }

    class LoginThroughBackend extends AsyncTask<Void, Void, Void> {

        String passwort;
        String email;

        public LoginThroughBackend(String p, String e) {
            passwort = p;
            email = e;
        }


        @Override
        protected Void doInBackground(Void... params) {

            try {
                //Das zu versendende JSONObjekt
                JSONObject obj = new JSONObject();
                obj.put("email", email);
                obj.put("passwort", passwort);

                //Definition einer URL
                final String url = "http://pizzabutlerbackend.krihi.com/user/login/";

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                ResponseEntity<User> response = restTemplate.getForEntity(url, User.class, email, passwort );

                //Ausgabe des Mock-Wertes
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    //Anbindung an das Backend muss noch erfolgen
    public void onClickPasswortVergessen(View v) {
        /*
        Intent passwortVergessen = new Intent(this, LocationActivity.class);
        startActivity(passwortVergessen); */
    }

}
