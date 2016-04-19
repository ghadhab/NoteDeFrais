package com.compta.firstak.notedefrais.GestionFacture;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.compta.firstak.notedefrais.*;
import com.compta.firstak.notedefrais.Gestion_Client.ListImageButton;
import com.compta.firstak.notedefrais.adapter.ListAdapterDec;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.Client;
import com.compta.firstak.notedefrais.entity.Fournisseur;
import com.compta.firstak.notedefrais.service.JsonService;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import org.json.JSONArray;

import java.util.ArrayList;

public class ListImageButtonDec extends Activity {
    /** Called when the activity is first created. */

   public static ListAdapterDec adapter;
    public static ArrayList<Fournisseur> items;
    ListView lv;
    private Button actualiserbutton;
    private RelativeLayout networkFailed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);



         lv = (ListView) findViewById( R.id.list);
        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);

        lv.setOnItemClickListener(
                new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0,
                                            View arg1, int arg2, long arg3) {


                    }
                });
        MakeJsonArrayFournisseurDec();
    }


    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (resultCode == 1) {

            }
        }
    }



    private void MakeJsonArrayFournisseurDec() {
        networkFailed.setVisibility(View.GONE);
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeJsonArrayFournisseurDec();
            }
        });
        //String url=AppController.getInstance().getUrlRacine()+AppConfig.URLGetAllClients;
        if (isNetworkAvailable()) {
            JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.getURLGetFournisseur_Dec(UsersAdapter.idFromSelect),
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.i("ResponseWsGetFournisdec", response.toString());
                            items= JsonService.getFournisseurDec(response);

                            ListAdapterDec listAdapterDec=new ListAdapterDec(getApplicationContext(),items,ListImageButtonDec.this);

                            lv.setAdapter(listAdapterDec);


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
