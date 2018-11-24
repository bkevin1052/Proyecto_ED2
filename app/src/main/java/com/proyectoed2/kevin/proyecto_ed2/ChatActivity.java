package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ChatAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ContactosAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ChatActivity extends AppCompatActivity implements OnFABMenuSelectedListener {


    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    ChatAdapter chatAdapter = null;

    private ProgressBar mProgressbar;

    private String Token;
    private String userName;
    public static String receptor;

    RecyclerView RecyclerChats;
    ArrayList<Chat> listaChats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mSubscriptions = new CompositeSubscription();
        init();

        //Acciones con los chats "CHAT ADAPTER SET ON CLICK LISTERNER"

        //adapterMensajes.setOnClickListener(view->{
            //PARA SELECCIONAR CUALQUIER CHAT EN EL RECYCLER VIEW
       //});




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
                userName = mSharedPreferences.getString(Constants.USERNAME,"");
                obtenerChats(userName);
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

    private void init(){
        //Floating Button Chat, no borrar
        final FloatingActionButton fabChat = findViewById(R.id.btnMenuChat);
        final FABRevealMenu fabMenu = findViewById(R.id.fabMenuChat);
        mProgressbar = (ProgressBar)findViewById(R.id.progress);
        RecyclerChats = (RecyclerView)findViewById(R.id.RecyclerListaChats);

        fabMenu.setMenu(R.menu.menu_chat);
        fabMenu.bindAnchorView(fabChat);
        fabMenu.setOnFABMenuSelectedListener(this);
        obtenerChats(SplashScreenActivity.usuario);
    }

    private void logOut(){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.USERNAME,"");
        editor.putString(Constants.TOKEN,"");
        editor.apply();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public void onMenuItemSelected(View view, int id) {
        if (id == R.id.menu_nuevoChat) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Token = mSharedPreferences.getString(Constants.TOKEN,"");
            userName = mSharedPreferences.getString(Constants.USERNAME,"");
            loadProfile();
            startActivity(new Intent(getApplicationContext(),ListaContactosActivity.class));
            Toast.makeText(getApplicationContext(), "Seleccione un contacto para chatear", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.menu_cerrarSesion){
            logOut();
        }else if(id == R.id.menu_eliminarCuenta){
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            eliminarUsuario(mSharedPreferences.getString(Constants.USERNAME,""));
        }
    }

    private void eliminarUsuario(String userName) {

        mSubscriptions.add(BackendClient.getRetrofit().eliminarUsuario(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    /**
     * Metodo que permite manejar el error
     */
    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);

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
        RecyclerChats.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, listaChats);
        RecyclerChats.setAdapter(chatAdapter);
        mProgressbar.setVisibility(View.INVISIBLE);

        chatAdapter.setOnClickListener(view ->{

            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Token = mSharedPreferences.getString(Constants.TOKEN,"");
            userName = mSharedPreferences.getString(Constants.USERNAME,"");
            loadProfile();
            Chat chat = listaChats.get(RecyclerChats.getChildAdapterPosition(view));//Obtiene la posicion del usuario

            if(SplashScreenActivity.usuario.compareTo(chat.getContacto1())==0)
                receptor = chat.getContacto2();
            else
                receptor = chat.getContacto1();
            startActivity(new Intent(getApplicationContext(),MensajeActivity.class));
        });
    }

    /**
     * Metodo que permite manejar la respuesta
     */
    private void handleResponse(Response response) {

        mProgressbar.setVisibility(View.GONE);
        showMessage(response.getMessage());
        logOut();
    }


}
