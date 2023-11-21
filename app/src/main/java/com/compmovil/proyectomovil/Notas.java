package com.compmovil.proyectomovil;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
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
//Equipo:
// Encinas Torres Estefania
// Peña Camarena Aaron
// Teran Soto Jose Luis
// */
public class Notas extends AppCompatActivity {

    private Button btnDatePicker, Btn_Save, Btn_Edit,btnEditId,btnFinalizarId;
    private EditText editTextTitulo, editTextDescripcion,editTextEditNote,editTextFinalizar;
    private TextView textViewFechaSeleccionada, textViewAutor, Shownotes;
    private CheckBox checkBoxFinalizado;
    private CardView NotaNueva;
    private LinearLayoutCompat NoteLayout;

    public static ArrayList<Nota> listaNotas = new ArrayList<>();
    public static final String PREFS_NAME = "MyPrefsFile2";
    private boolean editandoNota = false;

    private int posicionNotaEditando = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        listaNotas = obtenerListaNotasDesdePrefs();

        NoteLayout = findViewById(R.id.NoteLayout);

        String textoRecibido = getIntent().getStringExtra("_user");
        textViewAutor = findViewById(R.id.textViewAutor);
        textViewAutor.setText(textoRecibido);


        textViewFechaSeleccionada = findViewById(R.id.textViewFechaSeleccionada);
        Shownotes = findViewById(R.id.Shownotes);
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextEditNote = findViewById(R.id.editTextEditNote);
        editTextFinalizar = findViewById(R.id.editTextFinalizar);

        Btn_Save = findViewById(R.id.Btn_Save);
        btnEditId = findViewById(R.id.btnEditId);
        btnFinalizarId= findViewById(R.id.btnFinalizarId);

        Btn_Edit = findViewById(R.id.Btn_Edit);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        checkBoxFinalizado = findViewById(R.id.checkBoxFinalizado);
        NotaNueva = findViewById(R.id.NotaNueva);


        mostrarNotasEnTextView();

        deshabilitarEdicion();


