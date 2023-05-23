package com.example.trabajo_final_grupo_11_dam.Fragment;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.Pedido;
import com.example.trabajo_final_grupo_11_dam.PedidoAdapter;
import com.example.trabajo_final_grupo_11_dam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EncargosDisponiblesFragment extends Fragment {

    private List<Pedido> pedidos;
    private RecyclerView recyclerPedidos;
    private PedidoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_encargos_disponibles, container, false);
        recyclerPedidos = v.findViewById(R.id.encargos_disponibles_recycler_view);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        pedidos = new ArrayList<>();
        adapter = new PedidoAdapter(pedidos);
        recyclerPedidos.setAdapter(adapter);
        loadData();
        return v;
    }

    private void loadData() {
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/RetrievePedidos";

        Log.d("LoadData", "Enviando solicitud a: " + url);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("LoadData", "Respuesta recibida.");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject pedidoJson = response.getJSONObject(i);
                                Pedido pedido = new Pedido(
                                        pedidoJson.getInt("id_pedido"),
                                        pedidoJson.getString("Direccion_Restaurante"),
                                        pedidoJson.getString("Direccion_Cliente"),
                                        pedidoJson.getInt("Precio_Total"),
                                        pedidoJson.getInt("RestauranteID"),
                                        pedidoJson.getString("Cliente_Username")
                                );
                                pedidos.add(pedido);

                                // Register the data for each Pedido object
                                Log.d("LoadData", "Pedido #" + i + ": " + pedido.toString());
                            }
                            Log.d("LoadData", "Datos JSON analizados.");

                            adapter.notifyDataSetChanged();
                            Log.d("LoadData", "Datos cargados exitosamente.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LoadData", "Error al cargar los datos: " + error.getMessage());
                        error.printStackTrace();
                    }
                }
        );

        Volley.newRequestQueue(getContext()).add(request);
        Log.d("LoadData", "Solicitud agregada a la cola de solicitudes.");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pedidos Disponibles");
    }
}
