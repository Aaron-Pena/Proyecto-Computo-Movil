package com.compmovil.proyectomovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    private Button Btn_NewRegistrar;
    private EditText confirmpasswordField,newpasswordField, newuserField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Btn_NewRegistrar = findViewById(R.id.Btn_NewRegistrar);
        Btn_NewRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Poner aqui que tome el texto. Comparar los passwordsField para ver que coincidan
                //Que se agreguen a los usuario y no se repitan los usuarios
                Toast.makeText(getApplicationContext(), "Agregando usuario...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}