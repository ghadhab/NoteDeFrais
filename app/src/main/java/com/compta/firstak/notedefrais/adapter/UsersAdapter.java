package com.compta.firstak.notedefrais.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.compta.firstak.notedefrais.DashboardClient;
import com.compta.firstak.notedefrais.Gestion_Client.Client;
import com.compta.firstak.notedefrais.R;

import java.util.ArrayList;

/**
 * Created by mohamed on 12/01/2016.
 */
public class UsersAdapter extends ArrayAdapter<Client> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;

    }

    public UsersAdapter(Context context, ArrayList<Client> users) {
        super(context, R.layout.item_user, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Client user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_bg, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(user.name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),
                        DashboardClient.class);
                getContext().startActivity(intent);
                Toast.makeText(getContext(),String.valueOf(user.getId()),Toast.LENGTH_LONG).show();

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}