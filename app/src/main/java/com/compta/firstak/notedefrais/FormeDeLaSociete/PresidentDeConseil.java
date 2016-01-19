package com.compta.firstak.notedefrais.FormeDeLaSociete;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.compta.firstak.notedefrais.Gestion_Client.AjouterClient;
import com.compta.firstak.notedefrais.Gestion_Client.MainActivitySwipe;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mohamed on 10/08/2015.
 */
public class PresidentDeConseil extends Activity {
ImageButton AddPresidentConseil;
    private PopupWindow pwindo;
  Button  btnValiderPopup,btnCancelPopup,btnValiderPopup1,btnCancelPopup1,btnCancelPopup2,btnValiderPopup2,btnValiderPopup3,btnCancelPopup3,btnValiderPopup4,btnCancelPopup4,btnValiderPopup5,btnCancelPopup5,ValiderNombreAssocie,ValiderNombreActionnaire;
TextView txtRaisonSocialeActioannaire,txtViewMatriculeFiscaleActioannaire,txtViewNRegistreActioannaire,txtViewActionnaire;
    EditText EditTextRaisonSocialeActioannaire,EditTextMatriculeFiscaleActioannaire,EditTextNRegistreActioannaire,EditTextAdresseActioannaire;
    EditText EdittextPresidentdeconseil,EditTextDG,NomConseilDadministrtion,NombreAction,EditTextNomActionnaire,EditTextNomIndividelle,EditTextPrenomIndividelle,EditTextAssocie, EditTextNB, EditTextNbrNominale,EditTextNometPrenomGerant,EditTextTotal,EditTextAssociesuarl, EditTextNombrePartSuarl,EditTextNombreNominale,EditTextTotalsuarl,ValeurNominale,Total,EdittextDuMandat,EdittextAuMandat, EdittextDuConseil, EdittextAuConseil,EditTextNombreAssocie,EditTextNombreActionnaire, EditTextDuSarl, EditTextAuSarl,EditTextMatriculeFiscaleEI,EditTextAdresseEI,EditTextPartCapitalSarl,EditTextPartCapitalActionnaire;
    public static ArrayList<String> items;
    public static ListAdpterFormeSociete adapter;

   public static String RaisonSocialeActioannaire,MatriculeFiscaleActioannaire,NRegistreActioannaire,AdresseActioannaire,TypeDactionnaire,NomPrenomActionnaire;
    public static String NomPresident,NomDuConseil,NomActionnaire,NomIndividuelle,NomAssocie,PrenomIndividuelle,GetDG,NomAssociesuarl,Choix,NometPrenomGerant,MatriculeFiscaleEI,AdresseEI;
    public static int NombreAsoocie,TotalAssocie,NombrePartSuarl,TotalAssociesuarl,ValeurNominaleValue,TotalValue, NombreAssocie,NombreActionnaire,NbrNominale,NombreActionValue,PartCapitalSarl,PartCapitalActionnaire;
    public static Double NombreNominale;
    Calendar myCalendar = Calendar.getInstance();
     static ListView lv;
    JSONParser jsonParser = new JSONParser();

