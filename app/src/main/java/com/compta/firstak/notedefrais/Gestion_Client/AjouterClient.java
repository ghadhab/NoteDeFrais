package com.compta.firstak.notedefrais.Gestion_Client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.FormeDeLaSociete.PresidentDeConseil;
import com.compta.firstak.notedefrais.FormeDeLaSociete.SA;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mohamed on 04/08/2015.
 */
public class AjouterClient extends Activity implements AdapterView.OnItemSelectedListener {

    EditText RaisonSocialeClient, EmailClient, PhoneClient, FaxClient, AdresseClient, MF,RC,Capital,Activite,DateConstruction;
    TextView RaisonSocialeText, EmailClientText, PhoneClientText, FaxClientText, AdresseClientText, MFText,RCtext,CapitalText,ActivityText,DateConstructionText;
   public static String GetRaisonSociale,GetEmailClient,GetPhoneClient,GetFaxClient,GetAdresseClient,GetMF,GetRc,GetCapital,GetConstitution,GetFormeSociete;
    Button AjouterClient;
    public static boolean isclicked=false;
   public static String strName="";
    Calendar myCalendar = Calendar.getInstance();
    ImageButton Logo;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
public static int pos;
   public static int id;

    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    private  String reqGetClient;
    private  String reqUpdateClient;
    Client client=null;



    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaireajout);

        GetRaisonSociale="";
        GetEmailClient="";
        GetPhoneClient="";
        GetFaxClient="";
        GetAdresseClient="";
        GetMF="";
        GetRc="";
        GetCapital="";
        GetConstitution="";
        GetFormeSociete="";

        Logo=(ImageButton)findViewById(R.id.Logo);
        Logo.setBackground(this.getApplicationContext().getDrawable(R.drawable.addlogo));
        Logo.setOnClickListener(new View.OnClickListener() {
            int i=0;
            public void onClick(View v) {
                loadImagefromGallery(Logo);
               /* if(Logo.isClickable()) {
                    DashboardClient.spinnerArray.add("Facture"+i);
                    DashboardClient.adapter.notifyDataSetChanged();
                    i++;
                }
*/
            }
        });



        RaisonSocialeText = (TextView) findViewById(R.id.textViewRaisonSocialeClientText);
        EmailClientText = (TextView) findViewById(R.id.textViewEmailAddress);
        PhoneClientText = (TextView) findViewById(R.id.textViewPhoneNumber);
        FaxClientText = (TextView) findViewById(R.id.textViewFax);
        AdresseClientText = (TextView) findViewById(R.id.textViewAdresse);
        MFText = (TextView) findViewById(R.id.textViewMF);
        RCtext=(TextView)findViewById(R.id.textViewRC);
        CapitalText=(TextView)findViewById(R.id.textViewCapital);
        ActivityText=(TextView)findViewById(R.id.textViewActivite);
        DateConstructionText=(TextView)findViewById(R.id.textViewDate);

        RaisonSocialeClient = (EditText) findViewById(R.id.editTextRaisonSocialaClient);
        EmailClient = (EditText) findViewById(R.id.editTextEmailAddressInput);
        PhoneClient = (EditText) findViewById(R.id.editTextPhoneNumber);
        FaxClient = (EditText) findViewById(R.id.editTextFax);
        AdresseClient = (EditText) findViewById(R.id.editTextAdresse);
        MF = (EditText) findViewById(R.id.editTextMF);
        RC = (EditText) findViewById(R.id.editTextRC);
        Capital=(EditText)findViewById(R.id.editTextCapital);
        Activite=(EditText)findViewById(R.id.editTextActivity);
        DateConstruction=(EditText)findViewById(R.id.editTextDate);

        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);


        Spinner FormeSocieteSpinner = (Spinner) findViewById(R.id.FormeDeSociete);
        FormeSocieteSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.TableFormeSociete, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FormeSocieteSpinner.setAdapter(adapter);

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

        DateConstruction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AjouterClient.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        AjouterClient = (Button) findViewById(R.id.Ajouter);
        AjouterClient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
