package com.example.trabajo_final_grupo_11_dam;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Clase de utilidad para guardar y cargar el carrito de compras utilizando SharedPreferences.
 */
public class CartaPreferenceHelper {

    private static final String PREFS_NAME = "cart_prefs";
    private static final String CART_KEY = "cart_key";

    /**
     * Guarda el carrito de compras en SharedPreferences.
     * @param context Contexto de la aplicación.
     * @param cartList Lista de cartas del carrito de compras.
     */
    public static void saveCart(Context context, List<Carta> cartList) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    /**
     * Carga el carrito de compras desde SharedPreferences.
     * @param context Contexto de la aplicación.
     * @return Lista de cartas del carrito de compras.
     */
    public static List<Carta> loadCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_KEY, null);
        Type type = new TypeToken<List<Carta>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * Borra el carrito de compras de SharedPreferences.
     * @param context Contexto de la aplicación.
     */
    public static void clearCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(CART_KEY);
        editor.apply();
    }
}
