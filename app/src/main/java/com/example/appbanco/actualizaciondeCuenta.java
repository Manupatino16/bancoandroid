package com.example.appbanco;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class actualizaciondeCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_cuenta);

        EditText numeroCuenta = findViewById(R.id.etnumerocuenta);
        EditText actualizar = findViewById(R.id.etactualizar);
        Button buscar = findViewById(R.id.btnbuscar);
        Button update = findViewById(R.id.btnupdate);
        TextView balance = findViewById(R.id.tvbalancecuenta);


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String saccountnumber = numeroCuenta.getText().toString();
                sqlBanco ohBanco = new sqlBanco(getApplicationContext(), "dbbanco", null, 1);
                SQLiteDatabase dbs = ohBanco.getReadableDatabase();
                String query = "SELECT balance FROM account WHERE accountnumber = '" + saccountnumber + "'";
                Cursor caccount = dbs.rawQuery(query, null);


                if (caccount.moveToFirst()) {
                    String balancecuenta = caccount.getString(0);
                    balance.setText(balancecuenta);

                } else {
                    Toast.makeText(getApplicationContext(), "Numero de cuenta incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (parseInt(actualizar.getText().toString()) >= 1000000 && parseInt(actualizar.getText().toString()) <= 200000000){
                    sqlBanco ohBanco = new sqlBanco(getApplicationContext(),"dbbanco",null,1);
                    SQLiteDatabase dbUpdate = ohBanco.getWritableDatabase();
                    dbUpdate.execSQL("UPDATE account SET balance = '"+actualizar.getText().toString()+"' WHERE accountnumber = '"+numeroCuenta.getText().toString()+"'");
                    Toast.makeText(getApplicationContext(),"Balance Actualizado correctamente...",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"El nuevo balance debe estar entre 1000000 y 200000000",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}