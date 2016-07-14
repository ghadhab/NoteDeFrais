package com.compta.firstak.notedefrais.Gestion_Client;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.compta.firstak.notedefrais.GestionFacture.FournisseurDec;
import com.compta.firstak.notedefrais.Gestion_Salarie.Fiche_Paie;
import com.compta.firstak.notedefrais.Gestion_Salarie.Get_Salarier;
import com.compta.firstak.notedefrais.Gestion_Salarie.Salarier_Swipe;
import com.compta.firstak.notedefrais.MainActivity;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.entity.Facture;
import com.compta.firstak.notedefrais.GestionFacture.GetFacture;
import com.compta.firstak.notedefrais.GestionFacture.TableFacture;
import com.compta.firstak.notedefrais.adapter.FactureAdapter;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.service.JsonService;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by mohamed on 04/08/2015.
 */
public class DashboardClient extends Activity implements AdapterView.OnItemSelectedListener{
public  static int REQUEST_NUM_FACTURE=12;
    TextView Nom , getNom,Tableaux ,Statistique;

    ImageView Stat1,Stat2,Stat3;
    ImageButton Add,TableauImg,Tableau_Immo,ListeDec;
    ImageView Logo;
    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    public static int idFactureSelected;


    Spinner spinner;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getNom=(TextView)findViewById(R.id.GetNom);
        getNom.setText(UsersAdapter.NameFromSelect);


        TableauImg=(ImageButton)findViewById(R.id.ImageFactureTraiter);
        Tableau_Immo=(ImageButton)findViewById(R.id.ecritureCompta_Immo);
        ListeDec=(ImageButton)findViewById(R.id.ecritureCompta_Dec);
        ListeDec.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(DashboardClient.this,
                        FournisseurDec.class);
                startActivity(intent);
                //finish();
            }
        });

Add=(ImageButton)findViewById(R.id.Add);

        Add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(DashboardClient.this,
                        MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });

       TableauImg.setOnClickListener(new View.OnClickListener() {

           public void onClick(View v) {
               Intent intent = new Intent(DashboardClient.this,
                       TableFacture.class);
               startActivity(intent);
               //finish();
           }
       });
        Tableau_Immo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /*Intent intent = new Intent(DashboardClient.this,
                        TableFacture_Immo.class);
                startActivity(intent);*/
                Intent intent = new Intent(DashboardClient.this,
                        Salarier_Swipe.class);
                startActivity(intent);
                //finish();
            }
        });

        Logo=(ImageView)findViewById(R.id.Logo);

        spinner = (Spinner) findViewById(R.id.ListFacture);




        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);
        getAllFactures();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Facture mSelected = (Facture) parent.getItemAtPosition(position);
         idFactureSelected=mSelected.getId();
        if(mSelected.getId()!=-1) {
            Intent intent = new Intent(DashboardClient.this,
                    GetFacture.class);
            startActivity(intent);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
   @Override
    public void onBackPressed() {
        Intent intent = new Intent(DashboardClient.this,
                MainActivitySwipe.class);
        startActivity(intent);
    }




    private void getAllFactures() {
        networkFailed.setVisibility(View.GONE);
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllFactures();
            }
        });
        if (isNetworkAvailable()) {
            JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.URLGetAllFacture(UsersAdapter.idFromSelect),
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.i("ResponseWsGetFactures", response.toString());
                            ArrayList<Facture> listFacture=JsonService.getListFactures(response);


                            spinner.setOnItemSelectedListener(DashboardClient.this);

                            FactureAdapter adapter = new FactureAdapter(DashboardClient.this,
                                    R.layout.my_custum_list_view_facture,
                                    listFacture);
                            adapter.setDropDownViewResource(
                                    android.R.layout.simple_list_item_1);

                            spinner.setAdapter(adapter); // Set the custom adapter to the spinner

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



}
