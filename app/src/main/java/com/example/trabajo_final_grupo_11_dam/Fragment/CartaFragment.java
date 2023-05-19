package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.Carta;
import com.example.trabajo_final_grupo_11_dam.CartaAdapter;
import com.example.trabajo_final_grupo_11_dam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CartaFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartaAdapter adapter;
    private List<Carta> menuList;
    private int restaurantId;

    public static CartaFragment newInstance(int restaurantId) {
        CartaFragment fragment = new CartaFragment();
        Bundle args = new Bundle();
        args.putInt("restaurantId", restaurantId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carta, container, false);

        menuList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCarta);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartaAdapter(menuList, getContext());
        recyclerView.setAdapter(adapter);

        fetchMenuData();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            restaurantId = getArguments().getInt("restaurantId");
        }
    }

    private void fetchMenuData() {

        // Use the restaurantId from arguments
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/getMenuItems/" + restaurantId;

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Datos del Menú", "Datos del menú recibidos: " + response.toString());

                        ArrayList<Carta> menuItems = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject menuItem = response.getJSONObject(i);
                                String nombreProducto = menuItem.getString("Nombre_Producto");
                                int precioProducto = menuItem.getInt("Precio_Producto");
                                int restauranteID = menuItem.getInt("RestauranteID");

                                Carta carta = new Carta(nombreProducto, precioProducto, restauranteID);
                                menuItems.add(carta);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        menuList.clear();
                        menuList.addAll(menuItems);
                        adapter.notifyDataSetChanged();

                        Log.d("Datos del Menú", "Elementos del menú actualizados en la lista");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error de Volley", "Error al obtener los datos del menú: " + error.getMessage());
                    }
                });

        queue.add(jsonArrayRequest);
    }
}

