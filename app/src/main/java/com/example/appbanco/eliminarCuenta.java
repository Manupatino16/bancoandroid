package com.example.appbanco;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class eliminarCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cuenta);
        EditText numeroCuenta = findViewById(R.id.etnumerocuenta);
        Button eliminar = findViewById(R.id.btneliminar);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        sqlBanco ohBanco = new sqlBanco(getApplicationContext(),"dbbanco",null,1);
                        SQLiteDatabase dbDelete = ohBanco.getWritableDatabase();
                        dbDelete.execSQL("DELETE FROM account WHERE accountnumber = '"+numeroCuenta.getText().toString()+"'");
                        Toast.makeText(getApplicationContext(),"Contacto eliminado correctamente...",Toast.LENGTH_SHORT).show();
            }
        });
    }
}