package com.example.trabajo_final_grupo_11_dam.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.CarritoCompra;
import com.example.trabajo_final_grupo_11_dam.Carta;
import com.example.trabajo_final_grupo_11_dam.CartaAdapter;
import com.example.trabajo_final_grupo_11_dam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra el menú de un restaurante.
 */
public class CartaFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartaAdapter adapter;
    private List<Carta> menuList;
    private int restaurantId;
    private CarritoCompra carritoCompra;

    /**
     * Crea una nueva instancia de CartaFragment.
     * Obtiene la instancia de CarritoCompra al crearse el fragmento.
     */
    public CartaFragment() {
        this.carritoCompra = CarritoCompra.getInstance();
    }

    /**
     * Crea una nueva instancia de CartaFragment con el ID del restaurante especificado.
     *
     * @param restaurantId El ID del restaurante.
     * @return Una nueva instancia de CartaFragment.
     */
    public static CartaFragment newInstance(int restaurantId) {
        CartaFragment fragment = new CartaFragment();
        Bundle args = new Bundle();
        args.putInt("restaurantId", restaurantId);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Método llamado al crear la vista del fragmento.
     * Inicializa los elementos de la interfaz de usuario, configura el adaptador del RecyclerView
     * y obtiene los datos del menú desde el servidor.
     *
     * @param inflater           El LayoutInflater utilizado para inflar la vista.
     * @param container          El contenedor padre en el que se infla la vista.
     * @param savedInstanceState Los datos guardados del fragmento.
     * @return La vista inflada del fragmento.
     */

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carta, container, false);

        setHasOptionsMenu(true);  // Add this line

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().setTitle("Carta del Restaurante");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        menuList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewCarta);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartaAdapter(menuList, getContext(), carritoCompra);
        recyclerView.setAdapter(adapter);

        fetchMenuData();

        return view;
    }


    /**
     * Maneja los eventos de selección de elementos del menú de opciones.
     * Si el elemento seleccionado es el botón de inicio, se finaliza la actividad actual y se regresa a la actividad anterior.
     * @param item El elemento del menú seleccionado.
     * @return true si el evento se ha manejado correctamente, false en caso contrario.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Go back to the previous activity
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * Método llamado al crear el fragmento.
     * Obtiene el ID del restaurante desde los argumentos del fragmento.
     *
     * @param savedInstanceState Los datos guardados del fragmento.
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            restaurantId = getArguments().getInt("restaurantId");
        }
    }

    /**
     * Método privado para obtener los datos del menú desde el servidor.
     * Realiza una solicitud HTTP para obtener los elementos del menú del restaurante específico.
     * Actualiza la lista de elementos del menú y notifica al adaptador del cambio.
     */
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

