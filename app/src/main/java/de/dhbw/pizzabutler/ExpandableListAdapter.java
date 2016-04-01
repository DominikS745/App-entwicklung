package de.dhbw.pizzabutler;

/**
 * Created by Marvin on 04.03.16.
 */

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.dhbw.pizzabutler_entities.Produkt;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Produkt>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<Produkt>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
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

        final Produkt product = (Produkt) getChild(groupPosition, childPosition);
        final float[] preise = product.getPreis();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pizzaria_profil_child_item, null);
        }
        //Look up child Item data for population (reference to views)
        TextView Produkt = (TextView) convertView.findViewById(R.id.profil_ware);
        Button buttonPreisS = (Button) convertView.findViewById(R.id.button_preis_s);
        Button buttonPreisM = (Button) convertView.findViewById(R.id.button_preis_m);
        Button buttonPreisL = (Button) convertView.findViewById(R.id.button_preis_l);

        //Implement onClick Listener für Button S
        buttonPreisS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_context, "ChildPosition: " + childPosition + " GroupPosition: " + groupPosition, Toast.LENGTH_SHORT).show();

            }
        });

        //Implement OnClick Listener für Button M
        buttonPreisM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_context, "ChildPosition: " + childPosition + " GroupPosition: " + groupPosition, Toast.LENGTH_SHORT).show();

            }
        });

        //Implement OnClick Listener für Button L
        buttonPreisL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_context, "ChildPosition: " + childPosition + " GroupPosition: " + groupPosition, Toast.LENGTH_SHORT).show();

            }
        });

        Produkt.setText(product.getName());
        buttonPreisS.setText(String.valueOf(preise[0])+" €");
        if (preise.length > 1){
            buttonPreisM.setText(String.valueOf(preise[1])+" €");
        }
        if (preise.length > 2){
            buttonPreisL.setText(String.valueOf(preise[2])+" €");
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
        TextView Warengruppe = (TextView) convertView.findViewById(R.id.profil_warengruppe);


        Warengruppe.setText(headerTitle);

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
}
