package com.example.trabajo_final_grupo_11_dam;

public class Carta {

    private String nombreProducto;
    private double precioProducto;
    private int restauranteID;

    // Constructor
    public Carta(String nombreProducto, double precioProducto, int restauranteID) {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.restauranteID = restauranteID;
    }

    // Getters
    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public int getRestauranteID() {
        return restauranteID;
    }

    // Setters
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public void setRestauranteID(int restauranteID) {
        this.restauranteID = restauranteID;
    }
}


