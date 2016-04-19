package com.compta.firstak.notedefrais.GestionFacture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.Gestion_Client.DashboardClient;
import com.compta.firstak.notedefrais.MainActivity;
import com.compta.firstak.notedefrais.ParsingOcrResult;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.EcritureRow;
import com.compta.firstak.notedefrais.service.JsonService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableFacture extends Activity {
    public static ArrayList<String> DictionnaireCodeComptable;
    public static ArrayList<String> CodeComptable;
    public  StringBuilder text;
    TextView textView2;
  // EditText textView;
    public  int id;
    public int rowCount;
    public static boolean enableid13, enableid23, enableid25, enableid15;
    String TVaValue;
    int p = 0;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences prefs;
     int next = 0;
    String NPiece;
    private String reqAddEcriture,reqGetEcriture,reqAddReference;
    Bundle  extras ;
    int SizeRow;
    JSONObject postParam;
    static int GetTextCode;
    static String GetTextCompte,GetTextDesignation;
    Button myButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("ArrayList", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();

        extras = getIntent().getExtras();
        getAllEcritureByClient();

        readRawTextFile(getApplicationContext(), R.raw.plancomptable1);

    }

    private void getAllEcritureByClient() {

        if (isNetworkAvailable()) {
            JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.getURLGetEcritureByIdFacture(UsersAdapter.idFromSelect),
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(final JSONArray response) {
                            Log.i("ResponseWsGetFactures", response.toString());


//---------------------------------------------------------------------------------------------------------
                            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.GET,
                                    AppConfig.findFournisseurExploitation(UsersAdapter.idFromSelect, Formulaire.GetFournisseur), null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject CodeFourJsonFromJson) {
                                            Log.d("RespWSGetClient", CodeFourJsonFromJson.toString());

                                            try {
                                                CodeFourJsonFromJson.get("fournisseeur").toString();
                                                CodeFourJsonFromJson.getInt("code");
                                                CodeFourJsonFromJson.getInt("count");
                                                CodeFourJsonFromJson.getBoolean("exist");

                                            } catch (Throwable t) {

                                            }


//---------------------------------------------------------------------------------------------------------
                                            ArrayList<EcritureRow> listEcriture = JsonService.getListEcriture(response);
                                            String[] column = {"N Piece  | Date Saisie", "DATE PIECE", "N COMPTE GENERAL", "INTITULE COMPTE GENERAL",
                                                    "N COMPTE AUXILIAIRE", "INTITULE COMPTE AUXILIAIRE", "LIBELLE FACTURE", "DEBIT", "CREDIT", "SOLDE"};
                                            SizeRow = listEcriture.size();


                                            TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
                                            TableLayout tableLayout = new TableLayout(TableFacture.this);
                                            tableLayout.setBackgroundColor(Color.BLACK);

                                            // 2) create tableRow params
                                            TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
                                            tableRowParams.setMargins(1, 1, 1, 1);
                                            tableRowParams.weight = 1;

                                            myButton = new Button(TableFacture.this);
                                            myButton.setText("Valider");
                                            myButton.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                                                    TableLayout.LayoutParams.WRAP_CONTENT));


                                            for (int m = 0; m < 1; m++) {
                                                // 3) create tableRow
                                                TableRow tableRow1 = new TableRow(TableFacture.this);
                                                tableRow1.setBackgroundColor(Color.BLACK);

                                                for (int n = 0; n < 10; n++) {
                                                    // 4) create textView
                                                    TextView textView1 = new TextView(TableFacture.this);
                                                    textView1.setBackgroundColor(Color.parseColor("#791634"));
                                                    textView1.setTextColor(Color.WHITE);
                                                    textView1.setGravity(Gravity.CENTER);
                                                    textView1.setMaxLines(1);

                                                    if (m == 0) {
                                                        textView1.setText(column[n]);
                                                    }
                                                    tableRow1.addView(textView1, tableRowParams);
                                                }
                                                tableLayout.addView(tableRow1, tableLayoutParams);
                                            }

                                            //////
                                            // rowCount=0;
                                            rowCount = rowCount + 3;
                                            for (int k = 0; k < SizeRow; k++) {

                                                for (int i = 0; i < rowCount; i++) {
                                                    // 3) create tableRow
                                                    TableRow tableRow = new TableRow(TableFacture.this);
                                                    tableRow.setBackgroundColor(Color.BLACK);

                                                    for (int j = 0; j < column.length; j++) {
                                                        // 4) create textView

                                                        String s1 = Integer.toString(i);
                                                        String s2 = Integer.toString(j);
                                                        String s3 = s1 + s2;

                                                        id = Integer.parseInt(s3);
                                                        textView2 = new TextView(TableFacture.this);
                                                        textView2.setTextColor(Color.WHITE);
                                                        textView2.setGravity(Gravity.CENTER);
                                                        textView2.setMaxLines(1);


                                                        if (id == p) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonneNext() + "        " + listEcriture.get(k).getColonne1());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));

                                                        }
                                                        if (id == p + 1) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne2());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                        }
                                                        if (id == p + 2) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne3());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                        }
                                                        if (id == p + 3) {

                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne4());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                        }
                                                        if (id == p + 4) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne5());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));

                                                        }
                                                        if (id == p + 5) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne6());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));

                                                        }
                                                        if (id == p + 6) {
                                                            textView2.setText(listEcriture.get(k).getColonne7());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));

                                                        }
                                                        if (id == p + 7) {
                                                            textView2.setText(listEcriture.get(k).getColonne8());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));

                                                        }
                                                        if (id == p + 8) {
                                                            textView2.setText(listEcriture.get(k).getColonne9());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));

                                                        }
                                                        if (id == p + 9) {
                                                            textView2.setText(listEcriture.get(k).getColonne10());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));

                                                        }

                                                        if (id == p + 10) {
                                                            textView2.setText(listEcriture.get(k).getColonneNext() + "        " + listEcriture.get(k).getColonne11());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));

                                                        }
                                                        if (id == p + 11) {
                                                            textView2.setText(listEcriture.get(k).getColonne12());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));

                                                        }
                                                        if (id == p + 12) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne13());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));

                                                        }
                                                        if (id == p + 13) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne14());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));
                                                        }
                                                        if (id == p + 14) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne15());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));

                                                        }
                                                        if (id == p + 15) {
                                                            textView2.setInputType(InputType.TYPE_NULL);
                                                            textView2.setText(listEcriture.get(k).getColonne16());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));

                                                        }

                                                        if (id == p + 16) {
                                                            textView2.setText(listEcriture.get(k).getColonne17());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));
                                                        }
                                                        if (id == p + 17) {

                                                            textView2.setText(listEcriture.get(k).getColonne18());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));


                                                        }
                                                        if (id == p + 18) {
                                                            textView2.setText(listEcriture.get(k).getColonne19());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));
                                                        }
                                                        if (id == p + 19) {
                                                            textView2.setText(listEcriture.get(k).getColonne20());
                                                            textView2.setBackgroundColor(Color.parseColor("#206795"));
                                                        }
                                                        if (id == p + 20) {
                                                            textView2.setText(listEcriture.get(k).getColonneNext() + "        " + listEcriture.get(k).getColonne21());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        if (id == p + 21) {
                                                            textView2.setText(listEcriture.get(k).getColonne22());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        if (id == p + 22) {
                                                            textView2.setText(listEcriture.get(k).getColonne23());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        if (id == p + 23) {
                                                            textView2.setText(listEcriture.get(k).getColonne24());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        if (id == p + 24) {
                                                            textView2.setText(listEcriture.get(k).getColonne25());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        if (id == p + 25) {
                                                            textView2.setText(listEcriture.get(k).getColonne26());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }

                                                        if (id == p + 26) {
                                                            textView2.setText(listEcriture.get(k).getColonne27());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        if (id == p + 27) {
                                                            textView2.setText(listEcriture.get(k).getColonne28());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        if (id == p + 28) {
                                                            textView2.setText(listEcriture.get(k).getColonne29());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        if (id == p + 29) {
                                                            textView2.setText(listEcriture.get(k).getColonne30());
                                                            textView2.setBackgroundColor(Color.parseColor("#599cc6"));
                                                        }
                                                        // 5) add textView to tableRow
                                                        tableRow.addView(textView2, tableRowParams);

                                                    }

                                                    // 6) add tableRow to tableLayout
                                                    tableLayout.addView(tableRow, tableLayoutParams);


                                                }
                                            }
                                            ///////Damak


                                            if (Formulaire.isNew == true) {
                                                //rowCount = rowCount + 3;
                                                next = next + 1;


                                                postParam = new JSONObject();
                                                for (int i = 0; i < rowCount; i++) {
                                                    // 3) create tableRow
                                                    TableRow tableRow = new TableRow(TableFacture.this);
                                                    tableRow.setBackgroundColor(Color.BLACK);

                                                    for (int j = 0; j < column.length; j++) {
                                                        // 4) create textView

                                                        String s1 = Integer.toString(i);
                                                        String s2 = Integer.toString(j);
                                                        String s3 = s1 + s2;

                                                        id = Integer.parseInt(s3);
                                                        final EditText textView = new EditText(TableFacture.this);
                                                        textView.setTextColor(Color.WHITE);
                                                        textView.setGravity(Gravity.CENTER);
                                                        textView.setMaxLines(1);


                                                        if (rowCount > 3) {
                                                            p = (rowCount * 10) - 30;

                                                        }

                                                        reqAddEcriture = "json_obj_req_Add_EcritureComptable";
                                                        Log.i("UrlAddEcritureComptable", AppConfig.AddURLAddEcritureComptable);
                                                        JSONObject obj = null;
                                                        try {

                                                            obj = new JSONObject();
                                                            obj.put("id", String.valueOf(UsersAdapter.idFromSelect));
                                                            Log.d("My App", obj.toString());


                                                        } catch (Throwable t) {

                                                        }

                                                        try {
                                                            postParam.put("client", obj);
                                                            if (id == p) {
                                                                textView.setInputType(InputType.TYPE_NULL);
                                                                textView.setText(MainActivity.Date);
                                                                postParam.put("colonne1", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));

                                                            }
                                                            if (id == p + 1) {
                                                                textView.setInputType(InputType.TYPE_NULL);
                                                                textView.setText(Formulaire.GetDateText);
                                                                postParam.put("colonne2", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne2", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 2) {
                                                                // textView.setInputType(InputType.TYPE_NULL);

                                                                textView.setText(String.valueOf(Formulaire.GetCode));
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                GetTextCode = Integer.parseInt(textView.getText().toString());
                                                                postParam.put("colonne3", textView.getText().toString());
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne3", textView.getText().toString());
                                                                            GetTextCode = Integer.parseInt(textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 3) {

                                                                // textView.setInputType(InputType.TYPE_NULL);

                                                                // textView.setText(ParsingOcrResult.DesignationAtMin);
                                                                textView.setText(Formulaire.GetCompte);
                                                                GetTextCompte = textView.getText().toString();
                                                                postParam.put("colonne4", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne4", textView.getText().toString());
                                                                            GetTextCompte = textView.getText().toString();
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 4) {
                                                                //textView.setInputType(InputType.TYPE_NULL);
                                                                textView.setText(String.valueOf(Formulaire.GetCode));
                                                                postParam.put("colonne5", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne5", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 5) {
                                                                // textView.setInputType(InputType.TYPE_NULL);
                                                                textView.setText(Formulaire.GetCompte);
                                                                postParam.put("colonne6", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne6", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 6) {
                                                                textView.setText(Formulaire.GetDesignationText);
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                GetTextDesignation = textView.getText().toString();
                                                                postParam.put("colonne7", textView.getText().toString());
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne7", textView.getText().toString());
                                                                            GetTextDesignation = textView.getText().toString();
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });


                                                            }
                                                            if (id == p + 7) {
                                                                textView.setText(String.format("%.3f", Double.valueOf(Formulaire.GetHTVAtext)));
                                                                postParam.put("colonne8", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne8", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }



                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });


                                                            }
                                                            if (id == p + 8) {
                                                                textView.setText("");
                                                                postParam.put("colonne9", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne9", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 9) {
                                                                textView.setText(String.format("%.3f", Double.valueOf(Formulaire.GetHTVAtext)));
                                                                postParam.put("colonne10", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#0ac8b8"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne10", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });


                                                            }

                                                            if (id == p + 10) {
                                                                textView.setText(MainActivity.Date);
                                                                postParam.put("colonne11", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne11", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });


                                                            }
                                                            if (id == p + 11) {
                                                                textView.setText(Formulaire.GetDateText);
                                                                postParam.put("colonne12", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne12", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 12) {
                                                                //textView.setInputType(InputType.TYPE_NULL);
                                                                textView.setText("4366");
                                                                postParam.put("colonne13", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne13", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 13) {
                                                                // textView.setInputType(InputType.TYPE_NULL);
                                                                textView.setText("Taxes sur le chiffre d'affaires deductibles");
                                                                postParam.put("colonne14", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne14", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 14) {
                                                                // textView.setInputType(InputType.TYPE_NULL);
                                                                textView.setText("4366");
                                                                postParam.put("colonne15", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne15", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 15) {
                                                                // textView.setInputType(InputType.TYPE_NULL);
                                                                textView.setText("Taxes sur le chiffre d'affaires deductibles");
                                                                postParam.put("colonne16", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne16", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }

                                                            if (id == p + 16) {
                                                                textView.setText(Formulaire.GetDesignationText);
                                                                postParam.put("colonne17", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne17", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 17) {
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                try {
                                                                    Double TVAi = Double.parseDouble(Formulaire.GetTvaText.replace("%", ""));
                                                                    TVaValue =String.format("%.3f", Double.parseDouble(Formulaire.GetHTVAtext) * (TVAi / 100));
                                                                    // textView.setText(String.format("%.3f", TVaValue));
                                                                    textView.setText(TVaValue);
                                                                    postParam.put("colonne18", textView.getText().toString());
                                                                    textView.addTextChangedListener(new TextWatcher() {
                                                                        public void afterTextChanged(Editable s) {
                                                                            try {
                                                                                postParam.put("colonne18", textView.getText().toString());
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                        }

                                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                        }
                                                                    });
                                                                    //textView.setBackgroundColor(Color.parseColor("#206795"));
                                                                } catch (Exception ex) {

                                                                }

                                                            }
                                                            if (id == p + 18) {
                                                                textView.setText("");
                                                                postParam.put("colonne19", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne19", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });

                                                            }
                                                            if (id == p + 19) {
                                                                textView.setText(TVaValue);
                                                                postParam.put("colonne20", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#34e2d4"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne20", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 20) {
                                                                NPiece = MainActivity.Année;
                                                                textView.setOnClickListener(new View.OnClickListener() {

                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        Intent intent = new Intent();
                                                                        intent.setAction(android.content.Intent.ACTION_VIEW);
                                                                        intent.addCategory(android.content.Intent.CATEGORY_DEFAULT);
                                                                        intent.setDataAndType(Uri.parse(MainActivity.imageFileName), "image/*");
                                                                        startActivity(intent);
                                                                    }

                                                                });
                                                                textView.setText(MainActivity.Date);
                                                                postParam.put("colonne21", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                            }
                                                            if (id == p + 21) {
                                                                textView.setText(Formulaire.GetDateText);
                                                                postParam.put("colonne22", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));

                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne22", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 22) {
                                                                textView.setText("401");
                                                                postParam.put("colonne23", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne23", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 23) {
                                                                textView.setText(Formulaire.GetFournisseur);
                                                                postParam.put("colonne24", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne24", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 24) {
                                                                textView.setText(String.valueOf(CodeFourJsonFromJson.getInt("code")));
                                                                postParam.put("colonne25", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne25", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 25) {
                                                                textView.setText(Formulaire.GetFournisseur);
                                                                postParam.put("colonne26", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne26", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }

                                                            if (id == p + 26) {
                                                                textView.setText(Formulaire.GetDesignationText);

                                                                postParam.put("colonne27", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne27", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 27) {
                                                                textView.setText("");
                                                                postParam.put("colonne28", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                            }
                                                            if (id == p + 28) {
                                                                Double TTC=Double.valueOf(Formulaire.GetTottalText)- ParsingOcrResult.timbre;
                                                                textView.setText(String.valueOf(TTC));
                                                                postParam.put("colonne29", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne29", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            if (id == p + 29) {
                                                                Double TTC=Double.valueOf(Formulaire.GetTottalText)- ParsingOcrResult.timbre;
                                                                textView.setText(String.valueOf(TTC));
                                                                postParam.put("colonne30", textView.getText().toString());
                                                                textView.setBackgroundColor(Color.parseColor("#89eae2"));
                                                                textView.addTextChangedListener(new TextWatcher() {
                                                                    public void afterTextChanged(Editable s) {
                                                                        try {
                                                                            postParam.put("colonne30", textView.getText().toString());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                                    }

                                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                    }
                                                                });
                                                            }
                                                            postParam.put("fournisseur", Formulaire.GetFournisseur);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                        // 5) add textView to tableRow
                                                        tableRow.addView(textView, tableRowParams);

                                                    }

                                                    // 6) add tableRow to tableLayout
                                                    tableLayout.addView(tableRow, tableLayoutParams);


                                                }
                                            } else {
                                                myButton.setVisibility(View.GONE);
                                            }


                                            //tableLayout.addView(myButton);
                                            myButton.setOnClickListener(new View.OnClickListener() {

                                                public void onClick(View v) {
                                                    if (isNetworkAvailable()) {
                                                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppConfig.AddURLAddEcritureComptable,
                                                                postParam,
                                                                new Response.Listener<JSONObject>() {
                                                                    @Override
                                                                    public void onResponse(JSONObject ADDClientJsonFromJson) {
                                                                        Log.d("RespWSAddFournisseur", ADDClientJsonFromJson.toString());


                                                                        //------------------------------------------------------------
//if(Formulaire.GetDistance>10) {

                                                                        reqAddReference = "json_obj_req_Add_reference";
                                                                        Log.i("UrlAddReference", AppConfig.AddURLAddReference);

                                                                        final JSONObject postParam1 = new JSONObject();
                                                                        try {

                                                                            postParam1.put("client", UsersAdapter.NameFromSelect);
                                                                            postParam1.put("compte", GetTextCompte);
                                                                            postParam1.put("fournisseur", Formulaire.GetFournisseur);
                                                                            postParam1.put("libelle", GetTextDesignation);
                                                                            postParam1.put("numCompte", GetTextCode);


                                                                            Log.i("postParam1", postParam1.toString());

                                                                            //Log.i("UrlAddFournisseur", AppConfig.AddURLAddFacture);


                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Request.Method.POST, AppConfig.AddURLAddReference,
                                                                                postParam1,
                                                                                new Response.Listener<JSONObject>() {
                                                                                    @Override
                                                                                    public void onResponse(JSONObject ADDReferenceJsonFromJson) {
                                                                                        Log.d("RespWSAddReference", ADDReferenceJsonFromJson.toString());
                                                                                        myButton.setVisibility(View.GONE);

                                                                                    }
                                                                                }, new Response.ErrorListener() {
                                                                            @Override
                                                                            public void onErrorResponse(VolleyError error) {
                                                                                Log.i("ErrorMessageVolleyRef", "Error: " + error.getMessage());
                                                                                VolleyLog.d("TAGVolley", "Error: " + error.getMessage());
                                                                                Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                                                                                        R.anim.abc_slide_in_bottom);
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
                                                                        AppController.getInstance().addToRequestQueue(jsonObjReq1, reqAddReference);


//}

                                                                        // ----------------------------------------------------------


                                                                    }
                                                                }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Log.i("ErrorMessageVolleyEcr", "Error: " + error.getMessage());
                                                                VolleyLog.d("TAGVolley", "Error: " + error.getMessage());
                                                                Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                                                                        R.anim.abc_slide_in_bottom);
                                                            }
                                                        }) {


                                                            @Override
                                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                                HashMap<String, String> headers = new HashMap<String, String>();
                                                                return headers;
                                                            }

                                                            ;
                                                        };

                                                        AppController.getInstance().addToRequestQueue(jsonObjReq, reqAddEcriture);
                                                    } else {
                                                        Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                                                                R.anim.abc_slide_in_bottom);

                                                    }
                                                }
                                            });
                                            tableLayout.addView(myButton);
                                            ScrollView sv = new ScrollView(TableFacture.this);
                                            HorizontalScrollView hsv = new HorizontalScrollView(TableFacture.this);
                                            hsv.addView(tableLayout);
                                            sv.addView(hsv);
                                            setContentView(sv);


                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                                                    R.anim.abc_slide_in_bottom);

                                        }
                                    });

                            AppController.getInstance().addToRequestQueue(jsonObjReq, "jreq");


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




        //------------------------------------

//---------------------------------------------------------








}









    public String readRawTextFile(Context ctx, int resId) {
        try {
            DictionnaireCodeComptable = new ArrayList<String>();
            CodeComptable = new ArrayList<String>();
            InputStream inputStream = ctx.getResources().openRawResource(resId);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            text = new StringBuilder();


            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\=");
                for (int i = 0; i < data.length - 1; i = i + 2) {
                    DictionnaireCodeComptable.add(data[i + 1]);
                }
                for (int j = 0; j < data.length; j++) {
                    CodeComptable.add(data[j]);
                }
            }
            for (String ss : DictionnaireCodeComptable) {

                // System.out.println(ss);
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }
        System.out.println("txt  " + text.toString());
        return text.toString();

    }

    @Override
    public void onBackPressed() {
        //Formulaire.isNew = false;
        Intent intent = new Intent(TableFacture.this,
                DashboardClient.class);
        startActivity(intent);
    }

    public void onPause() {
        super.onPause();
        Formulaire.isNew = false;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }





}
