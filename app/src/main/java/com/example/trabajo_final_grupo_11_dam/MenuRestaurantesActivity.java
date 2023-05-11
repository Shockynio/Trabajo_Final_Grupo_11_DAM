package com.example.trabajo_final_grupo_11_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuRestaurantesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_restaurantes);
    }

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
    }
}