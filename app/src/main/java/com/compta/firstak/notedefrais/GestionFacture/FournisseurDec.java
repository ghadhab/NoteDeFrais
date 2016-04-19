package com.compta.firstak.notedefrais.GestionFacture;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.adapter.ListAdapterDec;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.EcritureRow;
import com.compta.firstak.notedefrais.entity.Fournisseur;
import com.compta.firstak.notedefrais.service.JsonService;

import org.json.JSONArray;

import java.util.ArrayList;

public class FournisseurDec extends AppCompatActivity {

    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_facture_by_client);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getAllEcritureByClient();


    }

    private void getAllEcritureByClient() {

        if (isNetworkAvailable()) {
            JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.getURLGetFournisseur_Dec(UsersAdapter.idFromSelect),
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.i("ResponseWsGetFactures", response.toString());
                            ArrayList<Fournisseur> fournisseur= JsonService.getFournisseurDec(response);

                            final TableLayout detailsTable1 = (TableLayout) findViewById(R.id.details_table);
                            final TableRow tableRow1 = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
                            TextView tv1;

                            tv1 = (TextView) tableRow1.findViewById(R.id.tableCell1);
                            tv1.setText("Code");
                            tv1.setTextColor(Color.RED);
                            //Filling in cells
                            tv1 = (TextView) tableRow1.findViewById(R.id.tableCell2);
                            tv1.setText("Fournisseur");
                            tv1.setTextColor(Color.RED);
                            tv1 = (TextView) tableRow1.findViewById(R.id.tableCell3);
                            tv1.setText("Solde");
                            tv1.setTextColor(Color.RED);

                            //Add row to the table
                            detailsTable1.addView(tableRow1);

                            for (Fournisseur nfceProduct : fournisseur) {
                                final TableLayout detailsTable = (TableLayout) findViewById(R.id.details_table);
                                final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
                                TextView tv;

                                tv = (TextView) tableRow.findViewById(R.id.tableCell1);
                                tv.setText(nfceProduct.getCode());
                                //Filling in cells
                                tv = (TextView) tableRow.findViewById(R.id.tableCell2);
                                tv.setText(nfceProduct.getNom_Fournisseur());
                                final TextView finalTv = tv;
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.i("txtAffichee", finalTv.getText().toString());
                                    }
                                });

                                tv = (TextView) tableRow.findViewById(R.id.tableCell3);
                                tv.setText(String.valueOf(nfceProduct.getTottal()));




                                //Add row to the table
                                detailsTable.addView(tableRow);
                            } //End for
                            ////

                            final TableLayout detailsTable2 = (TableLayout) findViewById(R.id.details_table);
                            final TableRow tableRow2 = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
                            TextView tv2;

tableRow2.getVirtualChildAt(0).setVisibility(View.GONE);
                            tv2 = (TextView) tableRow2.findViewById(R.id.tableCell2);
                            tv2.setText("TOTAL :");
                            tv2 = (TextView) tableRow2.findViewById(R.id.tableCell3);
                            tv2.setText(String.valueOf(JsonService.Total));

                            //Add row to the table
                            detailsTable2.addView(tableRow2);



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.abc_slide_in_bottom);

                }
            });

            AppController.getInstance().addToRequestQueue(jreq, "jreq");

        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);

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
