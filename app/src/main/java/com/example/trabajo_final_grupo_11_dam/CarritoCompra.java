package com.example.trabajo_final_grupo_11_dam;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompra {
    private static CarritoCompra instance;
    private List<Carta> cartaList;

    private CarritoCompra() {
        cartaList = new ArrayList<>();
    }

    public static synchronized CarritoCompra getInstance() {
        if (instance == null) {
            instance = new CarritoCompra();
        }
        return instance;
    }

    public List<Carta> getCartaList() {
        return cartaList;
    }

    public void addCarta(Carta carta) {
        this.cartaList.add(carta);
    }

    public void removeCarta(Carta carta) {
        this.cartaList.remove(carta);
    }

    public void clear() {
        this.cartaList.clear();
    }

    public void setCartaList(List<Carta> cartaList) {
        this.cartaList = cartaList;
    }
}
