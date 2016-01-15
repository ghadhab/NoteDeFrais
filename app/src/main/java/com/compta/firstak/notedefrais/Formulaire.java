package com.compta.firstak.notedefrais;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.ExpandableList.MainActivityList;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * Created by mohamed on 08/07/2015.
 */
public class Formulaire extends Activity {
   // public static ChoixPhotoResult LastResult;
    AutoCompleteTextView Nofacture, Matricule_fiscale, Mf, Tva, TottalTTC, NoTel, NoFax,Email,Web,HTVA,Fournisseur;
    EditText  Date,Designation;
    Calendar myCalendar = Calendar.getInstance();
    Button Submit;
   public static String GetTvaText,GetDesignationText,GetHTVAtext,GetTottalText,GetDateText,TvaListener,HTvaListener,TottalListener,GetNoFaxture,GetMatricule_fiscale,GetMF,GetNoTel ,GetNoFax ,GetEmail ,GetWeb,GetFournisseur;
    Double HTVAChange;
    public static boolean isNew;
    ChoixPhotoResult ch;
    ImageView Etat_NoFacture,Etat_MatriculeMatricule,Etat_Mf,Etat_Tva,Etat_TottalTTC,Etat_Htva,Etat_Notel,Etat_Nofax,Etat_Email,Etat_Site,Etat_Date;





    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    private  String reqAddFacture;
    private  String reqGetFournisseur;
    Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.formulaire);

        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);
      //  getFournisseurJsonObject();
       // MakeJsonArrayReq();
         ch = getIntent().getExtras().getParcelable("ch");
         extras = getIntent().getExtras();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,extras.getStringArray("OcrWordIntent"));
isNew=false;


        Etat_NoFacture=(ImageView)findViewById(R.id.etat_NFacture);
        Etat_MatriculeMatricule=(ImageView)findViewById(R.id.etat_MatriculeFiscale);
        Etat_Mf=(ImageView)findViewById(R.id.etat_MF);
        Etat_Tva=(ImageView)findViewById(R.id.etat_Tva);
        Etat_TottalTTC=(ImageView)findViewById(R.id.etat_TotalTTC);
        Etat_Htva=(ImageView)findViewById(R.id.etat_HTVA);
        Etat_Notel=(ImageView)findViewById(R.id.etat_NTel);
        Etat_Nofax=(ImageView)findViewById(R.id.etat_NFax);
        Etat_Email=(ImageView)findViewById(R.id.etat_Email);
        Etat_Site=(ImageView)findViewById(R.id.etat_Site);
        Etat_Date=(ImageView)findViewById(R.id.etat_Date);






