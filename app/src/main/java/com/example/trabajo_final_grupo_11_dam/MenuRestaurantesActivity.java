package com.example.trabajo_final_grupo_11_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuRestaurantesActivity extends AppCompatActivity {

    private Button   btnverCarta;
    private Button   btnañadirCarta;
    private Button   btnpedidos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_restaurantes);

        btnañadirCarta =(Button) findViewById(R.id.btn_añadir_carta);
        btnverCarta = (Button)findViewById(R.id.btn_ver_carta);
        btnpedidos = (Button)findViewById(R.id.btn_pedidos);

        btnañadirCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.btn_añadir_carta:

                        Intent i = new Intent(MenuRestaurantesActivity.this, AnyadirCartaActivity.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.btn_ver_carta:

                        findViewById(R.id.btn_enviar).setVisibility(View.VISIBLE);
                        break;
                    case R.id.btn_pedidos:
                        break;
                }
            }

        });
/*
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.btn_menurestaurante_carta) {
            Toast.makeText(this, "boton1", Toast.LENGTH_LONG).show();

        }else if (id==R.id.btn_menurestaurante_añadirCarta){
            Toast.makeText(this, "boton2", Toast.LENGTH_LONG).show();
        }else  if (id==R.id.btn_menurestaurante_pedidos){
            Toast.makeText(this, "boton3", Toast.LENGTH_LONG).show();
        }else if (id==R.id.btn_menurestaurante_cerrarsesion){
            Toast.makeText(this, "boton4", Toast.LENGTH_LONG).show();
        }

        return  super.onOptionsItemSelected(item);
    }*/
}

}