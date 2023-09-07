package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.Pedido;
import com.example.trabajo_final_grupo_11_dam.PedidoAdapter;
import com.example.trabajo_final_grupo_11_dam.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class EncargosDisponiblesFragment extends Fragment {

    private List<Pedido> pedidos;
    private List<Pedido> filteredPedidos;
    private RecyclerView recyclerPedidos;
    private PedidoAdapter adapter;
    private boolean showTakenOrders = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_encargos_disponibles, container, false);
        recyclerPedidos = v.findViewById(R.id.encargos_disponibles_recycler_view);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pedidos Disponibles");
        pedidos = new ArrayList<>();
        filteredPedidos = new ArrayList<>();
        adapter = new PedidoAdapter(filteredPedidos, new PedidoAdapter.PedidoClickListener() {
            @Override
            public void onPedidoClick(Pedido pedido, int position) {
                // Create a new AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // Set the title and message of the AlertDialog
                builder.setTitle("Confirmación");
                builder.setMessage("¿Quieres tomar este pedido?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Yes" button, so you can take the order.
                        pedido.setTaken(true);

                        // Start the request to update the order

                        String url = "https://trabajo-final-grupo-11.azurewebsites.net/pedido/" + pedido.getid_pedido();

                        // Get userEmail from SharedPreferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                        String userEmail = sharedPreferences.getString("email", "Error"); // Use a default value

                        // Set the parameters for the PUT request
                        HashMap<String, Object> params = new HashMap<>();
                        params.put("IsTaken", 1);
                        params.put("RepartidorAsignadoEmail", userEmail);
                        params.put("IsFinished", 0);

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("Pedido Update", "Success: " + response.toString());

                                        adapter.notifyItemChanged(position); // Notify adapter about data changed
                                        Toast.makeText(getContext(), "Has tomado el pedido con éxito.", Toast.LENGTH_LONG).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Pedido Update", "Error: " + error.toString());
                                // TODO: handle error
                            }
                        });

                        Volley.newRequestQueue(getContext()).add(request);
                    }
                });

                // Add a negative button to the AlertDialog
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        // Nothing happens
                    }
                });

                // Create the AlertDialog and display it
                AlertDialog dialog = builder.create();
                dialog.show();

                // Add log for AlertDialog creation
                Log.d("AlertDialog", "Created");
            }
        }, false, false);

        recyclerPedidos.setAdapter(adapter);

        // Add log for starting data loading
        Log.d("Data Loading", "Started");

        loadData();

        // Add log for finishing data loading
        Log.d("Data Loading", "Finished");

        return v;
    }





    private void loadData() {
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/RetrievePedidos";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


        // Crear listas separadas para pedidos tomados y no tomados
        List<Pedido> pedidosTomados = new ArrayList<>();
        List<Pedido> nuevosPedidos = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject pedidoJson = response.getJSONObject(i);
                                boolean isTaken = pedidoJson.getBoolean("IsTaken");
                                boolean isFinished = pedidoJson.getBoolean("IsFinished");

                                Date CreacionPedido = null;
                                Date TomaPedido = null;
                                Date FinalizacionPedido = null;

                                if (pedidoJson.has("CreacionPedido")) {
                                    CreacionPedido = sdf.parse(pedidoJson.getString("CreacionPedido"));
                                }
                                if (pedidoJson.has("TomaPedido") && !pedidoJson.isNull("TomaPedido")) {
                                    try {
                                        TomaPedido = sdf.parse(pedidoJson.getString("TomaPedido"));
                                    } catch (ParseException e) {
                                        Log.e("ParseException", "Error al parsear TomaPedido: " + e.getMessage());
                                    }
                                } else {
                                    TomaPedido = null;
                                }

                                if (pedidoJson.has("FinalizacionPedido") && !pedidoJson.isNull("FinalizacionPedido")) {
                                    try {
                                        FinalizacionPedido = sdf.parse(pedidoJson.getString("FinalizacionPedido"));
                                    } catch (ParseException e) {
                                        Log.e("ParseException", "Error al parsear FinalizacionPedido: " + e.getMessage());
                                    }
                                } else {
                                    FinalizacionPedido = null;
                                }


                                // Si el pedido ha sido tomado y finalizado, no lo agregamos a la lista
                                if (isTaken && isFinished) {
                                    continue;
                                }

                                Pedido pedido = new Pedido(
                                        pedidoJson.getInt("id_pedido"),
                                        pedidoJson.getString("Direccion_Restaurante"),
                                        pedidoJson.getString("Direccion_Cliente"),
                                        pedidoJson.getInt("Precio_Total"),
                                        pedidoJson.getInt("RestauranteID"),
                                        pedidoJson.getString("Cliente_Username"),
                                        isTaken,
                                        CreacionPedido,
                                        TomaPedido,
                                        FinalizacionPedido
                                );

                                pedido.setIsFinished(isFinished);

                                if (pedidoJson.has("RepartidorAsignadoEmail")) {
                                    pedido.setRepartidorAsignadoEmail(pedidoJson.getString("RepartidorAsignadoEmail"));
                                }

                                // Agregar el pedido a la lista correspondiente
                                if (isTaken) {
                                    pedidosTomados.add(pedido);
                                } else {
                                    nuevosPedidos.add(pedido);
                                }
                            }

                            // Unir las listas, con nuevosPedidos en la parte superior
                            pedidos.clear();
                            pedidos.addAll(nuevosPedidos);
                            pedidos.addAll(pedidosTomados);

                            // Also update filteredPedidos
                            filteredPedidos.clear();
                            filteredPedidos.addAll(pedidos);

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
        Log.d("LoadData", "Solicitud agregada a la cola de solicitudes.");
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pedidos Disponibles");
    }


    @Override
    public void onResume() {
        super.onResume();
        // Add the button to the AppBar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Pedidos Disponibles");

        // Create a new Button with custom style
        Button hideTakenButton = new Button(new ContextThemeWrapper(getActivity(), R.style.MyButtonStyle), null, 0);
        hideTakenButton.setText("Ocultar");
        hideTakenButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14); // Ajusta el tamaño a 14sp

        // Set custom text color
        hideTakenButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        // Set custom background
        hideTakenButton.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light));

        // Add OnClickListener
        hideTakenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility();
            }
        });

        actionBar.setCustomView(hideTakenButton);
        actionBar.setDisplayShowCustomEnabled(true);
    }



    @Override
    public void onPause() {
        super.onPause();
        // Remove the button from the AppBar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
    }

    // New method to handle visibility toggle
    private void toggleVisibility() {
        showTakenOrders = !showTakenOrders;
        filterPedidos();
    }

    // New method to filter pedidos based on the flag
    private void filterPedidos() {
        if (showTakenOrders) {
            filteredPedidos.clear();
            filteredPedidos.addAll(pedidos);
        } else {
            filteredPedidos.clear();
            for (Pedido pedido : pedidos) {
                if (!pedido.getIsTaken()) {
                    filteredPedidos.add(pedido);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
