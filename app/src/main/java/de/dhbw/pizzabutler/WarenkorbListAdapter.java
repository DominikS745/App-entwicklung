package de.dhbw.pizzabutler;

/**
 * Created by Marvin on 04.03.16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.springframework.util.support.Base64;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.dhbw.pizzabutler_entities.Pizzeria;
import de.dhbw.pizzabutler_entities.WarenkorbItem;

public class WarenkorbListAdapter extends ArrayAdapter<WarenkorbItem> {

    //Wichtig --> Irgendeine Entity finden oder anlegen, in der alle benötigten Daten stehen!
    Context context;
    private static LayoutInflater inflater = null;

    public WarenkorbListAdapter(Context context, ArrayList<WarenkorbItem> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get Item Data for this position
        WarenkorbItem data = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.warenkorb_list_item, parent, false);
        }
        // Lookup view for data population
        TextView produkt = (TextView) convertView.findViewById(R.id.warenkorb_product_name);
        TextView preis = (TextView) convertView.findViewById(R.id.warenkorb_produkt_preis);
        TextView variante = (TextView) convertView.findViewById(R.id.warenkorb_variante);
        Button button = (Button) convertView.findViewById(R.id.warenkorb_extras_aendern);
        button.setVisibility(View.INVISIBLE);
        button.setClickable(false);

        if(data.getBezeichnung().contains("Pizza")){
            button.setVisibility(View.VISIBLE);
            button.setClickable(true);
        }

        // Populate the data into the template view using the data object
        produkt.setText(data.getBezeichnung());
        preis.setText(String.valueOf(data.getPreis()));
        variante.setText(data.getVariante());

        // Return the completed view to render on screen
        return convertView;

    }


}