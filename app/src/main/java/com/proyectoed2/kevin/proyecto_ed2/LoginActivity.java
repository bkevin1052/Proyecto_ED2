package com.proyectoed2.kevin.proyecto_ed2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ChatAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.ContactosAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Adaptadores.MensajesAdapter;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.Network.NetworkCall;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class LoginActivity extends Activity {

    TextView Registrarse;
    Button IniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        Registrarse.setOnClickListener(view ->startActivity(new Intent(LoginActivity.this,RegistroActivity.class)));

        IniciarSesion.setOnClickListener(view ->startActivity(new Intent(LoginActivity.this,ChatActivity.class)));

    }

    private void init(){
        Registrarse = (TextView)findViewById(R.id.lblRegistrar);
        IniciarSesion = (Button)findViewById(R.id.btnIniciarSesion);
    }



}
