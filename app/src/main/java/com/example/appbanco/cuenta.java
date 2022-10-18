package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class cuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        Button createAccount = findViewById(R.id.btncrear);
        Button searchAccount = findViewById(R.id.btnbuscar);
        Button updateAccount = findViewById(R.id.btnactualizar);
        Button deleteAccount = findViewById(R.id.btneliminar);
        TextView usuarioC = findViewById(R.id.tvusuarioc);
        usuarioC.setText(usuarioC.getText().toString() + " " + getIntent().getStringExtra("sName") + " " + getIntent().getStringExtra("sRol"));



        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreacionDeCuenta.class));
            }
        });

        searchAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CuentaBuscar.class));
            }
        });

        updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),actualizaciondeCuenta.class));
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),eliminarCuenta.class));
            }
        });
    }
}