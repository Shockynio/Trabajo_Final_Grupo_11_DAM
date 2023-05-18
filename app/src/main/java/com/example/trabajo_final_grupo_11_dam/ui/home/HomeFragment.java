package com.example.trabajo_final_grupo_11_dam.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.R;
import com.example.trabajo_final_grupo_11_dam.RestaurantAdapter;
import com.example.trabajo_final_grupo_11_dam.Restaurantes;
import com.example.trabajo_final_grupo_11_dam.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RestaurantAdapter mRestaurantAdapter;
    private List<Restaurantes> restaurantesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = view.findViewById(R.id.restaurant_recycler_view); // replace with your actual RecyclerView ID
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the adapter with the list of restaurants and set it on the RecyclerView
        mRestaurantAdapter = new RestaurantAdapter(getActivity(), restaurantesList);
        mRecyclerView.setAdapter(mRestaurantAdapter);

        // Fetch restaurant data from the server
        fetchRestaurantData();

        Log.d("HomeFragment", "onCreateView() called");

        return view;
    }

    private void fetchRestaurantData() {
        // Clear the restaurant data before fetching new data
        clearRestaurantData();
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/RetrieveRestaurants";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        Log.d("HomeFragment", "fetchRestaurantData() called");
        Log.d("HomeFragment", "Sending request to server: " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("HomeFragment", "onResponse() called");

                            // Extract the "data" field from the response
                            JSONArray dataArray = response.getJSONArray("data");

                            // This method is called when the response is received
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject restaurantObject = dataArray.getJSONObject(i);
                                Log.d("HomeFragment", "Restaurant: " + restaurantObject.toString());

                                // Create a Restaurantes object and set its properties
                                Restaurantes restaurant = new Restaurantes();
                                restaurant.setNombre(restaurantObject.getString("Nombre"));
                                restaurant.setEmailContacto(restaurantObject.getString("Email_Contacto"));
                                restaurant.setTelefonoContacto(restaurantObject.getInt("Telefono_Contacto"));
                                restaurant.setDireccionLocal(restaurantObject.getString("Dirección_Local"));
                                restaurant.setEstiloComida(restaurantObject.getString("Estilo_Comida"));
                                restaurant.setHorarioApertura(restaurantObject.getString("Horario_Apertura"));
                                restaurant.setHorarioCierre(restaurantObject.getString("Horario_Cierre"));
                                restaurant.setCerrado(restaurantObject.getInt("Cerrado") == 1);

                                // Add the restaurant to the list
                                restaurantesList.add(restaurant);
                            }

                            // Notify the adapter that the data set has changed
                            mRestaurantAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("HomeFragment", "Error in onResponse(): " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // This method is called when an error occurs
                        Log.e("HomeFragment", "Error in onResponse(): " + error.getMessage());
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }


    private void clearRestaurantData() {
        restaurantesList.clear();
        mRestaurantAdapter.notifyDataSetChanged();
    }
}


