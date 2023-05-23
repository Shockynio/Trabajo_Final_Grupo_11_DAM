package com.example.trabajo_final_grupo_11_dam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajo_final_grupo_11_dam.Fragment.CartaFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase CartaMenuActivity es una actividad en Android que muestra la carta de menú de un restaurante.
 * Extiende la clase AppCompatActivity de Android.
 */
public class CartaMenuActivity extends AppCompatActivity {


    /**
     * Método de ciclo de vida que se llama cuando se crea la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_menu);

        // Fetch the restaurant ID from the Intent extras
        int restaurantId = getIntent().getIntExtra("restaurantId", -1);
        Log.d("CartaMenuActivity", "Received restaurantId from Intent: " + restaurantId);

        // Pass the restaurant ID to the CartaFragment
        CartaFragment cartaFragment = CartaFragment.newInstance(restaurantId);

        // Replace the FrameLayout in activity_carta_menu.xml with the CartaFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, cartaFragment).commit();
    }

    /**
     * Crea una nueva instancia del fragmento CartaFragment con el ID del restaurante como argumento.
     *
     * @param restaurantId El ID del restaurante.
     * @return El fragmento CartaFragment creado.
     */
    public static CartaFragment newInstance(int restaurantId) {
        CartaFragment fragment = new CartaFragment();

        // Supply the restaurantId input as an argument.
        Bundle args = new Bundle();
        args.putInt("restaurantId", restaurantId);
        fragment.setArguments(args);

        return fragment;
    }
}