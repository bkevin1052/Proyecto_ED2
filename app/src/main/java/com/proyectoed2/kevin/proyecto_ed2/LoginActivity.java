package com.proyectoed2.kevin.proyecto_ed2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class LoginActivity extends Activity {

    TextView Registrarse;
    Button IniciarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Registrarse = (TextView)findViewById(R.id.lblRegistrar);
        IniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);

        Registrarse.setOnClickListener(view ->startActivity(new Intent(LoginActivity.this,RegistroActivity.class)));

        IniciarSesion.setOnClickListener(view ->startActivity(new Intent(LoginActivity.this,ChatActivity.class)));

    }
}
