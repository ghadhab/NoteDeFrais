package com.compta.firstak.notedefrais.GestionFacture;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.Gestion_Client.DashboardClient;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mohamed on 19/01/2016.
 */
public class GetFacture extends Activity {

    TextView Fournisseur,NoFacture,MatriculeFiscale,MF,TVA,TottalTTC,HTVA,Tel,Fax,Email,Web,Date,Designation;
    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    private  String reqGetFacture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getformulairefacure);


        Fournisseur = (TextView) findViewById(R.id.Fournisseur);
        NoFacture = (TextView) findViewById(R.id.NoFacture);
        MatriculeFiscale = (TextView) findViewById(R.id.MatriculeFiscale);
        MF = (TextView) findViewById(R.id.MF);
        TVA = (TextView) findViewById(R.id.TVA);
        TottalTTC = (TextView) findViewById(R.id.TottalTTC);
        HTVA = (TextView) findViewById(R.id.HTVA);
        Tel = (TextView) findViewById(R.id.Tel);
        Fax = (TextView) findViewById(R.id.Fax);
        Email = (TextView) findViewById(R.id.Email);
        Web = (TextView) findViewById(R.id.Web);
        Date = (TextView) findViewById(R.id.Date);
        Designation = (TextView) findViewById(R.id.Designation);
        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);

        getFactureJsonObject();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    void getFactureJsonObject() {
       actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFactureJsonObject();
            }
        });
        if (isNetworkAvailable()) {
          /*  if (progressDialog==null)
            {
                progressDialog = MyCustomProgressDialog.ctor(Formulaire.this, "Chargement ... ");
                progressDialog.show();
            }*/
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqGetFacture = "json_obj_req_get_Facture";
            Log.i("UrlGetFacture", AppConfig.getURLGetFactureById(DashboardClient.idFactureSelected,UsersAdapter.idFromSelect));
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.GET,
                    AppConfig.getURLGetFactureById(DashboardClient.idFactureSelected,UsersAdapter.idFromSelect), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject FactureJsonFromJson) {
                            Log.d("RespWSGetClient", FactureJsonFromJson.toString());

                            try {
                                Fournisseur.setText(FactureJsonFromJson.get("fournisseur").toString());
                                NoFacture.setText(FactureJsonFromJson.get("numFacture").toString());
                                MatriculeFiscale.setText(FactureJsonFromJson.get("matriculeFiscale").toString());
                                MF.setText(FactureJsonFromJson.get("mf").toString());
                                TVA.setText(FactureJsonFromJson.get("tva").toString());
                                TottalTTC.setText(FactureJsonFromJson.get("totalttc").toString());
                                HTVA.setText(FactureJsonFromJson.get("htva").toString());
                                Tel.setText(FactureJsonFromJson.get("numTel").toString());
                                Fax.setText(FactureJsonFromJson.get("numFax").toString());
                                Email.setText(FactureJsonFromJson.get("email").toString());
                                Web.setText(FactureJsonFromJson.get("siteWeb").toString());
                                Date.setText(FactureJsonFromJson.get("date").toString());
                                Designation.setText(FactureJsonFromJson.get("designation").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("ErrorMessageVolley","Error: "+error.getMessage());
                    VolleyLog.d("TAGVolley", "Error: " + error.getMessage());
                   /* progressDialog.dismiss();
                    progressDialog=null;*/
                    // hide the progress dialog
                    Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.abc_slide_in_bottom);
                    networkFailed.startAnimation(animationTranslate);
                    networkFailed.setVisibility(View.VISIBLE);
                }
            });
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqGetFacture);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }


}
