package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CuentaBuscar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buscar_cuenta);
        EditText numcuenta = findViewById(R.id.etnumerocuenta);
        Button cbuscar = findViewById(R.id.btnbuscar);
        TextView cemail = findViewById(R.id.tvemail);
        TextView cfecha = findViewById(R.id.tvfecha);
        TextView cbalancecuenta = findViewById(R.id.tvbalancecuenta);

        cbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saccountnumber = numcuenta.getText().toString();
                sqlBanco ohBanco = new sqlBanco(getApplicationContext(), "dbbanco", null, 1);
                SQLiteDatabase dbs = ohBanco.getReadableDatabase();
                String query = "SELECT email, date, balance FROM account WHERE accountnumber = '" + saccountnumber + "'";
                Cursor caccount = dbs.rawQuery(query, null);
                if (caccount.moveToFirst()) {
                    String email = caccount.getString(0);
                    String fecha = caccount.getString(1);
                    String balancecuenta = caccount.getString(2);

                    cemail.setText(email);
                    cfecha.setText(fecha);
                    cbalancecuenta.setText(balancecuenta);

                } else {
                    Toast.makeText(getApplicationContext(), "Numero de cuenta incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}