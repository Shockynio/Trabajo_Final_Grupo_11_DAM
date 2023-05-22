package com.example.trabajo_final_grupo_11_dam.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.trabajo_final_grupo_11_dam.R;

import android.os.Handler;

import java.util.Objects;

/**
 * Esta clase representa la actividad de inicio (splash) de la aplicación.
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private Handler handler;

    /**
     * Método que se ejecuta al crear la actividad.
     * Configura la vista y las animaciones, oculta el ActionBar y realiza el redireccionamiento a otra actividad después de un tiempo determinado.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView loadingGif = findViewById(R.id.gif_loading);
        ImageView tituloGif = findViewById(R.id.gif_titulo);

        // Carga y muestra la animación de carga en la vista loadingGif utilizando la biblioteca Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.vaciogif)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(loadingGif);

        // Carga y muestra la animación del título en la vista tituloGif utilizando la biblioteca Glide
      /*  Glide.with(this)
                .asGif()
                .load(R.drawable.titulo2)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(tituloGif);*/

        // Ocluta el ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Crea un nuevo objeto Handler para manejar el retraso y redireccionamiento a otra actividad
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Se crea un Intent para iniciar la actividad MainLoginActivity
                Intent i = new Intent(SplashActivity.this, MainLoginActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
