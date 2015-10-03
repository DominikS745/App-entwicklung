package de.dhbw.pizzabutler;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.DefaultBHttpClientConnection;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class LoginActivity extends AppCompatActivity {

    //Variablen für die Backend-Anbindung
    Gson gson;
    ResponseLogin responseLoginObject;
    AsyncHttpClient client;
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
            RequestParams params = new RequestParams("?email", eingabeUser.getText().toString());
            params.put("passwort", eingabePasswort.getText().toString());
            if (backendConnection(params)) {
                startActivity(nutzerDatenAnzeigen);
            }
            ;
        } else {
            Toast failure = Toast.makeText(this, "Email oder Passwort falsch", Toast.LENGTH_SHORT);
            failure.show();
        }
    }

    public boolean backendConnection(RequestParams params) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://pizzabutlerentwbak.krihi.com/entwicklung/rest/user/login");

        try {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(); 
            parameters.add(new BasicNameValuePair("email", eingabeUser.getText().toString())); 
            parameters.add(new BasicNameValuePair("passwort", eingabePasswort.getText().toString()));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));

            HttpResponse response = httpClient.execute(httpPost);

            loginErfolgreich = true;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        client = new AsyncHttpClient();
        Log.i("Params", params.toString());
        Log.i("URL", urlLogin);
        Log.i("Weiterleitung", String.valueOf(loginErfolgreich));
        client.post(LoginActivity.this, urlLogin, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseStr = new String(responseBody);
                gson = new Gson();
                responseLoginObject = gson.fromJson(responseStr, ResponseLogin.class);
                loginErfolgreich = true;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_404), Toast.LENGTH_SHORT).show();
                } else if (statusCode == 500) {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_500), Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Test", String.valueOf(statusCode));
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_else), Toast.LENGTH_SHORT).show();
                }
            }
        }); */

        return loginErfolgreich;
    }

}
