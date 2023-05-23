package com.example.trabajo_final_grupo_11_dam;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class CartaPreferenceHelper {

    private static final String PREFS_NAME = "cart_prefs";
    private static final String CART_KEY = "cart_key";

    public static void saveCart(Context context, List<Carta> cartList) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    public static List<Carta> loadCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_KEY, null);
        Type type = new TypeToken<List<Carta>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static void clearCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(CART_KEY);
        editor.apply();
    }
}
