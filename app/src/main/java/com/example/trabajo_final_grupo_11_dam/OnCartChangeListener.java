package com.example.trabajo_final_grupo_11_dam;

import java.util.List;

/**
 * Interfaz para escuchar cambios en el carrito de compras.
 */
public interface OnCartChangeListener {
    /**
     * Método llamado cuando se producen cambios en el carrito de compras.
     * @param updatedCartList Lista actualizada de cartas en el carrito.
     */
    void onCartChange(List<Carta> updatedCartList);
}
