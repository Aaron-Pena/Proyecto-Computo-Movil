package com.compmovil.proyectomovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button Btn_Entrar;
    private EditText passwordField, userField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userField = findViewById(R.id.userField);
        passwordField = findViewById(R.id.passwordField);
        Btn_Entrar=findViewById(R.id.Btn_Entrar);

    }
}