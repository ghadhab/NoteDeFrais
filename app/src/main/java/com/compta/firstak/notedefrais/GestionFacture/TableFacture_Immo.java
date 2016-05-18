package com.compta.firstak.notedefrais.GestionFacture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.Gestion_Client.DashboardClient;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.EcritureRow;
import com.compta.firstak.notedefrais.service.JsonService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TableFacture_Immo extends Activity {
    public  StringBuilder text;
    TextView textView2;
    public  int id;
    public int rowCount;
    int p = 0;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences prefs;
    Bundle  extras ;
    int SizeRow;
    int Taux;
    Double Amortissement;
    Double Htva;
    String[] column= {"  Compte  ", "  Date  ", "  Nature  ", "  LIBELLE  ",
            "  HTVA  ", "  TAUX  ", "  AMORTISSEMENT DE L'EXERCICE  "};
    //,"Intitule Du Compte"
    ArrayList<EcritureRow> listEcriture;
    TableRow.LayoutParams tableRowParams;
    JSONObject postParam;
    TableLayout.LayoutParams tableLayoutParams;
    TableLayout tableLayout;
    int i;
    Calendar myCalendar = Calendar.getInstance();
    long elapsedDays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("ArrayList", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();

        extras = getIntent().getExtras();
        getAllEcritureByClient();


    }

    private void getAllEcritureByClient() {

        if (isNetworkAvailable()) {
            JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.getURLGetEcritureByIdFacture_Immo(UsersAdapter.idFromSelect),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.i("ResponseWsGetFactures", response.toString());
                             listEcriture = JsonService.getListEcriture(response);
                            SizeRow = listEcriture.size();


                             tableLayoutParams = new TableLayout.LayoutParams();
                             tableLayout = new TableLayout(TableFacture_Immo.this);
                            tableLayout.setBackgroundColor(Color.BLACK);

                            // 2) create tableRow params
                          tableRowParams = new TableRow.LayoutParams();
                            tableRowParams.setMargins(1, 1, 1, 1);
                            tableRowParams.weight = 1;


                            for (int m = 0; m < 1; m++) {
                                // 3) create tableRow
                                TableRow tableRow1 = new TableRow(TableFacture_Immo.this);
                                tableRow1.setBackgroundColor(Color.BLACK);

                                for (int n = 0; n < 7; n++) {
                                    // 4) create textView
                                    TextView textView1 = new TextView(TableFacture_Immo.this);
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


                            //-------------------------------------------------------------------------------------






                    rowCount = rowCount + 1;
                                            for (int k = 0; k < SizeRow; k++) {

                                                final int finalK = k;
                                                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                                        AppConfig.Get_referenceAmortissement_by_num_Compte(Integer.valueOf(listEcriture.get(k).getColonne3())), null,
                                                        new Response.Listener<JSONObject>() {
                                                            @Override
                                                            public void onResponse(JSONObject CodeFourJsonAmortissemnt) {
                                                                Log.d("RespWSGetClient", CodeFourJsonAmortissemnt.toString());
                                                                try {




                                                                    for ( i = 0; i < rowCount; i++) {





                                                    // 3) create tableRow
                                                    TableRow tableRow = new TableRow(TableFacture_Immo.this);
                                                    tableRow.setBackgroundColor(Color.BLACK);

                                                    for (int j = 0; j < column.length; j++) {
                                                        // 4) create textView

                                                        String s1 = Integer.toString(i);
                                                        String s2 = Integer.toString(j);
                                                        String s3 = s1 + s2;

                                                        id = Integer.parseInt(s3);
                                                        textView2 = new TextView(TableFacture_Immo.this);
                                                        textView2.setTextColor(Color.WHITE);
                                                        textView2.setGravity(Gravity.CENTER);
                                                        textView2.setMaxLines(1);


                                                        if (id == p) {
                                                            textView2.setText(listEcriture.get(finalK).getColonne3());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));


                                                        }
                                                        if (id == p + 1) {
                                                            textView2.setText(listEcriture.get(finalK).getColonne2());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                        }
                                                       /* if (id == p + 3) {
                                                            textView2.setText(CodeFourJsonAmortissemnt.get("intituleCompte").toString());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                        }*/

                                                        if (id == p + 2) {
                                                            if(Double.parseDouble(listEcriture.get(finalK).getColonne8())>200.0){
                                                            textView2.setText(CodeFourJsonAmortissemnt.get("nature").toString());}
                                                            else{
                                                                textView2.setText("Charge");
                                                            }
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                        }
                                                        if (id == p + 3) {
                                                            textView2.setMaxLines(1);
                                                            textView2.setText(listEcriture.get(finalK).getColonne27());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                        }
                                                        if (id == p + 4) {
                                                            textView2.setText(listEcriture.get(finalK).getColonne8());
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                            Htva = Double.parseDouble(listEcriture.get(finalK).getColonne8());
                                                        }
                                                        if (id == p + 5) {
                                                            if(Double.parseDouble(listEcriture.get(finalK).getColonne8())>200.0){
                                                                textView2.setText(String.valueOf(CodeFourJsonAmortissemnt.get("taux")+"%"));
                                                            }
                                                            else{
                                                                textView2.setText("100"+"%");
                                                            }
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));

                                                        }
                                                        if (id == p + 6) {

                                                            String myFormat = "dd/MM/yyyy";
                                                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);


                                                            SimpleDateFormat simpleDateFormat =
                                                                    new SimpleDateFormat("dd/MM/yyyy");

                                                            try {

                                                                int g=myCalendar.get(Calendar.YEAR);
                                                                String h=listEcriture.get(finalK).getColonne2();
                                                                String f="31/12/"+String.valueOf(g);
                                                                Date date1 = simpleDateFormat.parse(listEcriture.get(finalK).getColonne2());
                                                                Date date2 = simpleDateFormat.parse("31/12/"+g);

                                                              // printDifference(date1, date2);
                                                                long different = date2.getTime() - date1.getTime();

                                                                long secondsInMilli = 1000;
                                                                long minutesInMilli = secondsInMilli * 60;
                                                                long hoursInMilli = minutesInMilli * 60;
                                                                long daysInMilli = hoursInMilli * 24;

                                                                elapsedDays = different / daysInMilli;
                                                                different = different % daysInMilli;

                                                                System.out.printf(
                                                                        "%d days%n", elapsedDays/365
                                                                );

                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }

                                                            Double a=Double.valueOf(listEcriture.get(finalK).getColonne8());
                                                            double b= (int)CodeFourJsonAmortissemnt.get("taux")/100.0;
                                                            double c=((double)(elapsedDays)/365);
                                                            Amortissement = a *b*c;
                                                            //
                                                            if(Double.parseDouble(listEcriture.get(finalK).getColonne8())>200.0) {
                                                                textView2.setText(String.format("%.3f", Amortissement));
                                                            }
                                                            else{
                                                                textView2.setText("0.0");
                                                            }
                                                            textView2.setBackgroundColor(Color.parseColor("#004774"));
                                                        }
                                                        // 5) add textView to tableRow
                                                        tableRow.addView(textView2, tableRowParams);
                                                    }

                                                    // 6) add tableRow to tableLayout
                                                    tableLayout.addView(tableRow, tableLayoutParams);



                                                }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                //**********************************************************
                                                             /*   if (isNetworkAvailable()) {
                                                                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppConfig.AddURLAddImmo,
                                                                            postParam,
                                                                            new Response.Listener<JSONObject>() {
                                                                                @Override
                                                                                public void onResponse(JSONObject ADDClientJsonFromJson) {
                                                                                    Log.d("RespWSAddFournisseur", ADDClientJsonFromJson.toString());
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

                                                                    AppController.getInstance().addToRequestQueue(jsonObjReq, "reqAddImmo");
                                                                } else {
                                                                    Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                                                                            R.anim.abc_slide_in_bottom);

                                                                }
*/
                                                                //**********************************************************

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.i("ErrorMessageVolley", "Error: " + error.getMessage());
                                                        VolleyLog.d("TAGVolley", "Error: " + error.getMessage());

                                                        Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                                                                R.anim.abc_slide_in_bottom);
                                                    }
                                                });
                                                // Adding request to request queue
                                                AppController.getInstance().addToRequestQueue(jsonObjReq, "jsonObjReq");



                                            }
                                            ScrollView sv = new ScrollView(TableFacture_Immo.this);
                                            HorizontalScrollView hsv = new HorizontalScrollView(TableFacture_Immo.this);
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

                            AppController.getInstance().addToRequestQueue(jreq, "jreq");

                        }

    else

                        {
                            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.abc_slide_in_bottom);

                        }



}


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TableFacture_Immo.this,
                DashboardClient.class);
        startActivity(intent);
    }

    public void onPause() {
        super.onPause();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static boolean tryParseTaux(String value) {
        try {
            int a= Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void printDifference(Date startDate, Date endDate){

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        System.out.printf(
                "%d days%n", elapsedDays
              );


    }

}
