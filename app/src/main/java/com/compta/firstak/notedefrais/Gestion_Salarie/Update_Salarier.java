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
import com.compta.firstak.notedefrais.Gestion_Client.AjouterClient;
import com.compta.firstak.notedefrais.Gestion_Client.MainActivitySwipe;
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
public class Update_Salarier extends Activity{

    Calendar myCalendar = Calendar.getInstance();
    EditText adresse_edit,email_edit,fonction_edit,matricule_edit,NbrEnfant_edit,nom_edit,numCnss_edit,prenom_edit,salaireBrut_edit,situation_edit,DatePaie_edit,Cin_edit;
    public static String adresse,email,fonction,matricule,nbrEnfant,nom,numCnss,prenom,salaireBrut,situation,dateNaissance,NbrEnfant,Cin;
    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    String reqGetSalarie, TypeDactionnaire,reqUpdateSalarier;
    TextView NbreText;
     RadioButton radioCelib;
    RadioButton radioMarie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_salarier);




        adresse_edit=(EditText)findViewById(R.id.Adressetext);
        email_edit=(EditText)findViewById(R.id.Email_text);
        fonction_edit=(EditText)findViewById(R.id.Fonction_text);
        matricule_edit=(EditText)findViewById(R.id.MatriculeFiscale_text);
        NbrEnfant_edit=(EditText)findViewById(R.id.NbrEnfant_text);
        nom_edit=(EditText)findViewById(R.id.Nom_text);
        numCnss_edit=(EditText)findViewById(R.id.CNSS_text);
        prenom_edit=(EditText)findViewById(R.id.Prenom_text);
        salaireBrut_edit=(EditText)findViewById(R.id.Salaire_text);
        Cin_edit=(EditText)findViewById(R.id.CIN_text);

        DatePaie_edit=(EditText)findViewById(R.id.Date_text);
        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);
        NbrEnfant_edit=(EditText)findViewById(R.id.NbrEnfant_text);
         NbreText=(TextView)findViewById(R.id.NbrEnfant);

       radioCelib = (RadioButton) findViewById(R.id.Celibataire);
        radioMarie = (RadioButton)findViewById(R.id.Mariee);
        radioCelib.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TypeDactionnaire="celibataire";
                radioMarie.setChecked(false);
                NbrEnfant_edit.setVisibility(View.GONE);
                NbreText.setVisibility(View.GONE);
            }
        });


        radioMarie.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TypeDactionnaire = "marie";
                radioCelib.setChecked(false);
                NbrEnfant_edit.setVisibility(View.VISIBLE);
                NbreText.setVisibility(View.VISIBLE);
            }
        });







        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        DatePaie_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(Update_Salarier.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                //Toast.makeText(Fiche_Paie.this, "OKK", Toast.LENGTH_LONG).show();
            }
        });



        GetSalarieJsonObject();




      Button  AjouterClient = (Button) findViewById(R.id.submit);
        AjouterClient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                adresse= adresse_edit.getText().toString();
                email= email_edit.getText().toString();
                fonction=  fonction_edit.getText().toString();
                matricule=  matricule_edit.getText().toString();
                nom= nom_edit.getText().toString();
                numCnss=  numCnss_edit.getText().toString();
                prenom=  prenom_edit.getText().toString();
                salaireBrut= salaireBrut_edit.getText().toString();
                dateNaissance=  DatePaie_edit.getText().toString();
                //dateNaissance=formatDateTime(Fiche_Paie.this, DatePaie_edit.getText().toString());
                NbrEnfant= NbrEnfant_edit.getText().toString();
                Cin= Cin_edit.getText().toString();
            // Toast.makeText(Update_Salarier.this,dateNaissance, Toast.LENGTH_LONG).show();


                UpdateClientJsonObject();


                Intent intent = new Intent(Update_Salarier.this,
                        Salarier_Swipe.class);
                Update_Salarier.this.startActivity(intent);
                finish();
            }
        });



      /*  DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        try {
            java.lang.reflect.Field f[] = datePicker.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : f) {
                if (field.getName().equals("mDayPicker")) {
                    field.setAccessible(true);
                    Object dayPicker = new Object();
                    dayPicker = field.get(datePicker);
                    ((View) dayPicker).setVisibility(View.GONE);
                }
            }
        } catch (SecurityException e) {
            Log.d("ERROR", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        } catch (IllegalAccessException e) {
            Log.d("ERROR", e.getMessage());
        }*/

    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        DatePaie_edit.setText(sdf.format(myCalendar.getTime()));

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
            Log.i("UrlGetFacture", AppConfig.getURLGetSalarierById(Salarier_Swipe.idFromModifier));
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.GET,
                    AppConfig.getURLGetSalarierById(Salarier_Swipe.idFromModifier), null,
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
                                //salarie .setSituation(json_data_article.getString("situation"));
                                if(FactureJsonFromJson.get("situation").toString()=="celibataire"){
                                    radioCelib.setChecked(true);
                                    TypeDactionnaire="celibataire";
                                }
                                if(FactureJsonFromJson.get("situation").toString()=="marie"){
                                    radioMarie.setChecked(true);
                                    TypeDactionnaire="marie";
                                }

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
            reqUpdateSalarier = "json_obj_req_Update_Client";
            Log.i("UrlUpdateClientById", AppConfig.UpdateURLSalairierById);
            ////
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", String.valueOf(Salarier_Swipe.idFromModifier));

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());

            } catch (Throwable t) {

            }



            JSONObject postParam = new JSONObject();
            try {
                postParam.put("client", obj);
                postParam.put("id", Salarier_Swipe.idFromModifier);
                postParam.put("adresse", adresse);
                postParam.put("email", email);
                postParam.put("fonction", fonction);
                postParam.put("matricule", matricule);
                postParam.put("nbrEnfant", nbrEnfant);
                postParam.put("nom", nom);
                postParam.put("numCnss", numCnss);
                postParam.put("prenom", prenom);
                postParam.put("salaireBrut", salaireBrut);
                postParam.put("situation", TypeDactionnaire);
                Toast.makeText(Update_Salarier.this, TypeDactionnaire, Toast.LENGTH_LONG).show();
                postParam.put("dateNaissance",dateNaissance);
                postParam.put("cin", Cin);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("putParam",postParam.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.PUT,
                    AppConfig.UpdateURLSalairierById,  postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject UpdateSalarierJsonFromJson) {
                            Log.d("RespWSUpdateClient",  UpdateSalarierJsonFromJson.toString());


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
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqUpdateSalarier);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }




}
