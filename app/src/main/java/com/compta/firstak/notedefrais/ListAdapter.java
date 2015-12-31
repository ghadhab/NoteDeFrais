package com.compta.firstak.notedefrais;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    public ListAdapter(Context context,
						List<String> items ) {
		inflater = LayoutInflater.from( context );
        this.context = context;
        this.items = items;
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

        String item = items.get(position);
        View v = null;
        if( convertView != null )
        	v = convertView;
        else
        	v = inflater.inflate( R.layout.item, parent, false);
        TextView Nom = (TextView)v.findViewById( R.id.nom);
        Nom.setText( item );
		ImageButton button =
			(ImageButton)v.findViewById( R.id.button);
		button.setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
//listImageButton.call();
                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:55884977"));
                            context.startActivity(callIntent);
                        } catch (ActivityNotFoundException activityException) {

                        }
                    }
                });
        ImageButton Remove =
                (ImageButton)v.findViewById( R.id.Supprimer);
        Remove.setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        items.remove(position);
                        listImageButton.adapter.notifyDataSetChanged();
                    }
                    });
        ImageButton Modifier =
                (ImageButton)v.findViewById( R.id.Modifier);
        return v;

    }



    private Context context;
    private List<String> items;
	private LayoutInflater inflater;

        }
