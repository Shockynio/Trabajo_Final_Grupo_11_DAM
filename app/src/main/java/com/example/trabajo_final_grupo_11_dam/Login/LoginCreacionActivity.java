package com.example.trabajo_final_grupo_11_dam.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabajo_final_grupo_11_dam.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginCreacionActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etBirthday;
    private Button   btnCreateAccount;
    private EditText etFullName;
    private EditText etRepeatPassword;
    private EditText etPhone;
    private EditText etEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_creacion);

        etUsername = findViewById(R.id.tv_username);
        etPassword = findViewById(R.id.tv_password);
        etBirthday = findViewById(R.id.tv_birthday);
        btnCreateAccount = findViewById(R.id.btn_create_account);
        etFullName = findViewById(R.id.tv_full_name);
        etRepeatPassword = findViewById(R.id.tv_repeat_password);
        etPhone = findViewById(R.id.tv_phone);
        etEmail = findViewById(R.id.tv_email);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        etFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = isValidName(etFullName.getText().toString().trim());
                    setBackgroundColorBasedOnValidation(etFullName, isValid);
                    if (!isValid) {
                        etFullName.setError("Ingrese su nombre completo (nombre y dos apellidos)");
                    } else {
                        etFullName.setError(null);
                    }
                }
            }
        });
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setBackgroundColorBasedOnValidation(etUsername, !etUsername.getText().toString().trim().isEmpty());
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = isValidPassword(etPassword.getText().toString().trim());
                    setBackgroundColorBasedOnValidation(etPassword, isValid);
                    if (!isValid) {
                        etPassword.setError("La contraseña debe tener al menos 6 caracteres y 3 números");
                    } else {
                        etPassword.setError(null);
                    }
                    if (!etRepeatPassword.getText().toString().trim().isEmpty()) {
                        boolean passwordsMatch = checkPasswordsMatch(etPassword, etRepeatPassword);
                        setBackgroundColorBasedOnValidation(etRepeatPassword, passwordsMatch);
                    }
                }
            }
        });


        etRepeatPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = checkPasswordsMatch(etPassword, etRepeatPassword);
                    setBackgroundColorBasedOnValidation(etPassword, isValid);
                    setBackgroundColorBasedOnValidation(etRepeatPassword, isValid);
                }
            }
        });


        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = isValidSpanishMobileNumber(etPhone.getText().toString().trim());
                    setBackgroundColorBasedOnValidation(etPhone, isValid);
                    if (!isValid) {
                        etPhone.setError("Ingrese un número de teléfono válido");
                    } else {
                        etPhone.setError(null);
                    }
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = isValidEmail(etEmail.getText().toString().trim());
                    setBackgroundColorBasedOnValidation(etEmail, isValid);
                    if (!isValid) {
                        etEmail.setError("Ingrese un correo electrónico válido");
                    } else {
                        etEmail.setError(null);
                    }
                }
            }
        });
    }


    private void createAccount() {
        if (areAllFieldsValid()) {
            final String username = etUsername.getText().toString().trim(); // Get the input from the username field
            final String email = etEmail.getText().toString().trim(); // Get the input from the email field

            Util.Metodos metodos = new Util.Metodos(); // Create an instance of the Metodos class

            metodos.isUsernameTaken(LoginCreacionActivity.this, username, new VolleyCallback() {
                @Override
                public void onSuccess(boolean isTaken) {
                    if (!isTaken) {
                        metodos.isEmailTaken(LoginCreacionActivity.this, email, new VolleyCallback() {
                            @Override
                            public void onSuccess(boolean isTaken) {
                                if (!isTaken) {
                                    // Proceed with account creation
                                    createAccountRequest(username, email);
                                } else {
                                    Toast.makeText(getApplicationContext(), "El correo electrónico ya está en uso.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "El nombre de usuario ya está en uso.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Por favor, corrige los campos inválidos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccountRequest(String username, String email) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://trabajo-final-grupo-11.azurewebsites.net/createAccount";

        // Create a JSONObject to send as request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("Nombre_Completo", etFullName.getText().toString().trim());
            requestBody.put("Username", username);
            requestBody.put("Password", etPassword.getText().toString().trim());
            requestBody.put("Nacimiento", etBirthday.getText().toString().trim());
            requestBody.put("Telefono", etPhone.getText().toString().trim());
            requestBody.put("Email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request a JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    // Handle response here
                    Toast.makeText(getApplicationContext(), "La cuenta se ha creado correctamente!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle error here
                    Toast.makeText(getApplicationContext(), "Ha habido un error creando la cuenta.", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }


        private void showDatePickerDialog() {
            // Current date en el default
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Crear un nuevo date picker y enseñarlo
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            // Updatea la fecha en el TextDialog
                            etBirthday.setText(String.format("%d/%02d/%02d", year, month+1, dayOfMonth));
                        }
                    },
                    year, month, day
            );
            datePickerDialog.show();
        }







         //Comprobación de errores en el formulario a través de métodos

        private boolean checkPasswordsMatch(EditText passwordEditText, EditText repeatPasswordEditText) {
            String password = passwordEditText.getText().toString().trim();
            String repeatPassword = repeatPasswordEditText.getText().toString().trim();

            if (!password.equals(repeatPassword)) {
                repeatPasswordEditText.setError("Las contraseñas no coinciden");
                return false;
            } else {
                repeatPasswordEditText.setError(null);
                return true;
            }
        }

        private boolean isValidSpanishMobileNumber(String mobileNumber) {
            String mobileRegex = "^[6|7|9]\\d{8}$";
            return mobileNumber.matches(mobileRegex);
        }

        private boolean isValidName(String name) {
            String nameRegex = "^[A-Za-zÁ-Úá-úñÑ ]{2,}(\\s[A-Za-zÁ-Úá-úñÑ ]{2,}){2}$";
            return name.matches(nameRegex);
        }

        private boolean isValidEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
            return email.matches(emailRegex);
        }
        private boolean isValidPassword(String password) {
            String passwordRegex = "^(?=.*[0-9].*[0-9].*[0-9])(?=.*[a-zA-Z]).{8,}$";
            return password.matches(passwordRegex);
        }

        private void setBackgroundColorBasedOnValidation(EditText editText, boolean isValid) {
            if (isValid) {
                editText.setBackgroundColor(Color.WHITE);
            } else {
                editText.setBackgroundColor(Color.RED);
            }
        }

        private boolean areAllFieldsValid() {
            boolean usernameValid = !etUsername.getText().toString().trim().isEmpty();
            boolean passwordValid = etPassword.getText().toString().trim().length() >= 8; // Mínimo 8 carácteres de password
            boolean passwordsMatch = checkPasswordsMatch(etPassword, etRepeatPassword);
            boolean fullNameValid = isValidName(etFullName.getText().toString().trim());
            boolean phoneValid = isValidSpanishMobileNumber(etPhone.getText().toString().trim());
            boolean emailValid = isValidEmail(etEmail.getText().toString().trim());

            return usernameValid && passwordValid && passwordsMatch && fullNameValid && phoneValid && emailValid;
        }

    public interface VolleyCallback {
        void onSuccess(boolean isTaken);
    }


}

