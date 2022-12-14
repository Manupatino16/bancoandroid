package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String [] rols = {"Administrador","Usuario"};
    String rolSel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText email = findViewById(R.id.etemailreg);
        EditText name = findViewById(R.id.etnamereg);
        EditText password = findViewById(R.id.etpasswordreg);
        Spinner rol = findViewById(R.id.sprolreg);
        Button register = findViewById(R.id.btnregister);
        ArrayAdapter adpRol = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,rols);
        rol.setAdapter(adpRol);
        // Generar el evento para seleccionar un rol
        rol.setOnItemSelectedListener(this);
        //Evento click del botón registrar
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCustomer(email.getText().toString(),name.getText().toString(),password.getText().toString(),rolSel);
            }
        });

    }

    private void searchCustomer(String sEmail, String sName, String sPassword, String srolSel) {
        // Crear array para almacenar los dtos de la consulta
        ArrayList<String>dataCustomer = new ArrayList<String>();
        //Instanciar la clase sqlBanco
        sqlBanco ohBanco = new sqlBanco(this,"dbbanco",null,1);
        //Instanciar la clase SQLliteDatabase para el crud
        SQLiteDatabase db = ohBanco.getReadableDatabase();
        // Crear vble para la consulta
        String sql = "Select email From customer Where email = '"+sEmail+"'";
        //Ejecutar la instruccion que contiene la variable sql, a través de una tabla cursor
        Cursor cCustomer = db.rawQuery(sql,null);
        // Chequear si la tavla cursor cCustomer quedo cno, al menos un registro
        if (!cCustomer.moveToFirst()){ // No lo encontró
            // Agregar el cliente con todos sus datos
            // Instaciar la db en modo escritura porque se agregará un cliente
            SQLiteDatabase dbadd = ohBanco.getWritableDatabase();
            // Manejo de excepciones
            try {
                ContentValues cvCustomer = new ContentValues();
                cvCustomer.put("email", sEmail);
                cvCustomer.put("name", sName);
                cvCustomer.put("password", sPassword);
                cvCustomer.put("rol",srolSel);
                dbadd.insert("customer",null,cvCustomer);

                String query = "SELECT name, rol FROM customer WHERE email = '"+sEmail+"'";
                Cursor sCust = db.rawQuery(query,null);

                Toast.makeText(getApplicationContext(),"Cliente agragado correctamente",Toast.LENGTH_SHORT).show();
                //Chequeaer si el rol es administrador o usuario


                if(srolSel.equals("Administrador")){

                    if (sCust.moveToFirst()){
                        String name = sCust.getString(0);
                        String rol = sCust.getString(1);

                        Intent iCuenta = new Intent(getApplicationContext(),cuenta.class);
                        iCuenta.putExtra("sName", name);
                        iCuenta.putExtra("sRol",rol);
                        startActivity(iCuenta);

                    }


                }
                else{
                    startActivity(new Intent(getApplicationContext(),usuarios.class));

                }
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"ERROR: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Email existente!. Intente con otro ",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        rolSel = rols[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}