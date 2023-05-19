package com.example.trabajo_final_grupo_11_dam;

import android.util.Log;

public class Restaurantes {

    private String nombre;
    private String emailContacto;
    private int telefonoContacto;
    private String direccionLocal;
    private String estiloComida;
    private String horarioApertura;
    private String horarioCierre;
    private boolean cerrado;
    private int restaurantId;

    public Restaurantes() {
        // Default constructor
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public int getTelefonoContacto() {
        return telefonoContacto;
    }

    public String getDireccionLocal() {
        return direccionLocal;
    }

    public String getEstiloComida() {
        return estiloComida;
    }

    public String getHorarioApertura() {
        return horarioApertura;
    }

    public String getHorarioCierre() {
        return horarioCierre;
    }

    public boolean getCerrado() {
        return cerrado;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public void setTelefonoContacto(int telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public void setDireccionLocal(String direccionLocal) {
        this.direccionLocal = direccionLocal;
    }

    public void setEstiloComida(String estiloComida) {
        this.estiloComida = estiloComida;
    }

    public void setHorarioApertura(String horarioApertura) {
        this.horarioApertura = horarioApertura;
    }

    public void setHorarioCierre(String horarioCierre) {
        this.horarioCierre = horarioCierre;
    }

    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }


}

