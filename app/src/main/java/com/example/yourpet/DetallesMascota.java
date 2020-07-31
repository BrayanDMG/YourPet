package com.example.yourpet;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class DetallesMascota {

    private String nombre;
    private String tipo;
    private String id;
    private String raza;
    private String color;
    private String años;
    private String distrito;
    private String url;
    private String mkey;
    private String fecha;
    private String descripcion;
    private String user_id;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Exclude
    public String getMkey() {
        return mkey;
    }
    @Exclude
    public void setMkey(String key) {
        this.mkey = key;
    }

    public  DetallesMascota(){

    }

    public DetallesMascota(String nombre, String tipo, String id, String raza, String color, String años, String distrito, String url) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.id = id;
        this.raza = raza;
        this.color = color;
        this.años = años;
        this.distrito = distrito;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
