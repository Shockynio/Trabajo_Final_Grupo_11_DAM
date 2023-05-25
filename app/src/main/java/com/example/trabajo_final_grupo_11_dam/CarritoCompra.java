package com.example.trabajo_final_grupo_11_dam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el carrito de compras de la aplicación utilizando el patrón Singleton.
 */
public class CarritoCompra {

    private static CarritoCompra instance;
    private MutableLiveData<List<Carta>> cartaListLiveData;

    /**
     * Constructor privado de la clase CarritoCompra.
     * Inicializa el LiveData de la lista de cartas del carrito con una lista vacía.
     */
    private CarritoCompra() {
        cartaListLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * Método estático que retorna la instancia única de CarritoCompra.
     * Si no existe una instancia previa, se crea una nueva.
     * @return La instancia única de CarritoCompra.
     */
    public static synchronized CarritoCompra getInstance() {
        if (instance == null) {
            instance = new CarritoCompra();
        }
        return instance;
    }

    /**
     * Agrega una carta al carrito de compras.
     * @param carta La carta a agregar.
     */
    public void addCarta(Carta carta) {
        List<Carta> currentList = cartaListLiveData.getValue();
        currentList.add(carta);
        cartaListLiveData.setValue(currentList);
    }

    /**
     * Elimina todos los elementos del carrito de compras, dejándolo vacío.
     */
    public void clear() {
        cartaListLiveData.setValue(new ArrayList<>());
    }

    /**
     * Obtiene el LiveData de la lista de cartas del carrito.
     * @return El LiveData de la lista de cartas.
     */
    public LiveData<List<Carta>> getCartaListLiveData() {
        return cartaListLiveData;
    }
}