        // Configurar los listeners de los botones
        configurarBotones();



    }


    private ArrayList<Nota> obtenerListaNotasDesdePrefs() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("listaNotas", "");

        try {
            if (json.isEmpty()) {
                return new ArrayList<>();
            }

        Type type = new TypeToken<ArrayList<Nota>>() {}.getType();
        return gson.fromJson(json, type);
    } catch (Exception e) {
        Log.e("Notas", "Error al leer la lista de notas desde SharedPreferences", e);
        return new ArrayList<>();
    }
    }

    private void configurarBotones() {

        //Boton para guardar notas
        Btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = listaNotas.size() + 1;
                String autor = textViewAutor.getText().toString();
                String titulo = editTextTitulo.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String fecha = textViewFechaSeleccionada.getText().toString();
                boolean finalizado = checkBoxFinalizado.isChecked();
                saveNote(id,autor,titulo,descripcion,fecha,finalizado);
                mostrarNotaNueva();
                mostrarNotasEnTextView();
                Btn_Edit.setVisibility(View.VISIBLE);
            }
        });

        //Boton para Borrar por ID
        btnEditId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTexto = editTextEditNote.getText().toString();
                if (idTexto.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese un ID válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idTexto);
                eliminarNotaPorId(id);
                editTextEditNote.setText("");
            }
        });

        //Boton para Finalizar por id
        btnFinalizarId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTexto = editTextFinalizar.getText().toString();
                if (idTexto.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese un ID válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idTexto);
                cambiarEstadoFinalizado(id);
                editTextFinalizar.setText("");
            }
        });
        //Boton para Crear notas
        Btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Habilitar la edición al presionar el botón de editar
                habilitarEdicion();
            }
        });

        // Boton para fecha
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatePicker();
            }
        });

    }

    private void saveNote(int id,String autor, String titulo, String descripcion, String fecha, boolean finalizado){

        Notas.listaNotas.add(new Nota(id,autor,titulo,descripcion,fecha,finalizado));

        // Guarda la lista de usuarios en preferencias compartidas
        SharedPreferences preferences = getSharedPreferences(Notas.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Notas.listaNotas);
        editor.putString("listaNotas", json);
        editor.apply();

        Log.d("Notas", "Datos guardados: " + titulo);
        Toast.makeText(getApplicationContext(), "Nota Agregada", Toast.LENGTH_SHORT).show();



    }

    private void mostrarNotaNueva() {

        // Limpiar los campos de la nueva nota
        editTextTitulo.setText("");
        editTextDescripcion.setText("");
        textViewFechaSeleccionada.setText("00 / 00 / 00");
        checkBoxFinalizado.setChecked(false);

        // Deshabilitar la edición
        deshabilitarEdicion();

        // Cambiar el estado de la variable de edición
        editandoNota = false;
        posicionNotaEditando = -1;

    }

    private int buscarPosicionNotaPorId(int id) {
        for (int i = 0; i < listaNotas.size(); i++) {
            if (listaNotas.get(i).getId() == id) {
                return i; // Retorna la posición si encuentra la nota con el ID dado
            }
        }
        return -1; // Retorna -1 si no encuentra la nota con el ID dado
    }

    private void eliminarNotaPorId(int id) {
        int posicion = buscarPosicionNotaPorId(id);

        if (posicion != -1) {
            // Eliminar la nota de la lista
            listaNotas.remove(posicion);

            // Guardar la lista actualizada en preferencias compartidas
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(listaNotas);
            editor.putString("listaNotas", json);
            editor.apply();

            Toast.makeText(getApplicationContext(), "Nota eliminada correctamente", Toast.LENGTH_SHORT).show();
            mostrarNotasEnTextView(); // Actualizar la visualización de notas
        } else {
            Toast.makeText(getApplicationContext(), "No se encontró una nota con ese ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void cambiarEstadoFinalizado(int id) {
        int posicion = buscarPosicionNotaPorId(id);

        if (posicion != -1) {
            // Cambiar el estado finalizado de la nota
            Nota nota = listaNotas.get(posicion);
            nota.setFinalizado(!nota.isFinalizado());

            // Guardar la lista actualizada en preferencias compartidas
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(listaNotas);
            editor.putString("listaNotas", json);
            editor.apply();

            Toast.makeText(getApplicationContext(), "Estado finalizado cambiado correctamente", Toast.LENGTH_SHORT).show();
            mostrarNotasEnTextView(); // Actualizar la visualización de notas
        } else {
            Toast.makeText(getApplicationContext(), "No se encontró una nota con ese ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void habilitarEdicion() {
        // Habilitar la edición de los campos
        editTextTitulo.setEnabled(true);
        editTextDescripcion.setEnabled(true);
        btnDatePicker.setEnabled(true);
        checkBoxFinalizado.setEnabled(true);

        // Mostrar el botón de guardar
        Btn_Save.setVisibility(View.VISIBLE);
        //Esconder Edit
        Btn_Edit.setVisibility(View.GONE);
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

    private void mostrarNotasEnTextView() {
        StringBuilder notasTexto = new StringBuilder();

        for (Nota nota : listaNotas) {
            notasTexto.append("ID: ").append(nota.getId()).append("\n");
            notasTexto.append("Autor: ").append(nota.getAutor()).append("\n");
            notasTexto.append("Titulo: ").append(nota.getTitulo()).append("\n");
            notasTexto.append("Descripcion: ").append(nota.getDescripcion()).append("\n");
            notasTexto.append("Fecha: ").append(nota.getFecha()).append("\n");
            notasTexto.append("Status: ").append(nota.isFinalizado()).append("\n");
            notasTexto.append("------------------------------------------------\n\n");
        }

        Shownotes.setText(notasTexto.toString());
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
