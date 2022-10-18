package com.example.appbanco;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreacionDeCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        EditText email = findViewById(R.id.etaemail);
        EditText date = findViewById(R.id.etdate);
        EditText balance = findViewById(R.id.etbalance);
        Button create = findViewById(R.id.btncreate);
        TextView acnumber = findViewById(R.id.tvacnumber);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serchAccount(email.getText().toString(),date.getText().toString(),balance.getText().toString());
            }

            private void serchAccount(String semail, String sdate, String sbalance) {
                sqlBanco ohBanco = new sqlBanco(getApplicationContext(),"dbbanco",null,1);
                SQLiteDatabase db = ohBanco.getReadableDatabase();
                String sql = "Select email From customer Where email = '"+semail+"'";
                Cursor cAccount = db.rawQuery(sql,null);
                if(cAccount.moveToFirst()) {
                    SQLiteDatabase dbadd = ohBanco.getWritableDatabase();
                    try {

                        if(parseInt(balance.getText().toString()) >= 1000000 && parseInt(balance.getText().toString()) <= 200000000){
                            ContentValues cvAccount = new ContentValues();
                            cvAccount.put("email", semail);
                            cvAccount.put("date", sdate);
                            cvAccount.put("balance", sbalance);
                            dbadd.insert("account", null, cvAccount);
                            //dbadd.close();
                            Toast.makeText(getApplicationContext(), "Cliente agregado correctamente", Toast.LENGTH_SHORT).show();

                            String acsql = "Select accountnumber from account order by accountnumber desc limit 1;";
                            Cursor sacNumber = db.rawQuery(acsql,null);

                            if (sacNumber.moveToFirst()){
                                //Toast.makeText(getApplicationContext(), "Nro de cuenta "+sacNumber.getInt(0), Toast.LENGTH_SHORT).show();
                                String numero = sacNumber.getString(0);

                                acnumber.setText(numero);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "El balance debe estar entre 1000000 y 200000000", Toast.LENGTH_SHORT).show();
                        }



                        //dbadd.close();
                        //int numero = sacNumber.getInt(0);

                        //acnumber.setText(numero);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Error:  "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Este email ya tiene una cuenta o no se ha registrado",Toast.LENGTH_SHORT).show();

                }
            }


        });


    }
}