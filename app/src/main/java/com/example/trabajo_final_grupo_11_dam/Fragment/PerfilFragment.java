package com.example.trabajo_final_grupo_11_dam.Fragment;

import static Util.Metodos.isEmailTaken;
import static Util.Metodos.isValidEmail;
import static Util.Metodos.isValidName;
import static Util.Metodos.isValidSpanishMobileNumber;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.Login.LoginCreacionActivity;
import com.example.trabajo_final_grupo_11_dam.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Fragmento que muestra y permite editar el perfil del usuario.
 */
public class PerfilFragment extends Fragment {

    private TextView tvPDCalle;
    private EditText tvPNNomCompleto;
    private TextView tvPEEmail2;
    private EditText tvPDCalleInfo;
    private EditText tvPTMobil2;
    private EditText tvNacimiento;
    private Switch editModeSwitch;
    private Button saveButton;
    private boolean isEditModeEnabled = false;
    private String oldEmail;

    /**
     * Constructor público de la clase PerfilFragment.
     * Este constructor no requiere argumentos.
     */
    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        tvPDCalle = root.findViewById(R.id.tv_pd_calle);
        tvPDCalleInfo = root.findViewById(R.id.tv_pd_calle_info);
        tvPEEmail2 = root.findViewById(R.id.tv_pe_email2);
        tvPNNomCompleto = root.findViewById(R.id.tv_pn_nom_completo);
        tvPTMobil2 = root.findViewById(R.id.tv_pt_mobil2);
        tvNacimiento = root.findViewById(R.id.tv_pd_nacimiento1);
        editModeSwitch = root.findViewById(R.id.switch_edit_mode);
        saveButton = root.findViewById(R.id.button_save);


        enableEditMode(false);

        // Set click listeners
        editModeSwitch.setOnCheckedChangeListener(this::onEditModeChanged);
        saveButton.setOnClickListener(this::onSaveButtonClicked);

