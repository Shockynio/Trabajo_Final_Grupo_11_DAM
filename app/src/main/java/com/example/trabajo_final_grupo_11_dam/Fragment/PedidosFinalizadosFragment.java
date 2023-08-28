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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

public class PedidosFinalizadosFragment extends Fragment {
    private List<Pedido> pedidosFinalizados;
    private RecyclerView recyclerView;
    private PedidoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pedidos_finalizados, container, false);

        // Configuración del RecyclerView y el adaptador
        recyclerView = v.findViewById(R.id.pedidos_finalizados_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pedidos Finalizados");

        pedidosFinalizados = new ArrayList<>();

        // Inicializa el adaptador con la lista de pedidos finalizados y un nuevo listener
        adapter = new PedidoAdapter(pedidosFinalizados, new PedidoAdapter.PedidoClickListener() {
            @Override
            public void onPedidoClick(Pedido pedido, int position) {
                // Aquí puedes manejar clics para los pedidos finalizados si lo necesitas
            }
        }, false);  // El último parámetro es 'false' porque no es de EncargosEscogidosFragment

        recyclerView.setAdapter(adapter);

        // Carga los datos
        loadData();

        return v;
    }

    private void loadData() {
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/RetrievePedidos";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                            String userEmail = sharedPreferences.getString("email", "Error");

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject pedidoJson = response.getJSONObject(i);

                                // Si el pedido ha sido finalizado y el correo electrónico del repartidor coincide, lo agregamos a la lista
                                if (pedidoJson.getBoolean("IsFinished") && pedidoJson.getString("RepartidorAsignadoEmail").equals(userEmail)) {
                                    Pedido pedido = new Pedido(
                                            pedidoJson.getInt("id_pedido"),
                                            pedidoJson.getString("Direccion_Restaurante"),
                                            pedidoJson.getString("Direccion_Cliente"),
                                            pedidoJson.getInt("Precio_Total"),
                                            pedidoJson.getInt("RestauranteID"),
                                            pedidoJson.getString("Cliente_Username"),
                                            pedidoJson.getBoolean("IsTaken")
                                    );

                                    pedidosFinalizados.add(pedido);
                                }
                            }

                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores
                    }
                }
        );

        Volley.newRequestQueue(getContext()).add(request);
    }
}







