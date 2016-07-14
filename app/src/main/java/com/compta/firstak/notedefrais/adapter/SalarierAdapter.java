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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.Gestion_Client.DashboardClient;
import com.compta.firstak.notedefrais.Gestion_Client.MainActivitySwipe;
import com.compta.firstak.notedefrais.Gestion_Salarie.Get_Salarier;
import com.compta.firstak.notedefrais.Gestion_Salarie.Salarier_Swipe;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.Client;
import com.compta.firstak.notedefrais.entity.Salarier;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed on 12/01/2016.
 */
public class SalarierAdapter extends ArrayAdapter<Salarier> {
    // View lookup cache
    public static int idFromSelect;
    private Salarier_Swipe activity;
    private int idFromLongSelect;
   public static String NameFromSelect;
    private static class ViewHolder {
        TextView name;

    }

    public SalarierAdapter(Context context, ArrayList<Salarier> users, Salarier_Swipe activity) {
        super(context, R.layout.item_user, users);
        this.activity=activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Salarier user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_bg, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(user.nom);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),
                        Get_Salarier.class);
                getContext().startActivity(intent);
                Toast.makeText(getContext(), String.valueOf(user.getId()), Toast.LENGTH_LONG).show();
                idFromSelect = user.getId();
                NameFromSelect=user.nom;
                //   PhoneFromSelect =user.getPhone();

            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), String.valueOf(user.getId()), Toast.LENGTH_LONG).show();
                // final int pos = position;
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setTitle("Voulez vous supprimer " + user.getId());
                idFromLongSelect= user.getId();
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SupprimerSalarierJsonObject();
                        // items.remove(pos);
                        remove(user);
                        notifyDataSetChanged();

                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();
                return false;
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    void SupprimerSalarierJsonObject() {
        activity.getViewActualiserbutton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupprimerSalarierJsonObject();
            }
        });
        if (isNetworkAvailable()) {
            activity.getViewNetworkFailed().setVisibility(View.GONE);
            // Tag used to cancel the request
            String reqDesactiverClient = "json_obj_UrlDeleteById";
            Log.i("UrlDeleteById", AppConfig.DeleteURLSalarierById(idFromLongSelect));

            //Log.i("putParam",postParam.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.DELETE,
                    AppConfig.DeleteURLSalarierById(idFromLongSelect),  null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject UpdateClientJsonFromJson) {
                            Log.d("RespWSArchivageClient",  UpdateClientJsonFromJson.toString());

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("ErrorMessageVolley","Error: "+error.getMessage());
                    VolleyLog.d("TAGVolley", "Error: " + error.getMessage());
                    Animation animationTranslate = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                            R.anim.abc_slide_in_bottom);
                    activity.getViewNetworkFailed().startAnimation(animationTranslate);
                    activity.getViewNetworkFailed().setVisibility(View.VISIBLE);
                }
            }){


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    // headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqDesactiverClient);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            activity.getViewNetworkFailed().startAnimation(animationTranslate);
            activity.getViewNetworkFailed().setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}