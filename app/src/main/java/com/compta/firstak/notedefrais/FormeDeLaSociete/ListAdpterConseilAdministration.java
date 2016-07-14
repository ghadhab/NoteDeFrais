package com.compta.firstak.notedefrais.FormeDeLaSociete;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.Gestion_Client.ListImageButton;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.ConseilAdministration;
import com.compta.firstak.notedefrais.entity.PresidentConseil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mohamed on 10/08/2015.
 */
public class ListAdpterConseilAdministration extends BaseAdapter {

    private Context context;
    private List<ConseilAdministration> items;
    private LayoutInflater inflater;
    private Activity activity;
    Button actualiserbutton;
    RelativeLayout networkFailed;
    Calendar myCalendar = Calendar.getInstance();

    public ListAdpterConseilAdministration(Context context,
                                           List<ConseilAdministration> items, Activity activity, Button actualiserbutton, RelativeLayout networkFailed) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.activity=activity;
        this.actualiserbutton=actualiserbutton;
        this.networkFailed=networkFailed;
    }


    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ListImageButton listImageButton=new ListImageButton();

        final ConseilAdministration item = items.get(position);
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = inflater.inflate( R.layout.itemformesociete, parent, false);
        TextView Nom = (TextView)v.findViewById( R.id.nomPresidentConseil);
        Nom.setText(item.getNomPrenom());
        ImageButton Supprimer =
                (ImageButton)v.findViewById( R.id.Supprimer);
        Supprimer.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        items.remove(position);
                        notifyDataSetChanged();
                    }
                });
        ImageButton Modifier =
                (ImageButton)v.findViewById( R.id.Modifier);
        Modifier.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("itemAModifier", item.toString());
                        initiatePopupWindowConseilDadministration(position, item);
                    }
                });
        return v;

    }


    //////
        //////////////////////////////////////////////////////////////////////
    //--------------------------------------------ConseilDadministration------------------------------------------------------

    private void initiatePopupWindowConseilDadministration(final int posotion,final ConseilAdministration itemConseilAdministration) {
        final Dialog dialog = new Dialog(activity,R.style.AlertDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popupconseiladministration);

        Button  btnValiderPopup1 = (Button) dialog.findViewById(R.id.ValiderConseildadministration);
        Button btnCancelPopup1 = (Button) dialog.findViewById(R.id.CancelConseildadministration);
        final EditText NomConseilDadministrtion=(EditText)dialog.findViewById(R.id.EditTextNomConseilAdministration);
        final Button buttonDuConseil=(Button)dialog.findViewById(R.id.buttonDuConseil);
        final Button  buttonAuConseil=(Button)dialog.findViewById(R.id.buttonAuConseil);

        NomConseilDadministrtion.setText(itemConseilAdministration.getNomPrenom());
        buttonDuConseil.setText(itemConseilAdministration.getDuMandat());
        buttonAuConseil.setText(itemConseilAdministration.getAuMandat());
        //----------------------------Date Du------------------------------------------
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                buttonDuConseil.setText(sdf.format(myCalendar.getTime()));
            }

        };
        buttonDuConseil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(activity, date, myCalendar
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
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                buttonAuConseil.setText(sdf.format(myCalendar.getTime()));
            }

        };
        buttonAuConseil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(activity, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //-----------------------------------------------------------------------------------


        btnValiderPopup1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

             String   NomDuConseil=NomConseilDadministrtion.getText().toString();

                ConseilAdministration conseilAdministration=new ConseilAdministration(itemConseilAdministration.getId(),NomDuConseil,buttonDuConseil.getText().toString(),buttonAuConseil.getText().toString(),itemConseilAdministration.getIdClient());
                dialog.dismiss();
                ///////////////////////
                updatepresident(conseilAdministration);
                items.remove(posotion);
                items.add(posotion,conseilAdministration);
                //items.add(0,NomAssociesuarl );
                notifyDataSetChanged();
                //CreateNewSuarl();
                dialog.dismiss();
            }
        });

        btnCancelPopup1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogAnimation;
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }


    ////////////////////////////////////////////////////////////////////////////////////



    void updatepresident(final ConseilAdministration conseilAdministration) {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatepresident(conseilAdministration);
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
           String reqUpdateClient = "json_obj_req_ajout_President";
            Log.i("url_Ajout_President", AppConfig.url_Ajout_Conseil);
            ////
            Log.i("url_Ajout_President", AppConfig.url_Ajout_Conseil);
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", conseilAdministration.getIdClient());

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());


            } catch (Throwable t) {

            }
            JSONObject postParam = new JSONObject();
            try {
                postParam.put("id",conseilAdministration.getId());
                postParam.put("client", obj);
                postParam.put("nom", String.valueOf(conseilAdministration.getNomPrenom()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("putParam",postParam.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.PUT,
                    AppConfig.url_Ajout_Conseil,  postParam,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject UpdateClientJsonFromJson) {
                            Log.d("RespWSUpdateClient",  UpdateClientJsonFromJson.toString());


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("ErrorMessageVolley","Error: "+error.getMessage());
                    VolleyLog.d("TAGVolley", "Error: " + error.getMessage());
                    Animation animationTranslate = AnimationUtils.loadAnimation(context,
                            R.anim.abc_slide_in_bottom);
                    networkFailed.startAnimation(animationTranslate);
                    networkFailed.setVisibility(View.VISIBLE);
                }
            }){


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    // headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqUpdateClient);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(context,
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}