package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ContactosAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.MensajesAdapter;
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

public class ListaContactosActivity extends AppCompatActivity {


    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;
    RecyclerView RecyclerlistaContactos;
    ContactosAdapter adapterContactos= null;
    private ProgressBar mProgressbar;

    ArrayList<Usuario> listaContactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        init();
    }

    private void init(){
        mProgressbar = (ProgressBar)findViewById(R.id.progress);
        RecyclerlistaContactos = (RecyclerView)findViewById(R.id.RecyclerListaContactos);
        mSubscriptions = new CompositeSubscription();
        RecyclerlistaContactos.setLayoutManager(new LinearLayoutManager(this));
        obtenerContactos();
        mProgressbar.setVisibility(View.VISIBLE);


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
                obtenerContactos();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        mProgressbar.setVisibility(View.INVISIBLE);
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
    private void handleResponse(List<Usuario> response) {
        listaContactos.clear();
        for(int i = 0; i < response.size();i++){
            listaContactos.add(response.get(i));
        }
        RecyclerlistaContactos.setLayoutManager(new LinearLayoutManager(this));
        adapterContactos = new ContactosAdapter(this, listaContactos);
        RecyclerlistaContactos.setAdapter(adapterContactos);
        mProgressbar.setVisibility(View.INVISIBLE);

        adapterContactos.setOnClickListener(view ->{
            Usuario nombre = listaContactos.get(RecyclerlistaContactos.getChildAdapterPosition(view));//Obtiene la posicion del usuario
            Toast.makeText(getApplicationContext(),"Este contacto se llama " + nombre.getUserName() ,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MensajeActivity.class));
        });
    }
}
