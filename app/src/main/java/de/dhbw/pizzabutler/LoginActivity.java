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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private String user = "test@mail.com";
    private String password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        EditText eingabeUser = (EditText) findViewById(R.id.benutzername);
        EditText eingabePasswort = (EditText) findViewById(R.id.passwort_login);

        RequestParams params = new RequestParams();
        params.put("email",eingabeUser.getText().toString());
        params.put("passwort",eingabePasswort.getText().toString());
        invokeWS(params);

        if(user.equals(eingabeUser.getText().toString()) && password.equals(eingabePasswort.getText().toString())){
            Intent nutzerDatenAnzeigen = new Intent(this, NutzerDatenActivity.class);
            startActivity(nutzerDatenAnzeigen);
        } else {
            Toast failure = Toast.makeText(this, "Email oder Passwort falsch", Toast.LENGTH_SHORT);
            failure.show();
        }
    }

    public void invokeWS(RequestParams params) {
        //Aufruf des Client + Uebergabe der Eingabewerte
        AsyncHttpClient loginConnect = new AsyncHttpClient();
        String uri = "http://pizzabutlerentwbak.krihi.com/entwicklung/rest/user/login";
        loginConnect.post(uri, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = responseBody.toString();
                    JSONObject login = new JSONObject(response);
                    String userVorhanden = login.getString("Erfolgreich");
                    if (user.equals("0")) {
                        Intent nutzerDatenAnzeigen = new Intent(LoginActivity.this, NutzerDatenActivity.class);
                        startActivity(nutzerDatenAnzeigen);
                    }
                    else if (user.equals("-1")) {
                        Toast.makeText(LoginActivity.this, getString(R.string.status_code_1), Toast.LENGTH_SHORT).show();
                    }
                    else if (user.equals("-2")) {
                        Toast.makeText(LoginActivity.this, getString(R.string.status_code_2), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, getString(R.string.catch_block), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_404), Toast.LENGTH_SHORT).show();
                }
                else if (statusCode == 500) {
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_500), Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Test", String.valueOf(statusCode));
                    Toast.makeText(LoginActivity.this, getString(R.string.status_code_else), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
