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

public class CartaMenuActivity extends AppCompatActivity {



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

    public static CartaFragment newInstance(int restaurantId) {
        CartaFragment fragment = new CartaFragment();

        // Supply the restaurantId input as an argument.
        Bundle args = new Bundle();
        args.putInt("restaurantId", restaurantId);
        fragment.setArguments(args);

        return fragment;
    }
}