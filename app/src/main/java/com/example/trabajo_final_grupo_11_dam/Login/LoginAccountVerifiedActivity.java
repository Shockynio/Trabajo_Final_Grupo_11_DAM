package com.example.trabajo_final_grupo_11_dam.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajo_final_grupo_11_dam.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Actividad para la verificación de cuenta de inicio de sesión.
 */
public class LoginAccountVerifiedActivity extends AppCompatActivity {

    /**
     * Método llamado cuando se crea la actividad.
     * @param savedInstanceState Los datos guardados de la actividad anteriormente destruida.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verificado);

        Uri data = this.getIntent().getData();
        String tokenValue = null;
        if (data != null) {
            tokenValue = data.getQueryParameter("token");
        }

        final String token = tokenValue;

        // Define the verifyAccountButton
        Button verifyAccountButton = findViewById(R.id.verifyAccountButton);

        verifyAccountButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Método llamado cuando se hace clic en el botón de verificación de cuenta.
             * @param v La vista que ha sido clicada.
             */
            @Override
            public void onClick(View v) {

                if (token == null || token.isEmpty()) {
                    Toast.makeText(LoginAccountVerifiedActivity.this, "Token is missing.", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpClient client = new OkHttpClient();

                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

                    // Use Gson to create JSON string
                    Gson gson = new Gson();
                    Map<String, String> params = new HashMap<>();
                    params.put("token", token);
                    String json = gson.toJson(params);

                    RequestBody body = RequestBody.create(mediaType, json);

                    Request request = new Request.Builder()
                            .url("https://trabajo-final-grupo-11.azurewebsites.net/verifyAccount")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        /**
                         * Método llamado cuando la llamada HTTP falla.
                         * @param call La llamada HTTP.
                         * @param e La excepción que causó el fallo.
                         */
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        /**
                         * Método llamado cuando se recibe una respuesta HTTP.
                         * @param call La llamada HTTP.
                         * @param response La respuesta HTTP recibida.
                         * @throws IOException Si ocurre un error al manejar la respuesta.
                         */
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    /**
                                     * Método ejecutado en el hilo de la interfaz de usuario para mostrar un mensaje de éxito.
                                     */
                                    public void run() {
                                        Toast.makeText(LoginAccountVerifiedActivity.this, "La cuenta ha sido verificada correctamente!", Toast.LENGTH_SHORT).show();


                                        Intent intent = new Intent(LoginAccountVerifiedActivity.this, MainLoginActivity.class);
                                        intent.putExtra("EXTRA_TOKEN", token);
                                        startActivity(intent);

                                        finish();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}

