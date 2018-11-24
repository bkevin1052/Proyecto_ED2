package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mylibrary.SDES;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ChatAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;

import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.MensajesAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;
import com.stfalcon.chatkit.messages.MessageInput;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MensajeActivity extends AppCompatActivity{

    int[] P10,P8,IP,EP,P4;
    String llave;
    RecyclerView RecyclerChats;
    ArrayList<Chat> listaChats = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;
    RecyclerView RecyclerlistaMensajes;
    MensajesAdapter adapterMensajes;
    Mensaje mensaje_saliente;
    Chat nuevoChat = new Chat();
    MessageInput nuevoMensaje;
    boolean isFinished;
    RecyclerView RecyclerMensajes;
    ArrayList<Mensaje> listaMensajes = new ArrayList<>();
    List<String> listaParametros = new ArrayList<>();

    String Token,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        P10 = new int[10];
        P8=new int[8];
        IP = new int[8];
        EP = new int[8];
        P4 = new int[4];
        lecturaPermutaciones();
        isFinished = false;
        mSubscriptions = new CompositeSubscription();
        init();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        RecyclerlistaMensajes.setLayoutManager(new LinearLayoutManager(this));

        //ENVIO DE MENSAJES
        nuevoMensaje.setInputListener(mensaje -> {
            mensaje_saliente = new Mensaje();

            String contraseniaCifrada = "";
            char[] caracter = String.valueOf(mensaje).toCharArray();
            SDES sdesCifrado;

            try {
                for (char c : caracter) {


                    sdesCifrado = new SDES((char) c, llave, P10, P8, IP, EP, P4);
                    contraseniaCifrada += sdesCifrado.encriptar();
                }
            }
            catch(Exception e)
            {
                String error = e.getMessage();
            }
            mensaje_saliente.setMensaje(contraseniaCifrada);
            mensaje_saliente.setEmisor(mSharedPreferences.getString(Constants.USERNAME,null));
            mensaje_saliente.setReceptor(SplashScreenActivity.receptor);
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Token = mSharedPreferences.getString(Constants.TOKEN,"");
            userName = mSharedPreferences.getString(Constants.USERNAME,"");
            loadProfile();
            CrearMensaje(mensaje_saliente);

            try {
                Thread.sleep(1000);
            }
            catch(Exception e)
                    {}
            return true;
        });
        Chat chat = new Chat();
        chat.setContacto1(SplashScreenActivity.usuario);
        chat.setContacto2(SplashScreenActivity.receptor);
        chat.setLlave("");


        nuevoMensaje.setAttachmentsListener(() -> {
            //ABRIR UNA VISTA PARA SELECCIONAR ARCHIVO
            Toast.makeText(getApplicationContext(),"Seleccione un archivo",Toast.LENGTH_SHORT).show();
        });


        listaParametros.add(chat.getContacto1());
        listaParametros.add(chat.getContacto2());
        obtenerMensajes(listaParametros);
        //adapterMensajes.setOnClickListener(view ->{
            //PARA SELECCIONAR CUALQUIER MENSAJE EN EL RECYCLER VIEW
        //});


        //Acciones
    }
    /**
     * Metodo para obtener contactos
     */
    private void obtenerChats(String userName) {
        mSubscriptions.add(BackendClient.getRetrofit().obtenerChats(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse2,this::handleError));
    }

    private void handleResponse2(List<Chat> response) {
        listaChats.clear();
        for(int i = 0; i < response.size();i++){
            listaChats.add(response.get(i));
        }
    }

    private void lecturaPermutaciones() {

        try {
            InputStream in = this.getResources().openRawResource(R.raw.config);
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String linea;
            String[] permutaciones = new String[2];
            if (in != null) {
                while ((linea = rd.readLine()) != null) {
                    permutaciones = linea.split("\\|");
                }
                for (int i = 0; i < permutaciones[0].length(); i++) {
                    P10[i] = Integer.parseInt(String.valueOf(permutaciones[0].charAt(i)));
                }
                for (int i = 0; i < permutaciones[1].length(); i++) {
                    P8[i] = Integer.parseInt(String.valueOf(permutaciones[1].charAt(i)));
                }
                for (int i = 0; i < permutaciones[2].length(); i++) {
                    IP[i] = Integer.parseInt(String.valueOf(permutaciones[2].charAt(i)));
                }
                for (int i = 0; i < permutaciones[3].length(); i++) {
                    EP[i] = Integer.parseInt(String.valueOf(permutaciones[3].charAt(i)));
                }
                for (int i = 0; i < permutaciones[4].length(); i++) {
                    P4[i] = Integer.parseInt(String.valueOf(permutaciones[4].charAt(i)));
                }
            }
        }
        catch (Exception e)
        {
            String error = e.getMessage();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_mensaje,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.settings:
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                Token = mSharedPreferences.getString(Constants.TOKEN,"");
                userName = mSharedPreferences.getString(Constants.USERNAME,"");
                loadProfile();
                //obtenerMensajes(listaParametros);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que permite cargar el perfil del usuario
     */
    private void loadProfile() {

        mSubscriptions.add(BackendClient.getRetrofit(Token).obtenerUsuario(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseToken,this::handleErrorToken));
    }

    /**
     * Metodo que permite manejar la respuesta
     */
    private void handleResponseToken(Usuario user) {
        Toast.makeText(getApplicationContext(),"Tu sesion aun es valida :D".toUpperCase(),Toast.LENGTH_SHORT).show();
    }

    /**
     * Metodo que permite manejar el error
     */
    private void handleErrorToken(Throwable error) {

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showMessage(response.getMessage());
                new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)), 3000);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)), 3000);
            }
            catch(Exception e)
            {
                showMessage("Error de conexion!");
            }
        }
    }

    /**
     * Metodo para obtener contactos
     */
    private void obtenerMensajes(List<String> listaParametros) {
        mSubscriptions.add(BackendClient.getRetrofit().obtenerMensajes(listaParametros)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse3,this::handleError));
    }

    private void handleResponse3(List<Chat> response) {
        listaMensajes.clear();
        for(int i = 0; i < response.get(0).listaMensajes.size();i++){

            listaMensajes.add(response.get(0).listaMensajes.get(i));
        }
        nuevoChat.listaMensajes = listaMensajes;
        llave = response.get(0).getLlave();
        adapterMensajes.llave = response.get(0).getLlave();
        RecyclerlistaMensajes.setLayoutManager(new LinearLayoutManager(this));
        adapterMensajes = new MensajesAdapter(this, listaMensajes);
        RecyclerlistaMensajes.setAdapter(adapterMensajes);
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

            //showMessage("Error de conexion !");
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