if(extras.getBoolean("TestTel")==true){
    Etat_Notel.setImageResource(R.drawable.greeen_button);
}
        if(extras.getBoolean("TestNfacture")==false){
            Etat_NoFacture.setImageResource(R.drawable.button_orange);
        }
        if(extras.getBoolean("TestMatriculeFiscal")==true){
            Etat_MatriculeMatricule.setImageResource(R.drawable.greeen_button);
        }
        if(extras.getBoolean("TestMF")==false){
            Etat_Mf.setImageResource(R.drawable.button_orange);
        }
        if(extras.getBoolean("TestTva")==true){
            Etat_Tva.setImageResource(R.drawable.greeen_button);
        }
        if(extras.getBoolean("TestTottal")==true){
            Etat_TottalTTC.setImageResource(R.drawable.greeen_button);
        }
        if(extras.getBoolean("TestHTVA")==true){
            Etat_Htva.setImageResource(R.drawable.greeen_button);
        }
        if(extras.getBoolean("TestFax")==true){
            Etat_Nofax.setImageResource(R.drawable.greeen_button);
        }
        if(extras.getBoolean("TestEmail")==true){
            Etat_Email.setImageResource(R.drawable.greeen_button);
        }
        if(extras.getBoolean("TestSie")==true){
            Etat_Site.setImageResource(R.drawable.greeen_button);
        }
        if(extras.getBoolean("TestDate")==true){
            Etat_Date.setImageResource(R.drawable.greeen_button);
        }


        Fournisseur= (AutoCompleteTextView) findViewById(R.id.Fournisseur);
        Fournisseur.setAdapter(adapter);
        Nofacture = (AutoCompleteTextView) findViewById(R.id.NoFacture);
        Nofacture.setAdapter(adapter);
        Matricule_fiscale = (AutoCompleteTextView) findViewById(R.id.MatriculeFiscale);
        Matricule_fiscale.setAdapter(adapter);
        Mf = (AutoCompleteTextView) findViewById(R.id.MF);
        Mf.setAdapter(adapter);
        Tva = (AutoCompleteTextView) findViewById(R.id.TVA);
        Tva.setAdapter(adapter);
        Tva.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TvaListener = Tva.getText().toString();
               /* HTVAChange = ((Double.parseDouble(TottalListener) / (1 + (Double.parseDouble(TvaListener)) / 100)));
                HTVA.setText(Double.toString(HTVAChange));*/
            }
        });
        TottalTTC = (AutoCompleteTextView) findViewById(R.id.TottalTTC);
        TottalTTC.setAdapter(adapter);
        TottalTTC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TottalListener = TottalTTC.getText().toString();
            }
        });
        NoTel = (AutoCompleteTextView) findViewById(R.id.Tel);
        NoTel.setAdapter(adapter);
        NoFax = (AutoCompleteTextView) findViewById(R.id.Fax);
        NoFax.setAdapter(adapter);
        Date = (EditText) findViewById(R.id.Date);
        Email = (AutoCompleteTextView) findViewById(R.id.Email);
        Email.setAdapter(adapter);
        Web = (AutoCompleteTextView) findViewById(R.id.Web);
        Web.setAdapter(adapter);
        Designation = (EditText) findViewById(R.id.Designation);
        HTVA=(AutoCompleteTextView) findViewById(R.id.HTVA);
        HTVA.setAdapter(adapter);




    HTVAChange=ch.HTVA;
        HTVA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HTvaListener = HTVA.getText().toString();

            }
        });
       Nofacture.setText(ch.NoFacture);
        Matricule_fiscale.setText(ch.MatriculeFiscale);
        Mf.setText(ch.MF);
        Tva.setText(ch.TVA.replace("l","1").replace("o","0"));
        TottalTTC.setText(Double.toString(ch.Tottal));
        NoTel.setText(ch.Tel);
        NoFax.setText(ch.Fax);
        Date.setText(ch.Date);

        Email.setText(ch.Email+"/");

        Email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Linkify.addLinks(Email, Linkify.EMAIL_ADDRESSES);

            }
        });
        Web.setText(ch.SiteWeb);
        Web.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Linkify.addLinks(Web, Linkify.WEB_URLS);

            }
        });

        HTVA.setText(Double.toString(ch.HTVA));
        Submit = (Button) findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            int i = 0;

            public void onClick(View v) {
                isNew = true;
                TableFacture.rowCount = TableFacture.rowCount + 3;


                GetFournisseur=Fournisseur.getText().toString();
                GetNoFaxture=Nofacture.getText().toString();
                 GetMatricule_fiscale=Matricule_fiscale.getText().toString();
                 GetMF=Mf.getText().toString();
                 GetNoTel=NoTel.getText().toString();
                 GetNoFax=NoFax.getText().toString();
                 GetEmail=Email.getText().toString();
                 GetWeb=Web.getText().toString();
                GetTvaText = Tva.getText().toString();
                GetTottalText = TottalTTC.getText().toString();
                GetDateText = Date.getText().toString();
                GetHTVAtext = HTVA.getText().toString();
                GetDesignationText = Designation.getText().toString();
                ParsingOcrResult.ParseDesignation(GetDesignationText);
                // Log.i("  ", "distaaaaaaaaance " + ParsingOcrResult.DesignationAtMin.toString() + "    Code  " + ParsingOcrResult.Code);
                //Toast.makeText(getApplicationContext(), ParsingOcrResult.DesignationAtMin.toString() + "    Code  " + ParsingOcrResult.Code,
                //      Toast.LENGTH_LONG).show();
               /* if(Submit.isClickable()) {
                    DashboardClient.spinnerArray.add("Facture"+i);
                    DashboardClient.adapter.notifyDataSetChanged();
                    i++;
                }*/

                AddFactureJsonObject();

                Intent intent = new Intent(Formulaire.this,
                        TableFacture.class);
                intent.putExtra("ch", ch);
                startActivity(intent);
            }
        });

        Toast.makeText(getApplicationContext(),extras.getString("Designation"),
                Toast.LENGTH_LONG).show();
        Designation.setText(extras.getString("Designation"));

     /*   final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        Date.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Formulaire.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        Date.setText(sdf.format(myCalendar.getTime()));*/

    }

    void getFournisseurJsonObject() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFournisseurJsonObject();
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
            reqGetFournisseur = "json_obj_req_get_fournisseur";
            Log.i("UrlGetFavorisByUser", AppConfig.URL_GET_ALL_FOURNISEUR );
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.GET,
                    AppConfig.URL_GET_ALL_FOURNISEUR, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject favorisJsonFromJson) {
                            Log.d("RespWSGetFournisseurs", favorisJsonFromJson.toString());
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
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqGetFournisseur);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }

    private void MakeJsonArrayReq() {

        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeJsonArrayReq();
            }
        });
        if (isNetworkAvailable()) {
        JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.URL_GET_ALL_FOURNISEUR,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("ResponseWsGetFourniseur", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                String name = jo.getString("adresse");
                                Log.i("MakeJsonArrayReq",name);
                                //countries.add(name);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                      //  PD.dismiss();
                       // adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.abc_slide_in_bottom);
                networkFailed.startAnimation(animationTranslate);
                networkFailed.setVisibility(View.VISIBLE);
            }
        });

        AppController.getInstance().addToRequestQueue(jreq, "jreq");

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

    void AddFactureJsonObject() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFactureJsonObject();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqAddFacture = "json_obj_req_Add_Client";
            Log.i("UrlAddClientById", AppConfig.AddURLAddFacture);
            final JSONObject postParam = new JSONObject();
            try {
               // Log.i("ClientJsonn", extras.getString("ClientJson"));
               // postParam.put("client",extras.getString("ClientJson"));
                postParam.put("fournisseur", GetFournisseur);
                postParam.put("numFacture", GetNoFaxture);
                postParam.put("matriculeFiscale", GetMatricule_fiscale);
                postParam.put("mf", GetMF);
                postParam.put("numTel", GetNoTel);
                postParam.put("numFax", GetNoFax);
                postParam.put("email", GetEmail);
                postParam.put("siteWeb", GetWeb);
                postParam.put("tva", GetTvaText);
                postParam.put("totalttc",GetTottalText);
                postParam.put("date", GetDateText);
                postParam.put("htva", GetHTVAtext);
                postParam.put("designation", GetDesignationText);


                Log.i("postParam", postParam.toString());

                Log.i("UrlAddClientById", AppConfig.AddURLAddFacture);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.POST,AppConfig.AddURLAddFacture,
                    postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ADDClientJsonFromJson) {
                            Log.d("RespWSAddFacture",  ADDClientJsonFromJson.toString());

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
            }) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    // headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                ;
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqAddFacture);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }


}
