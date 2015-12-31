package com.compta.firstak.notedefrais.ExpandableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.compta.firstak.notedefrais.Formulaire;
import com.compta.firstak.notedefrais.MainActivity;
import com.compta.firstak.notedefrais.R;
import com.compta.firstak.notedefrais.TableFacture;

public class MainActivityList extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	List<String> t10,t20,t30,t40,t50,t60,t70 ;
	public static String  strname ,strname1,strname2,strname3,strname4;
	public static String Code1,Code2,Code3,Code4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainlist);


		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
										int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				/*Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();*/
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
			/*	Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();
*/
			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				/*Toast.makeText(
						getApplicationContext(),
								listDataChild.get(
								listDataHeader.get(groupPosition)).get(
								childPosition), Toast.LENGTH_SHORT)
						.show();*/
				strname = listDataChild.get(
						listDataHeader.get(groupPosition)).get(
						childPosition);
				for (int k = 0; k < TableFacture.CodeComptable.size(); k++) {
						if(TableFacture.enableid13==true) {
							strname1=strname;
							if (strname1 == TableFacture.CodeComptable.get(k)) {

								Code1 = TableFacture.CodeComptable.get(k - 1);
							}
						}
					if(TableFacture.enableid23==true) {
						strname2=strname;
						if (strname2 == TableFacture.CodeComptable.get(k)) {

							Code2 = TableFacture.CodeComptable.get(k - 1);
						}
					}
						if(TableFacture.enableid15==true) {
							strname3=strname;
							if (strname3 == TableFacture.CodeComptable.get(k)) {

								Code3 = TableFacture.CodeComptable.get(k - 1);
							}
						}
					if(TableFacture.enableid25==true) {
						strname4=strname;
						if (strname4 == TableFacture.CodeComptable.get(k)) {

							Code4 = TableFacture.CodeComptable.get(k - 1);
						}
					}
				}
				Intent intent = new Intent(MainActivityList.this,
						TableFacture.class);
				startActivity(intent);
				finish();
				return false;
			}
		});
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("10");
		listDataHeader.add("20");
		listDataHeader.add("30");
		listDataHeader.add("40");
		listDataHeader.add("50");
		listDataHeader.add("60");
		listDataHeader.add("70");

		 t10 = new ArrayList<String>();
		t20 = new ArrayList<String>();
		t30 = new ArrayList<String>();
		t40 = new ArrayList<String>();
		t50 = new ArrayList<String>();
		t60 = new ArrayList<String>();
		t70 = new ArrayList<String>();

		for(int i=0;i<84;i++){
			t10.add(TableFacture.DictionnaireCodeComptable.get(i));
		}

		for(int i=84;i<162;i++){
			t20.add(TableFacture.DictionnaireCodeComptable.get(i));
		}
		for(int i=162;i<183;i++){
			t30.add(TableFacture.DictionnaireCodeComptable.get(i));
		}
		for(int i=183;i<311;i++){
			t40.add(TableFacture.DictionnaireCodeComptable.get(i));
		}
		for(int i=312;i<352;i++){
			t50.add(TableFacture.DictionnaireCodeComptable.get(i));
		}
		for(int i=353;i<516;i++){
			t60.add(TableFacture.DictionnaireCodeComptable.get(i));
		}
		for(int i=515;i<580;i++){
			t70.add(TableFacture.DictionnaireCodeComptable.get(i));
		}






		listDataChild.put(listDataHeader.get(0), t10); // Header, Child data
		listDataChild.put(listDataHeader.get(1), t20);
		listDataChild.put(listDataHeader.get(2), t30);
		listDataChild.put(listDataHeader.get(3), t40);
		listDataChild.put(listDataHeader.get(4), t50);
		listDataChild.put(listDataHeader.get(5), t60);
		listDataChild.put(listDataHeader.get(6), t70);
	}
}
