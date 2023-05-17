package com.example.trabajo_final_grupo_11_dam.Login;

import Gradients.BorderGradientDrawable;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.example.trabajo_final_grupo_11_dam.MenuActivity;
import com.example.trabajo_final_grupo_11_dam.MenuRepartidoresActivity;
import com.example.trabajo_final_grupo_11_dam.MenuRestaurantesActivity;
import com.example.trabajo_final_grupo_11_dam.R;

import org.json.JSONException;
import org.json.JSONObject;


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


       /* int borderColor1 = ContextCompat.getColor(this, R.color.border_color1);
        int borderColor2 = ContextCompat.getColor(this, R.color.border_color2);
        Resources resources = getResources();
        int strokeWidth = resources.getDimensionPixelSize(R.dimen.border_stroke_width);

        BorderGradientDrawable borderGradientDrawable = new BorderGradientDrawable(this, borderColor1, borderColor2, strokeWidth);
        Drawable[] layers = {borderGradientDrawable, btnIniciarSesion.getBackground()};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        btnIniciarSesion.setBackground(layerDrawable);*/


        //admin and admin

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().equals("cliente") && etContrasena.getText().toString().equals("cliente")){
                    //correct
                    Toast.makeText(MainLoginActivity.this,"BIENVENIDO CREADOR A CLIENTES",Toast.LENGTH_SHORT).show();
                    Intent iniciarSesionCliente = new Intent(MainLoginActivity.this, MenuActivity.class);
                    startActivity(iniciarSesionCliente);
                }else if(etEmail.getText().toString().equals("repartidor") && etContrasena.getText().toString().equals("repartidor")) {
                    //correct
                    Toast.makeText(MainLoginActivity.this, "BIENVENIDO CREADOR A EMPLEADOS", Toast.LENGTH_SHORT).show();
                    Intent inciarSesionRepartidor = new Intent(MainLoginActivity.this, MenuRepartidoresActivity.class);
                    startActivity(inciarSesionRepartidor);
                }else if(etEmail.getText().toString().equals("restaurante") && etContrasena.getText().toString().equals("restaurante")) {
                    //correct
                    Toast.makeText(MainLoginActivity.this, "BIENVENIDO CREADOR A RESTAURANTES", Toast.LENGTH_SHORT).show();
                    Intent inciarSesionRestaurante = new Intent(MainLoginActivity.this, MenuRestaurantesActivity.class);
                    startActivity(inciarSesionRestaurante);
                }else
                    //incorrect
                    Toast.makeText(MainLoginActivity.this,"LOGIN FAILED !!!",Toast.LENGTH_SHORT).show();
            }
        });

        tvContrasenaOlvidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
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


