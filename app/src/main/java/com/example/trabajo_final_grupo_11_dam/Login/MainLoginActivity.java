package com.example.trabajo_final_grupo_11_dam.Login;

import Gradients.BorderGradientDrawable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import android.content.DialogInterface;
import android.content.Intent;

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


public class MainLoginActivity extends AppCompatActivity {

    private TextView tvCreateAccount;
    private TextView tvSolicitud;
    private Button   btnIniciarSesion;
    private TextView tvContrasenaOlvidade;
    private EditText etEmail;
    private EditText etContrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.et_email);
        etContrasena = findViewById(R.id.et_contraseña);

        tvSolicitud = findViewById(R.id.tv_Solicitud);
        btnIniciarSesion = findViewById(R.id.btn_iniciar);
        tvContrasenaOlvidade =  findViewById(R.id.tv_contraseña_olvidada);
        tvCreateAccount = findViewById(R.id.tv_crear_cuenta);




        //admin and admin

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etContrasena.getText().toString();

                Log.d("Login", "Email: " + email);
                Log.d("Login", "Password: " + password);

                RequestQueue queue = Volley.newRequestQueue(MainLoginActivity.this);

                String url = "https://trabajo-final-grupo-11.azurewebsites.net/login";

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("Email", email);
                    jsonBody.put("Password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Login", "Response: " + response.toString());
                                try {
                                    boolean success = response.getBoolean("success");
                                    if (success) {
                                        Log.d("Login", "Login successful");
                                        // Login was successful. Start the next activity.
                                        Intent iniciarSesionCliente = new Intent(MainLoginActivity.this, MenuActivity.class);
                                        startActivity(iniciarSesionCliente);
                                    } else {
                                        Log.d("Login", "Login failed");
                                        // Login failed. Show an error message.
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

                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
            }
        });








        tvContrasenaOlvidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });

             // FIX DEL BUG DEL ENTER DEL CAMPO DE CORREO
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
                    return true; // Consume the space key event
                }
                return false; // Let other key events be handled normally
            }
        });


        etContrasena.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    btnIniciarSesion.requestFocus();
                    return true; // Consume the Enter key event
                }
                return false; // Let other key events be handled normally
            }
        });




        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(MainLoginActivity.this, LoginCreacionActivity.class);
                startActivity(createAccountIntent);
            }
        });

        tvSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent solicitudIntent = new Intent(MainLoginActivity.this, LoginSolicitudActivity.class);
                startActivity(solicitudIntent);
            }
        });

    }

    public void showDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Pasamos el email
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

                // Add the request to the RequestQueue
                queue.add(jsonObjectRequest);
            }
        });
        builder.create().show();
            }
    }


