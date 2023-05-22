package com.example.trabajo_final_grupo_11_dam;

public class Carta {

    private String nombreProducto;
    private double precioProducto;
    private int restauranteID;

    // Constructor
    /**
     * Contructor de la clase Carta
     * @param nombreProducto
     * @param precioProducto
     * @param restauranteID
     */
    public Carta(String nombreProducto, double precioProducto, int restauranteID) {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.restauranteID = restauranteID;
    }

    // Getters

    /**
     * Getter: Devuelve el nombre del procducto en formato String.
     * @return nombre del producto
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Getter: Devuelve el precio del procuto en decimal.
     * @return precio del producto
     */
    public double getPrecioProducto() {
        return precioProducto;
    }

    /**
     * Getter: Devuelve el ID del restaurante en enteros.
     * @return ID del restaurante
     */
    public int getRestauranteID() {
        return restauranteID;
    }

    // Setters

    /**
     * Setter: Escojer el nombre del producto, en formato String.
     * @param nombreProducto
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Setter: Escojer el precio del producto, en decimales.
     * @param precioProducto
     */
    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    /**
     * Setter: Escojer el ID del restaurante, en entero.
     * @param restauranteID
     */
    public void setRestauranteID(int restauranteID) {
        this.restauranteID = restauranteID;
    }
}


