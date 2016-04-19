package com.compta.firstak.notedefrais.GestionFacture;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.EcritureRow;
import com.compta.firstak.notedefrais.service.JsonService;

import org.json.JSONArray;

import java.util.ArrayList;

public class TableFactureByClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_facture_by_client);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        getAllEcritureByClient();


    }

    private void getAllEcritureByClient() {

        if (isNetworkAvailable()) {
            JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.getURLGetEcritureByIdFacture(45),
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.i("ResponseWsGetFactures", response.toString());
                            ArrayList<EcritureRow> listEcriture= JsonService.getListEcritureByRow(response);
                            String[] column = {"N Piece  | Date Saisie", "DATE PIECE", "N COMPTE GENERAL", "INTITULE COMPTE GENERAL",
                                    "N COMPTE AUXILIAIRE", "INTITULE COMPTE AUXILIAIRE", "LIBELLE FACTURE", "DEBIT", "CREDIT", "SOLDE"};
                            //SizeRow=listEcriture.size();

                            ////
                            for (EcritureRow nfceProduct : listEcriture) {
                                final TableLayout detailsTable = (TableLayout) findViewById(R.id.details_table);
                                final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
                                TextView tv;

                                //Filling in cells
                                tv = (TextView) tableRow.findViewById(R.id.tableCell1);
                                tv.setText(nfceProduct.getColonne1());
                                final TextView finalTv = tv;
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.i("txtAffichee", finalTv.getText().toString());
                                    }
                                });

                                tv = (TextView) tableRow.findViewById(R.id.tableCell2);
                                tv.setText(nfceProduct.getColonne2());

                                tv = (TextView) tableRow.findViewById(R.id.tableCell3);
                                tv.setText(nfceProduct.getColonne3());

                              //  tv = (TextView) tableRow.findViewById(R.id.tableCell4);
                                tv.setText(nfceProduct.getColonne4());

                                //Add row to the table
                                detailsTable.addView(tableRow);
                            } //End for
                            ////
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