    View  ViewEditActionnaire ;
    public static  int i;
    public static  int j;
    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    private  String reqAddEI,reqAddConseil, reqAddPresident, reqAddActionnaire,reqAddSuarl;
    private  String reqUpdateClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presidentdeconseil);
         lv = (ListView) findViewById( R.id.membres);
        items = new ArrayList<String>();
        adapter = new ListAdpterFormeSociete( this, items);



        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);

        ImageButton buttonSupprimer =
                (ImageButton)findViewById(R.id.btnDelette);

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                lv.setAdapter(null);
                items.clear();
            }
        });

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0,
                                            View arg1, int arg2, long arg3) {


                    }
                });
        AddPresidentConseil = (ImageButton) findViewById(R.id.btnAjouter);
        AddPresidentConseil.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(AjouterClient.pos==2) {
                    if (SA.itemPosition == 0) {
                        initiatePopupWindow();

                    }
                    if (SA.itemPosition == 1) {
                        initiatePopupWindowConseilDadministration();

                    }

                    if (SA.itemPosition == 2) {
                        final Dialog dialog = new Dialog(PresidentDeConseil.this);
                        dialog.setContentView(R.layout.nombreactionnaire);
                        dialog.setTitle("Select Nombre Actionnaire");

                        ValiderNombreActionnaire = (Button) dialog.findViewById(R.id.ValiderNombreactioannaire);
                        ValiderNombreActionnaire.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                EditTextNombreActionnaire=(EditText)dialog.findViewById(R.id.EditTextNombreactioannaire);
                                NombreActionnaire=Integer.parseInt(EditTextNombreActionnaire.getText().toString());
                                j=NombreActionnaire;
                                for(int i=0;i<NombreActionnaire;i++){
                                    //initiatePopupWindowActionnaire();
                                }
                                dialog.dismiss();
                                initiatePopupWindowActionnaire();
                            }
                        });

                        dialog.show();

                    }
                }

                if(AjouterClient.pos==3){

                    initiatePopupWindowEntrepriseIndividuelle();
                }
                if(AjouterClient.pos==1){

                    final Dialog dialog = new Dialog(PresidentDeConseil.this);
                    dialog.setContentView(R.layout.nombreassocie);
                    dialog.setTitle("Choisir Nombre des associes");

                    ValiderNombreAssocie = (Button) dialog.findViewById(R.id.ValiderNombre);
                    ValiderNombreAssocie.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            EditTextNombreAssocie=(EditText)dialog.findViewById(R.id.EditTextNombre);
                            NombreAssocie=Integer.parseInt(EditTextNombreAssocie.getText().toString());


                            if(NombreAssocie>=7) {
                                i = NombreAssocie;
                                dialog.dismiss();
                                initiatePopupWindowSarl();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Invalid number of Associe",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    dialog.show();

                }
                if(AjouterClient.pos==4){
                    initiatePopupWindowSuarl();
                }
            }
        });


    }
    //--------------------------------------------PresidentDeConseil------------------------------------------------------
    private void initiatePopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) PresidentDeConseil.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popuppresidentdeconseil,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 900, 900, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            btnValiderPopup = (Button) layout.findViewById(R.id.Valider);
            btnCancelPopup = (Button) layout.findViewById(R.id.Cancel);
            EdittextPresidentdeconseil=(EditText)layout.findViewById(R.id.EditTextPresidentDeConseil);
           EditTextDG=(EditText)layout.findViewById(R.id.EditTextDG);
            EdittextDuMandat=(EditText)layout.findViewById(R.id.editextDuMandat);
            EdittextAuMandat=(EditText)layout.findViewById(R.id.editTextAuMandat);


            EditTextDG.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                            PresidentDeConseil.this);
                    builderSingle.setIcon(R.mipmap.ic_launcher);
                    builderSingle.setTitle("Select :");
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            PresidentDeConseil.this,
                            android.R.layout.select_dialog_singlechoice);
                    arrayAdapter.add("PDG");
                    arrayAdapter.add("PCA");
                    arrayAdapter.add("DG");
                    builderSingle.setNegativeButton("cancel",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builderSingle.setAdapter(arrayAdapter,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   Choix = arrayAdapter.getItem(which);
                                   EditTextDG.setText(Choix.toString());

                                }
                            });
                    builderSingle.show();
                }
            });

            //----------------------------Date Du------------------------------------------
           final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };
            EdittextDuMandat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    new DatePickerDialog(PresidentDeConseil.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            //--------------------------------   Date AU    ----------------------------------------------
            final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel1();
                }

            };
            EdittextAuMandat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    new DatePickerDialog(PresidentDeConseil.this, date1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            //-----------------------------------------------------------------------------------
            btnValiderPopup.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    NomPresident=EdittextPresidentdeconseil.getText().toString();
                    GetDG=EditTextDG.getText().toString();
                   addItemList();
                    lv.setAdapter(adapter);
                    CreateNewPresident();
                    pwindo.dismiss();
                }
            });

            btnCancelPopup.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    pwindo.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------ConseilDadministration------------------------------------------------------

    private PopupWindow pwindo1;
    private void initiatePopupWindowConseilDadministration() {
        try {
            LayoutInflater inflater = (LayoutInflater) PresidentDeConseil.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popupconseiladministration,
                    (ViewGroup) findViewById(R.id.popup_element1));
            pwindo1 = new PopupWindow(layout, 900, 900, true);
            pwindo1.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnValiderPopup1 = (Button) layout.findViewById(R.id.ValiderConseildadministration);
            btnCancelPopup1 = (Button) layout.findViewById(R.id.CancelConseildadministration);
            NomConseilDadministrtion=(EditText)layout.findViewById(R.id.EditTextNomConseilAdministration);
            EdittextDuConseil=(EditText)layout.findViewById(R.id.editextDuConseil);
            EdittextAuConseil=(EditText)layout.findViewById(R.id.editTextAuConseil);
            //----------------------------Date Du------------------------------------------
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel2();
                }

            };
            EdittextDuConseil.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    new DatePickerDialog(PresidentDeConseil.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            //--------------------------------   Date AU    ----------------------------------------------
            final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel3();
                }

            };
            EdittextAuConseil.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    new DatePickerDialog(PresidentDeConseil.this, date1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            //-----------------------------------------------------------------------------------


            btnValiderPopup1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    NomDuConseil=NomConseilDadministrtion.getText().toString();
                    try {
                        addItemList1();
                        lv.setAdapter(adapter);
                      CreateNewConseil();
                        pwindo1.dismiss();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                }
                }
            });

            btnCancelPopup1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                        pwindo1.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    //--------------------------------------------Actionnaire------------------------------------------------------

    private PopupWindow pwindo2;
    private void initiatePopupWindowActionnaire() {
        try {
            LayoutInflater inflater = (LayoutInflater) PresidentDeConseil.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popupactionnaire,
                    (ViewGroup) findViewById(R.id.popup_element2));
            pwindo2 = new PopupWindow(layout, 900, 1200, true);
            pwindo2.showAtLocation(layout, Gravity.CENTER, 0, 0);


            txtRaisonSocialeActioannaire=(TextView) layout.findViewById(R.id.txtViewRaisonSocialeActioannaire);
            txtViewMatriculeFiscaleActioannaire=(TextView) layout.findViewById(R.id.txtViewMatriculeFiscaleActioannaire);
            txtViewNRegistreActioannaire=(TextView) layout.findViewById(R.id.txtViewNRegistreActioannaire);
            txtViewActionnaire=(TextView) layout.findViewById(R.id.txtViewActionnaire);



            btnValiderPopup2 = (Button) layout.findViewById(R.id.ValiderActionnaire);
            btnCancelPopup2 = (Button) layout.findViewById(R.id.CancelActionnaire);
            EditTextNomActionnaire=(EditText)layout.findViewById(R.id.EditTextActionnaire);
            NombreAction=(EditText)layout.findViewById(R.id.EditTextNombreDesAction);
            ValeurNominale= (EditText)layout.findViewById(R.id.EditTextValeurNominale);
            Total=(EditText)layout.findViewById(R.id.EditTextTotal);
            EditTextPartCapitalActionnaire=(EditText)layout.findViewById(R.id.EditTextPartCapitalActionnaire);
            EditTextRaisonSocialeActioannaire=(EditText)layout.findViewById(R.id.EditTextRaisonSocialeActioannaire);
            EditTextMatriculeFiscaleActioannaire=(EditText)layout.findViewById(R.id.EditTextMatriculeFiscaleActioannaire);
            EditTextNRegistreActioannaire=(EditText)layout.findViewById(R.id. EditTextNRegistreActioannaire);
            EditTextAdresseActioannaire=(EditText)layout.findViewById(R.id.EditTextAdresseActioannaire);


            final RadioButton radioPersonneMorale = (RadioButton) layout.findViewById(R.id.PersonneMorale);
            final RadioButton radioPersonnePhysique = (RadioButton) layout.findViewById(R.id.PersonnePhysique);
            radioPersonnePhysique.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TypeDactionnaire="Personne Physique";
                    radioPersonneMorale.setChecked(false);

                   EditTextNomActionnaire.setVisibility(View.VISIBLE);
                  txtViewActionnaire.setVisibility(View.VISIBLE);

                    EditTextRaisonSocialeActioannaire.setVisibility(View.GONE);;
                    txtRaisonSocialeActioannaire.setVisibility(View.GONE);
                    EditTextMatriculeFiscaleActioannaire.setVisibility(View.GONE);
                    txtViewMatriculeFiscaleActioannaire.setVisibility(View.GONE);
                    EditTextNRegistreActioannaire.setVisibility(View.GONE);
                    txtViewNRegistreActioannaire.setVisibility(View.GONE);
                    NomPrenomActionnaire= NomActionnaire;
                }
            });


            radioPersonneMorale.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TypeDactionnaire = "Personne Morale";
                    radioPersonnePhysique.setChecked(false);

                    EditTextNomActionnaire.setVisibility(View.GONE);
                    txtViewActionnaire.setVisibility(View.GONE);
                    txtRaisonSocialeActioannaire.setVisibility(View.VISIBLE);
                    EditTextRaisonSocialeActioannaire.setVisibility(View.VISIBLE);
                    EditTextMatriculeFiscaleActioannaire.setVisibility(View.VISIBLE);
                    txtViewMatriculeFiscaleActioannaire.setVisibility(View.VISIBLE);
                    EditTextNRegistreActioannaire.setVisibility(View.VISIBLE);
                    txtViewNRegistreActioannaire.setVisibility(View.VISIBLE);
                    NomPrenomActionnaire = RaisonSocialeActioannaire;
                }
            });


            btnValiderPopup2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NomActionnaire=EditTextNomActionnaire.getText().toString();
                    NombreActionValue=Integer.parseInt(NombreAction.getText().toString());
                    ValeurNominaleValue=Integer.parseInt(ValeurNominale.getText().toString());
                    TotalValue=Integer.parseInt(Total.getText().toString());
                    PartCapitalActionnaire=Integer.parseInt(EditTextPartCapitalActionnaire.getText().toString());

                    RaisonSocialeActioannaire=EditTextRaisonSocialeActioannaire.getText().toString();
                    MatriculeFiscaleActioannaire= EditTextMatriculeFiscaleActioannaire.getText().toString();
                    NRegistreActioannaire=EditTextNRegistreActioannaire.getText().toString();
                    AdresseActioannaire=EditTextAdresseActioannaire.getText().toString();

                    addItemList2();
                    lv.setAdapter(adapter);
                    CreateNewActionnaire();
                    pwindo2.dismiss();
                    if(j>1){
                        initiatePopupWindowActionnaire();
                        j--;
                        Toast.makeText(getApplicationContext(),"aaaaaaaaaaa"+ String.valueOf(j),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

            btnCancelPopup2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    pwindo2.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //--------------------------------------------EntrepriseIndividuelle ------------------------------------------------------

    private PopupWindow pwindo3;
    private void initiatePopupWindowEntrepriseIndividuelle() {
        try {
            LayoutInflater inflater = (LayoutInflater) PresidentDeConseil.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popupentrepriseindividuelle,
                    (ViewGroup) findViewById(R.id.popup_element3));
            pwindo3 = new PopupWindow(layout, 900, 1200, true);
            pwindo3.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnValiderPopup3 = (Button) layout.findViewById(R.id.ValiderIndivideuelle);
            btnCancelPopup3 = (Button) layout.findViewById(R.id.CancelIndivideuelle);
            EditTextNomIndividelle=(EditText)layout.findViewById(R.id.EditTextNomNomIndividuelle);
            EditTextPrenomIndividelle=(EditText)layout.findViewById(R.id.EditTextPrenomIndividuelle);
            EditTextMatriculeFiscaleEI=(EditText)layout.findViewById(R.id.EditTextMatriculeFiscale);
            EditTextAdresseEI=(EditText)layout.findViewById(R.id.EditTextAdresseEI);
            btnValiderPopup3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NomIndividuelle=EditTextNomIndividelle.getText().toString();
                    PrenomIndividuelle=EditTextPrenomIndividelle.getText().toString();
                    MatriculeFiscaleEI=EditTextMatriculeFiscaleEI.getText().toString();
                    AdresseEI=EditTextAdresseEI.getText().toString();
                    addItemList3();
                    lv.setAdapter(adapter);
                   CreateNewEntrepriseIndiv();
                    pwindo3.dismiss();
                }
            });

            btnCancelPopup3.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    pwindo3.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------SARL------------------------------------------------------


    private PopupWindow pwindo4;
    private void initiatePopupWindowSarl() {
        try {

                LayoutInflater inflater = (LayoutInflater) PresidentDeConseil.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popupsarl,
                        (ViewGroup) findViewById(R.id.popup_element4));
                btnValiderPopup4 = (Button) layout.findViewById(R.id.ValiderSarl);
                btnCancelPopup4 = (Button) layout.findViewById(R.id.CancelSarl);
                EditTextAssocie=(EditText)layout.findViewById(R.id.EditTextNomAssocie);
                EditTextNB=(EditText)layout.findViewById(R.id.EditTextNumberAssocie);
                EditTextNbrNominale=(EditText)layout.findViewById(R.id.EditTextNbrNominale);
                EditTextTotal=(EditText)layout.findViewById(R.id.EditTextTotal);
            EditTextPartCapitalSarl=(EditText)layout.findViewById(R.id.EditTextPartCapitalSarl);
            EditTextNometPrenomGerant=(EditText)layout.findViewById(R.id.EditTextNometPrenomGerant);
            EditTextDuSarl=(EditText)layout.findViewById(R.id.editextDuSarl);
            EditTextAuSarl=(EditText)layout.findViewById(R.id.editTextAuSarl);
                pwindo4 = new PopupWindow(layout, 900, 1200, true);
                pwindo4.showAtLocation(layout, Gravity.CENTER, 0, 0);

            //----------------------------Date Du------------------------------------------
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel4();
                }

            };
            EditTextDuSarl.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    new DatePickerDialog(PresidentDeConseil.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            //--------------------------------   Date AU    ----------------------------------------------
            final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel5();
                }

            };
            EditTextAuSarl.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    new DatePickerDialog(PresidentDeConseil.this, date1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

                btnValiderPopup4.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {


                        NomAssocie = EditTextAssocie.getText().toString();
                        NombreAsoocie = Integer.parseInt(EditTextNB.getText().toString());
                        NbrNominale = Integer.parseInt(EditTextNbrNominale.getText().toString());
                        TotalAssocie = Integer.parseInt(EditTextTotal.getText().toString());
                        NometPrenomGerant = EditTextNometPrenomGerant.getText().toString();
                        PartCapitalSarl = Integer.parseInt(EditTextPartCapitalSarl.getText().toString());
                        /*Toast.makeText(getApplicationContext(), "Nom "+NomAssocie+"  Nombre "+String.valueOf(NombreAsoocie)+" Nominale "+String.valueOf(NbrNominale)+" Total  "+String.valueOf(TotalAssocie),
                                Toast.LENGTH_LONG).show();*/
                        addItemList4();
                        lv.setAdapter(adapter);
                        CreateNewSarl();
                        pwindo4.dismiss();

                       if(i>1) {
                            initiatePopupWindowSarl();
                            i--;
                            Toast.makeText(getApplicationContext(), "aaaaaaaaaaa" + String.valueOf(i),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });

                btnCancelPopup4.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        pwindo4.dismiss();
                    }
                });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //--------------------------------------------SUARL------------------------------------------------------
    private PopupWindow pwindo5;
    private void initiatePopupWindowSuarl() {
        try {
            LayoutInflater inflater = (LayoutInflater) PresidentDeConseil.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popupsuarl,
                    (ViewGroup) findViewById(R.id.popup_element5));
            pwindo5 = new PopupWindow(layout,900 , 1200, true);
            pwindo5.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnValiderPopup5 = (Button) layout.findViewById(R.id.ValiderSuarl);
            btnCancelPopup5 = (Button) layout.findViewById(R.id.CancelSuarl);
            EditTextAssociesuarl=(EditText)layout.findViewById(R.id.EditTextNomAssociesuarl);
            EditTextNombrePartSuarl=(EditText)layout.findViewById(R.id.EditTextNombrePartSuarl);
            EditTextNombreNominale=(EditText)layout.findViewById(R.id. EditTextNombreNominale);
            EditTextTotalsuarl=(EditText)layout.findViewById(R.id.EditTextTotalsuarl);

            btnValiderPopup5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    NomAssociesuarl=EditTextAssociesuarl.getText().toString();
                    NombrePartSuarl = Integer.parseInt(EditTextNombrePartSuarl.getText().toString());
                    NombreNominale = Double.parseDouble(EditTextNombreNominale.getText().toString());
                    TotalAssociesuarl = Integer.parseInt(EditTextTotalsuarl.getText().toString());
                    addItemList5();
                    lv.setAdapter(adapter);
                    CreateNewSuarl();
                    pwindo5.dismiss();

                }
            });

            btnCancelPopup5.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    pwindo5.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        EdittextDuMandat.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel1() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        EdittextAuMandat.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel2() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        EdittextDuConseil.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel3() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        EdittextAuConseil.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel4() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        EditTextDuSarl.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel5() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        EditTextAuSarl.setText(sdf.format(myCalendar.getTime()));
    }

    public static void addItemList() {
        items.add(0,NomPresident );
            adapter.notifyDataSetChanged();
        }
    public static void addItemList1() {
        items.add(0,NomDuConseil );
        adapter.notifyDataSetChanged();
    }
    public static void addItemList2() {
        items.add(0,NomPrenomActionnaire );
        adapter.notifyDataSetChanged();
    }
    public static void addItemList3() {
        items.add(0,NomIndividuelle );
        adapter.notifyDataSetChanged();
    }
    public static void addItemList4() {
        items.add(0,NomAssocie );
        adapter.notifyDataSetChanged();
    }
    public static void addItemList5() {
        items.add(0,NomAssociesuarl );
        adapter.notifyDataSetChanged();
    }

    //--------------------------------------------------------------------------------------

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    void CreateNewSarl() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewSarl();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqUpdateClient = "json_obj_req_Add_Sarl";
            Log.i("url_Ajout_SARL", AppConfig.url_Ajout_SARL);
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", String.valueOf(AjouterClient.id));

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());

            } catch (Throwable t) {

            }
            final JSONObject postParam = new JSONObject();
            try {
                postParam.put("client", obj);
                postParam.put("nomAssocie", NomAssocie);
                postParam.put("nombre", String.valueOf(NombreAsoocie));
                postParam.put("nbrNominale",String.valueOf(NbrNominale));
                postParam.put("total", String.valueOf(TotalAssocie));
                postParam.put("nometPrenomGerant",NometPrenomGerant);
                postParam.put("partCapitalSarl",String.valueOf(PartCapitalSarl));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.POST,AppConfig.url_Ajout_SARL,
                    postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ADDClientJsonFromJson) {
                            Log.d("RespWSAddSarl",  ADDClientJsonFromJson.toString());

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
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqUpdateClient);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }







    //---------------------------------------------------------------------------------------

    void CreateNewEntrepriseIndiv() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewEntrepriseIndiv();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqAddEI = "json_obj_req_Add_EntrpriseIndivid";
            Log.i("url_EntrepriseIndiv", AppConfig.url_Ajout_EntrepriseIndiv);
           /* JSONObject jsonObj = new JSONObject();
            try{
                jsonObj.put("client",String.valueOf(AjouterClient.id));
            }
            catch (Exception e) {

            }*/
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id",String.valueOf(AjouterClient.id));

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());

            } catch (Throwable t) {

            }

            final JSONObject postParam = new JSONObject();
            try {
                postParam.put("client", obj);
                postParam.put("nomIndividuelle", NomIndividuelle);
                postParam.put("prenomIndividuelle",PrenomIndividuelle);
                postParam.put("matriculeFiscaleEi",MatriculeFiscaleEI);
                postParam.put("adresseEi", AdresseEI);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.POST,AppConfig.url_Ajout_EntrepriseIndiv,
                    postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ADDClientJsonFromJson) {
                            Log.d("RespWSAddEI",  ADDClientJsonFromJson.toString());

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

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqAddEI);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }



    void CreateNewConseil() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewConseil();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqAddConseil = "json_obj_req_Add_EntrpriseIndivid";
            Log.i("url_Conseil", AppConfig.url_Ajout_Conseil);
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", String.valueOf(AjouterClient.id));

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());


            } catch (Throwable t) {

            }
            final JSONObject postParam = new JSONObject();
            try {
                postParam.put("client", obj);
                postParam.put("nom", NomIndividuelle);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.POST,AppConfig.url_Ajout_Conseil,
                    postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ADDClientJsonFromJson) {
                            Log.d("RespWSAddEI",  ADDClientJsonFromJson.toString());

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
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqAddConseil);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }



    void CreateNewPresident() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewPresident();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqAddPresident = "json_obj_req_Add_President";
            Log.i("url_President", AppConfig.url_Ajout_President);
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", String.valueOf(AjouterClient.id));

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());


            } catch (Throwable t) {

            }
            final JSONObject postParam = new JSONObject();
            try {
                postParam.put("client", obj);
                postParam.put("nomPresident", NomPresident);
                postParam.put("dg", Choix);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.POST,AppConfig.url_Ajout_President,
                    postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ADDClientJsonFromJson) {
                            Log.d("RespWSAddPresident",  ADDClientJsonFromJson.toString());

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
            AppController.getInstance().addToRequestQueue(jsonObjReq,  reqAddPresident);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }



    void CreateNewActionnaire() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewActionnaire();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqAddActionnaire = "json_obj_req_Add_Actionnaire";
            Log.i("url_Actionnaire", AppConfig.url_Ajout_Actionnaire);
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", String.valueOf(AjouterClient.id));

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());


            } catch (Throwable t) {

            }
            final JSONObject postParam = new JSONObject();
            try {
                postParam.put("client", obj);
                postParam.put("nomActionnaire", NomActionnaire);
                postParam.put("nombreActionValue", String.valueOf(NombreActionValue));
                postParam.put("valeurNominaleValue",String.valueOf(ValeurNominaleValue));
                postParam.put("totalValue",String.valueOf(TotalValue));
                postParam.put("partCapitalActionnaire",String.valueOf(PartCapitalActionnaire));
                postParam.put("raisonSocialeActioannaire",RaisonSocialeActioannaire);
                postParam.put("matriculeFiscaleActioannaire",MatriculeFiscaleActioannaire);
                postParam.put("nregistreActioannaire",NRegistreActioannaire);
                postParam.put("adresseActioannaire",AdresseActioannaire);
                postParam.put("typeDactionnaire",TypeDactionnaire);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.POST,AppConfig.url_Ajout_Actionnaire,
                    postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ADDClientJsonFromJson) {
                            Log.d("RespWSAddPresident",  ADDClientJsonFromJson.toString());

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
                    return headers;
                }

                ;
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq,  reqAddActionnaire);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }



    void CreateNewSuarl() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewSuarl();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqAddSuarl = "json_obj_req_Add_Suarl";
            Log.i("url_Suarl", AppConfig.url_Ajout_Suarl);
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", String.valueOf(AjouterClient.id));

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());


            } catch (Throwable t) {

            }
            final JSONObject postParam = new JSONObject();
            try {
                postParam.put("client", obj);
                postParam.put("nomAssociesuarl", NomAssociesuarl);
                postParam.put("nombrePartSuarl",  String.valueOf(NombrePartSuarl));
                postParam.put("nombreNominale",String.valueOf(NombreNominale));
                postParam.put("totalAssociesuarl",String.valueOf(TotalAssociesuarl));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.POST,AppConfig.url_Ajout_Suarl,
                    postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ADDClientJsonFromJson) {
                            Log.d("RespWSAddSuarl",  ADDClientJsonFromJson.toString());

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
                    return headers;
                }

                ;
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq,  reqAddSuarl);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }





















    }
