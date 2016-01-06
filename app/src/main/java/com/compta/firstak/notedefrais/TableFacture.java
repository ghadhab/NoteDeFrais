package com.compta.firstak.notedefrais;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.compta.firstak.notedefrais.ExpandableList.MainActivityList;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TableFacture extends Activity {
    public static ArrayList<String> DictionnaireCodeComptable;
    public static ArrayList<String> CodeComptable;
    public static StringBuilder text;
    static EditText textView;
    static TextView textView1;
    public static int id;
    static int rowCount;
    public static boolean enableid13, enableid23, enableid25, enableid15;
    String TVaValue;
    static TableLayout tableLayout;
    // ChoixPhotoResult ch1;
    public static ArrayList<String> a = new ArrayList<String>();
    public static ArrayList<String> couleur = new ArrayList<String>();
    int p=0;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences prefs;
    static String[] playlists;
    TableRow tableRow;
   static int next=0;
    String NPiece;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String[] column = {"N Piece  | Date Saisie", "DATE PIECE", "N COMPTE GENERAL", "INTITULE COMPTE GENERAL",
                "N COMPTE AUXILIAIRE", "INTITULE COMPTE AUXILIAIRE", "LIBELLE FACTURE", "DEBIT", "CREDIT", "SOLDE"};


        prefs = getSharedPreferences("ArrayList", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();


        int cl = column.length;
        ScrollView sv = new ScrollView(this);
        TableLayout tableLayout = createTableLayout(column, cl);
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.addView(tableLayout);
        sv.addView(hsv);
        setContentView(sv);
        readRawTextFile(getApplicationContext(), R.raw.plancomptable1);

    }

    private TableLayout createTableLayout(String[] cv, int columnCount) {
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        tableLayout = new TableLayout(this);
        tableLayout.setBackgroundColor(Color.BLACK);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        for (int m = 0; m < 1; m++) {
            // 3) create tableRow
            TableRow tableRow1 = new TableRow(this);
            tableRow1.setBackgroundColor(Color.BLACK);

            for (int n = 0; n < 10; n++) {
                // 4) create textView
                textView1 = new TextView(this);
                textView1.setBackgroundColor(Color.parseColor("#791634"));
                textView1.setTextColor(Color.WHITE);
                textView1.setGravity(Gravity.CENTER);
                textView1.setMaxLines(1);

                if (m == 0) {
                    textView1.setText(cv[n]);
                }
                tableRow1.addView(textView1, tableRowParams);
            }
            tableLayout.addView(tableRow1, tableLayoutParams);
        }


        if (Formulaire.isNew == true) {
            rowCount = rowCount + 3;
next=next+1;
            for (int i = 0; i < rowCount; i++) {
                // 3) create tableRow
                tableRow = new TableRow(this);
                tableRow.setBackgroundColor(Color.BLACK);

                for (int j = 0; j < columnCount; j++) {
                    // 4) create textView

                    String s1 = Integer.toString(i);
                    String s2 = Integer.toString(j);
                    String s3 = s1 + s2;

                    id = Integer.parseInt(s3);
                    textView = new EditText(this);
                    textView.setTextColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setMaxLines(1);


                    if (rowCount > 3) {
                        p = (rowCount * 10) - 30;

                    }
                    if (id == p) {
                        textView.setInputType(InputType.TYPE_NULL);
                            textView.setText(next+"/"+ MainActivity.Année + "    " + MainActivity.Date);
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));

                    }
                    if (id == p + 1) {
                        textView.setInputType(InputType.TYPE_NULL);
                        textView.setText(Formulaire.DateText);
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));
                    }
                    if (id == p + 2) {
                        textView.setInputType(InputType.TYPE_NULL);
                        textView.setText(ParsingOcrResult.Code);
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));
                    }
                    if (id == p + 3) {

                        textView.setInputType(InputType.TYPE_NULL);
                        textView.setText(ParsingOcrResult.DesignationAtMin);
                        textView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v3) {
                                enableid13 = true;
                                enableid23 = false;
                                enableid15 = false;
                                enableid25 = false;
                                Intent intent = new Intent(TableFacture.this,
                                        MainActivityList.class);
                                startActivity(intent);
                                rowCount = rowCount - 3;
                                //Formulaire.isNew = false;
                            }
                        });
                        if (MainActivityList.strname1 != null) {
                            textView.setText(MainActivityList.strname1);
                        }
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));
                    }
                    if (id == p + 4) {
                        textView.setInputType(InputType.TYPE_NULL);
                        //textView.setText(MainActivityList.Code3);
                        textView.setText(ParsingOcrResult.Code);
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));

                    }
                    if (id == p + 5) {
                        textView.setInputType(InputType.TYPE_NULL);
                        textView.setText(ParsingOcrResult.DesignationAtMin);
                        textView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v4) {
                                enableid13 = false;
                                enableid23 = false;
                                enableid25 = false;
                                enableid15 = true;
                                rowCount = rowCount - 3;
                                Intent intent = new Intent(TableFacture.this,
                                        MainActivityList.class);
                                startActivity(intent);
                            }
                        });
                        if (MainActivityList.strname3 != null) {
                            textView.setText(MainActivityList.strname3);
                        }
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));

                    }
                    if (id == p + 6) {
                        textView.setText(Formulaire.DesignationText);
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));

                    }
                    if (id == p + 7) {
                        textView.setText(String.valueOf(Formulaire.HTVAtext));
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));

                    }
                    if (id == p + 8) {
                        textView.setText("");
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));

                    }
                    if (id == p + 9) {
                        textView.setText(String.valueOf(Formulaire.HTVAtext));
                        a.add(textView.getText().toString());
                        couleur.add("#004774");
                        textView.setBackgroundColor(Color.parseColor("#004774"));

                    }

                    if (id == p + 10) {
                        textView.setText(next+"/"+ MainActivity.Année + "    " + MainActivity.Date);
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));

                    }
                    if (id == p + 11) {
                        textView.setText(Formulaire.DateText);
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));

                    }
                    if (id == p + 12) {
                        textView.setInputType(InputType.TYPE_NULL);
                        //textView.setText(MainActivityList.Code2);
                        textView.setText("4366");
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));

                    }
                    if (id == p + 13) {
                        textView.setInputType(InputType.TYPE_NULL);
                        textView.setText("Taxes sur le chiffre d'affaires deductibles");
                        textView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v6) {
                                enableid13 = false;
                                enableid23 = true;
                                enableid15 = false;
                                enableid25 = false;
                                rowCount = rowCount - 3;
                                Intent intent = new Intent(TableFacture.this,
                                        MainActivityList.class);
                                startActivity(intent);
                            }
                        });
                        if (MainActivityList.strname4 != null) {
                            textView.setText(MainActivityList.strname2);
                        }
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));
                    }
                    if (id == p + 14) {
                        textView.setInputType(InputType.TYPE_NULL);
                        // textView.setText(MainActivityList.Code4);
                        textView.setText("4366");
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));

                    }
                    if (id == p + 15) {
                        textView.setInputType(InputType.TYPE_NULL);
                        textView.setText("Taxes sur le chiffre d'affaires deductibles");
                        textView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v5) {
                                enableid13 = false;
                                enableid23 = false;
                                enableid25 = true;
                                enableid15 = false;
                                rowCount = rowCount - 3;
                                Intent intent = new Intent(TableFacture.this,
                                        MainActivityList.class);
                                startActivity(intent);
                            }
                        });
                        if (MainActivityList.strname4 != null) {
                            textView.setText(MainActivityList.strname4);
                        }
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));

                    }

                    if (id == p + 16) {
                        textView.setText(Formulaire.DesignationText);
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));
                    }
                    if (id == p + 17) {
                        try {
                            Double TVAi = Double.parseDouble(Formulaire.TvaText.replace("%", ""));
                            TVaValue = String.valueOf(Double.parseDouble(Formulaire.HTVAtext) * (TVAi / 100));
                            textView.setText(TVaValue);
                            a.add(textView.getText().toString());
                            couleur.add("#206795");
                            textView.setBackgroundColor(Color.parseColor("#206795"));
                        } catch (Exception ex) {
                        }

                    }
                    if (id == p + 18) {
                        textView.setText("");
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));
                    }
                    if (id == p + 19) {
                        textView.setText(TVaValue);
                        a.add(textView.getText().toString());
                        couleur.add("#206795");
                        textView.setBackgroundColor(Color.parseColor("#206795"));
                    }
                    if (id == p + 20) {
                     NPiece=next+"/"+ MainActivity.Année;
                        textView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view)
                            {
                               Intent intent = new Intent();
                                intent.setAction(android.content.Intent.ACTION_VIEW);
                                intent.addCategory(android.content.Intent.CATEGORY_DEFAULT);
                                intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath()+MainActivity.imageFileName),"image/*");
                                startActivity(intent);
                               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.imageFileName)));
                            }

                        });
                        textView.setText(NPiece+ "    " + MainActivity.Date);
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    if (id == p + 21) {
                        textView.setText(Formulaire.DateText);
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    if (id == p + 22) {
                        textView.setText("4011");
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    if (id == p + 23) {
                        textView.setText("Fournisseurs d'exploitation");
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    if (id == p + 24) {
                        textView.setText("4011");
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    if (id == p + 25) {
                        textView.setText("Fournisseurs d'exploitation");
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }

                    if (id == p + 26) {
                        textView.setText(Formulaire.DesignationText);
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    if (id == p + 27) {
                        textView.setText("");
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    if (id == p + 28) {
                        textView.setText(Formulaire.TottalText);
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    if (id == p + 29) {
                        textView.setText(Formulaire.TottalText);
                        //            textView.setBackgroundColor("#599cc6");
                        a.add(textView.getText().toString());
                        couleur.add("#599cc6");
                        textView.setBackgroundColor(Color.parseColor("#599cc6"));
                    }
                    // if (a.size() != 0) {
     /*                   for (int b = 0; b < id; b++) {
                           // String ss = a.get(b);
                            textView.setText(a.get(b));
                            textView.setBackgroundColor(Color.parseColor(couleur.get(b)));
                            }*/
                    // }

                    // 5) add textView to tableRow
                    tableRow.addView(textView, tableRowParams);

                }

                // 6) add tableRow to tableLayout
                tableLayout.addView(tableRow, tableLayoutParams);

            }

        }
          /*  for (String ss : playlists) {

                System.out.println(ss);
            }*/

        return tableLayout;
    }

    public String readRawTextFile(Context ctx, int resId) {
        try {
            DictionnaireCodeComptable = new ArrayList<String>();
            CodeComptable = new ArrayList<String>();
            InputStream inputStream = ctx.getResources().openRawResource(resId);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            text = new StringBuilder();


            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\=");
                for (int i = 0; i < data.length - 1; i = i + 2) {
                    DictionnaireCodeComptable.add(data[i + 1]);
                }
                for (int j = 0; j < data.length; j++) {
                    CodeComptable.add(data[j]);
                }
            }
            for (String ss : DictionnaireCodeComptable) {

                System.out.println(ss);
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }
        System.out.println("txt  " + text.toString());
        return text.toString();

    }

    @Override
    public void onBackPressed() {
        //Formulaire.isNew = false;
        Intent intent = new Intent(TableFacture.this,
                DashboardClient.class);
        startActivity(intent);
    }

    public void onPause() {
        super.onPause();
        Formulaire.isNew = false;

    }
}
