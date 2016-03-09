package de.dhbw.pizzabutler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class StartActivity extends BaseActivity {

    //Variabeln fuer Location-Abfragen
    LocationManager locationManager;
    LocationListener listener;

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_start);


        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

    }

    //Nutzen der PLZ durch Location-Abfrage und Aufruf der Pizzeria-Liste
    private void callPizzaList(String plz){
        Intent intent = new Intent(this, ListPizzariaActivity.class);
        intent.putExtra("plz" , plz);
        startActivity(intent);
    }

    //Start der schrittweisen Location-Abfrage durch Button-Click
    public void onClickButton(View v) {
        instantiateLocationListener();
    }

    //Instantiierung der LocationListener und Aufruf der Location-Abfrage
    public void instantiateLocationListener(){
        //Instantiierung LocationManager + Listener mit vorgefertigten Funktionen
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationListener() {

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider){}

            //is ALWAYS called when Provider is not available
            public void onProviderDisabled(String provider) {}

            public void onLocationChanged(Location location) {}
        };

        checkLocation();
    }

    //fragt die aktuelle Location ab (aktuell: nur Netzwerkabfrage)
    private void checkLocation() {
        try {
            //fragt Locations ab, ohne dass diese sich aendern muss
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, listener);
            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

            /**
             * Wenn beide Provider keine Location zurueckgeben, wird ein Dialog aufgerufen, der zur Aktivierung
             * der Standortdienste auffordert
             */
            if(null == location) {
                callDialog();
            }

            else{
                //unregister Listener + anschließender Ausgabe der Location
                locationManager.removeUpdates(listener);
                getLocation(location);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Aufruf des Dialogs
    private void callDialog() {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //Aufruf der Einstellungen
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, 1);
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                                //Neuueberpruefung der Location
                                checkLocation();
                            }
                        })
                .setCancelable(false);

        //Anzeigen des Dialogfeldes
        builder.create().show();
    }

    //Rueckgabe der PLZ und Aufruf der weiterfuehrenden Anwendungslogik
    private void getLocation(Location location) {
        String locationString = convertLocation(location.getLatitude(), location.getLongitude());

        //Split des Ergebnis-String
        String[] parts = locationString.split("-");

        //Auswahl des Ergebnis
        String ort = parts[0];
        String plz = parts[1];

        callPizzaList(plz);
    }

    //Umwandlung des Laengen- + Breitengrades in PLZ und Ort
    private String convertLocation(double latitude, double longitude){
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

    //Aufruf nach Rückkehr von Einstellungen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        checkLocation();
    }
}
