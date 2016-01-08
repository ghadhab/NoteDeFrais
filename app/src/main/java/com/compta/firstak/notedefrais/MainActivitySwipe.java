package com.compta.firstak.notedefrais;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;
import java.util.ArrayList;


public class MainActivitySwipe extends ListActivity implements SwipeActionAdapter.SwipeActionListener {

    public static ArrayList<String> items;
    protected static SwipeActionAdapter mAdapter;
    private int mPreviousVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.activity_swipe);

        items = new ArrayList<String>();
        items.add("Client 1 ");
        items.add("Client 2 ");
        items.add("Client 4");
        items.add("Client 5");
        items.add("Client 6");
        items.add("Client 7");
        items.add("Client 8");
        items.add("Client 9");
        items.add("Client 10");
        items.add("Client 11");
        items.add("Client 12");
        items.add("Client 13");
        items.add("Client 14");
        items.add("Client 15");
        items.add("Client 16");

        ArrayAdapter<String> stringAdapter = new ArrayAdapter<>(
                this,
                R.layout.row_bg,
                R.id.text,
                items
        );
        mAdapter = new SwipeActionAdapter(stringAdapter);
        mAdapter.setSwipeActionListener(this)
                .setDimBackgrounds(true)
                .setListView(getListView());
        setListAdapter(mAdapter);

        mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_bg_right)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.row_bg_right);

        ListView lv = getListView();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> listView, final View view, int position, long id) {
               /* Toast.makeText(
                        MainActivitySwipe.this,
                        "Clicked "+mAdapter.getItem(position),
                        Toast.LENGTH_SHORT
                ).show();*/
              /*  RelativeLayout item=(RelativeLayout)findViewById(R.id.item);
                item.setBackgroundColor(Color.parseColor("#791838"));
                item.setBackgroundResource(R.drawable.delete);*/
                final int pos = position;
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivitySwipe.this);
                adb.setTitle("Voulez vous supprimer " + mAdapter.getItem(position));
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(pos);
                        mAdapter.notifyDataSetChanged();

                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();
                return true;
            }
        });

        final FloatingActionButton buttonAjouter = (FloatingActionButton) findViewById(R.id.buttonAjouter);
        buttonAjouter.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                buttonAjouter.show(true);
                buttonAjouter.setShowAnimation(AnimationUtils.loadAnimation(MainActivitySwipe.this, R.anim.show_from_bottom));
                buttonAjouter.setHideAnimation(AnimationUtils.loadAnimation(MainActivitySwipe.this, R.anim.hide_to_bottom));
            }
        }, 300);


        buttonAjouter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySwipe.this,
                        AjouterClient.class);
                startActivity(intent);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {

                Intent intent = new Intent(MainActivitySwipe.this,
                        DashboardClient.class);
                startActivity(intent);

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

            switch (direction) {
                case DIRECTION_FAR_LEFT:
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:55884977"));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        MainActivitySwipe.this.startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {

                    }
                    break;
                case DIRECTION_NORMAL_LEFT:
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:55884977"));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        MainActivitySwipe.this.startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {

                    }
                    break;
                case DIRECTION_FAR_RIGHT:

                    break;
                case DIRECTION_NORMAL_RIGHT:

                    break;
            }
        }
    }
}
