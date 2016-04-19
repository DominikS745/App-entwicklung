package de.dhbw.pizzabutler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import org.springframework.util.support.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.dhbw.pizzabutler_adapter.ExpandableListAdapter;
import de.dhbw.pizzabutler_entities.Bestellposition;
import de.dhbw.pizzabutler_entities.Bestellung;
import de.dhbw.pizzabutler_entities.Kategorie;
import de.dhbw.pizzabutler_entities.Pizzeria;
import de.dhbw.pizzabutler_entities.Produkt;
import de.dhbw.pizzabutler_entities.Speisekarte;

/**
 * Created by Marvin on 28.02.16.
 */
public class PizzariaProfilActivity extends BaseActivity {


    //Variablen für Expandable List Adapter (Tutorial:http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    Bitmap bild;
    Pizzeria pizzeria;
    Kategorie kategorie;
    Speisekarte speisekarte;
    Bestellung bestellung;

    //Custom Code
    List<String> listDataHeader;
    HashMap<String, List<Produkt>> listDataChild;

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_pizzaria_profil);

        //Daten für Anzeige erhalten
        pizzeria = (Pizzeria) getIntent().getSerializableExtra("pizzeria");
        speisekarte = (Speisekarte) getIntent().getSerializableExtra("speisekarte");

        //Bild setzen
        ImageView PizzariaBild = (ImageView) findViewById(R.id.pizzaria_bild);
        bild = processPicture(pizzeria.getBild());
        PizzariaBild.setImageBitmap(bild);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.listview_pizzaria_profil);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

    }

    //OnClick Funktion für Aufruf des Warenkorbs
    public void OnClickWeiter(View v) {
        List<Bestellposition> bestellungen = listAdapter.getBestellungen();
        if(bestellungen == null) {
            Toast.makeText(this, "Bitte bestellen Sie etwas.", Toast.LENGTH_SHORT).show();
        }
        else if(bestellungen.size() < 1){
            Toast.makeText(this, "Bitte bestellen Sie etwas.", Toast.LENGTH_SHORT).show();
        }

        else{
            Intent intent = new Intent(PizzariaProfilActivity.this, WarenkorbActivity.class);
            Bestellposition[] bestellpositionen = new Bestellposition[bestellungen.size()];
            bestellungen.toArray(bestellpositionen);
            bestellung = new Bestellung();
            bestellung.setBestellpositionen(bestellpositionen);
            intent.putExtra("mindestbestellwert" , pizzeria.getMindestbestellwert());
            intent.putExtra("zusatzbelage" , speisekarte.getKategorien()[0].getZusatzbelaege());
            intent.putExtra("lieferkosten", pizzeria.getLieferkosten());
            intent.putExtra("warenkorb", bestellung);
            intent.putExtra("restaurantID" , pizzeria.getId());
            startActivity(intent);
        }
    }

    //Verarbeitung des Bilds
    public Bitmap processPicture(String base64) {
        try {
            byte[] byteArray = Base64.decode(base64);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Produkt>>();

        listDataHeader.clear();
        listDataChild.clear();

        // Fügt Kategorien hinzue
        for (int i = 0; i < speisekarte.getKategorien().length; i++) {
            listDataHeader.add(speisekarte.getKategorien()[i].getName());

            // Fügt Datensätze hinzu
            kategorie = speisekarte.getKategorien()[i];
            List<Produkt> data = new ArrayList<Produkt>();
            for (int a = 0; a < kategorie.getProdukte().length; a++)
            {
                data.add(kategorie.getProdukte()[a]);
            }
            listDataChild.put(listDataHeader.get(i), data);
        }
    }


    //On Click Methoden der Buttons für Informationen
    public void OnClickHaus(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(pizzeria.getName() + "\n" + pizzeria.getStrasse() + " " + pizzeria.getHausnummer() + "\n"
        +  pizzeria.getPlz() + " " + pizzeria.getOrt())
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //Aufruf der Einstellungen
                                d.dismiss();
                            }
                        });
        //Anzeigen des Dialogfeldes
        builder.create().show();
    }

    public void OnClickTel(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Telefon: " + pizzeria.getTelefonnummer())
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //Aufruf der Einstellungen
                                d.dismiss();
                            }
                        });
        //Anzeigen des Dialogfeldes
        builder.create().show();
    }

    public void OnClickUhr(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        
        builder.setMessage("Öffnungszeiten: \n \n" + "Montag: " + pizzeria.getOeffnungszeiten()[1].getVon() + "-" +pizzeria.getOeffnungszeiten()[1].getBis()
                        + "\nDienstag: " + pizzeria.getOeffnungszeiten()[2].getVon() + "-" + pizzeria.getOeffnungszeiten()[2].getBis()
                        + "\nMittwoch: " + pizzeria.getOeffnungszeiten()[3].getVon() + "-" +pizzeria.getOeffnungszeiten()[3].getBis()
                        + "\nDonnerstag: " + pizzeria.getOeffnungszeiten()[4].getVon() + "-" +pizzeria.getOeffnungszeiten()[4].getBis()
                        + "\nFreitag: " + pizzeria.getOeffnungszeiten()[5].getVon() + "-" +pizzeria.getOeffnungszeiten()[5].getBis()
                        + "\nSamstag: " + pizzeria.getOeffnungszeiten()[6].getVon() + "-" +pizzeria.getOeffnungszeiten()[6].getBis()
                        + "\nSonntag: " + pizzeria.getOeffnungszeiten()[0].getVon() + "-" +pizzeria.getOeffnungszeiten()[0].getBis()
        )
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //Aufruf der Einstellungen
                                d.dismiss();
                            }
                        });
        //Anzeigen des Dialogfeldes
        builder.create().show();
    }

    public void OnClickEuro(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Lieferkosten: " + pizzeria.getLieferkosten() + "€\n" + "Mindestbestellwert: " + pizzeria.getMindestbestellwert() + " €")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //Aufruf der Einstellungen
                                d.dismiss();
                            }
                        });
        //Anzeigen des Dialogfeldes
        builder.create().show();
    }
}
