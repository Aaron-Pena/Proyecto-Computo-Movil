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

import java.util.ArrayList;
//Equipo:
// Encinas Torres Estefania
// Peña Camarena Aaron
// Teran Soto Jose Luis
// */
public class Registro extends AppCompatActivity {

    private Button Btn_NewRegistrar;
    private EditText confirmpasswordField, newpasswordField, newuserField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        newuserField = findViewById(R.id.newuserField);
        newpasswordField = findViewById(R.id.newpasswordField);
        confirmpasswordField = findViewById(R.id.confirmpasswordField);

        Btn_NewRegistrar = findViewById(R.id.Btn_NewRegistrar);
        Btn_NewRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUser = newuserField.getText().toString();
                String newPassword = newpasswordField.getText().toString();
                String confirmPassword = confirmpasswordField.getText().toString();

                // Verifica que las contraseñas coincidan
                if (newPassword.equals(confirmPassword)) {
                    // Verifica que el nombre de usuario no exista
                    if (!usuarioExistente(newUser)) {
                        // Agrega el nuevo usuario a la lista en MainActivity
                        agregarNuevoUsuario(newUser, newPassword);

                        Toast.makeText(getApplicationContext(), "Usuario agregado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean usuarioExistente(String newUser) {
        // Verifica si el nombre de usuario ya existe en la lista en MainActivity
        for (Users u : MainActivity.userList) {
            if (u.getUser().equals(newUser)) {
                return true;
            }
        }
        return false;
    }

    private void agregarNuevoUsuario(String newUser, String newPassword) {
        // Verifica que la lista de usuarios en MainActivity esté inicializada
        if (MainActivity.userList == null) {
            MainActivity.userList = new ArrayList<>();
        }

        // Verifica que el nombre de usuario no exista
        if (!usuarioExistente(newUser)) {
            // Agrega un nuevo usuario a la lista en MainActivity
            MainActivity.userList.add(new Users(newUser, newPassword));

            // Guarda la lista de usuarios en preferencias compartidas
            SharedPreferences preferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(MainActivity.userList);
            editor.putString("userList", json);
            editor.apply();
            System.out.println("User: "+newUser+"\nPassword: "+newPassword);
            Toast.makeText(getApplicationContext(), "Usuario agregado correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Registro.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
        }
    }
}