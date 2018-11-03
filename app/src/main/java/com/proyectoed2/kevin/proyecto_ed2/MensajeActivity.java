package com.proyectoed2.kevin.proyecto_ed2;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;

public class MensajeActivity extends AppCompatActivity implements OnFABMenuSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        //Floating Button Mensaje, no borrar
        final FloatingActionButton fabMensaje = findViewById(R.id.btnMenuMensaje);
        final FABRevealMenu fabMenu = findViewById(R.id.fabMenuMensaje);
        fabMenu.setMenu(R.menu.menu_mensaje);
        fabMenu.bindAnchorView(fabMensaje);
        fabMenu.setOnFABMenuSelectedListener(this);


        //Acciones
    }

    @Override
    public void onMenuItemSelected(View view, int id) {
        if (id == R.id.menu_adjuntar) {
            Toast.makeText(getApplicationContext(), "Seleccione un documento, para enviar", Toast.LENGTH_SHORT).show();
            //Acciones a realizar
        } else if (id == R.id.menu_imagen) {
            Toast.makeText(getApplicationContext(), "Seleccione una imagen, para enviar", Toast.LENGTH_SHORT).show();
            //Acciones a realizar
        }
    }
}
