package com.compta.firstak.notedefrais.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.compta.firstak.notedefrais.entity.Facture;
import com.compta.firstak.notedefrais.R;

import java.util.ArrayList;

public class FactureAdapter extends ArrayAdapter<Facture>{

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<Facture> values;

    public FactureAdapter(Context context, int textViewResourceId,
                          ArrayList<Facture> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
       return values.size();
    }

    public Facture getItem(int position){
       return values.get(position);
    }

    public long getItemId(int position){
       return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        Activity activity = (Activity) getContext();
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.my_custum_list_view_facture, null);
        TextView textViewTitle = (TextView) rowView.findViewById(R.id.title_choose);
        textViewTitle.setText(values.get(position).getName()+"  "+values.get(position).getNofacture());

        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
    	
        TextView label = new TextView(context);
        
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getName()+"  "+values.get(position).getNofacture());
        label.setPadding(20, 20, 20, 20);

        return label;
    }
}