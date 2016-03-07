package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.support.Base64;
import org.springframework.web.client.RestTemplate;

import de.dhbw.pizzabutler_entities.Pizzeria;

/**
 * Created by Marvin on 28.02.16.
 */
public class ListPizzariaActivity extends BaseActivity {

    ListView listView;

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    //Custom Code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_pizzaria_liste);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listview_pizzaria);

        new ListThroughBackend().execute();

        // Defined Array values to show in ListView
        String[] values = new String[]{"Pizzaria 1", "Pizzaria 2", "Pizzaria 3", "Pizzaria 4", "Pizzaria 5",
                "Pizzaria 6", "Pizzaria 7", "Pizzaria 8", "Pizzaria 9", "Pizzaria 10", "Pizzaria 11"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);


        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                //provisorische ID; die echte ID muss über das Pizzeria-Objekt aufgerufen werden
                new DetailThroughBackend(itemValue).execute();
/*
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
*/
                //Aufruf des Pizzaria Profils
                Intent intent = new Intent(ListPizzariaActivity.this, PizzariaProfilActivity.class);
                startActivity(intent);
            }

        });


        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

    }





    //Verarbeitung des Bilds
    public Bitmap processPicture(String base64) {
        try {
            byte[] byteArray = Base64.decode(base64);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            //Provisorische Anzeige (die vermutlich das Layout etwas zerlegt. Später löschen!
            ImageView image = (ImageView) findViewById(R.id.imageView1);
            image.setImageBitmap(bitmap);

            return bitmap;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private class ListThroughBackend extends AsyncTask<Void, Void, Void> {

        ResponseEntity<Pizzeria[]> response;

        public ListThroughBackend(){

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Definition einer URL
                final String url = "http://pizzaButlerBackend.krihi.com/pizzeria";

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                response = restTemplate.getForEntity(url, Pizzeria[].class);

                //Ausgabe des Statuscodes
                System.out.println(response.getStatusCode());

                Pizzeria[] pizzerien = response.getBody();

                //Hier sollte die Listenuebersicht der Pizzerien befuellt werden
                for(int i=0; i<pizzerien.length; i++){
                    System.out.println("Name: " + pizzerien[i].getName());
                    System.out.println("Hausnummer: " + pizzerien[i].getHausnummer());
                    System.out.println("Lieferkosten: " + pizzerien[i].getLieferkosten());
                    System.out.println("PLZ: " + pizzerien[i].getPlz());
                    System.out.println("Ort: " + pizzerien[i].getOrt());
                    System.out.println("Straße: " + pizzerien[i].getStrasse());
                    System.out.println("Öffnungszeiten: " + pizzerien[i].getOeffnungszeiten().length);
                    System.out.println("Von :" + pizzerien[i].getOeffnungszeiten()[i].getVon());
                    System.out.println("Bis :" + pizzerien[i].getOeffnungszeiten()[i].getBis());
                    System.out.println("Tag :" + pizzerien[i].getOeffnungszeiten()[i].getTag());
                    //Verarbeitung des Bilds
                    processPicture(pizzerien[i].getBild());
                }

            } catch (Exception e) {
                Log.e("RegistrierenActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }

    private class DetailThroughBackend extends AsyncTask<Void, Void, Void> {

        ResponseEntity<Pizzeria> response;
        String id;

        public DetailThroughBackend(String pId){
            id = pId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Definition einer URL
                final String url = "http://pizzaButlerBackend.krihi.com/pizzeria";

                //Kommunikation mit Backend über ein REST-Template
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                response = restTemplate.getForEntity(url, Pizzeria.class, id);

                //Ausgabe des Statuscodes
                System.out.println(response.getStatusCode());

            } catch (Exception e) {
                Log.e("RegistrierenActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Starten der Detailansicht einer Pizzeria
            // - Kommentierung entfernen und Activity-Bezeichnung anpassen,sobald implementiert
            /*Intent detailansicht = new Intent(ListPizzariaActivity.this, DetailPizzariaActivity.class);

            Bitmap bitmap = processPicture(response.getBody().getBild());

            detailansicht.putExtra("name", response.getBody().getName());
            detailansicht.putExtra("anrede", response.getBody().getBeschreibung());
            detailansicht.putExtra("vorname", response.getBody().getMindestbestellwert());
            detailansicht.putExtra("nachname", response.getBody().getOeffnungszeiten());
            detailansicht.putExtra("strasse", response.getBody().getStrasse());
            detailansicht.putExtra("hausnummer", response.getBody().getHausnummer());
            detailansicht.putExtra("plz", response.getBody().getPlz());
            detailansicht.putExtra("ort", response.getBody().getOrt());
            detailansicht.putExtra("passwort", response.getBody().getLieferkosten());
            detailansicht.putExtra("email", response.getBody().getEmail());
            detailansicht.putExtra("bild", bitmap);

            startActivity(detailansicht);*/
        }
    }
}
