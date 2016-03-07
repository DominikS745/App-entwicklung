package de.dhbw.pizzabutler;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marvin on 28.02.16.
 */
public class PizzariaProfilActivity extends BaseActivity {


    //Variablen für Expandable List Adapter (Tutorial:http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;


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

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Pizza");
        listDataHeader.add("Pasta");
        listDataHeader.add("Coming Soon..");

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

}
