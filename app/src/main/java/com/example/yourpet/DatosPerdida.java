package com.example.yourpet;

public class DatosPerdida {

    private String descripcion;
    private String numeroContacto;
    private String fechaLost;


    public DatosPerdida() {
    }

    public DatosPerdida(String descripcion, String numeroContacto, String fechaLost) {
        this.descripcion = descripcion;
        this.numeroContacto = numeroContacto;
        this.fechaLost = fechaLost;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getFechaLost() {
        return fechaLost;
    }

    public void setFechaLost(String fechaLost) {
        this.fechaLost = fechaLost;
    }
}
