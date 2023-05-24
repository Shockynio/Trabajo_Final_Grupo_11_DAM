package com.example.trabajo_final_grupo_11_dam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompra {

    private static CarritoCompra instance;
    private MutableLiveData<List<Carta>> cartaListLiveData;

    private CarritoCompra() {
        cartaListLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    public static synchronized CarritoCompra getInstance() {
        if (instance == null) {
            instance = new CarritoCompra();
        }
        return instance;
    }

    public void addCarta(Carta carta) {
        List<Carta> currentList = cartaListLiveData.getValue();
        currentList.add(carta);
        cartaListLiveData.setValue(currentList);
    }

    public void clear() {
        cartaListLiveData.setValue(new ArrayList<>());
    }

    public LiveData<List<Carta>> getCartaListLiveData() {
        return cartaListLiveData;
    }
}
