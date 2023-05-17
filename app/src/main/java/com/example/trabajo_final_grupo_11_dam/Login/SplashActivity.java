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


public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView loadingGif = findViewById(R.id.gif_loading);
        ImageView tituloGif = findViewById(R.id.gif_titulo);

        Glide.with(this)
                .asGif()
                .load(R.drawable.logonaranjito)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(loadingGif);

        Glide.with(this)
                .asGif()
                .load(R.drawable.text)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(tituloGif);

        //Quito el ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainLoginActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}