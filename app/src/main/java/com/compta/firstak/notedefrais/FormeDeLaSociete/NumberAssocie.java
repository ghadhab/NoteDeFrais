package com.compta.firstak.notedefrais.FormeDeLaSociete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.compta.firstak.notedefrais.R;

/**
 * Created by mohamed on 24/08/2015.
 */
public class NumberAssocie extends Activity {
EditText EditTextNombreAssocie;
    Button ValiderNombreAssocie;
    public static int NombreAssocie;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nombreassocie);
        EditTextNombreAssocie=(EditText)findViewById(R.id.EditTextNombre);
        EditTextNombreAssocie.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                NombreAssocie=Integer.parseInt(EditTextNombreAssocie.getText().toString());
            }
        });


        ValiderNombreAssocie=(Button)findViewById(R.id.ValiderNombre);
        ValiderNombreAssocie.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });
    }
}
