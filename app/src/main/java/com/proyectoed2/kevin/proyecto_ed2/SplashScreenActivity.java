package com.proyectoed2.kevin.proyecto_ed2;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

public class SplashScreenActivity extends Activity {

    CircularFillableLoaders imagen;
    TextView Titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Titulo = (TextView) findViewById(R.id.titulo);
        imagen = (CircularFillableLoaders) findViewById(R.id.logoImagen);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animacion);

        Titulo.setAnimation(animation);

        new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)), 3000);
    }
}
