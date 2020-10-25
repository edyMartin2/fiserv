package com.gonzmor.fiserv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gonzmor.fiserv.databaseAll.*;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class addCard extends AppCompatActivity {
    private TextInputEditText Numero, Cvv, Fecha;
    private MaterialButton btnSave;
    private controllerWallet db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        //conexion a la db
        db = new controllerWallet(this);

        Numero = findViewById(R.id.NumeroCapture);
        Cvv = findViewById(R.id.CvvCapture);
        Fecha = findViewById(R.id.FechaCapture);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                if (Numero.getText() != null && Cvv.getText() != null && Fecha.getText() != null){
                    db.insertNewWallet(Numero.getText().toString(), Cvv.getText().toString(),Fecha.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(addCard.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}