isclicked=true;
                GetRaisonSociale= RaisonSocialeClient.getText().toString();
                GetEmailClient=EmailClient.getText().toString();
                GetPhoneClient=PhoneClient.getText().toString();
                GetFaxClient=FaxClient.getText().toString();
                GetAdresseClient=AdresseClient.getText().toString();
                GetMF=MF.getText().toString();
                GetRc=RC.getText().toString();
                GetCapital=Capital.getText().toString();
                GetConstitution=DateConstruction.getText().toString();

                AddClientJsonObject();
                if (client!=null)
                {
                    // Prepare data intent
                    Intent data = new Intent();
                    data.putExtra("client", client);
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data);
                }
                finish();



            }
        });

        Activite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                        AjouterClient.this);
                builderSingle.setIcon(R.mipmap.ic_launcher);
                builderSingle.setTitle("Select Activity :");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        AjouterClient.this,
                        android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Industrie");
                arrayAdapter.add("Commerce");
                arrayAdapter.add("Service");
                arrayAdapter.add("Autre");
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
                                 strName = arrayAdapter.getItem(which);
                                Activite.setText(strName.toString());

                            }
                        });
                builderSingle.show();
            }
        });
    }


    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        DateConstruction.setText(sdf.format(myCalendar.getTime()));

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pos=position;
        if(position==1){
            GetFormeSociete="SARL";
            Intent intent = new Intent(AjouterClient.this,
                    PresidentDeConseil.class);
            startActivity(intent);
           // new CreateNewClient().execute();

        }
        if (position == 2) {
            GetFormeSociete="SA";
            Intent intent = new Intent(AjouterClient.this,
                   SA.class);
            startActivity(intent);
           // new CreateNewClient().execute();

        }
        if(position==3){
            GetFormeSociete="Entreprise Individuelle";
            Intent intent = new Intent(AjouterClient.this,
                    PresidentDeConseil.class);
            startActivity(intent);
           // new CreateNewClient().execute();
        }
        if(position==4){
            GetFormeSociete="SUARL";
            Intent intent = new Intent(AjouterClient.this,
                    PresidentDeConseil.class);
            startActivity(intent);
          //  new CreateNewClient().execute();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    void AddClientJsonObject() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClientJsonObject();
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            reqUpdateClient = "json_obj_req_Add_Client";
            Log.i("UrlAddClientById", AppConfig.AddURLAddClient);
            final JSONObject postParam = new JSONObject();
            try {
                postParam.put("raisonSociale", GetRaisonSociale);
                postParam.put("email", GetEmailClient);
                postParam.put("phone", GetPhoneClient);
                postParam.put("fax", GetFaxClient);
                postParam.put("adresse", GetAdresseClient);
                postParam.put("matriculeFiscale", GetMF);
                postParam.put("registre", GetRc);
                postParam.put("capital", GetCapital);
                postParam.put("activite", strName);
                postParam.put("constitution",GetConstitution);
                postParam.put("formeSociete", GetFormeSociete);
                postParam.put("archivage", "false");

                if(GetFormeSociete=="SARL"){
                    postParam.put("FormeSociete", GetFormeSociete);

                }
                if(GetFormeSociete=="SA"){
                    postParam.put("FormeSociete", GetFormeSociete);
                }
                if(GetFormeSociete=="Entreprise Individuelle"){
                    postParam.put("FormeSociete", GetFormeSociete);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.POST,AppConfig.AddURLAddClient,
                    postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject ADDClientJsonFromJson) {
                            Log.d("RespWSAddClient",  ADDClientJsonFromJson.toString());
                            try {
                                id= ADDClientJsonFromJson.getInt("id");
                                Log.i("id_client", String.valueOf(id));
                                client=new Client();
                                client.setId(id);
                                client.setName(ADDClientJsonFromJson.getString("raisonSociale"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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


    public void loadImagefromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
             String aaaa=   Base64.decode(imgDecodableString,Base64.DEFAULT).toString();
                cursor.close();
                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);

                Drawable d = new BitmapDrawable(getResources(), BitmapFactory.decodeFile(imgDecodableString));
                Logo.setBackground(d);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
               // Toast.makeText(this, imageEncoded, Toast.LENGTH_LONG).show();
                Log.e("LOOK", imageEncoded);
            } catch (Exception e) {
                Toast.makeText(this, "wrong", Toast.LENGTH_LONG).show();
            }
        }
        if ( resultCode == RESULT_OK && null != data)
        {
            if (data.hasExtra("returnKey1")) {
                Toast.makeText(this, data.getExtras().getString("returnKey1"),
                        Toast.LENGTH_SHORT).show();
            }
        }


    }

}