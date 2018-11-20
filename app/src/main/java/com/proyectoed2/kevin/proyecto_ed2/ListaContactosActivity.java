package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ContactosAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.MensajesAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ListaContactosActivity extends AppCompatActivity {


    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    RecyclerView RecyclerlistaContactos;
    ContactosAdapter adapterContactos;

    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        init();
        mSubscriptions = new CompositeSubscription();
        RecyclerlistaContactos.setLayoutManager(new LinearLayoutManager(this));

        obtenerContactos();
    }

    private void init(){
        mProgressbar = (ProgressBar)findViewById(R.id.progress);
        RecyclerlistaContactos = (RecyclerView)findViewById(R.id.RecyclerListaContactos);
        mProgressbar.setVisibility(View.VISIBLE);
    }

    /**
     * Metodo para obtener contactos
     */
    private void obtenerContactos() {

        mSubscriptions.add(BackendClient.getRetrofit().obtenerContactos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        mProgressbar.setVisibility(View.GONE);
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

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showMessage("Error de conexion !");
        }
    }

    /**
     * Metodo que permite manejar la respuesta
     */
    private void handleResponse(ArrayList<Usuario> response) {
       adapterContactos = new ContactosAdapter(this, response);
       RecyclerlistaContactos.setAdapter(adapterContactos);

    }
}
