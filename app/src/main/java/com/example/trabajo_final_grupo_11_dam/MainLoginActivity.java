package com.example.trabajo_final_grupo_11_dam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainLoginActivity extends AppCompatActivity {

    private Button btnCreateAccount;
    private Button btnSolicitud;
    private Button btnIniciarSesion;
    private TextView tvContraseñaOlvidade;
    private EditText etEmail;
    private EditText etContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etEmail = (EditText) findViewById(R.id.et_email);
        EditText etContraseña = (EditText) findViewById(R.id.et_contraseña);

        btnCreateAccount = findViewById(R.id.btn_crearCuenta);
        btnSolicitud = findViewById(R.id.btn_solicitud);
        btnIniciarSesion = findViewById(R.id.btn_iniciar);

        //admin and admin

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().equals("cliente") && etContraseña.getText().toString().equals("cliente")){
                    //correct
                    Toast.makeText(MainLoginActivity.this,"BIENVENIDO CREADOR A CLIENTES",Toast.LENGTH_SHORT).show();
                    Intent iniciarSesionCliente = new Intent(MainLoginActivity.this, MenuClientesActivity.class);
                    startActivity(iniciarSesionCliente);
                }else if(etEmail.getText().toString().equals("repartidor") && etContraseña.getText().toString().equals("repartidor")) {
                    //correct
                    Toast.makeText(MainLoginActivity.this, "BIENVENIDO CREADOR A EMPLEADOS", Toast.LENGTH_SHORT).show();
                    Intent inciarSesionRepartidor = new Intent(MainLoginActivity.this, MenuRepartidoresActivity.class);
                    startActivity(inciarSesionRepartidor);
                }else if(etEmail.getText().toString().equals("restaurante") && etContraseña.getText().toString().equals("restaurante")) {
                    //correct
                    Toast.makeText(MainLoginActivity.this, "BIENVENIDO CREADOR A RESTAURANTES", Toast.LENGTH_SHORT).show();
                    Intent inciarSesionRestaurante = new Intent(MainLoginActivity.this, MenuRestaurantesActivity.class);
                    startActivity(inciarSesionRestaurante);
                }else
                    //incorrect
                    Toast.makeText(MainLoginActivity.this,"LOGIN FAILED !!!",Toast.LENGTH_SHORT).show();
                }
        });


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(MainLoginActivity.this, LoginCreacionActivity.class);
                startActivity(createAccountIntent);
            }
        });

        btnSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent solicitudIntent = new Intent(MainLoginActivity.this, LoginSolicitudActivity.class);
                startActivity(solicitudIntent);
            }
        });
    }
}


