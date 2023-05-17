package com.example.trabajo_final_grupo_11_dam.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.trabajo_final_grupo_11_dam.R;
import com.google.gson.Gson;

public class LoginContraseñaResetActivity extends AppCompatActivity {

    private EditText newPasswordEditText, confirmPasswordEditText;
    private Button resetPasswordButton;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_contrasena);

        newPasswordEditText = findViewById(R.id.new_password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        resetPasswordButton = findViewById(R.id.reset_password_button);

        Uri data = getIntent().getData();


        token = data.getQueryParameter("token");

        newPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newPassword = newPasswordEditText.getText().toString().trim();
                    String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                    if (!newPassword.equals(confirmPassword)) {
                        newPasswordEditText.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        confirmPasswordEditText.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(LoginContraseñaResetActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    } else if (!isValidPassword(newPassword)) {
                        newPasswordEditText.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        confirmPasswordEditText.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(LoginContraseñaResetActivity.this, "Contraseña inválida", Toast.LENGTH_SHORT).show();
                    } else {
                        newPasswordEditText.getBackground().setColorFilter(null);
                        confirmPasswordEditText.getBackground().setColorFilter(null);
                    }
                }
            }
        });

        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newPassword = newPasswordEditText.getText().toString().trim();
                    String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                    if (!newPassword.equals(confirmPassword)) {
                        newPasswordEditText.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        confirmPasswordEditText.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(LoginContraseñaResetActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    } else if (!isValidPassword(newPassword)) {
                        newPasswordEditText.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        confirmPasswordEditText.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(LoginContraseñaResetActivity.this, "Contraseña inválida", Toast.LENGTH_SHORT).show();
                    } else {
                        newPasswordEditText.getBackground().setColorFilter(null);
                        confirmPasswordEditText.getBackground().setColorFilter(null);
                    }
                }
            }
        });


        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(LoginContraseñaResetActivity.this, "Ambos campos deben rellenarse.", Toast.LENGTH_SHORT).show();
                } else if(token == null || token.isEmpty()) {
                    Toast.makeText(LoginContraseñaResetActivity.this, "Token is missing.", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(newPassword)) {
                    Toast.makeText(LoginContraseñaResetActivity.this, "La contraseña debe tener al menos 8 caracteres, 1 letra y 3 números.", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(LoginContraseñaResetActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpClient client = new OkHttpClient();

                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

                    // Use Gson to create JSON string
                    Gson gson = new Gson();
                    Map<String, String> params = new HashMap<>();
                    params.put("token", token);
                    params.put("newPassword", newPassword);
                    String json = gson.toJson(params);

                    RequestBody body = RequestBody.create(mediaType, json);

                    Request request = new Request.Builder()
                            .url("https://trabajo-final-grupo-11.azurewebsites.net/resetPassword")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginContraseñaResetActivity.this, "La contraseña se ha restablecido correctamente!", Toast.LENGTH_SHORT).show();

                                        // Devolvemos al usuario al menu de login principal
                                        Intent intent = new Intent(LoginContraseñaResetActivity.this, MainLoginActivity.class);
                                        startActivity(intent);
                                        finish();  // Cerramos la activity actual
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginContraseñaResetActivity.this, "Error al restablecer la contraseña", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

        private boolean isValidPassword(String password) {
            String passwordRegex = "^(?=.*[0-9].*[0-9].*[0-9])(?=.*[a-zA-Z]).{8,}$";
            return password.matches(passwordRegex);
        }

}

