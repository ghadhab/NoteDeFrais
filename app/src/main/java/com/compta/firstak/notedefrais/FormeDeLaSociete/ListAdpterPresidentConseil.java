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
import android.view.WindowManager;
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
import com.compta.firstak.notedefrais.entity.PresidentConseil;
import com.compta.firstak.notedefrais.entity.Suarl;

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
public class ListAdpterPresidentConseil extends BaseAdapter {

    private Context context;
    private List<PresidentConseil> items;
    private LayoutInflater inflater;
    private Activity activity;
    Button actualiserbutton;
    RelativeLayout networkFailed;
    Calendar myCalendar = Calendar.getInstance();

    public ListAdpterPresidentConseil(Context context,
                                      List<PresidentConseil> items, Activity activity, Button actualiserbutton, RelativeLayout networkFailed) {
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

        final PresidentConseil item = items.get(position);
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
                        initiatePopupWindow(position, item);
                    }
                });
        return v;

    }




    //////// president de conseil
    private void initiatePopupWindow(final int posotion, final PresidentConseil itemPresidentConseil) {

        final Dialog dialog = new Dialog(activity,R.style.AlertDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popuppresidentdeconseil);

        Button btnValiderPopup = (Button) dialog.findViewById(R.id.Valider);
        Button btnCancelPopup = (Button) dialog.findViewById(R.id.Cancel);
        final EditText EdittextPresidentdeconseil=(EditText)dialog.findViewById(R.id.EditTextPresidentDeConseil);
        final Button ButtonDG=(Button)dialog.findViewById(R.id.ButtonDG);
        final Button bottonDuMandat=(Button)dialog.findViewById(R.id.buttonDuMandat);
        final Button buttonAuMandat=(Button)dialog.findViewById(R.id.buttonAuMandat);
        EdittextPresidentdeconseil.setText(itemPresidentConseil.getNomPrenom());
        ButtonDG.setText(itemPresidentConseil.getFonction());
        bottonDuMandat.setText(itemPresidentConseil.getDuMandat());
        buttonAuMandat.setText(itemPresidentConseil.getAuMandat());


        ButtonDG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                        activity);
                builderSingle.setIcon(R.mipmap.ic_launcher);
                builderSingle.setTitle("Select :");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        activity,
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
                                String Choix = arrayAdapter.getItem(which);
                                ButtonDG.setText(Choix.toString());

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
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                bottonDuMandat.setText(sdf.format(myCalendar.getTime()));
            }

        };
        bottonDuMandat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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

                buttonAuMandat.setText(sdf.format(myCalendar.getTime()));
            }

        };
        buttonAuMandat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                new DatePickerDialog(activity, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //-----------------------------------------------------------------------------------
        btnValiderPopup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String NomPresident=EdittextPresidentdeconseil.getText().toString();
                String GetDG=ButtonDG.getText().toString();

               PresidentConseil presidentConseil=new PresidentConseil(itemPresidentConseil.getId(),NomPresident,GetDG,bottonDuMandat.getText().toString(),buttonAuMandat.getText().toString(),itemPresidentConseil.getIdClient());
                                dialog.dismiss();
                ///////////////////////
                updatepresident(presidentConseil);
                items.remove(posotion);
                items.add(posotion,presidentConseil);
                //items.add(0,NomAssociesuarl );
                notifyDataSetChanged();
                //CreateNewSuarl();
                dialog.dismiss();
                /////////////////////////////////
            }
        });

        btnCancelPopup.setOnClickListener(new View.OnClickListener() {

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

    //////



    void updatepresident(final PresidentConseil presidentConseil) {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatepresident(presidentConseil);
            }
        });
        if (isNetworkAvailable()) {
            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
           String reqUpdateClient = "json_obj_req_ajout_President";
            Log.i("url_Ajout_President", AppConfig.url_Ajout_President);
            ////
            Log.i("url_Ajout_President", AppConfig.url_Ajout_President);
            JSONObject obj=null;
            try {

                obj = new JSONObject();
                obj.put("id", presidentConseil.getIdClient());

                // obj = new JSONObject(String.valueOf(AjouterClient.id));

                Log.d("My App", obj.toString());


            } catch (Throwable t) {

            }
            JSONObject postParam = new JSONObject();
            try {
               postParam.put("client", obj);
                postParam.put("id",presidentConseil.getId());
                postParam.put("dg", presidentConseil.getFonction());
                postParam.put("nomPresident",  String.valueOf(presidentConseil.getNomPrenom()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("putParam",postParam.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.PUT,
                    AppConfig.url_Ajout_President,  postParam,
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