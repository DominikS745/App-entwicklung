package de.dhbw.pizzabutler_adapter;

/**
 * Created by Marvin on 04.03.16.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.dhbw.pizzabutler.R;
import de.dhbw.pizzabutler_entities.Bestellposition;
import de.dhbw.pizzabutler_entities.Bestellung;
import de.dhbw.pizzabutler_entities.Produkt;
import de.dhbw.pizzabutler_entities.Variante;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Produkt>> _listDataChild;
    private List<Bestellposition> bestellungen;
    private Produkt product;
    private float[] preise;
    private Button buttonPreisS;
    private Button buttonPreisM;
    private Button buttonPreisL;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<Produkt>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        bestellungen = new ArrayList<Bestellposition>();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        product = (Produkt) getChild(groupPosition, childPosition);
        preise = product.getPreis();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pizzaria_profil_child_item, null);
        }

        //Look up child Item data for population (reference to views)
        TextView produktET = (TextView) convertView.findViewById(R.id.profil_ware);
        buttonPreisS = (Button) convertView.findViewById(R.id.button_preis_s);
        buttonPreisM = (Button) convertView.findViewById(R.id.button_preis_m);
        buttonPreisL = (Button) convertView.findViewById(R.id.button_preis_l);

        //Implement onClick Listener für Button S
        buttonPreisS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(_context, "Produkt zum Warenkorb hinzugefügt", Toast.LENGTH_SHORT);
                toast.show();
                Produkt produkt = (Produkt) getChild(groupPosition, childPosition);
                Variante variante = new Variante();
                variante.setBezeichnung("klein");
                Bestellposition bestellposition = new Bestellposition();
                bestellposition.setProdukt(produkt);
                bestellposition.setVariante(variante);
                bestellposition.setPreis(produkt.getPreis()[0]);
                addBestellung(bestellposition);
            }
        });

        //Implement OnClick Listener für Button M
        buttonPreisM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(_context, "Produkt zum Warenkorb hinzugefügt", Toast.LENGTH_SHORT);
                toast.show();
                Produkt produkt = (Produkt) getChild(groupPosition, childPosition);
                Variante variante = new Variante();
                variante.setBezeichnung("mittel");
                Bestellposition bestellposition = new Bestellposition();
                bestellposition.setProdukt(produkt);
                bestellposition.setVariante(variante);
                bestellposition.setPreis(produkt.getPreis()[1]);
                addBestellung(bestellposition);
            }
        });

        //Implement OnClick Listener für Button L
        buttonPreisL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(_context, "Produkt zum Warenkorb hinzugefügt", Toast.LENGTH_SHORT);
                toast.show();
                Produkt produkt = (Produkt) getChild(groupPosition, childPosition);
                Variante variante = new Variante();
                variante.setBezeichnung("gross");
                Bestellposition bestellposition = new Bestellposition();
                bestellposition.setProdukt(produkt);
                bestellposition.setVariante(variante);
                bestellposition.setPreis(produkt.getPreis()[2]);
                addBestellung(bestellposition);
            }
        });

        produktET.setText(product.getName());
        buttonPreisS.setText(String.valueOf(preise[0]) + " €");
        buttonPreisM.setClickable(false);
        buttonPreisM.setVisibility(View.INVISIBLE);
        buttonPreisL.setClickable(false);
        buttonPreisL.setVisibility(View.INVISIBLE);

        if(preise.length > 2) {
            buttonPreisL.setClickable(true);
            buttonPreisL.setVisibility(View.VISIBLE);
            buttonPreisL.setText(String.valueOf(preise[2]) + "€");
            buttonPreisM.setClickable(true);
            buttonPreisM.setVisibility(View.VISIBLE);
            buttonPreisM.setText(String.valueOf(preise[1]) + "€");
        }
        else if(preise.length > 1) {
            buttonPreisM.setClickable(true);
            buttonPreisM.setVisibility(View.VISIBLE);
            buttonPreisM.setText(String.valueOf(preise[1]) + "€");
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pizzaria_profil_group_item, null);
        }
        //Look up group Item data for population (reference to views)
        TextView warengruppeET = (TextView) convertView.findViewById(R.id.profil_warengruppe);

        warengruppeET.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void addBestellung(Bestellposition bestellposition){
        bestellungen.add(bestellposition);
    }

    public List<Bestellposition> getBestellungen(){
        return bestellungen;
    }
}
