package com.proyectoed2.kevin.proyecto_ed2;

import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.MensajesAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.Network.NetworkCall;
import com.stfalcon.chatkit.messages.MessageInput;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajeActivity extends AppCompatActivity implements OnFABMenuSelectedListener {


    RecyclerView RecyclerlistaMensajes;
    MensajesAdapter adapterMensajes;
    Chat nuevoChat = new Chat();
    MessageInput nuevoMensaje;
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

        nuevoMensaje = (MessageInput)findViewById(R.id.nuevoMensaje);
        RecyclerlistaMensajes = (RecyclerView)findViewById(R.id.RecyclerListaMensajes);
        RecyclerlistaMensajes.setLayoutManager(new LinearLayoutManager(this));

        nuevoMensaje.setInputListener(mensaje -> {
            Mensaje mensaje_saliente = new Mensaje();
            mensaje_saliente.setMensaje(String.valueOf(mensaje));
            nuevoChat.listaMensajes.add(mensaje_saliente);
            adapterMensajes = new MensajesAdapter(this,nuevoChat.listaMensajes);
            RecyclerlistaMensajes.setAdapter(adapterMensajes);
            return true;
        });


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
