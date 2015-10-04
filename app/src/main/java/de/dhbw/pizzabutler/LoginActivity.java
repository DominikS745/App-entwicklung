package de.dhbw.pizzabutler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
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
    Gson gson;
    ResponseLogin responseLoginObject;
    private static AsyncHttpClient client;
    private String user = "test";
    private String password = "test";
    private EditText eingabeUser;
    private EditText eingabePasswort;
    private String urlLogin = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/user/login";
    private boolean loginErfolgreich = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        if (user.equals(eingabeUser.getText().toString()) && password.equals(eingabePasswort.getText().toString())) {
            Intent nutzerDatenAnzeigen = new Intent(this, NutzerDatenActivity.class);
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

            if (backendConnection(entity)) {
                startActivity(nutzerDatenAnzeigen);
            }
            ;
        } else {
            Toast failure = Toast.makeText(this, "Email oder Passwort falsch", Toast.LENGTH_SHORT);
            failure.show();
        }
    }

    public boolean backendConnection(StringEntity entity) {
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client = new AsyncHttpClient();
        Log.i("Params", entity.toString());
        Log.i("URL", urlLogin);
        Log.i("Weiterleitung", String.valueOf(loginErfolgreich));
        client.post(LoginActivity.this, urlLogin, entity, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString.equals("0")) {
                    loginErfolgreich = true;
                } else if (responseString.equals("-1")) {
                    Log.i("Return -1", "Reading succes");
                } else if (responseString.equals("-2")) {
                    Log.i("Return -2", "Reading succes");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_404), Toast.LENGTH_SHORT).show();
                } else if (statusCode == 500) {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_500), Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Test", String.valueOf(statusCode));
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_else), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return loginErfolgreich;
    }

}
