package com.compta.firstak.notedefrais.FormeDeLaSociete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.compta.firstak.notedefrais.R;

/**
 * Created by mohamed on 10/08/2015.
 */
public class SA extends Activity{
    ListView listView ;
    static int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sa);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listOptionSa);

        // Defined Array values to show in ListView
        String[] values = new String[] {
                "President de conseil / DG",
                "Conseil d'administration",
                "Acitionnaires",
        };

      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.textviewsa,values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                    Intent intent = new Intent(SA.this,
                            PresidentDeConseil.class);
                    startActivity(intent);
                    finish();
            }
        });
    }

}

