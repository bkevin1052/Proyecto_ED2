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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;

import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.MensajesAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.Network.NetworkCall;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;
import com.stfalcon.chatkit.messages.MessageInput;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MensajeActivity extends AppCompatActivity{

    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;
    RecyclerView RecyclerlistaMensajes;
    MensajesAdapter adapterMensajes;
    Mensaje mensaje_saliente;
    Chat nuevoChat = new Chat();
    MessageInput nuevoMensaje;
    boolean isFinished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        isFinished = false;
        mSubscriptions = new CompositeSubscription();
        init();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        RecyclerlistaMensajes.setLayoutManager(new LinearLayoutManager(this));

        //ENVIO DE MENSAJES
        nuevoMensaje.setInputListener(mensaje -> {
            mensaje_saliente = new Mensaje();
            mensaje_saliente.setMensaje(String.valueOf(mensaje));
            mensaje_saliente.setEmisor(mSharedPreferences.getString(Constants.USERNAME,null));
            mensaje_saliente.setReceptor(ChatActivity.receptor);
            CrearMensaje(mensaje_saliente);
            try {
                Thread.sleep(2000);
            }
            catch(Exception e)
                    {}
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

    /**
     * Metodo para crear mensajes
     */
    private void CrearMensaje(Mensaje mensaje) {
        mSubscriptions.add(BackendClient.getRetrofit().crearmensaje(mensaje)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse2,this::handleError));
    }

    private void handleError(Throwable error) {

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showMessage("Error de conexion !");
        }
        isFinished = true;
    }

    private void handleResponse2(Response response ) {
        nuevoChat.listaMensajes.add(mensaje_saliente);
        adapterMensajes = new MensajesAdapter(this,nuevoChat.listaMensajes);
        RecyclerlistaMensajes.setAdapter(adapterMensajes);
        isFinished = true;

    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
