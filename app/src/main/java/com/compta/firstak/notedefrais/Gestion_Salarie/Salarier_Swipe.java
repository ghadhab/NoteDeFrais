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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.compta.firstak.notedefrais.Gestion_Client.AjouterClient;
import com.compta.firstak.notedefrais.Gestion_Client.Modifier_Client;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.adapter.UsersAdapter;
import com.compta.firstak.notedefrais.app.AppConfig;
import com.compta.firstak.notedefrais.app.AppController;
import com.compta.firstak.notedefrais.entity.Client;
import com.compta.firstak.notedefrais.service.JsonService;
import com.github.clans.fab.FloatingActionButton;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import org.json.JSONArray;

import java.util.ArrayList;


public class Salarier_Swipe extends ListActivity implements SwipeActionAdapter.SwipeActionListener {
    private Button actualiserbutton;
    private RelativeLayout networkFailed;
    public static ArrayList<Client> items;
    protected static SwipeActionAdapter mAdapter;
    private int mPreviousVisibleItem;
    ListView lv;
    public static int REQUESTADD=22;
public static  int idFromModifier;
    private  String reqUpdateClient,reqGetIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.activity_swipe);
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


        /*ArrayAdapter<String> stringAdapter = new ArrayAdapter<>(
                this,
                R.layout.row_bg,
                R.id.text,
                items
        );*/


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
                        AjouterClient.class);
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
                    Client addedClient = data.getExtras().getParcelable("client");
                    items.add(addedClient);
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
                    Modifier_Client.class);
            Client item =items.get(position);
            switch (direction) {
                case DIRECTION_FAR_LEFT:
                    try {
                        String number = item.getPhone();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" +number));
                        startActivity(callIntent);
                        Toast.makeText(Salarier_Swipe.this,item.getName()+"  "+String.valueOf(item.getId())+"   tel"+item.getPhone()+"t",Toast.LENGTH_LONG).show();
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        Salarier_Swipe.this.startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {

                    }
                    break;
                case DIRECTION_NORMAL_LEFT:
                    try {
                        String number = item.getPhone();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + number));
                        startActivity(callIntent);
                        Toast.makeText(Salarier_Swipe.this,item.getName()+"  "+String.valueOf(item.getId())+"   tel"+item.getPhone()+"t",Toast.LENGTH_LONG).show();
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        Salarier_Swipe.this.startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {

                    }
                    break;
                case DIRECTION_FAR_RIGHT:
                    startActivity(intent);
                      idFromModifier=item.getId();
                    Toast.makeText(Salarier_Swipe.this,item.getName()+"  "+String.valueOf(item.getId()),Toast.LENGTH_LONG).show();
                    break;
                case DIRECTION_NORMAL_RIGHT:
                    idFromModifier=item.getId();
                    startActivity(intent);
                    Toast.makeText(Salarier_Swipe.this,item.getName()+"  "+String.valueOf(item.getId()),Toast.LENGTH_LONG).show();
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
            JsonArrayRequest jreq = new JsonArrayRequest(AppConfig.URLGetAllClients,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.i("ResponseWsGetFourniseur", response.toString());
                            items=JsonService.getClients(response);

                            UsersAdapter usersAdapter=new UsersAdapter(Salarier_Swipe.this,items,Salarier_Swipe.this);

                            mAdapter = new SwipeActionAdapter(usersAdapter);
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

}
