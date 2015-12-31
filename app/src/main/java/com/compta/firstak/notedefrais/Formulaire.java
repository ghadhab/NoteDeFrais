package com.compta.firstak.notedefrais;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.compta.firstak.notedefrais.ExpandableList.MainActivityList;

import java.util.Calendar;


/**
 *
 * Created by mohamed on 08/07/2015.
 */
public class Formulaire extends Activity {
   // public static ChoixPhotoResult LastResult;
    AutoCompleteTextView Nofacture, Matricule_fiscale, Mf, Tva, TottalTTC, NoTel, NoFax,Email,Web,HTVA;
    EditText  Date,Designation;
    Calendar myCalendar = Calendar.getInstance();
    Button Submit;
   public static String TvaText,DesignationText,HTVAtext,TottalText,DateText,TvaListener,HTvaListener,TottalListener;
    Double HTVAChange;
    public static boolean isNew;
    ChoixPhotoResult ch;
    ImageView Etat_NoFacture,Etat_MatriculeMatricule,Etat_Mf,Etat_Tva,Etat_TottalTTC,Etat_Htva,Etat_Notel,Etat_Nofax,Etat_Email,Etat_Site,Etat_Date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.formulaire);

         ch = getIntent().getExtras().getParcelable("ch");
        Bundle extras = getIntent().getExtras();

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
        Tva.setText(ch.TVA);
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
                TvaText = Tva.getText().toString();
                TottalText = TottalTTC.getText().toString();
                DateText = Date.getText().toString();
                HTVAtext = HTVA.getText().toString();
                DesignationText = Designation.getText().toString();
                ParsingOcrResult.ParseDesignation(DesignationText);
               // Log.i("  ", "distaaaaaaaaance " + ParsingOcrResult.DesignationAtMin.toString() + "    Code  " + ParsingOcrResult.Code);
                //Toast.makeText(getApplicationContext(), ParsingOcrResult.DesignationAtMin.toString() + "    Code  " + ParsingOcrResult.Code,
                  //      Toast.LENGTH_LONG).show();
               /* if(Submit.isClickable()) {
                    DashboardClient.spinnerArray.add("Facture"+i);
                    DashboardClient.adapter.notifyDataSetChanged();
                    i++;
                }*/

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

}
