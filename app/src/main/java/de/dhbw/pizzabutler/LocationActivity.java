package de.dhbw.pizzabutler;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    Location lastLocation;

    //Variablen, zur Anzeige der Daten (nicht unbedingt relevant)
    TextView l;
    TextView b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        buildGoogleApiClient();

        //Definition der Textausgabefelder
        l = (TextView) findViewById(R.id.textLaengenGrad);
        b = (TextView) findViewById(R.id.textBreitenGrad);

        //Connect with Google-API
        mGoogleApiClient.connect();
    }
    //instantiiert einen Google-API-Client mit der Location-API
    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //Verbindung ausstehend
    @Override
    public void onConnectionSuspended(int i) {}

    //Verbindung fehlgeschlagen
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        l.setText("Laengengrad: Da ist ein Fehler aufgetreten.");
        b.setText("Breitengrad: Da ist ein Fehler aufgetreten.");
    }

    //Verbindung erfolgreich
    @Override
    public void onConnected(Bundle connectionHint)
    {
        //Letzte Location abfragen
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //Umwandlung des Laengen- + Breitengrades in PLZ
        String location = getLocation(lastLocation.getLatitude(), lastLocation.getLongitude());

        //Split des Ergebnis-String
        String[] parts = location.split("-");

        //Ausgabe des Ergebnis
        String ort = parts[0];
        String plz = parts[1];

        b.setText("Sie befinden sich momentan in motherfucking " + ort + ".");
        l.setText("Dieser Ort hat die PLZ: " + plz);
    }

    //Umwandlung des Laengen- + Breitengrades in PLZ
    private String getLocation(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this, Locale.GERMAN);
        try {

        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            String ort = addresses.get(0).getLocality();
            String plz = addresses.get(0).getPostalCode();

            return ort + "-" + plz;

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}