package com.compmovil.proyectomovil;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Notas extends AppCompatActivity {

    private Button btnDatePicker, Btn_Create, Btn_Save, Btn_Delete, Btn_Edit,Btn_Admin;
    private EditText editTextTitulo, editTextDescripcion;
    private TextView textViewFechaSeleccionada, textViewAutor;
    private CheckBox checkBoxFinalizado;
    private CardView NotaNueva;
    private LinearLayout NoteLayout;

    public static ArrayList<Nota> listaNotas = new ArrayList<>();
    public static final String PREFS_NAME = "MyPrefsFile2";

    // Variable para controlar si se está editando una nota
    private boolean editandoNota = false;

    // Variable para mantener la posición de la nota en edición
    private int posicionNotaEditando = -1;

    private int _id= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        listaNotas = obtenerListaNotasDesdePrefs();



        String textoRecibido = getIntent().getStringExtra("_user");
        textViewAutor = findViewById(R.id.textViewAutor);
        textViewAutor.setText(textoRecibido);


        textViewFechaSeleccionada = findViewById(R.id.textViewFechaSeleccionada);
        textViewAutor = findViewById(R.id.textViewAutor);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);

        Btn_Create = findViewById(R.id.Btn_Create);
        Btn_Save = findViewById(R.id.Btn_Save);
        Btn_Delete = findViewById(R.id.Btn_Delete);
        Btn_Edit = findViewById(R.id.Btn_Edit);
        Btn_Admin=findViewById(R.id.Btn_Admin);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        checkBoxFinalizado = findViewById(R.id.checkBoxFinalizado);
        NotaNueva = findViewById(R.id.NotaNueva);

        // Ocultar la NotaNueva al inicio
        NotaNueva.setVisibility(View.GONE);

        // Configurar los listeners de los botones
        configurarBotones();


        // Boton para fecha
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatePicker();
            }
        });
    }



    private ArrayList<Nota> obtenerListaNotasDesdePrefs() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("listaNotas", "");

        if (json.isEmpty()) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<ArrayList<Nota>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void configurarBotones() {
        Btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar la NotaNueva al presionar el botón de crear
                mostrarNotaNueva();
            }
        });

        Btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = _id++;
                String autor = textViewAutor.getText().toString();
                String titulo = editTextTitulo.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String fecha = textViewFechaSeleccionada.getText().toString();
                boolean finalizado = checkBoxFinalizado.isChecked();
                saveNote(id,autor,titulo,descripcion,fecha,finalizado);
            }
        });

        Btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Habilitar la edición al presionar el botón de editar
                habilitarEdicion();
            }
        });

        Btn_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Notas", "Tamaño de listaNotas: " + listaNotas.size());
                for (Nota autor : Notas.listaNotas) {
                    System.out.println("ID: "+autor.getId()+"\nAutor: " + autor.getAutor() +"\nTitulo: "+autor.getTitulo()+ "\nDescripcion: "+ autor.getDescripcion()+"\nFecha: "+autor.getFecha()+"\nStatus:"+autor.isFinalizado());
                }

            }
        });

    }

    private void saveNote(int id,String autor, String titulo, String descripcion, String fecha, boolean finalizado){

        if (Notas.listaNotas == null) {
            Notas.listaNotas = new ArrayList<>();
        }

        Notas.listaNotas.add(new Nota(id,autor,titulo,descripcion,fecha,finalizado));

        // Guarda la lista de usuarios en preferencias compartidas
        SharedPreferences preferences = getSharedPreferences(Notas.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Notas.listaNotas);
        editor.putString("listaNotas", json);
        editor.apply();
        System.out.println("Datos: "+titulo);
        Toast.makeText(getApplicationContext(), "Nota Agregada", Toast.LENGTH_SHORT).show();

    }

    private void mostrarNotaNueva() {

        // Mostrar la NotaNueva
        NotaNueva.setVisibility(View.VISIBLE);

        // Limpiar los campos de la nueva nota
        editTextTitulo.setText("");
        editTextDescripcion.setText("");
        textViewFechaSeleccionada.setText("00/00/0000");
        checkBoxFinalizado.setChecked(false);

        // Deshabilitar la edición
        deshabilitarEdicion();

        // Cambiar el estado de la variable de edición
        editandoNota = false;
        posicionNotaEditando = -1;

    }

    private void habilitarEdicion() {
        // Habilitar la edición de los campos
        editTextTitulo.setEnabled(true);
        editTextDescripcion.setEnabled(true);
        btnDatePicker.setEnabled(true);
        checkBoxFinalizado.setEnabled(true);

        // Mostrar el botón de guardar
        Btn_Save.setVisibility(View.VISIBLE);
    }

    private void deshabilitarEdicion() {
        // Deshabilitar la edición de los campos
        editTextTitulo.setEnabled(false);
        editTextDescripcion.setEnabled(false);
        btnDatePicker.setEnabled(false);
        checkBoxFinalizado.setEnabled(false);

        // Ocultar el botón de guardar
        Btn_Save.setVisibility(View.GONE);
    }

    public void onDatePickerButtonClick(View view) {
        mostrarDatePicker();
    }

    private void mostrarDatePicker() {
        // Obtén la fecha actual
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int día = calendario.get(Calendar.DAY_OF_MONTH);

        // Crea un DatePickerDialog y muestra la interfaz de usuario del calendario
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int año, int mes, int día) {
                        // Actualiza la TextView con la fecha seleccionada
                        String fechaSeleccionada = día + "/" + (mes + 1) + "/" + año;
                        textViewFechaSeleccionada.setText(fechaSeleccionada);
                    }
                }, año, mes, día);
        datePickerDialog.show();
    }



}
