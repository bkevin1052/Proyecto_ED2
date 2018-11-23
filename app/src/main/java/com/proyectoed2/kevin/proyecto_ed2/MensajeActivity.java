package com.proyectoed2.kevin.proyecto_ed2;

import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;
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
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;
import com.stfalcon.chatkit.messages.MessageInput;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajeActivity extends AppCompatActivity{

    private SharedPreferences mSharedPreferences;
    RecyclerView RecyclerlistaMensajes;
    MensajesAdapter adapterMensajes;
    Chat nuevoChat = new Chat();
    MessageInput nuevoMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        init();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        RecyclerlistaMensajes.setLayoutManager(new LinearLayoutManager(this));

        //ENVIO DE MENSAJES
        nuevoMensaje.setInputListener(mensaje -> {
            Mensaje mensaje_saliente = new Mensaje();
            mensaje_saliente.setMensaje(String.valueOf(mensaje));
            mensaje_saliente.setEmisor(mSharedPreferences.getString(Constants.USERNAME,null));
            nuevoChat.listaMensajes.add(mensaje_saliente);
            adapterMensajes = new MensajesAdapter(this,nuevoChat.listaMensajes);
            RecyclerlistaMensajes.setAdapter(adapterMensajes);
            return true;
        });

        nuevoMensaje.setAttachmentsListener(() -> {
            //ABRIR UNA VISTA PARA SELECCIONAR ARCHIVO
            Toast.makeText(getApplicationContext(),"Seleccione un archivo",Toast.LENGTH_SHORT).show();
        });

        //adapterMensajes.setOnClickListener(view ->{
            //PARA SELECCIONAR CUALQUIER MENSAJE EN EL RECYCLER VIEW
        //});


        //Acciones
    }

    /**
     * Menu de inicializacion de objetos layout
     */
    private void init(){
        nuevoMensaje = (MessageInput)findViewById(R.id.nuevoMensaje);
        RecyclerlistaMensajes = (RecyclerView)findViewById(R.id.RecyclerListaMensajes);
    }

}
