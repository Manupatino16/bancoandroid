package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email = findViewById(R.id.etemail);
        EditText password = findViewById(R.id.etpassword);
        Button startsession = findViewById(R.id.btnstartsesion);
        TextView reglink = findViewById(R.id.tvregister);
        //
        reglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Invocar la actividad del registro
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        startsession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString();
                String sPassword = password.getText().toString();


                if (!sEmail.isEmpty() && !sPassword.isEmpty()){
                        //conexion a la base de datos
                        sqlBanco ohBanco = new sqlBanco(getApplicationContext(), "dbbanco", null, 1);

                        SQLiteDatabase sDataBase = ohBanco.getReadableDatabase();
                        String query = "SELECT name, rol FROM customer WHERE email = '"+sEmail+"' and password = '"+sPassword+"'";

                    Cursor sCust = sDataBase.rawQuery(query,null);

                        if (sCust.moveToFirst()){
                            String rol = sCust.getString(1);
                            String name = sCust.getString(0);

                            if (rol.equals("Administrador")){
                                //invocar la actividad de cuenta con el parametro name

                                Intent iCuenta = new Intent(getApplicationContext(),cuenta.class);
                                iCuenta.putExtra("sName", name);
                                iCuenta.putExtra("sRol",rol);
                                startActivity(iCuenta);

                            } else{

                                startActivity(new Intent (getApplicationContext(),usuarios.class));
                            }


                        }else{

                            Toast.makeText(getApplicationContext(),"The email or password dont match please be secure you enter the correct one",Toast.LENGTH_SHORT).show();

                        }

                    }else{

                        Toast.makeText(getApplicationContext(),"Please enter an email or password",Toast.LENGTH_SHORT).show();

                    }





            }
        });


    }
}