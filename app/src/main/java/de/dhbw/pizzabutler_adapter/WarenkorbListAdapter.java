package de.dhbw.pizzabutler_adapter;

/**
 * Created by Marvin on 04.03.16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import de.dhbw.pizzabutler.R;
import de.dhbw.pizzabutler_entities.WarenkorbItem;

public class WarenkorbListAdapter extends ArrayAdapter<WarenkorbItem> {

    private WarenkorbItem data;
    private ArrayList<WarenkorbItem> warenliste;
    private TextView anzahlMenge;
    Context context;
    private static LayoutInflater inflater = null;


    public WarenkorbListAdapter(Context context, ArrayList<WarenkorbItem> data) {
        super(context, 0, data);
        warenliste = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //Get Item Data for this position
        data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.warenkorb_list_item, parent, false);
        }
        // Lookup view for data population
        TextView produkt = (TextView) convertView.findViewById(R.id.warenkorb_product_name);
        TextView preis = (TextView) convertView.findViewById(R.id.warenkorb_produkt_preis);
        TextView variante = (TextView) convertView.findViewById(R.id.warenkorb_variante);
        Button button = (Button) convertView.findViewById(R.id.warenkorb_extras_aendern);
        Button addButton = (Button) convertView.findViewById(R.id.addButton);
        Button deleteButton = (Button) convertView.findViewById(R.id.deleteButton);
        anzahlMenge = (TextView) convertView.findViewById(R.id.anzahlMenge);

        button.setVisibility(View.INVISIBLE);
        button.setClickable(false);

        if(data.getBezeichnung().contains("Pizza")){
            button.setVisibility(View.VISIBLE);
            button.setClickable(true);
        }

        anzahlMenge.setText(String.valueOf(data.getAnzahl()));

        // Populate the data into the template view using the data object
        produkt.setText(data.getBezeichnung());
        variante.setText(data.getVariante());
        preis.setText(String.valueOf(data.getPreis()));

        //onClickListener für die Bearbeitung der Extras
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(warenliste.get(position).getAnzahl());
                warenliste.get(position).setAnzahl(warenliste.get(position).getAnzahl() + 1);
                //Ansatz stimmt, aber a) lädt zu langsam und b) nicht funktional für alle TextViews/Produkte
            }
        });

        //onClickListener für das Hinzufügen von Produkten
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hier einen Dialog aufrufen und kosten verändern
                System.out.println("test");
            }
        });

        //onClickListener für das Löschen von Produkten
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ergebnis = deleteProduct();
                if(ergebnis == 0) {

                }
            }
        });


        // Return the completed view to render on screen
        return convertView;

    }

    private void addProduct(){

        //int ergebnis = Integer.parseInt(String.valueOf(anzahlMenge.getText())) + 1;
        //anzahlMenge.setText(String.valueOf(ergebnis));
        System.out.println("müsste geaddet worden sein");
    }

    private int deleteProduct(){

       // int ergebnis = Integer.parseInt(String.valueOf(anzahlMenge.getText())) - 1;

       // if(ergebnis > 0){
          //  anzahlMenge.setText(String.valueOf(ergebnis));
          //  return ergebnis;
      //  }
      //  else{
            return 0;
        //}

    }

}