package com.example.trabajo_final_grupo_11_dam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import java.util.Objects;


public class SplashActivity extends AppCompatActivity {

        private TextView tv_bienvenida;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            tv_bienvenida = findViewById(R.id.tv_bienvenida);

            //Quito el ActionBar
            Objects.requireNonNull(getSupportActionBar()).hide(
            );

            new TypewriterTask().execute(getString(R.string.splash_texto));
        }


        private class TypewriterTask extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... strings) {
                String message = strings[0];
                for (int i = 0; i < message.length(); i++) {
                    final int index = i;
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_bienvenida.setText(message.substring(0, index+1));
                            }
                        });
                        Thread.sleep(100); // Poner tiempo de espera entre la subida de cada caracter
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                super.onPostExecute(aVoid);
                //Crear intent para la activity a la que llamaremos
                Intent intent = new Intent(com.example.trabajo_final_grupo_11_dam.SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
}