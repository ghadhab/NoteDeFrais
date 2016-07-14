package com.compta.firstak.notedefrais.FormeDeLaSociete;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.Gestion_Client.AjouterClient;
import com.compta.firstak.notedefrais.Gestion_Client.ListImageButton;
import com.compta.firstak.notedefrais.Gestion_Client.MainActivitySwipe;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.Suarl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mohamed on 10/08/2015.
 */
public class ListAdpterSuarl extends BaseAdapter {

    private Context context;
    private List<Suarl> items;
    private LayoutInflater inflater;
    private Activity activity;
    Button actualiserbutton;
    RelativeLayout networkFailed;

    public ListAdpterSuarl(Context context,
                           List<Suarl> items,Activity activity,Button actualiserbutton,RelativeLayout networkFailed) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.activity=activity;
        this.actualiserbutton=actualiserbutton;
        this.networkFailed=networkFailed;
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

        final Suarl item = items.get(position);
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = inflater.inflate( R.layout.itemformesociete, parent, false);
        TextView Nom = (TextView)v.findViewById( R.id.nomPresidentConseil);
        Nom.setText(item.getNomAssociesuarl());
        ImageButton Supprimer =
                (ImageButton)v.findViewById( R.id.Supprimer);
        Supprimer.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        items.remove(position);
                        notifyDataSetChanged();
                    }
                });
        ImageButton Modifier =
                (ImageButton)v.findViewById( R.id.Modifier);
        Modifier.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("itemAModifier", item.toString());
                        initiatePopupWindowSuarl(position,item);
                    }
                });
        return v;

    }




    //--------------------------------------------SUARL------------------------------------------------------
    private void initiatePopupWindowSuarl(final int posotion, final Suarl itemSuarl) {
        final Dialog dialog = new Dialog(activity,R.style.AlertDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popupsuarl);

        Button btnValiderPopup5 = (Button) dialog.findViewById(R.id.ValiderSuarl);
        Button btnCancelPopup5 = (Button) dialog.findViewById(R.id.CancelSuarl);
        final EditText EditTextAssociesuarl=(EditText)dialog.findViewById(R.id.EditTextNomAssociesuarl);
        final EditText EditTextNombrePartSuarl=(EditText)dialog.findViewById(R.id.EditTextNombrePartSuarl);
        final EditText EditTextNombreNominale=(EditText)dialog.findViewById(R.id. EditTextNombreNominale);
        final EditText EditTextTotalsuarl=(EditText)dialog.findViewById(R.id.EditTextTotalsuarl);

        EditTextAssociesuarl.setText(itemSuarl.getNomAssociesuarl());
        EditTextNombrePartSuarl.setText(String.valueOf(itemSuarl.getNombrePartSuarl()));
        EditTextNombreNominale.setText(String.valueOf(itemSuarl.getNombreNominale()));
        EditTextTotalsuarl.setText(String.valueOf(itemSuarl.getTotalAssociesuarl()));

        btnValiderPopup5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

              String  NomAssociesuarl = EditTextAssociesuarl.getText().toString();
               int  NombrePartSuarl = Integer.parseInt(EditTextNombrePartSuarl.getText().toString());
                 double NombreNominale = Double.parseDouble(EditTextNombreNominale.getText().toString());
                int TotalAssociesuarl = Integer.parseInt(EditTextTotalsuarl.getText().toString());
                Suarl suarl=new Suarl(itemSuarl.getId(),NomAssociesuarl,NombrePartSuarl,NombreNominale,TotalAssociesuarl,itemSuarl.getIdClient());
                updateSuarl(suarl);
                items.remove(posotion);
                items.add(posotion,suarl);
                //items.add(0,NomAssociesuarl );
                notifyDataSetChanged();
                //CreateNewSuarl();
                dialog.dismiss();

            }
        });

        btnCancelPopup5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogAnimation;
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }



    void updateSuarl(final Suarl suarl) {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSuarl(suarl);
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
           String reqUpdateClient = "json_obj_req_Update_Client";
            Log.i("UrlUpdateClientById", AppConfig.UpdateURLUpdateClientById);
            ////
            Log.i("url_Suarl", AppConfig.url_Ajout_Suarl);
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", suarl.getIdClient());

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());


            } catch (Throwable t) {

            }
            JSONObject postParam = new JSONObject();
            try {
               postParam.put("client", obj);
                postParam.put("id",suarl.getId());
                postParam.put("nomAssociesuarl", suarl.getNomAssociesuarl());
                postParam.put("nombrePartSuarl",  String.valueOf(suarl.getNombrePartSuarl()));
                postParam.put("nombreNominale",String.valueOf(suarl.getNombreNominale()));
                postParam.put("totalAssociesuarl",String.valueOf(suarl.getTotalAssociesuarl()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("putParam",postParam.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.PUT,
                    AppConfig.url_Ajout_Suarl,  postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject UpdateClientJsonFromJson) {
                            Log.d("RespWSUpdateClient",  UpdateClientJsonFromJson.toString());


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("ErrorMessageVolley","Error: "+error.getMessage());
                    VolleyLog.d("TAGVolley", "Error: " + error.getMessage());
                    Animation animationTranslate = AnimationUtils.loadAnimation(context,
                            R.anim.abc_slide_in_bottom);
                    networkFailed.startAnimation(animationTranslate);
                    networkFailed.setVisibility(View.VISIBLE);
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
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqUpdateClient);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(context,
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}