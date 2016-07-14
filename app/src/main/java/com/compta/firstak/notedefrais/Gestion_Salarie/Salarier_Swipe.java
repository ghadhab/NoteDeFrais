package com.compta.firstak.notedefrais.Gestion_Salarie;
import android.Manifest;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.adapter.SalarierAdapter;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.Client;
import com.compta.firstak.notedefrais.entity.Salarier;
import com.compta.firstak.notedefrais.service.JsonService;
import com.compta.firstak.notedefrais.util.MultipartRequest;
import com.github.clans.fab.FloatingActionButton;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Salarier_Swipe extends ListActivity implements SwipeActionAdapter.SwipeActionListener {
    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    public static ArrayList<Salarier> items;
    protected static SwipeActionAdapter mAdapter;
    private int mPreviousVisibleItem;
    ListView lv;
    public static int REQUESTADD=22;
public static  int idFromModifier , idFromGetPaie;
    private  String reqUpdateClient,reqGetIp,reqGetFicheDePaie;
   public static String Salaire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.salarie_swipe);
///URLGetAllClients
        actualiserbutton = (Button) findViewById(R.id.button1);
        networkFailed = (RelativeLayout) findViewById(R.id.network_failed);
        // Execute some code after 2 seconds have passed
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                MakeJsonArrayReq();
            }
        }, 12000);



        lv = getListView();


        final FloatingActionButton buttonAjouter = (FloatingActionButton) findViewById(R.id.buttonAjouter);
        buttonAjouter.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                buttonAjouter.show(true);
                buttonAjouter.setShowAnimation(AnimationUtils.loadAnimation(Salarier_Swipe.this, R.anim.show_from_bottom));
                buttonAjouter.setHideAnimation(AnimationUtils.loadAnimation(Salarier_Swipe.this, R.anim.hide_to_bottom));
            }
        }, 300);


        buttonAjouter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Salarier_Swipe.this,
                        Fiche_Paie.class);
              //  startActivity(intent);
                startActivityForResult(intent,REQUESTADD);
                //finish();
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > mPreviousVisibleItem) {
                    buttonAjouter.hide(true);
                } else if (firstVisibleItem < mPreviousVisibleItem) {
                    buttonAjouter.show(true);
                }
                mPreviousVisibleItem = firstVisibleItem;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("etape1","true");
        if (resultCode==RESULT_OK) {
            if (requestCode == REQUESTADD) {
                Log.i("fromAjoutClient", "true");
                if (data.getExtras() != null) {
                    Salarier addedSalarier = data.getExtras().getParcelable("Salarier");
                    items.add(addedSalarier);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {



    }

    @Override
    public boolean hasActions(int position, SwipeDirection direction) {
        if (direction.isLeft()) return true;
        if (direction.isRight()) return true;
        return false;
    }

    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction) {
        return false;
    }


    @Override
    public void onSwipe(int[] positionList, SwipeDirection[] directionList) {
        for (int i = 0; i < positionList.length; i++) {
            SwipeDirection direction = directionList[i];
            int position = positionList[i];
           Intent intent = new Intent(Salarier_Swipe.this,
                    Update_Salarier.class);
            Salarier item =items.get(position);
            switch (direction) {
                case DIRECTION_FAR_LEFT:
                    idFromGetPaie=item.getId();
                    GetFicheDePaieJsonObject();

                    break;
                case DIRECTION_NORMAL_LEFT:
                    idFromGetPaie=item.getId();
                    GetFicheDePaieJsonObject();

                    break;
                case DIRECTION_FAR_RIGHT:
                    startActivity(intent);
                      idFromModifier=item.getId();
                    Toast.makeText(Salarier_Swipe.this,item.getNom()+"  "+String.valueOf(item.getId()),Toast.LENGTH_LONG).show();
                    break;
                case DIRECTION_NORMAL_RIGHT:
                    idFromModifier=item.getId();
                  startActivity(intent);
                    Toast.makeText(Salarier_Swipe.this,item.getNom()+"  "+String.valueOf(item.getId()),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private void MakeJsonArrayReq() {
        networkFailed.setVisibility(View.GONE);
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeJsonArrayReq();
            }
        });
        //String url=AppController.getInstance().getUrlRacine()+AppConfig.URLGetAllClients;
        if (isNetworkAvailable()) {
            JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.URLGetAllSalaries,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            items=JsonService.getSalarie(response);

                            SalarierAdapter salarierAdapter=new SalarierAdapter(Salarier_Swipe.this,items,Salarier_Swipe.this);

                            mAdapter = new SwipeActionAdapter(salarierAdapter);
                            mAdapter.setSwipeActionListener(Salarier_Swipe.this)
                                    .setDimBackgrounds(true)
                                    .setListView(getListView());
                            setListAdapter(mAdapter);

                            mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.row_bg_left)
                                    .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                                    .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_bg_right)
                                    .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.row_bg_right);

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

    public Button getViewActualiserbutton()
    {
        return actualiserbutton;
    }

    public RelativeLayout getViewNetworkFailed()
    {
        return networkFailed;
    }






    void GetFicheDePaieJsonObject() {
        actualiserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetFicheDePaieJsonObject();
            }
        });
        if (isNetworkAvailable()) {
          /*  if (progressDialog==null)
            {
                progressDialog = MyCustomProgressDialog.ctor(Formulaire.this, "Chargement ... ");
                progressDialog.show();
            }*/

            networkFailed.setVisibility(View.GONE);
            // Tag used to cancel the request
            Log.i("UrlGetFacture", AppConfig.Get_ficheDePaie(idFromGetPaie));
            JsonObjectRequest jsonObjReq = new JsonObjectRequest ( Request.Method.GET,
                    AppConfig.Get_ficheDePaie(idFromGetPaie), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject FactureJsonFromJson) {
                            Log.d("RespWSGetSalarier", FactureJsonFromJson.toString());

                            try {
                                Salaire=  FactureJsonFromJson.get("salaireNet").toString();
                                Toast.makeText(Salarier_Swipe.this,"Fiche de Paie :   "+Salaire,Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("ErrorMessageVolley","Error: "+error.getMessage());
                    VolleyLog.d("TAGVolley", "Error: " + error.getMessage());
                   /* progressDialog.dismiss();
                    progressDialog=null;*/
                    // hide the progress dialog
                    Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.abc_slide_in_bottom);
                    networkFailed.startAnimation(animationTranslate);
                    networkFailed.setVisibility(View.VISIBLE);
                }
            });
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, reqGetFicheDePaie);
        } else {
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.abc_slide_in_bottom);
            networkFailed.startAnimation(animationTranslate);
            networkFailed.setVisibility(View.VISIBLE);
        }
    }

}
