package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity {

    TextView LogIn;
    Button CrearUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        LogIn = (TextView)findViewById(R.id.lblIniciarSesion);
        CrearUsuario = (Button) findViewById(R.id.btnCrearCuenta);


        LogIn.setOnClickListener(view ->startActivity(new Intent(RegistroActivity.this,LoginActivity.class)));

        CrearUsuario.setOnClickListener(view ->startActivity(new Intent(RegistroActivity.this,LoginActivity.class)));
    }
}
