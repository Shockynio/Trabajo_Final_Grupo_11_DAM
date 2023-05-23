package com.example.trabajo_final_grupo_11_dam;

import android.util.Log;

/**
 * Clase que representa un restaurante.
 */
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

    /**
     * Constructor por defecto de la clase Restaurantes.
     */
    public Restaurantes() {    }

    // Getters
    /**
     * Obtiene el nombre del restaurante.
     * @return El nombre del restaurante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el email de contacto del restaurante.
     * @return El email de contacto del restaurante.
     */
    public String getEmailContacto() {
        return emailContacto;
    }

    /**
     * Obtiene el teléfono de contacto del restaurante.
     * @return El teléfono de contacto del restaurante.
     */
    public int getTelefonoContacto() {
        return telefonoContacto;
    }

    /**
     * Obtiene la dirección local del restaurante.
     * @return La dirección local del restaurante.
     */
    public String getDireccionLocal() {
        return direccionLocal;
    }

    /**
     * Obtiene el estilo de comida del restaurante.
     * @return El estilo de comida del restaurante.
     */
    public String getEstiloComida() {
        return estiloComida;
    }

    /**
     * Obtiene la hora de apertura del restaurante.
     * @return La hora de apertura del restaurante.
     */
    public String getHorarioApertura() {
        return horarioApertura;
    }

    /**
     * Obtiene la hora de cierre del restaurante.
     * @return La hora de cierre del restaurante.
     */
    public String getHorarioCierre() {
        return horarioCierre;
    }

    /**
     * Indica si el restaurante está cerrado.
     * @return true si el restaurante está cerrado, false si está abierto.
     */
    public boolean getCerrado() {
        return cerrado;
    }

    /**
     * Obtiene el ID del restaurante.
     * @return El ID del restaurante.
     */
    public int getRestaurantId() {
        return restaurantId;
    }

    // Setters
    /**
     * Establece el nombre del restaurante.
     * @param nombre El nombre del restaurante.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el email de contacto del restaurante.
     * @param emailContacto El email de contacto del restaurante.
     */
    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    /**
     * Establece el teléfono de contacto del restaurante.
     * @param telefonoContacto El teléfono de contacto del restaurante.
     */
    public void setTelefonoContacto(int telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    /**
     * Establece la dirección local del restaurante.
     * @param direccionLocal La dirección local del restaurante.
     */
    public void setDireccionLocal(String direccionLocal) {
        this.direccionLocal = direccionLocal;
    }

    /**
     * Establece el estilo de comida del restaurante.
     *
     * @param estiloComida El estilo de comida del restaurante.
     */
    public void setEstiloComida(String estiloComida) {
        this.estiloComida = estiloComida;
    }

    /**
     * Establece el horario de apertura del restaurante.
     *
     * @param horarioApertura El horario de apertura del restaurante.
     */
    public void setHorarioApertura(String horarioApertura) { this.horarioApertura = horarioApertura; }

    /**
     * Establece el horario de cierre del restaurante.
     *
     * @param horarioCierre El horario de cierre del restaurante.
     */
    public void setHorarioCierre(String horarioCierre) {
        this.horarioCierre = horarioCierre;
    }

    /**
     * Establece si el restaurante está cerrado o no.
     *
     * @param cerrado true si el restaurante está cerrado, false en caso contrario.
     */
    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }

    /**
     * Establece el ID del restaurante.
     *
     * @param restaurantId El ID del restaurante.
     */
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }


}

