package com.example.trabajo_final_grupo_11_dam.Login;

import Gradients.BorderGradientDrawable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.example.trabajo_final_grupo_11_dam.MenuActivity;

import com.example.trabajo_final_grupo_11_dam.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * Esta clase representa la actividad principal de inicio de sesión.
 */
public class MainLoginActivity extends AppCompatActivity {

    private TextView tvCreateAccount;
    private TextView tvSolicitud;
    private Button   btnIniciarSesion;
    private TextView tvContrasenaOlvidade;
    private EditText etEmail;
    private EditText etContrasena;


    /**
     * Método que se ejecuta al crear la actividad.
     * Configura los elementos de la interfaz y los listeners.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener el correo electrónico y la contraseña ingresados por el usuario
        etEmail = findViewById(R.id.et_email);
        etContrasena = findViewById(R.id.et_contraseña);

        tvSolicitud = findViewById(R.id.tv_Solicitud);
        btnIniciarSesion = findViewById(R.id.btn_iniciar);
        tvContrasenaOlvidade =  findViewById(R.id.tv_contraseña_olvidada);
        tvCreateAccount = findViewById(R.id.tv_crear_cuenta);

        // Configuración del evento de clic para el botón de inicio de sesión
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etContrasena.getText().toString();

                Log.d("Login", "Email: " + email);
                Log.d("Login", "Password: " + password);

                // Crear una cola de solicitudes para enviar la solicitud de inicio de sesión al servidor
                RequestQueue queue = Volley.newRequestQueue(MainLoginActivity.this);

                String url = "https://trabajo-final-grupo-11.azurewebsites.net/login";

                // Crear un objeto JSON con el correo electrónico y la contraseña
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("Email", email);
                    jsonBody.put("Password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Crear la solicitud JSON para enviar los datos de inicio de sesión al servidor
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Login", "Response: " + response.toString());
                                try {
                                    boolean success = response.getBoolean("success");
                                    if (success) {
                                        Log.d("Login", "Login successful");

                                        // El inicio de sesión fue exitoso. Almacenar el correo electrónico del usuario.
                                        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        Log.d("Login", "Storing user's email: " + email);
                                        editor.putString("email", email);

                                        // New code
                                        String direccion_entrega = response.getString("direccion_entrega");
                                        String nombre_completo = response.getString("nombre_completo");
                                        Log.d("Login", "Storing user's delivery address: " + direccion_entrega);
                                        Log.d("Login", "Storing user's full name: " + nombre_completo);
                                        editor.putString("direccion_entrega", direccion_entrega);
                                        editor.putString("nombre_completo", nombre_completo);

                                        editor.apply();

                                        // Iniciar la siguiente actividad.
                                        Intent iniciarSesionCliente = new Intent(MainLoginActivity.this, MenuActivity.class);
                                        startActivity(iniciarSesionCliente);
                                    } else {
                                        Log.d("Login", "Login failed");
                                        // El inicio de sesión falló. Mostrar un mensaje de error.
                                        String message = response.getString("error");
                                        Toast.makeText(MainLoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Login", "Error: " + error.getMessage(), error);

                                String errorMessage = "An error occurred during login";

                                if (error.networkResponse != null && error.networkResponse.data != null) {
                                    try {
                                        String errorResponse = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                                        JSONObject errorObject = new JSONObject(errorResponse);

                                        if (errorObject.has("error")) {
                                            errorMessage = errorObject.getString("error");
                                        }
                                    } catch (UnsupportedEncodingException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                Toast.makeText(MainLoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });

                // Agregar la solicitud a la cola de solicitudes.
                queue.add(jsonObjectRequest);
            }
        });







        // Configuración del evento de clic para el enlace de "Contraseña olvidada"
        tvContrasenaOlvidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });

        // FIX DEL BUG DEL ENTER DEL CAMPO DE CORREO

        // Solución para el error relacionado con la tecla Enter en el campo de correo electrónico
        etEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        View nextField = v.focusSearch(View.FOCUS_DOWN);
                        if (nextField != null) {
                            nextField.requestFocus();
                        }
                    }
                    return true; // Consume el evento de tecla Enter
                }
                return false; // Permite que otros eventos de teclas sean manejados normalmente
            }
        });


        // Solución para el error relacionado con la tecla Enter en el campo de contraseña
        etContrasena.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    btnIniciarSesion.requestFocus();
                    return true; // Consume el evento de tecla Enter
                }
                return false; // Permite que otros eventos de teclas sean manejados normalmente
            }
        });



        // Configuración del evento de clic para el enlace de "Crear cuenta"
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(MainLoginActivity.this, LoginCreacionActivity.class);
                startActivity(createAccountIntent);
            }
        });

        // Configuración del evento de clic para el enlace de "Solicitud"
        tvSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent solicitudIntent = new Intent(MainLoginActivity.this, LoginSolicitudActivity.class);
                startActivity(solicitudIntent);
            }
        });

    }

    /**
     * Método para mostrar el cuadro de diálogo de recuperación de contraseña.
     * @param v Vista que desencadenó el evento de clic.
     */
    public void showDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Obtener el email
        String email = etEmail.getText().toString();

        // Check si el campo de email está vacio
        if (email.isEmpty()) {
            // Si lo está, se le envía un mensaje al usuario para recordarle que debe rellenar el campo.
            builder.setMessage("Por favor, introduzca su correo electrónico");
        } else {
            // Si está correcto, se le pregunta si quiere recibir el correo de restablecimiento
            builder.setMessage("¿Quiere recibir un correo electrónico para restablecer su contraseña?");
        }

        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Si el email está vacío, no hacer nada
                if (email.isEmpty()) {
                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(MainLoginActivity.this);
                String url = "https://trabajo-final-grupo-11.azurewebsites.net/forgotPassword";  // Using HTTP instead of HTTPS

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Manejar respuesta correcta del server
                                Toast.makeText(MainLoginActivity.this, "¡El correo electrónico de restablecimiento de contraseña se envió con éxito!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Manejar respuesta incorrecta
                                Toast.makeText(MainLoginActivity.this, "Error al enviar el correo electrónico de restablecimiento de contraseña", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                // Agregar la solicitud a la cola de solicitudes/RequestQueue
                queue.add(jsonObjectRequest);
            }
        });
        builder.create().show();
            }
    }


