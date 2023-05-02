package com.example.trabajo_final_grupo_11_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Objects;


public class LoginSolicitudActivity extends AppCompatActivity implements  View.OnClickListener {
    EditText etNombre;
    EditText etEmail;
    EditText etTelefono;
    EditText etDireccion;
    EditText etExperienciaRepartidor;
    EditText etMasInfo;
    EditText etNombreRestaurante;
    Spinner  spTipoDeComida;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_solicitud);


        etNombre = (EditText) findViewById(R.id.tv_nombre);
        etNombreRestaurante = (EditText) findViewById(R.id.tv_nombre_restaurante);
        etEmail = (EditText) findViewById(R.id.tv_email_solicitud);
        etTelefono = (EditText) findViewById(R.id.tv_telefono);
        etDireccion = (EditText) findViewById(R.id.tv_direccion);
        etExperienciaRepartidor = (EditText) findViewById(R.id.tv_experiencia_repartidor);
        etMasInfo = (EditText) findViewById(R.id.tv_mas_info);
        spTipoDeComida = findViewById(R.id.sp_tipo_comida);
        ImageView iconoRepartidor = findViewById(R.id.ic_repartidor);
        ImageView iconoRestaurante = findViewById(R.id.ic_restaurante);

        iconoRepartidor.setOnClickListener(this);
        iconoRestaurante.setOnClickListener(this);
        findViewById(R.id.btn_repartidor).setOnClickListener(this);
        findViewById(R.id.btn_restaurante).setOnClickListener(this);
        findViewById(R.id.btn_enviar).setOnClickListener(this);
        findViewById(R.id.btn_enviar).setVisibility(View.GONE);

        ImageView gif_business = findViewById(R.id.gif_business);

        Glide.with(this)
                .asGif()
                .load(R.drawable.openbusiness)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(gif_business);

        //Quito el ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Carga el array de tipos de comida desde el archivo de recursos
        String[] tiposDeComida = getResources().getStringArray(R.array.tipos_de_comida);

        // Crea el ArrayAdapter personalizado
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, tiposDeComida) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }

            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Devuelve falso si la posición es 0 (Tipo de comida), haciéndolo no seleccionable
            }
        };

        // Especificar el diseño a utilizar cuando aparezca la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establece el adaptador y configura el comportamiento del Spinner
        spTipoDeComida.setAdapter(adapter);
        spTipoDeComida.setSelection(0, false);
        spTipoDeComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.GRAY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        etNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String nombre = etNombre.getText().toString().trim();
                    if (!isValidName(nombre)) {
                        etNombre.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(LoginSolicitudActivity.this, "Nombre o apellidos inválidos", Toast.LENGTH_SHORT).show();
                    } else {
                        etNombre.getBackground().setColorFilter(null);
                    }
                }
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String email = etEmail.getText().toString().trim();
                    if (!isValidEmail(email)) {
                        etEmail.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(LoginSolicitudActivity.this, "Email inválido", Toast.LENGTH_SHORT).show();
                    } else {
                        etEmail.getBackground().setColorFilter(null);
                    }
                }
            }
        });

        etTelefono.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String telefono = etTelefono.getText().toString().trim();
                    if (!isValidSpanishMobileNumber(telefono)) {
                        etTelefono.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
                        Toast.makeText(LoginSolicitudActivity.this, "Número de teléfono inválido", Toast.LENGTH_SHORT).show();
                    } else {
                        etTelefono.getBackground().setColorFilter(null);
                    }
                }
            }
        });
    }

    // He refactorizado el sistema para que aparezcan los elementos si es repartidor/restaurante en vez de usar tantos ifs :)

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_repartidor:
            case R.id.btn_repartidor:
                mostrarFormulario(true);
                findViewById(R.id.btn_enviar).setVisibility(View.VISIBLE);
                break;
            case R.id.ic_restaurante:
            case R.id.btn_restaurante:
                mostrarFormulario(false);
                findViewById(R.id.btn_enviar).setVisibility(View.VISIBLE);
                break;
            case R.id.btn_enviar:
                break;
        }
    }
    private void mostrarFormulario(boolean esRepartidor) {
        // Ocultar los iconos de repartidor y restaurante
        findViewById(R.id.ic_repartidor).setVisibility(View.GONE);
        findViewById(R.id.ic_restaurante).setVisibility(View.GONE);

        // Mostrar elementos comunes
        etEmail.setVisibility(View.VISIBLE);
        etTelefono.setVisibility(View.VISIBLE);
        etDireccion.setVisibility(View.VISIBLE);
        etMasInfo.setVisibility(View.VISIBLE);
        findViewById(R.id.btn_enviar).setVisibility(View.VISIBLE);
        findViewById(R.id.gif_business).setVisibility(View.GONE);

        if (esRepartidor) {
            etNombre.setHint(getString(R.string.textNombreyApellidos));
            etNombre.setVisibility(View.VISIBLE);
            etExperienciaRepartidor.setVisibility(View.VISIBLE);

            etNombreRestaurante.setVisibility(View.GONE);
            spTipoDeComida.setVisibility(View.GONE);
        } else {
            etNombre.setHint(getString(R.string.textNombreyApellidosManager));
            etNombre.setVisibility(View.VISIBLE);
            etNombreRestaurante.setVisibility(View.VISIBLE);
            spTipoDeComida.setVisibility(View.VISIBLE);

            etExperienciaRepartidor.setVisibility(View.GONE);
        }
    }




         //Comprobación de errores en el formulario a través de métodos

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


            // Puede que lo necesitemos luego, por si hay que limpiar el array de información

            private void limpiar() {
                etNombre.setText("");
                etEmail.setText("");
                etTelefono.setText("");
                etDireccion.setText("");
                etExperienciaRepartidor.setText("");
                etMasInfo.setText("");
                spTipoDeComida.setSelection(0);
                etNombreRestaurante.setText("");
            }
}