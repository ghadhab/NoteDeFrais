package com.compta.firstak.notedefrais.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.GestionFacture.ListImageButtonDec;
import com.compta.firstak.notedefrais.Gestion_Client.DashboardClient;
import com.compta.firstak.notedefrais.Gestion_Client.ListImageButton;
import com.compta.firstak.notedefrais.Gestion_Client.MainActivitySwipe;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.Client;
import com.compta.firstak.notedefrais.entity.Fournisseur;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdapterDec extends ArrayAdapter<Fournisseur> {

    public static int idFromSelect;
    private ListImageButtonDec activity;
    public static String NameFromSelect;
    private static class ViewHolder {
        TextView name,Tottal;

    }

    public ListAdapterDec(Context context, ArrayList<Fournisseur> fournisseurs,ListImageButtonDec activity) {
        super(context, R.layout.item_dec, fournisseurs);
        this.activity=activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Fournisseur fournisseur = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_dec, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.nom);
            viewHolder.Tottal = (TextView) convertView.findViewById(R.id.Tottal_Fournisseur);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(fournisseur.getNom_Fournisseur());
        viewHolder.Tottal.setText(String.valueOf(fournisseur.getTottal()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), String.valueOf(fournisseur.getTottal()), Toast.LENGTH_LONG).show();
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}