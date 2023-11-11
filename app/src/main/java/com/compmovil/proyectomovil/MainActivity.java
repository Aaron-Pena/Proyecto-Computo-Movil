package com.compmovil.proyectomovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button Btn_Entrar,Btn_Registrar,Btn_admin;
    private EditText passwordField, userField;

    public static ArrayList<Users> userList = new ArrayList<>();
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recupera la lista de usuarios almacenada en preferencias compartidas
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("userList", null);
        Type type = new TypeToken<ArrayList<Users>>() {}.getType();
        userList = gson.fromJson(json, type);

        // Si la lista de usuarios es nula, inicialízala como una nueva lista
        if (userList == null) {
            userList = new ArrayList<>();
            userList.add(new Users("Isaac", "123"));
        }

        userField = findViewById(R.id.userField);
        passwordField = findViewById(R.id.passwordField);

        Btn_Entrar=findViewById(R.id.Btn_Entrar);
        Btn_Registrar = findViewById(R.id.Btn_Registrar);
        Btn_admin = findViewById(R.id.Btn_admin);



        Btn_Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userIngresado = userField.getText().toString();
                String passwordIngresada = passwordField.getText().toString();

                // Verifica las credenciales
                if (verificarCredenciales(userIngresado, passwordIngresada)) {
                    Toast.makeText(getApplicationContext(), "Procesando...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Notas.class);
                    intent.putExtra("_user", userIngresado);
                    System.out.println("Mandara esto: "+userField.getText().toString());
                    startActivity(intent);
                    finish(); // Cierra la actividad actual
                } else {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos. \nVuelva a intentar", Toast.LENGTH_SHORT).show();
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

        Btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatosUsuarios();
            }
        });

    }

    private boolean verificarCredenciales(String user, String password) {
        for (Users u : userList) {
            if (u.getUser().equals(user) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void mostrarDatosUsuarios() {
        for (Users user : MainActivity.userList) {
            System.out.println("Usuario: " + user.getUser() + ", Contraseña: " + user.getPassword());
        }
    }
}
