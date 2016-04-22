package de.dhbw.pizzabutler_adapter;

/**
 * Created by Marvin on 04.03.16.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import de.dhbw.pizzabutler.R;
import de.dhbw.pizzabutler_entities.Bestellposition;
import de.dhbw.pizzabutler_entities.WarenkorbItem;
import de.dhbw.pizzabutler_entities.Zusatzbelag;

public class WarenkorbListAdapter extends ArrayAdapter<WarenkorbItem> {

    private WarenkorbItem data;
    private ArrayList<WarenkorbItem> warenliste;
    private TextView anzahlMenge;
    Context context;
    private static LayoutInflater inflater = null;
    private Zusatzbelag[] zusatzbelage;
    private ArrayList<Integer> selectedItems;
    private boolean[] checkedValues;
    private Bestellposition[] bestellpositionen;
    private double temp;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private TextView preis;

    public WarenkorbListAdapter(Context mContext, ArrayList<WarenkorbItem> data, Zusatzbelag[] zusatzbelags, Bestellposition[] mBestellpositionen) {
        super(mContext, 0, data);
        context = mContext;
        if(warenliste == null){
            warenliste = data;
        }
        zusatzbelage = zusatzbelags;
        bestellpositionen = mBestellpositionen;
        preferences = context.getSharedPreferences("bestellwert", Context.MODE_PRIVATE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.warenkorb_list_item, parent, false);
        }
        // Lookup view for data population
        TextView produkt = (TextView) convertView.findViewById(R.id.warenkorb_product_name);
        preis = (TextView) convertView.findViewById(R.id.warenkorb_produkt_preis);
        TextView variante = (TextView) convertView.findViewById(R.id.warenkorb_variante);
        Button button = (Button) convertView.findViewById(R.id.warenkorb_extras_aendern);
        Button addButton = (Button) convertView.findViewById(R.id.addButton);
        Button deleteButton = (Button) convertView.findViewById(R.id.deleteButton);
        anzahlMenge = (TextView) convertView.findViewById(R.id.anzahlMenge);

        button.setVisibility(View.INVISIBLE);
        button.setClickable(false);

        if(warenliste.get(position).getBezeichnung().contains("Pizza")){
            button.setVisibility(View.VISIBLE);
            button.setClickable(true);
        }

        anzahlMenge.setText(String.valueOf(warenliste.get(position).getAnzahl()));

        // Populate the data into the template view using the data object
        produkt.setText(warenliste.get(position).getBezeichnung());
        variante.setText(warenliste.get(position).getVariante());
        temp = warenliste.get(position).getPreis();
        temp = Math.floor(temp * 100) / 100;
        preis.setText(String.valueOf(temp));

        preis.setText(String.valueOf(temp));

        //onClickListener für das Hinzufügen von Produkten
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warenliste.get(position).setAnzahl(warenliste.get(position).getAnzahl() + 1);
                changePrice(position);
                calculatePrice();
                notifyDataSetChanged();
            }
        });

        //onClickListener für das Hinzufügen von Extras (bei Pizzen)
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Auslesen der möglichen Zusatzbeläge
                Zusatzbelag[] belage = warenliste.get(position).getZusatzbelage();

                //Befüllung der möglichen Zusatzbeläge
                CharSequence[] items = new CharSequence[zusatzbelage.length];
                for (int i = 0; i < zusatzbelage.length; i++) {
                    items[i] = zusatzbelage[i].getName() + " (" + zusatzbelage[i].getPreis() + " €)";
                }

                // arraylist to keep the selected items
                selectedItems = new ArrayList<Integer>();
                checkedValues = new boolean[zusatzbelage.length];

                //bereits markierte Belage erkennen
                if(belage != null) {
                    for(int a = 0; a < belage.length; a++){
                        System.out.println(belage[a].getName());
                        for(int b = 0; b < zusatzbelage.length; b++){
                            if(belage[a].getName().equals(zusatzbelage[b].getName())){
                                selectedItems.add(b);
                                checkedValues[b] = true;
                            }
                        }
                    }

                    //Befüllen der false-Werte der checkedValues
                    for(int c = 0; c < checkedValues.length; c++){
                        if(checkedValues[c] != true){
                            checkedValues[c] = false;
                        }
                    }
                }
                //keine Vorbelegung der Checkboxen
                else{
                    checkedValues = null;
                }

                //Dialog zur Änderung der Zusatzbeläge
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Wählen Sie zusätzliche Beläge.")
                        .setMultiChoiceItems(items, checkedValues, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    selectedItems.add(indexSelected);
                                } else if (selectedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    selectedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Preis anpassen
                                Zusatzbelag[] belage = new Zusatzbelag[selectedItems.size()];
                                for(int i = 0; i<selectedItems.size(); i++){
                                    belage[i] = zusatzbelage[selectedItems.get(i)];
                                }
                                warenliste.get(position).setZusatzbelage(belage);
                                changePrice(position);
                                calculatePrice();
                             }}).create();
             dialog.show();
             }
        });

        //onClickListener für das Löschen von Produkten
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warenliste.get(position).setAnzahl(warenliste.get(position).getAnzahl() - 1);
                if(warenliste.get(position).getAnzahl() == 0) {
                    warenliste.remove(position);
                    calculatePrice();
                }
                else{
                    changePrice(position);
                    calculatePrice();
                }
                notifyDataSetChanged();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    private void changePrice(int position){

        double basispreis = 0;

        for(int a = 0; a<bestellpositionen.length; a++){
            if (bestellpositionen[a].getProdukt().getName().equals(warenliste.get(position).getBezeichnung())){
                basispreis = bestellpositionen[a].getPreis();
            }
        }
        int anzahl = warenliste.get(position).getAnzahl();
        Zusatzbelag[] extras = warenliste.get(position).getZusatzbelage();
        double belegpreis = 0;

        if(extras != null) {
            for (int i = 0; i < extras.length; i++) {
                belegpreis = belegpreis + extras[i].getPreis();

            }
        }
        double endpreis = (basispreis + belegpreis) * anzahl;
        endpreis = Math.floor(endpreis * 100) / 100;
        warenliste.get(position).setPreis(endpreis);
        notifyDataSetChanged();
    }

    public ArrayList<WarenkorbItem> getWarenliste() {
        return warenliste;
    }

    public void setWarenliste(ArrayList<WarenkorbItem> warenliste) {
        this.warenliste = warenliste;
    }

    public void calculatePrice() {
        double bestellwert = 0;
        for(int i = 0; i<warenliste.size(); i++) {
            bestellwert = bestellwert + warenliste.get(i).getPreis();
        }
        bestellwert = Math.floor(bestellwert * 100) / 100;

        //Speichern des Bestellwertes
        editor = preferences.edit();
        editor.putFloat("bestellwert", ((float) bestellwert));
        editor.commit();
    }
}