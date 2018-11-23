package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ChatAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.Network.NetworkCall;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;

import java.io.IOException;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ChatActivity extends AppCompatActivity implements OnFABMenuSelectedListener {


    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;

    private ProgressBar mProgressbar;

    private String Token;
    private String userName;

    ChatAdapter chatAdapter;
    RecyclerView RecyclerChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mSubscriptions = new CompositeSubscription();
        init();

        //Acciones con los chats "CHAT ADAPTER SET ON CLICK LISTERNER"

        //chatAdapter.setOnClickListener(view->{
            //PARA SELECCIONAR CUALQUIER CHAT EN EL RECYCLER VIEW
       //});




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
//    private void obtenerChats() {
//        mSubscriptions.add(BackendClient.getRetrofit()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse2,this::handleError));
//    }

    private void handleResponse2(List<Chat> chats) {

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
