package com.example.trabajo_final_grupo_11_dam;

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
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import Gradients.BorderGradientDrawable;

public class MainLoginActivity extends AppCompatActivity {

    private Button   btnCreateAccount;
    private Button   btnSolicitud;
    private Button   btnIniciarSesion;
    private TextView tvContrasenaOlvidade;
    private EditText etEmail;
    private EditText etContrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etEmail = (EditText) findViewById(R.id.et_email);
        EditText etContrasena = (EditText) findViewById(R.id.et_contraseña);

        btnCreateAccount = findViewById(R.id.btn_crear_cuenta);
        btnSolicitud = findViewById(R.id.btn_solicitud);
        btnIniciarSesion = findViewById(R.id.btn_iniciar);
        tvContrasenaOlvidade =  findViewById(R.id.tv_contraseña_olvidada);


        int borderColor1 = ContextCompat.getColor(this, R.color.border_color1);
        int borderColor2 = ContextCompat.getColor(this, R.color.border_color2);
        Resources resources = getResources();
        int strokeWidth = resources.getDimensionPixelSize(R.dimen.border_stroke_width);

        BorderGradientDrawable borderGradientDrawable = new BorderGradientDrawable(this, borderColor1, borderColor2, strokeWidth);
        Drawable[] layers = {borderGradientDrawable, btnIniciarSesion.getBackground()};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        btnIniciarSesion.setBackground(layerDrawable);


        //admin and admin

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().equals("cliente") && etContrasena.getText().toString().equals("cliente")){
                    //correct
                    Toast.makeText(MainLoginActivity.this,"BIENVENIDO CREADOR A CLIENTES",Toast.LENGTH_SHORT).show();
                    Intent iniciarSesionCliente = new Intent(MainLoginActivity.this, MenuClientesActivity.class);
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

    public void showDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quiere que le enviemos la contraseña a su email?");
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Enviar email
                Toast.makeText(MainLoginActivity.this, "EMAIL ENVIADO", Toast.LENGTH_SHORT).show();
            }
        });
       /* builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //nothing
            }
        });*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


