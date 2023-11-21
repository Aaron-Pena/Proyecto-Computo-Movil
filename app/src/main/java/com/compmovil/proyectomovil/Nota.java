package com.compmovil.proyectomovil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
//Equipo:
// Encinas Torres Estefania
// Peña Camarena Aaron
// Teran Soto Jose Luis
// */
public class Nota  {

    private int id;
    private String autor;
    private String titulo;
    private String descripcion;
    private String fecha;

    private boolean finalizado;

    // Constructor
    public Nota(int id,String autor, String titulo, String descripcion, String fecha, boolean finalizado) {
        this.id=id;
        this.autor = autor;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.finalizado = finalizado;
    }

    // Getters y Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }





}