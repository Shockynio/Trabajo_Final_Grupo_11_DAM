package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.Pedido;
import com.example.trabajo_final_grupo_11_dam.PedidoAdapter;
import com.example.trabajo_final_grupo_11_dam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class EncargosEscogidosragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Pedido> mPedidos;
    private PedidoAdapter mAdapter;
    private String repartidorEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encargos_escogidosragment, container, false);

        mRecyclerView = view.findViewById(R.id.encargos_disponibles_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mis Pedidos A Entregar");

        // Initialise the ArrayList and the adapter
        mPedidos = new ArrayList<>();
        mAdapter = new PedidoAdapter(mPedidos, new PedidoAdapter.PedidoClickListener() {
            @Override
            public void onPedidoClick(Pedido pedido, int position) {
                // Handle the click event
            }
        });

        // Set the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // Retrieve the repartidor's email from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        repartidorEmail = sharedPreferences.getString("email", "");

        loadPedidos();

        return view;
    }

    private void loadPedidos() {
        // Create a new request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // The URL of the server-side endpoint to fetch pedidos by repartidor's email
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/pedidos/" + repartidorEmail;

        // Create the GET request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray pedidosArray = response.getJSONArray("pedidos");
                                mPedidos.clear();

                                for (int i = 0; i < pedidosArray.length(); i++) {
                                    JSONObject pedidoObject = pedidosArray.getJSONObject(i);
                                    int idPedido = pedidoObject.getInt("id_pedido");
                                    String direccionRestaurante = pedidoObject.getString("Direccion_Restaurante");
                                    String direccionCliente = pedidoObject.getString("Direccion_Cliente");
                                    int precioTotal = pedidoObject.getInt("Precio_Total");
                                    int restauranteId = pedidoObject.getInt("RestauranteID");
                                    String clienteUsername = pedidoObject.getString("Cliente_Username");
                                    boolean isTaken = pedidoObject.getBoolean("IsTaken");

                                    Pedido pedido = new Pedido(idPedido, direccionRestaurante, direccionCliente, precioTotal, restauranteId, clienteUsername, isTaken);
                                    mPedidos.add(pedido);
                                }

                                mAdapter.notifyDataSetChanged();
                            } else {
                                String error = response.getString("error");
                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("EncargosEscogidos", "Error: " + error.getMessage());
                Toast.makeText(getContext(), "Error occurred while retrieving pedidos", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the queue
        queue.add(jsonObjectRequest);
    }
}

