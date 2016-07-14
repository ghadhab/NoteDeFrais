package com.compta.firstak.notedefrais.Gestion_Salarie;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.Gestion_Client.DashboardClient;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.adapter.SalarierAdapter;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by mohamed on 09/05/2016.
 */
public class Get_Salarier extends Activity{

    Calendar myCalendar = Calendar.getInstance();
    TextView adresse_edit,Situation_text,email_edit,fonction_edit,matricule_edit,NbrEnfant_edit,nom_edit,numCnss_edit,prenom_edit,salaireBrut_edit,situation_edit,DatePaie_edit,Cin_edit;
    private RelativeLayout networkFailed;
    String reqGetSalarie, TypeDactionnaire;
    TextView NbreText;
    Button actualiserbutton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_salarier);


        adresse_edit = (TextView) findViewById(R.id.Adressetext);
        email_edit = (TextView) findViewById(R.id.Email_text);
        fonction_edit = (TextView) findViewById(R.id.Fonction_text);
        matricule_edit = (TextView) findViewById(R.id.MatriculeFiscale_text);
        NbrEnfant_edit = (TextView) findViewById(R.id.NbrEnfant_text);
        nom_edit = (TextView) findViewById(R.id.Nom_text);
        numCnss_edit = (TextView) findViewById(R.id.CNSS_text);
        prenom_edit = (TextView) findViewById(R.id.Prenom_text);
        salaireBrut_edit = (TextView) findViewById(R.id.Salaire_text);
        Cin_edit = (TextView) findViewById(R.id.CIN_text);
        Situation_text= (TextView) findViewById(R.id.Situation_text);
        DatePaie_edit = (TextView) findViewById(R.id.Date_text);
        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);
        NbrEnfant_edit = (TextView) findViewById(R.id.NbrEnfant_text);
        NbreText = (TextView) findViewById(R.id.NbrEnfant);

        GetSalarieJsonObject();
    }



    void GetSalarieJsonObject() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSalarieJsonObject();
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
            Log.i("UrlGetFacture", AppConfig.getURLGetSalarierById(SalarierAdapter.idFromSelect));
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.GET,
                    AppConfig.getURLGetSalarierById(SalarierAdapter.idFromSelect), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject FactureJsonFromJson) {
                            Log.d("RespWSGetSalarier", FactureJsonFromJson.toString());

                            try {
                                adresse_edit.setText(FactureJsonFromJson.get("adresse").toString());
                                email_edit.setText(FactureJsonFromJson.get("email").toString());
                                fonction_edit.setText(FactureJsonFromJson.get("fonction").toString());
                                matricule_edit.setText(FactureJsonFromJson.get("matricule").toString());
                                NbrEnfant_edit.setText(FactureJsonFromJson.get("nbrEnfant").toString());
                                nom_edit.setText(FactureJsonFromJson.get("nom").toString());
                                numCnss_edit.setText(FactureJsonFromJson.get("numCnss").toString());
                                prenom_edit.setText(FactureJsonFromJson.get("prenom").toString());
                                salaireBrut_edit.setText(FactureJsonFromJson.get("salaireBrut").toString());
                                DatePaie_edit.setText(FactureJsonFromJson.get("dateNaissance").toString());
                                Cin_edit.setText(FactureJsonFromJson.get("cin").toString());
                                Situation_text.setText(FactureJsonFromJson.get("situation").toString());


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
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqGetSalarie);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
