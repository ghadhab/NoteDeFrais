package com.compta.firstak.notedefrais;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.compta.firstak.notedefrais.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListImageButton extends Activity {
    /** Called when the activity is first created. */

  public static  ArrayList<String> items;
   public static ListAdapter adapter;
    JSONParser jsonParser = new JSONParser();
    private static String url_GetRaisonSocialeBase="http://192.168.43.247/comptable/Client/GetRaisonSociale.php";
    private static String url_SupprimerAllClient="http://192.168.43.247/comptable/Client/Delete_Client.php";

    private static String url_Update_Archivage_Client="http://192.168.43.247/comptable/Client/Update_Archivage_Client.php";
    public static String GetRaisonSocialeFromBase;
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

       /* Toast.makeText(getApplicationContext(),AjouterClient.FormeSociete,
                Toast.LENGTH_LONG).show();*/


         lv = (ListView) findViewById( R.id.list );

        ImageButton buttonSuppAllClient =
                (ImageButton)findViewById(R.id.btnDeletteAllClient);

        buttonSuppAllClient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                lv.setAdapter(null);
                items.clear();
                //new SupprimerAllClient().execute();
                new Update_Archivage_Client().execute();
            }
        });
        ImageButton buttonAjouter =
                (ImageButton)findViewById(R.id.buttonAjouter);

        buttonAjouter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ListImageButton.this,
                        AjouterClient.class);
                startActivity(intent);
                //finish();
            }
        });

        lv.setOnItemClickListener(
                new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0,
                                            View arg1, int arg2, long arg3) {
                        Intent intent = new Intent(ListImageButton.this,
                                DashboardClient.class);
                        startActivity(intent);
                        //finish();

                    }
                });
        items = new ArrayList<String>();
        items.add( "Client 1 ");
        items.add( "Client 2 ");
        items.add( "Client 3");
        adapter = new ListAdapter( this, items);
        lv.setAdapter(adapter);

    }
    public void addItemList() {
        if (AjouterClient.GetRaisonSociale!=null) {
            new LoadRaisonSociale().execute();
            items.add(0, AjouterClient.GetRaisonSociale);
            AjouterClient.GetRaisonSociale = null;
            adapter.notifyDataSetChanged();
        }

        // itemAdapter.notifyDataSetChanged();*
    }
    @Override
    public void onResume(){
        super.onResume();
        if(AjouterClient.isclicked==true){
            addItemList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (resultCode == 1) {

            }
        }
    }
        class LoadRaisonSociale extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", String.valueOf(AjouterClient.id)));
            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(url_GetRaisonSocialeBase, "POST", params);
            try {

                GetRaisonSocialeFromBase = json.getString("RaisonSociale");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Create Response", json.toString());

            return null;
        }

    }
   /* class SupprimerAllClient extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jsonParser.makeHttpRequest(url_SupprimerAllClient, "POST", params);
            Log.d("Create Response", json.toString());

            return null;
        }

    }*/
    class Update_Archivage_Client extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jsonParser.makeHttpRequest(url_Update_Archivage_Client, "POST", params);
            Log.d("Create Response", json.toString());

            return null;
        }

    }
    }
