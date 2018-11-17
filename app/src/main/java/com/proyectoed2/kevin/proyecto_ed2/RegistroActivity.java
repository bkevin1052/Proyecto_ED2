package com.proyectoed2.kevin.proyecto_ed2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Mensaje;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.utils.Validacion;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RegistroActivity extends AppCompatActivity {


    private ProgressBar mProgressbar;
    private EditText userName;
    private EditText password;
    private EditText correo;
    private String mToken;
    private String mUserName;

    private CompositeSubscription mSubscriptions;

    TextView LogIn;
    Button CrearUsuario;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mSubscriptions = new CompositeSubscription();
        init();

        CrearUsuario.setOnClickListener(view -> {
            registrarse();
            startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
        });

        LogIn.setOnClickListener(view -> startActivity(new Intent(RegistroActivity.this, LoginActivity.class)));
    }


    private void init() {
        LogIn = (TextView) findViewById(R.id.lblIniciarSesion);
        CrearUsuario = (Button) findViewById(R.id.btnCrearCuenta);
        userName = (EditText) findViewById(R.id.txtUsuario);
        password = (EditText) findViewById(R.id.txtContrasenia);
        correo = (EditText) findViewById(R.id.txtCorreo);
        mProgressbar = (ProgressBar) findViewById(R.id.progress);
        mProgressbar.setVisibility(View.INVISIBLE);
        //prueba de registro
        userName.setText("bkevin10");
        correo.setText("bkevin1052@gmail.com");
        password.setText("pepito123");
    }

    private void registrarse() {

        setError();

        String nombreUsuario = userName.getText().toString();
        String email = correo.getText().toString();
        String contrasenia = password.getText().toString();

        int err = 0;

        if (!Validacion.validateCorreo(email)) {

            err++;
            Toast.makeText(getApplicationContext(), "Nombre de usaurio no debe ser vacia", Toast.LENGTH_SHORT);
        }

        if (!Validacion.validateFields(nombreUsuario)) {

            err++;
            Toast.makeText(getApplicationContext(), "Correo debe ser valido", Toast.LENGTH_SHORT);
        }

        if (!Validacion.validateFields(contrasenia)) {

            err++;
            Toast.makeText(getApplicationContext(), "Contraseña no debe ser vacia", Toast.LENGTH_SHORT);
        }

        if (err == 0) {
            Usuario user = new Usuario();
            user.setUserName(nombreUsuario);
            //EN ESTA PARTE SE CIFRA LA CONTRASEÑA
            user.setCorreo(email);
            user.setContrasenia(contrasenia);
            mProgressbar.setVisibility(View.VISIBLE);
            Registro(user);
        } else {

            showMessage("Ingrese valores validos!");
        }
    }

        private void Registro(Usuario user){
            mSubscriptions.add(BackendClient.getRetrofit().registro(user)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(response->Response(response),error -> onError(error)));
        }



        private void showMessage (String message){
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        private void setError() {

            userName.setError(null);
            correo.setError(null);
            password.setError(null);
        }

        private void Response (Response response){

            mProgressbar.setVisibility(View.GONE);
            showMessage(response.getMessage());
        }

        private void onError (Throwable error){

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
    }

