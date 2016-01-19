package com.compta.firstak.notedefrais;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.compta.firstak.notedefrais.Gestion_Client.Client;
import com.compta.firstak.notedefrais.Gestion_Client.MainActivitySwipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 04/08/2015.
 */
public class DashboardClient extends Activity implements AdapterView.OnItemSelectedListener{
public  static int REQUEST_NUM_FACTURE=12;
    TextView Nom , getNom,Tableaux ,Statistique;

    ImageView Stat1,Stat2,Stat3;
    ImageButton Add,TableauImg;
    ImageView Logo;



   public static  List<String> spinnerArray;
   public static ArrayAdapter<String> adapter;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

//if(TableFacture.rowCount>0) {
    TableFacture.rowCount = TableFacture.rowCount - 3;
//}
        getNom=(TextView)findViewById(R.id.GetNom);


        TableauImg=(ImageButton)findViewById(R.id.ImageFactureTraiter);


Add=(ImageButton)findViewById(R.id.Add);

        Add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                 Intent intent = new Intent(DashboardClient.this,
                        MainActivity.class);
              //  Client addedClient1 = getIntent().getExtras().getParcelable("client");
               // intent.putExtra("addedClient1",addedClient1);
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


        Logo=(ImageView)findViewById(R.id.Logo);

        Spinner spinner = (Spinner) findViewById(R.id.ListFacture);
        spinner.setOnItemSelectedListener(this);
         spinnerArray =  new ArrayList<>();
        spinnerArray.add("");
        spinnerArray.add("Facture 1");
        spinnerArray.add("Facture 2");
        spinnerArray.add("Facture 3");
        spinnerArray.add("Facture 4");
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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


}
