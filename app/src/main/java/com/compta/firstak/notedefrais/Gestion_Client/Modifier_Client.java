package com.compta.firstak.notedefrais.Gestion_Client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mohamed on 11/01/2016.
 */
public class Modifier_Client extends Activity {
    EditText GetRaisonSocialeClient, GetEmailClient, GetPhoneClient, GetFaxClient, GetAdresseClient, GetMFClient,GetRcClient,GetCapitalClient,GetActiviteClient,GetDateConstructionClient,GetFormeSocieteClient;
    public String GetRaisonSociale,GetEmail,GetPhone,GetFax,GetAdresse,GetMF,GetRc,GetCapital,GetConstitution,GetFormeSociete,GetActivite;
    Button ValiderClient;
    ImageButton GetLogo;
    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    private  String reqGetClient;
    private  String reqUpdateClient;
    public String  ClientJson ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifierclient);

        GetRaisonSociale="";
        GetEmail="";
        GetPhone="";
        GetFax="";
        GetAdresse="";
        GetMF="";
        GetRc="";
        GetCapital="";
        GetConstitution="";
        GetFormeSociete="";
        GetActivite="";
        GetRaisonSocialeClient = (EditText) findViewById(R.id.editTextRaisonSocialaClient);
        GetEmailClient = (EditText) findViewById(R.id.editTextEmailAddressInput);
        GetPhoneClient = (EditText) findViewById(R.id.editTextPhoneNumber);
        GetFaxClient = (EditText) findViewById(R.id.editTextFax);
        GetAdresseClient = (EditText) findViewById(R.id.editTextAdresse);
        GetMFClient = (EditText) findViewById(R.id.editTextMF);
        GetRcClient = (EditText) findViewById(R.id.editTextRC);
        GetCapitalClient=(EditText)findViewById(R.id.editTextCapital);
        GetActiviteClient=(EditText)findViewById(R.id.editTextActivity);
        GetDateConstructionClient=(EditText)findViewById(R.id.editTextDate);
    GetFormeSocieteClient=(EditText)findViewById(R.id.editTextFormeSociete);

        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);


        getClientJsonObject();


       ValiderClient = (Button) findViewById(R.id.ValiderClient);
        ValiderClient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                GetRaisonSociale= GetRaisonSocialeClient.getText().toString();
                GetEmail=GetEmailClient.getText().toString();
                GetPhone=GetPhoneClient.getText().toString();
                GetFax=GetFaxClient.getText().toString();
                GetAdresse=GetAdresseClient.getText().toString();
                GetMF=GetMFClient.getText().toString();
                GetRc=GetRcClient.getText().toString();
                GetCapital=GetMFClient.getText().toString();
                GetConstitution=GetDateConstructionClient.getText().toString();
                GetActivite=GetActiviteClient.getText().toString();
                GetFormeSociete=GetFormeSocieteClient.getText().toString();

                UpdateClientJsonObject();


                Intent intent = new Intent(Modifier_Client.this,
                        MainActivitySwipe.class);
              //  intent.putExtra("ClientJson", ClientJson);
               // Log.i("ClientJson",ClientJson);
                startActivity(intent);
                finish();

            }
        });

    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    void getClientJsonObject() {
        /*actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClientJsonObject();
            }
        });*/
    if (isNetworkAvailable()) {
          /*  if (progressDialog==null)
            {
                progressDialog = MyCustomProgressDialog.ctor(Formulaire.this, "Chargement ... ");
                progressDialog.show();
            }*/
        networkFailed.setVisibility(View.GONE);
        // Tag used to cancel the request
        reqGetClient = "json_obj_req_get_Client";
        Log.i("UrlGetFavorisByUser", AppConfig.getURLGetClientById(16));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.GET,
                AppConfig.getURLGetClientById(16), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject ClientJsonFromJson) {
                        Log.d("RespWSGetClient", ClientJsonFromJson.toString());

                        try {
                            GetRaisonSocialeClient.setText(ClientJsonFromJson.get("raisonSociale").toString());
                            GetEmailClient.setText(ClientJsonFromJson.get("email").toString());
                            GetPhoneClient.setText(ClientJsonFromJson.get("phone").toString());
                            GetFaxClient.setText(ClientJsonFromJson.get("fax").toString());
                            GetAdresseClient.setText(ClientJsonFromJson.get("adresse").toString());
                            GetMFClient.setText(ClientJsonFromJson.get("matriculeFiscale").toString());
                            GetRcClient.setText(ClientJsonFromJson.get("registre").toString());
                            GetCapitalClient.setText(ClientJsonFromJson.get("capital").toString());
                            GetActiviteClient.setText(ClientJsonFromJson.get("activite").toString());
                            GetDateConstructionClient.setText(ClientJsonFromJson.get("constitution").toString());
                            GetFormeSocieteClient.setText(ClientJsonFromJson.get("formeSociete").toString());

                            ClientJson= ClientJsonFromJson.toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                           /* ArrayList<Favoris> listFavorises= JsonService.getFavoris(favorisJsonFromJson);
                            RecyclerView.Adapter listFavorisAdap= new FavorisReycleViewAdapter(FavorisActivity.this, listFavorises);
                            mRecyclerView.setAdapter(listFavorisAdap);
                            progressDialog.dismiss();
                            progressDialog=null;*/
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
        AppController.getInstance().addToRequestQueue(jsonObjReq, reqGetClient);
    } else {
        Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.abc_slide_in_bottom);
        networkFailed.startAnimation(animationTranslate);
        networkFailed.setVisibility(View.VISIBLE);
    }
}




    void UpdateClientJsonObject() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateClientJsonObject();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqUpdateClient = "json_obj_req_Update_Client";
            Log.i("UrlUpdateClientById", AppConfig.UpdateURLUpdateClientById);
            ////
            JSONObject postParam = new JSONObject();
            try {
                postParam.put("id", 16);
                postParam.put("raisonSociale", GetRaisonSociale);
                postParam.put("logo", "kkkkk");
                postParam.put("archivage", "false");
                postParam.put("email", GetEmail);
                postParam.put("phone", GetPhone);
                postParam.put("fax", GetFax);
                postParam.put("adresse", GetAdresse);
                postParam.put("matriculeFiscale", GetMF);
                postParam.put("registre", GetRc);
                postParam.put("capital", GetCapital);
                postParam.put("activite", GetConstitution);
                postParam.put("constitution", GetActivite);
                postParam.put("formeSociete", GetFormeSociete);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("putParam",postParam.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.PUT,
                    AppConfig.UpdateURLUpdateClientById,  postParam,
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
                    Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
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
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }




}
