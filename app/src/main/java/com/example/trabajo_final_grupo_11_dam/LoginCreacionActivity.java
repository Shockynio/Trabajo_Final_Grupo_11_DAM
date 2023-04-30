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

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText birthdayEditText;
    private Button createAccountButton;
    private EditText fullNameEditText;
    private EditText repeatPasswordEditText;
    private EditText phoneEditText;
    private EditText emailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_creacion);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        birthdayEditText = findViewById(R.id.birthday);
        createAccountButton = findViewById(R.id.create_account_button);
        fullNameEditText = findViewById(R.id.full_name);
        repeatPasswordEditText = findViewById(R.id.repeat_password);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        birthdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        fullNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = isValidName(fullNameEditText.getText().toString().trim());
                    setBackgroundColorBasedOnValidation(fullNameEditText, isValid);
                    if (!isValid) {
                        fullNameEditText.setError("Ingrese su nombre completo (nombre y dos apellidos)");
                    } else {
                        fullNameEditText.setError(null);
                    }
                }
            }
        });
        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setBackgroundColorBasedOnValidation(usernameEditText, !usernameEditText.getText().toString().trim().isEmpty());
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = isValidPassword(passwordEditText.getText().toString().trim());
                    setBackgroundColorBasedOnValidation(passwordEditText, isValid);
                    if (!isValid) {
                        passwordEditText.setError("La contraseña debe tener al menos 6 caracteres y 3 números");
                    } else {
                        passwordEditText.setError(null);
                    }
                    if (!repeatPasswordEditText.getText().toString().trim().isEmpty()) {
                        boolean passwordsMatch = checkPasswordsMatch(passwordEditText, repeatPasswordEditText);
                        setBackgroundColorBasedOnValidation(repeatPasswordEditText, passwordsMatch);
                    }
                }
            }
        });


        repeatPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = checkPasswordsMatch(passwordEditText, repeatPasswordEditText);
                    setBackgroundColorBasedOnValidation(passwordEditText, isValid);
                    setBackgroundColorBasedOnValidation(repeatPasswordEditText, isValid);
                }
            }
        });


        phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = isValidSpanishMobileNumber(phoneEditText.getText().toString().trim());
                    setBackgroundColorBasedOnValidation(phoneEditText, isValid);
                    if (!isValid) {
                        phoneEditText.setError("Ingrese un número de teléfono válido");
                    } else {
                        phoneEditText.setError(null);
                    }
                }
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean isValid = isValidEmail(emailEditText.getText().toString().trim());
                    setBackgroundColorBasedOnValidation(emailEditText, isValid);
                    if (!isValid) {
                        emailEditText.setError("Ingrese un correo electrónico válido");
                    } else {
                        emailEditText.setError(null);
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
                            birthdayEditText.setText(String.format("%d/%02d/%02d", year, month+1, dayOfMonth));
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
            boolean usernameValid = !usernameEditText.getText().toString().trim().isEmpty();
            boolean passwordValid = passwordEditText.getText().toString().trim().length() >= 8; // Mínimo 8 carácteres de password
            boolean passwordsMatch = checkPasswordsMatch(passwordEditText, repeatPasswordEditText);
            boolean fullNameValid = isValidName(fullNameEditText.getText().toString().trim());
            boolean phoneValid = isValidSpanishMobileNumber(phoneEditText.getText().toString().trim());
            boolean emailValid = isValidEmail(emailEditText.getText().toString().trim());

            return usernameValid && passwordValid && passwordsMatch && fullNameValid && phoneValid && emailValid;
        }

    private boolean isUsernameTaken(String username) {
        // TODO: Remplazar este placeholder con la pregunta que le haremos al SQL cuando tengamos la database para saber si el usuario ya está cogido
        return false;
    }

}