        saveButton.setVisibility(View.GONE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mi perfil");



        tvPNNomCompleto.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    tvPTMobil2.requestFocus();
                    return true;
                }
                return false;
            }
        });

        tvPTMobil2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    tvPDCalleInfo.requestFocus();
                    return true;
                }
                return false;
            }
        });

        tvPDCalleInfo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    tvNacimiento.requestFocus();
                    return true;
                }
                return false;
            }
        });

        tvNacimiento.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    saveButton.requestFocus();
                    return true;
                }
                return false;
            }
        });



        fetchUserProfile();

        return root;
    }

    /**
     * Método que se ejecuta cuando cambia el estado del interruptor de cambio de modo de edición.
     *
     * @param buttonView Vista del interruptor de cambio de modo de edición.
     * @param isChecked  Valor que indica si el modo de edición está habilitado o deshabilitado.
     */
    private void onEditModeChanged(CompoundButton buttonView, boolean isChecked) {
        isEditModeEnabled = isChecked;
        enableEditMode(isEditModeEnabled);

        // Toggle the visibility of the save button
        saveButton.setVisibility(isEditModeEnabled ? View.VISIBLE : View.GONE);
    }

    /**
     * Método que habilita o deshabilita el modo de edición.
     *
     * @param isEnabled Valor que indica si se debe habilitar o deshabilitar el modo de edición.
     */
    private void enableEditMode(boolean isEnabled) {

        tvPNNomCompleto.setEnabled(isEnabled);
        tvPNNomCompleto.setFocusable(isEnabled);
        tvPNNomCompleto.setFocusableInTouchMode(isEnabled);

        tvPEEmail2.setEnabled(isEnabled);
        tvPEEmail2.setFocusable(isEnabled);
        tvPEEmail2.setFocusableInTouchMode(isEnabled);

        tvPDCalleInfo.setEnabled(isEnabled);
        tvPDCalleInfo.setFocusable(isEnabled);
        tvPDCalleInfo.setFocusableInTouchMode(isEnabled);

        tvPTMobil2.setEnabled(isEnabled);
        tvPTMobil2.setFocusable(isEnabled);
        tvPTMobil2.setFocusableInTouchMode(isEnabled);

        tvNacimiento.setEnabled(isEnabled);
        tvNacimiento.setFocusable(isEnabled);
        tvNacimiento.setFocusableInTouchMode(isEnabled);

        if(!isEnabled) {
            // Hide keyboard and clear focus when edit mode is disabled
            View currentView = this.getView();
            if(currentView != null) {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);

                tvPNNomCompleto.clearFocus();
                tvPEEmail2.clearFocus();
                tvPDCalleInfo.clearFocus();
                tvPTMobil2.clearFocus();
                tvNacimiento.clearFocus();
            }
        }
    }

    /**
     * Método que se ejecuta cuando se hace clic en el botón de guardar.
     *
     * @param view Vista del botón de guardar.
     */
    private void onSaveButtonClicked(View view) {
        if (isEditModeEnabled) {
            // Perform data validation and update the database
            String nombreCompleto = tvPNNomCompleto.getText().toString();
            String email = tvPEEmail2.getText().toString();
            String direccionEntrega = tvPDCalleInfo.getText().toString();
            String telefono = tvPTMobil2.getText().toString();
            String nacimiento = tvNacimiento.getText().toString();

            // Data validation
            if (!isValidName(nombreCompleto)) {
                // Invalid name
                Toast.makeText(getContext(), "Escribe un nombre y dos apellidos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                // Invalid email
                Toast.makeText(getContext(), "Escribe un email válido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidSpanishMobileNumber(telefono)) {
                // Invalid phone number
                Toast.makeText(getContext(), "Escribe un teléfono válido", Toast.LENGTH_SHORT).show();
                return;
            }

                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("Email", email);
                            jsonBody.put("Nombre_completo", nombreCompleto);
                            jsonBody.put("Direccion_Entrega", direccionEntrega);
                            jsonBody.put("Telefono", telefono);
                            jsonBody.put("Nacimiento", nacimiento);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("UpdateProfile", "Request Body: " + jsonBody.toString());

                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        String url = "https://trabajo-final-grupo-11.azurewebsites.net/updateUserProfile";

                        // Create a PUT request with the JSON body
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Handle the response
                                        Log.d("UpdateProfile", "Response: " + response.toString());

                                        // Show success message
                                        Toast.makeText(getContext(), "¡Se ha actualizado el perfil correctamente!", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Handle error
                                        Log.e("UpdateProfile", "Error: " + error.getMessage(), error);

                                        // Show error message
                                        Toast.makeText(getContext(), "Ha habido un error actualizando los datos de perfil.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        // Add the request to the RequestQueue.
                        queue.add(jsonObjectRequest);

                        // Disable edit mode after saving the changes
                        isEditModeEnabled = false;
                        enableEditMode(false);
                    }
                }
    /**
     * Método que obtiene el perfil del usuario.
     */
        private void fetchUserProfile() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/userProfile?Email=" + email;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the JSON response
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.d("UserProfile", "Raw JSON Response: " + response);

                            // Update the TextViews
                            String nombreCompleto = jsonResponse.getString("Nombre_completo");
                            String email = jsonResponse.getString("Email");
                            String nacimiento = jsonResponse.getString("Nacimiento");
                            String telefono = jsonResponse.getString("Telefono");
                            int permissionLevel = jsonResponse.getInt("Permission_Level");
                            String direccionEntrega = jsonResponse.getString("Direccion_Entrega");

                            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                            Date date = null;
                            String formattedDate = null; // declare here
                            try {
                                date = sourceFormat.parse(nacimiento);
                                // Format the date without the time portion
                                SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                formattedDate = destFormat.format(date);  // assign value here

                                tvNacimiento.setText(formattedDate); // Set the TextView to the formatted date
                                // Log the formatted date
                                Log.d("UserProfile", "Formatted Date: " + formattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Log.e("UserProfile", "Error parsing date: " + e.getMessage());
                            }

                            tvPNNomCompleto.setText(nombreCompleto);
                            tvPEEmail2.setText(email);
                            tvPDCalleInfo.setText(direccionEntrega);
                            tvPTMobil2.setText(telefono);
                            tvNacimiento.setText(formattedDate);  // use formatted date here

                            Log.d("UserProfile", "Name: " + nombreCompleto);
                            Log.d("UserProfile", "Email: " + email);
                            Log.d("UserProfile", "Birthdate: " + formattedDate);
                            Log.d("UserProfile", "Phone: " + telefono);
                            Log.d("UserProfile", "Direccion: " + direccionEntrega);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.e("UserProfile", "Error fetching user profile: " + error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

