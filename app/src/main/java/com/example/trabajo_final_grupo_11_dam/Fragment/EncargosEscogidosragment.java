package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private boolean isFromEncargosEscogidosFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encargos_escogidosragment, container, false);

        mRecyclerView = view.findViewById(R.id.encargos_disponibles_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button btnCancelar = view.findViewById(R.id.btn_fee_cancelar);
        Button btnEntregado = view.findViewById(R.id.btn_fee_completo);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido selectedPedido = mAdapter.getSelectedPedido();

                // Verificar si hay un pedido seleccionado
                if (selectedPedido != null) {
                    // Crear un cuadro de diálogo de alerta para confirmar la cancelación
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Confirmación");
                    builder.setMessage("¿Estás seguro de que quieres cancelar este pedido?");

                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // TODO: implementar lógica para devolver el pedido a la pool de pedidos disponibles
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Nada pasa si el usuario cancela el cuadro de diálogo
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // Mostrar un mensaje si no se ha seleccionado ningún pedido
                    Toast.makeText(getContext(), "Por favor selecciona un pedido para cancelar.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEntregado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido selectedPedido = mAdapter.getSelectedPedido();

                // Verificar si hay un pedido seleccionado
                if (selectedPedido != null) {
                    // Crear un cuadro de diálogo de alerta para confirmar la entrega
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Confirmación");
                    builder.setMessage("¿Has entregado este pedido?");

                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Implementar lógica para marcar el pedido como entregado
                            // Crear una nueva solicitud para marcar el pedido como entregado
                            String url = "https://trabajo-final-grupo-11.azurewebsites.net/pedido/entregado/" + selectedPedido.getid_pedido();

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                boolean success = response.getBoolean("success");
                                                if (success) {
                                                    Toast.makeText(getContext(), "Pedido marcado como entregado exitosamente.", Toast.LENGTH_SHORT).show();
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
                                    Log.e("EntregaPedido", "Error: " + error.getMessage());
                                    Toast.makeText(getContext(), "Error occurred while marking the pedido as entregado.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            // Agregar la solicitud a la cola
                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            queue.add(jsonObjectRequest);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Nada pasa si el usuario cancela el cuadro de diálogo
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // Mostrar un mensaje si no se ha seleccionado ningún pedido
                    Toast.makeText(getContext(), "Por favor selecciona un pedido para entregar.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mis Pedidos A Entregar");

        // Initialise the ArrayList and the adapter
        mPedidos = new ArrayList<>();
        mAdapter = new PedidoAdapter(mPedidos, new PedidoAdapter.PedidoClickListener() {
            @Override
            public void onPedidoClick(Pedido pedido, int position) {
                // Handle the click event
            }
        }, true);


        // Set the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // Retrieve the repartidor's email from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        repartidorEmail = sharedPreferences.getString("email", "");

        loadPedidos();

        return view;
    }

    public void setFromEncargosEscogidosFragment(boolean isFromEncargosEscogidosFragment) {
        this.isFromEncargosEscogidosFragment = isFromEncargosEscogidosFragment;
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
