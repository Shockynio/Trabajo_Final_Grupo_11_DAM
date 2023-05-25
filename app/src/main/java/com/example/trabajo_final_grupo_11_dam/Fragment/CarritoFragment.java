package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.CarritoCompra;
import com.example.trabajo_final_grupo_11_dam.CarritoCompraAdapter;
import com.example.trabajo_final_grupo_11_dam.Carta;
import com.example.trabajo_final_grupo_11_dam.CartaPreferenceHelper;
import com.example.trabajo_final_grupo_11_dam.Login.MainLoginActivity;
import com.example.trabajo_final_grupo_11_dam.OnCartChangeListener;
import com.example.trabajo_final_grupo_11_dam.Pedido;
import com.example.trabajo_final_grupo_11_dam.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.trabajo_final_grupo_11_dam.Restaurantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CarritoFragment extends Fragment implements OnCartChangeListener {

    private RecyclerView rvCarrito;
    private CarritoCompraAdapter adapter;
    private CarritoCompra carritoCompra;
    private Button btnConfirmarPedido, btnCancelerPedido;
    private TextView totalDelCarritoTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Carrito");

        rvCarrito = view.findViewById(R.id.rv_carrito);
        rvCarrito.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCarrito.setLayoutManager(layoutManager);

        totalDelCarritoTextView = view.findViewById(R.id.text_cantidad);

        carritoCompra = CarritoCompra.getInstance();

        adapter = new CarritoCompraAdapter(new ArrayList<>(), getContext(), this);
        rvCarrito.setAdapter(adapter);

        btnConfirmarPedido = view.findViewById(R.id.btn_confirmar_pedido);
        btnCancelerPedido = view.findViewById(R.id.btn_cancelar_pedido);


        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate total price
                double totalPrice = 0;
                int restaurantId = 0;
                for (Carta carta : CarritoCompra.getInstance().getCartaListLiveData().getValue()) {
                    totalPrice += carta.getPrecioProducto();
                    restaurantId = carta.getRestauranteID();
                }

                final double finalTotalPrice = totalPrice;
                final int finalRestaurantId = restaurantId;

                // Fetch restaurant data
                fetchRestaurantById(restaurantId, new RestaurantCallback() {
                    @Override
                    public void onCallback(Restaurantes restaurant) {
                        // Now that you have fetched the restaurant, you can use it to create the order
                        createOrder(finalTotalPrice, restaurant, finalRestaurantId);
                        Toast.makeText(getContext(), "¡Pedido confirmado! Recuerda que el pago debe hacerse en efectivo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        btnCancelerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carritoCompra.clear();
                adapter.notifyDataSetChanged();
                Log.d("CarritoFragment", "Se ha borrado el pedido");
            }
        });

        carritoCompra.getCartaListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Carta>>() {
            @Override
            public void onChanged(@Nullable final List<Carta> updatedCartaList) {
                adapter.setItemsCarrito(updatedCartaList);
            }
        });

        return view;
    }

    @Override
    public void onCartChange(List<Carta> updatedCartList) {
        int total = 0;
        for (Carta carta : updatedCartList) {
            total += carta.getPrecioProducto();
        }
        totalDelCarritoTextView.setText(String.valueOf(total));
    }



    private void fetchRestaurantById(int id, final RestaurantCallback restaurantCallback) {
        // This is your existing fetchRestaurantById function which should call the callback with the fetched restaurant

        // Create a new request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // The URL of your server-side script
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/restaurant/" + id;

        // Create the GET request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            Restaurantes restaurant = new Restaurantes();
                            restaurant.setDireccionLocal(jsonObject.getString("Dirección_Local"));
                            restaurantCallback.onCallback(restaurant);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("CarritoFragment", "Error: " + error.getMessage());
                    }
                }
        );

        // Add the request to the queue
        queue.add(jsonArrayRequest);
    }


    private void createOrder(double totalPrice, Restaurantes restaurant, int restaurantId) {
        // Create a new request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // The URL of your server-side script
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/createOrder";

        // Create a new JSONObject to hold the data
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Direccion_Restaurante", restaurant.getDireccionLocal());
            jsonBody.put("Direccion_Cliente", "Calle Alamos nº8 3ºC"); // TODO: Crear la lógica de dirección cliente
            jsonBody.put("Precio_Total", totalPrice);
            jsonBody.put("RestauranteID", restaurantId);
            jsonBody.put("Cliente_Username", "Enrique González Gutiérrez"); // TODO: Crear la lógica nombre cliente
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create the POST request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("CarritoFragment", "Order was successful");
                        CarritoCompra.getInstance().clear();
                        adapter.notifyDataSetChanged();
                        Log.d("CarritoFragment", "¡Se ha confirmado el Pedido! Recuerda que el pago es en efectivo");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CarritoFragment", "Error: " + error.getMessage());
            }
        }
        );

        // Add the request to the queue
        queue.add(jsonObjectRequest);
    }


    public interface RestaurantCallback {
        void onCallback(Restaurantes restaurant);
    }
}



