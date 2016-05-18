package com.compta.firstak.notedefrais.Gestion_Client;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.compta.firstak.notedefrais.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mohamed on 09/05/2016.
 */
public class Fiche_Paie extends Activity{

    Calendar myCalendar = Calendar.getInstance();
    EditText DatePaie;
    String GetConstitution;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paie);

        DatePaie=(EditText)findViewById(R.id.Date_text);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        DatePaie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(Fiche_Paie.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                //Toast.makeText(Fiche_Paie.this, "OKK", Toast.LENGTH_LONG).show();
            }
        });


      Button  AjouterClient = (Button) findViewById(R.id.submit);
        AjouterClient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                GetConstitution=DatePaie.getText().toString();

            }
        });



      /*  DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        try {
            java.lang.reflect.Field f[] = datePicker.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : f) {
                if (field.getName().equals("mDayPicker")) {
                    field.setAccessible(true);
                    Object dayPicker = new Object();
                    dayPicker = field.get(datePicker);
                    ((View) dayPicker).setVisibility(View.GONE);
                }
            }
        } catch (SecurityException e) {
            Log.d("ERROR", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        } catch (IllegalAccessException e) {
            Log.d("ERROR", e.getMessage());
        }*/

    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        DatePaie.setText(sdf.format(myCalendar.getTime()));

    }
}
