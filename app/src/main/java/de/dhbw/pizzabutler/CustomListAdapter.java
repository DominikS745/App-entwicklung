package de.dhbw.pizzabutler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.dhbw.pizzabutler_entities.Pizzeria;

/**
 * Created by Marvin on 07.03.16.
 */

public class CustomListAdapter extends ArrayAdapter<Pizzeria> {
    Context context;
    String[] data;
    private static LayoutInflater inflater = null;

    public CustomListAdapter(Context context, ArrayList<Pizzeria> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get Item Data for this position
        Pizzeria data = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pizzaria_liste_item, parent, false);
        }
        // Lookup view for data population
        TextView PizzariaName = (TextView) convertView.findViewById(R.id.pizzaria_name);
        TextView PizzariaStrasse = (TextView) convertView.findViewById(R.id.pizzaria_adresse);
        // Populate the data into the template view using the data object
        PizzariaName.setText(data.getName());
        PizzariaStrasse.setText(data.getStrasse());
        // Return the completed view to render on screen
        return convertView;

    }


}

