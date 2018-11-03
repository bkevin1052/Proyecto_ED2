package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;

public class ChatActivity extends AppCompatActivity implements OnFABMenuSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //Floating Button Chat, no borrar
        final FloatingActionButton fabChat = findViewById(R.id.btnMenuChat);
        final FABRevealMenu fabMenu = findViewById(R.id.fabMenuChat);
        fabMenu.setMenu(R.menu.menu_chat);
        fabMenu.bindAnchorView(fabChat);
        fabMenu.setOnFABMenuSelectedListener(this);

        //Acciones


    }

    @Override
    public void onMenuItemSelected(View view, int id) {
        if (id == R.id.menu_nuevoChat) {
            Toast.makeText(getApplicationContext(), "Nuevo Chat Creado", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MensajeActivity.class));
            //Acciones a realizar
        }
    }

}
