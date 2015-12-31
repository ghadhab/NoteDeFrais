package com.compta.firstak.notedefrais.FormeDeLaSociete;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.compta.firstak.notedefrais.ListImageButton;
import com.compta.firstak.notedefrais.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 10/08/2015.
 */
public class ListAdpterFormeSociete extends BaseAdapter {

    public  ListAdpterFormeSociete(Context context,
                       List<String> items ) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
    }


    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ListImageButton listImageButton=new ListImageButton();

        String item = items.get(position);
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = inflater.inflate( R.layout.itemformesociete, parent, false);
        TextView Nom = (TextView)v.findViewById( R.id.nomPresidentConseil);
        Nom.setText(item);
        ImageButton Supprimer =
                (ImageButton)v.findViewById( R.id.Supprimer);
        Supprimer.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        items.remove(position);
                        PresidentDeConseil.adapter.notifyDataSetChanged();
                    }
                });
        ImageButton Modifier =
                (ImageButton)v.findViewById( R.id.Modifier);
        Modifier.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                    }
                });
        return v;

    }



    private Context context;
    private List<String> items;
    private LayoutInflater inflater;
}