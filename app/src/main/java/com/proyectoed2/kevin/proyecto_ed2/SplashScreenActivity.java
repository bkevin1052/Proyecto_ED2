package com.proyectoed2.kevin.proyecto_ed2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SplashScreenActivity extends Activity {

    private CircularFillableLoaders imagen;
    private TextView Titulo;
    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;

    private String Token;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mSubscriptions = new CompositeSubscription();
        Titulo = (TextView) findViewById(R.id.titulo);
        imagen = (CircularFillableLoaders) findViewById(R.id.logoImagen);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animacion);
        Titulo.setAnimation(animation);
        initSharedPreferences();
    }

    /**
     * Metodo que permite verificar si el usuario ya se habia logeado
     */
    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Token = mSharedPreferences.getString(Constants.TOKEN,"");
        userName = mSharedPreferences.getString(Constants.USERNAME,"");
        if(Token!="" && userName != ""){
            loadProfile();
        }else{
            new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)), 3000);

        }
    }

    /**
     * Metodo que permite cargar el perfil del usuario
     */
    private void loadProfile() {

        mSubscriptions.add(BackendClient.getRetrofit(Token).obtenerUsuario(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    /**
     * Metodo que permite manejar la respuesta
     */
    private void handleResponse(Usuario user) {


        //SE OBTIENE LA INFORMACION DEL USUARIO;
        new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(),ChatActivity.class)), 3000);
        Toast.makeText(getApplicationContext(),"BIENVENIDO " + user.getUserName() .toUpperCase(),Toast.LENGTH_SHORT).show();
    }

    /**
     * Metodo que permite manejar el error
     */
    private void handleError(Throwable error) {

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
            showMessage("Error de conexion!");
        }
    }

    /**
     * Metodo que permite mostrar un mensaje
     */
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
