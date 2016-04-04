package de.dhbw.pizzabutler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.springframework.util.support.Base64;
import org.w3c.dom.Text;

import java.util.ArrayList;

import de.dhbw.pizzabutler_entities.Pizzeria;

/**
 * Created by Marvin on 07.03.16.
 */

public class CustomListAdapter extends ArrayAdapter<Pizzeria> {
    Context context;
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
        TextView PizzariaHausnummer = (TextView) convertView.findViewById(R.id.pizzaria_hausnummer);
        TextView PizzariaPlz = (TextView) convertView.findViewById(R.id.pizzaria_plz);
        TextView PizzariaOrt = (TextView) convertView.findViewById(R.id.pizzaria_ort);
        TextView PizzariaLieferkosten = (TextView) convertView.findViewById(R.id.pizzaria_lieferkosten_betrag);
        TextView PizzariaMindestbestellwert = (TextView) convertView.findViewById(R.id.pizzaria_minbestellwert_betrag);
        TextView PizzariaStatus = (TextView) convertView.findViewById(R.id.pizzaria_offen_geschlossen);
        ImageView PizzariaBild = (ImageView) convertView.findViewById(R.id.pizzaria_bild);

        // Populate the data into the template view using the data object
        PizzariaName.setText(data.getName());
        PizzariaStrasse.setText(data.getStrasse());
        PizzariaHausnummer.setText(data.getHausnummer());
        PizzariaPlz.setText(data.getPlz());
        PizzariaOrt.setText(data.getOrt());
        PizzariaLieferkosten.setText(data.getLieferkosten().toString());
        PizzariaMindestbestellwert.setText(data.getMindestbestellwert().toString());

        //Aufbereiten des Bilds
        Bitmap pic = processPicture(data.getBild());
        PizzariaBild.setImageBitmap(pic);

        // Return the completed view to render on screen
        return convertView;

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


}

