package com.example.trabajo_final_grupo_11_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;


import java.util.Calendar;

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

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etBirthday = findViewById(R.id.birthday);
        btnCreateAccount = findViewById(R.id.create_account_button);
        etFullName = findViewById(R.id.full_name);
        etRepeatPassword = findViewById(R.id.repeat_password);
        etPhone = findViewById(R.id.phone);
        etEmail = findViewById(R.id.email);


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
            // TODO: Logica de creación de cuentas irá aquí
        } else {
            Toast.makeText(this, "Por favor, corrige los campos inválidos.", Toast.LENGTH_SHORT).show();
        }

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

    private boolean isUsernameTaken(String username) {
        // TODO: Remplazar este placeholder con la pregunta que le haremos al SQL cuando tengamos la database para saber si el usuario ya está cogido
        return false;
    }

}



