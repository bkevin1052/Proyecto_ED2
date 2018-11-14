package com.proyectoed2.kevin.proyecto_ed2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ChatAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ContactosAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.MensajesAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.UsuarioModel;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.Network.NetworkCall;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends Activity {

    private RecyclerView ChatsRecycler,ContactosRecycler,MensajesRecycler;
    TextView Registrarse;
    Button IniciarSesion;
    public static ChatAdapter ChatsAdapter;
    public static ContactosAdapter ContactosAdapter;
    public static MensajesAdapter MensajesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Registrarse = (TextView)findViewById(R.id.lblRegistrar);
        IniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);

        Registrarse.setOnClickListener(view ->startActivity(new Intent(LoginActivity.this,RegistroActivity.class)));

        IniciarSesion.setOnClickListener(view ->startActivity(new Intent(LoginActivity.this,ChatActivity.class)));

    }

    private void init() {
        ChatsRecycler = (RecyclerView) findViewById(R.id.RecyclerListaChats);
        LinearLayoutManager layoutManagerChats = new LinearLayoutManager(this);
        ChatsRecycler.setLayoutManager(layoutManagerChats );
        ChatsAdapter = new ChatAdapter(this, new ArrayList<>());
        ChatsRecycler.setAdapter(ChatsAdapter);

        ContactosRecycler = (RecyclerView)findViewById(R.id.RecyclerListaContactos);
        LinearLayoutManager layoutManagerContactos = new LinearLayoutManager(this);
        ContactosRecycler.setLayoutManager(layoutManagerContactos);
        ContactosAdapter = new ContactosAdapter(this, new ArrayList<>());
        ContactosRecycler.setAdapter(ContactosAdapter);

        MensajesRecycler = (RecyclerView)findViewById(R.id.RecyclerListaMensajes);
        LinearLayoutManager layoutManagerMensajes = new LinearLayoutManager(this);
        MensajesRecycler.setLayoutManager(layoutManagerMensajes);
        MensajesAdapter = new MensajesAdapter(this, new ArrayList<>());
        MensajesRecycler.setAdapter(MensajesAdapter);
        loadPost();
    }

    private void loadPost() {
        NetworkCall service = BackendClient.createRetrofitService(NetworkCall.class);
        Call<UsuarioModel> call = service.getUsuarios();
        call.enqueue(new UsuarioListCallback());
    }

    private void updatePost(ArrayList<Usuario> data){
        ChatsAdapter.addAll(data);
        ChatsAdapter.notifyDataSetChanged();
    }

    public class UsuarioListCallback implements Callback<UsuarioModel> {

        @Override
        public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
            if (response.body() != null) {
                if (response.body().isSuccess()) {
                    updatePost(response.body().getData());
                } else {
                    Toast.makeText(LoginActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Response Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<UsuarioModel> call, Throwable t) {

            t.printStackTrace();
        }
    }

}
