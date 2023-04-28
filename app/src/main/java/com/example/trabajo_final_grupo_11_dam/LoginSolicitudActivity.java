package com.example.trabajo_final_grupo_11_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginSolicitudActivity extends AppCompatActivity implements  View.OnClickListener{
    EditText txtNombre,txtEmail,texttelefono,txtDireccion,txtExperienciaRepartidor,txtmasinfo,txtTipoDeComida,txtNombreRestaurante;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_solicitud);


        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtNombreRestaurante = (EditText) findViewById(R.id.txtNombreRestaurante);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        texttelefono = (EditText) findViewById(R.id.texttelefono);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
        txtExperienciaRepartidor = (EditText) findViewById(R.id.txtExperienciaRepartidor);
        txtmasinfo = (EditText) findViewById(R.id.txtmasinfo);
        txtTipoDeComida = (EditText) findViewById(R.id.txtTipoDeComida);

        findViewById(R.id.btRepartidor).setOnClickListener(this);
        findViewById(R.id.btRestaurante).setOnClickListener(this);
        findViewById(R.id.btEnviar).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btRepartidor:
                if (txtNombre.getVisibility() == View. INVISIBLE);{
                txtNombre.setVisibility(View.VISIBLE);
                txtNombreRestaurante.setVisibility(View.INVISIBLE);
                }

                if (txtEmail.getVisibility() == View. INVISIBLE);{
                txtEmail.setVisibility(View.VISIBLE);
                }

                if (texttelefono.getVisibility() == View. INVISIBLE);{
                texttelefono.setVisibility(View.VISIBLE);
                }

                if (txtDireccion.getVisibility() == View. INVISIBLE);{
                txtDireccion.setVisibility(View.VISIBLE);
                }

                if (txtExperienciaRepartidor.getVisibility() == View. INVISIBLE);{
                txtExperienciaRepartidor.setVisibility(View.VISIBLE);
                txtTipoDeComida.setVisibility(View.INVISIBLE);
                }

                if (txtmasinfo.getVisibility() == View. INVISIBLE);{
                txtmasinfo.setVisibility(View.VISIBLE);
                }
            break;
            case R.id.btRestaurante:
                if (txtNombreRestaurante.getVisibility() == View. INVISIBLE);{
                txtNombreRestaurante.setVisibility(View.VISIBLE);
                txtNombre.setVisibility(View.INVISIBLE);
                }

                if (txtEmail.getVisibility() == View. INVISIBLE);{
                txtEmail.setVisibility(View.VISIBLE);
                }

                if (texttelefono.getVisibility() == View. INVISIBLE);{
                texttelefono.setVisibility(View.VISIBLE);
                }

                if (txtDireccion.getVisibility() == View. INVISIBLE);{
                txtDireccion.setVisibility(View.VISIBLE);
                }

                if (txtmasinfo.getVisibility() == View. INVISIBLE);{
                txtmasinfo.setVisibility(View.VISIBLE);
                }

                if (txtTipoDeComida.getVisibility() == View. INVISIBLE);{
                txtTipoDeComida.setVisibility(View.VISIBLE);
                txtExperienciaRepartidor.setVisibility(View.INVISIBLE);
                }

                break;


        }

    }
}