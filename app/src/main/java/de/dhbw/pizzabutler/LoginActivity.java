package de.dhbw.pizzabutler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class LoginActivity extends AppCompatActivity {
    //Variablen für die Backend-Anbindung
    private static AsyncHttpClient client;
    private String urlLogin = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/user/login";
    private boolean loginErfolgreich = false; //Steuert die Weiterleitung an die nächste Activity
    //Variablen für das Auslesen der Usereingaben
    private EditText eingabeUser;
    private EditText eingabePasswort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Übergabe der Eingabefelder in Variablen
        eingabeUser = (EditText) findViewById(R.id.benutzername);
        eingabePasswort = (EditText) findViewById(R.id.passwort_login);
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
            Intent nutzerDatenAnzeigen = new Intent(this, NutzerDatenActivity.class);
            //Erzeugen des JSON-Objektes welches übertragen wird ans Backend
            JSONObject jsonParams = new JSONObject();
            StringEntity entity = null;
            try {
                jsonParams.put("email", eingabeUser.getText().toString());
                jsonParams.put("passwort", eingabePasswort.getText().toString());
                entity = new StringEntity(jsonParams.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //Aufruf der Anbindung an das Backend, Übergabe des JSONObjektes als String
            if (backendConnection(entity)) {
                startActivity(nutzerDatenAnzeigen);
            }
        } else {
            Toast failure = Toast.makeText(this, getString(R.string.rückgabewert_minus1), Toast.LENGTH_SHORT);
            failure.show();
        }
    }

    /**
     * Herstellen einer Verbindung an das Backend - Reaktion auf den HTTP Status Code und den Rückgabestring des Backend
     *
     * @param entity - JSONObject das in einem String gespeichert wurde, enthält die Nutzereingaben in folgenden Key-Value-Paaren:
     *               email:benutzereingabe und passwort:benutzereingabe
     * @return Rückgabewert nur true wenn zum Backend connected + der Aufruf erfolgreich anhand der Rückgabe
     * Mögliche Rückgaben + Bedeutung:
     * 0 --> Aufruf erfolgreich | -1 --> email oder passwort ist ungültig | -2 es gibt den User offenbar nicht
     * <p/>
     * In onSucces wird auf den StatusCode 200 reagiert und dann auf die Response des Backend eingegangen
     * In onFailure werden Fehlerhafte StatusCodes abgefangen und teilweise speziell behandelt
     */
    public boolean backendConnection(StringEntity entity) {
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client = new AsyncHttpClient();
        client.post(LoginActivity.this, urlLogin, entity, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString.equals("0")) {
                    loginErfolgreich = true;
                } else if (responseString.equals("-1")) {
                    Toast.makeText(LoginActivity.this, getString(R.string.rückgabewert_minus1), Toast.LENGTH_SHORT).show();
                } else if (responseString.equals("-2")) {
                    Toast.makeText(LoginActivity.this, getString(R.string.rückgabewert_minus2), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_404), Toast.LENGTH_SHORT).show();
                } else if (statusCode == 500) {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_500), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_else), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return loginErfolgreich;
    }

    //Anbindung an das Backend muss noch erfolgen
    public void onClickPasswortVergessen(View v) {
        /*
        Intent passwortVergessen = new Intent(this, LocationActivity.class);
        startActivity(passwortVergessen); */
    }

}
