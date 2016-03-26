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

import org.springframework.util.support.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.dhbw.pizzabutler_entities.Pizzeria;
import de.dhbw.pizzabutler_entities.Speisekarte;

/**
 * Created by Marvin on 28.02.16.
 */
public class PizzariaProfilActivity extends BaseActivity {


    //Variablen für Expandable List Adapter (Tutorial:http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    Bitmap bild;
    Pizzeria mPizzeria;

    //Custom Code
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_pizzaria_profil);

        //Daten für Anzeige erhalten
        Pizzeria pizzeria = (Pizzeria) getIntent().getSerializableExtra("pizzeria");
        Speisekarte speisekarte = (Speisekarte) getIntent().getSerializableExtra("speisekarte");
        mPizzeria = pizzeria;

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
        Intent intent = new Intent(PizzariaProfilActivity.this, WarenkorbActivity.class);
        startActivity(intent);
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
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Pizza");
        listDataHeader.add("Pasta");
        listDataHeader.add("Salat");

        // Adding child data
        List<String> pizza = new ArrayList<String>();
        pizza.add("Pizza Schinken");
        pizza.add("Pizza Salami");
        pizza.add("Pizza Käse");
        pizza.add("Pizza Tuna");


        List<String> pasta = new ArrayList<String>();
        pasta.add("Spaghetti");
        pasta.add("Noodles");
        pasta.add("Noodles 2");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("Hier");
        comingSoon.add("Noch");
        comingSoon.add("Anderes");
        comingSoon.add("Essen");

        listDataChild.put(listDataHeader.get(0), pizza); // Header, Child data
        listDataChild.put(listDataHeader.get(1), pasta);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    //On Click Methoden der Buttons für Informationen
    public void OnClickHaus(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mPizzeria.getName() + "\n" + mPizzeria.getStrasse() + " " + mPizzeria.getHausnummer() + "\n"
        +  mPizzeria.getPlz() + " " + mPizzeria.getOrt())
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
        builder.setMessage("Telefon: Noch keine Methode für getTel verfügbar")
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
        builder.setMessage("Öffnungszeiten: \n" + "Dominik mach du das, ich kenn mich mit der Entity nicht aus :D")
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
        builder.setMessage("Lieferkosten: " + mPizzeria.getLieferkosten() + "€\n" + "Mindestbestellwert: " + mPizzeria.getMindestbestellwert() + " €")
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
