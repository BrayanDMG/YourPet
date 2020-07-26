package com.example.yourpet;

import android.net.Uri;

import java.io.Serializable;

public class Mascota implements Serializable {
    private String nombre;
    private String tipo;
    private String id;
    private String raza;
    private String color;
    private String años;
    private String distrito;
    private Uri dir;



    public Mascota() {
    }

    public Mascota(String nombre, String tipo, String id, String raza, String color, String años, String distrito, Uri dir) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.id = id;
        this.raza = raza;
        this.color = color;
        this.años = años;
        this.distrito = distrito;
        this.dir = dir;
    }

    public Mascota(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Mascota(String nombre, String tipo, String id, String raza, String color, String años, String distrito) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.id = id;
        this.raza = raza;
        this.color = color;
        this.años = años;
        this.distrito = distrito;
    }
    public Uri getDir() {
        return dir;
    }

    public void setDir(Uri dir) {
        this.dir = dir;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAños() {
        return años;
    }

    public void setAños(String años) {
        this.años = años;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Mascota(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
