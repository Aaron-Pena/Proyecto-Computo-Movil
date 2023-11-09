package com.compmovil.proyectomovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button Btn_Entrar,Btn_Registrar;
    private EditText passwordField, userField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userField = findViewById(R.id.userField);
        passwordField = findViewById(R.id.passwordField);

        Btn_Entrar=findViewById(R.id.Btn_Entrar);
        Btn_Registrar = findViewById(R.id.Btn_Registrar);


        Btn_Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userField.getText().toString().equals("123")&&passwordField.getText().toString().equals("123")){
                    Toast.makeText(getApplicationContext(), "Procesando...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Notas.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos. \nVuelva a intentar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Redirigiendo...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Registro.class);
                    startActivity(intent);
            }
        });
    }
}