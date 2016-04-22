package de.dhbw.pizzabutler_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.springframework.util.support.Base64;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.dhbw.pizzabutler.R;
import de.dhbw.pizzabutler_entities.Oeffnungszeiten;
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
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
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
        PizzariaStatus.setText(berechneOeffnungszeit(data.getOeffnungszeiten()));

        ratingBar.setRating(data.getBewertung());

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

    private String berechneOeffnungszeit(Oeffnungszeiten[] data) {
        String status = null;
        GregorianCalendar calendar = new GregorianCalendar();
        //Sonntag = 1
        int tag = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        //Stunden entsprechen den realen Stunden
        int stunde = calendar.get(Calendar.HOUR_OF_DAY);
        //Minuten entsprechen den realen Minuten
        int minute = calendar.get(Calendar.MINUTE);

        int zeit = stunde*100 + minute;

        int von = Integer.parseInt(data[tag].getVon());
        int bis = Integer.parseInt(data[tag].getBis());

        if(von < zeit && bis > zeit) {
            status = "offen";
        }
        else{
            status = "geschlossen";

        }

        return status;
    }
}

