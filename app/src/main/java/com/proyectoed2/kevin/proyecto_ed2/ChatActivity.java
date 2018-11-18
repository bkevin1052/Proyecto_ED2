package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.Network.NetworkCall;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;

import java.io.IOException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mSubscriptions = new CompositeSubscription();
        init();

        //Acciones con los chats "CHAT ADAPTER SET ON CLICK LISTERNER"


    }

    private void init(){
        //Floating Button Chat, no borrar
        final FloatingActionButton fabChat = findViewById(R.id.btnMenuChat);
        final FABRevealMenu fabMenu = findViewById(R.id.fabMenuChat);
        mProgressbar = (ProgressBar)findViewById(R.id.progress);
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
            Toast.makeText(getApplicationContext(), "Seleccione un contacto para chatear", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MensajeActivity.class));
            //Acciones a realizar
        }else if(id == R.id.menu_cerrarSesion){
            logOut();
        }else if(id == R.id.menu_eliminarCuenta){
            //Falta eliminar usuarios
        }
    }


}
