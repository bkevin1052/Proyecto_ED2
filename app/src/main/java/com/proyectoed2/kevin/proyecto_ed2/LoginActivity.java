package com.proyectoed2.kevin.proyecto_ed2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.utils.Constants;
import com.proyectoed2.kevin.proyecto_ed2.utils.Validacion;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class LoginActivity extends Activity {


    private TextView Registrarse;
    private Button IniciarSesion;

    private EditText userName;
    private EditText password;

    private TextInputLayout consejoUsername;
    private TextInputLayout consejoPassword;

    private CompositeSubscription mSubscriptions;
    private SharedPreferences mSharedPreferences;

    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSubscriptions = new CompositeSubscription();
        init();
        initSharedPreferences();


        Registrarse.setOnClickListener(view ->startActivity(new Intent(LoginActivity.this,RegistroActivity.class)));

        IniciarSesion.setOnClickListener(view ->logIn());

    }

    /**
     * Metodo para iniciar todos los objetos del layout
     */
    private void init(){
        Registrarse = (TextView)findViewById(R.id.lblRegistrar);
        IniciarSesion = (Button)findViewById(R.id.btnIniciarSesion);
        userName = (EditText)findViewById(R.id.txtUsuario);
        password = (EditText)findViewById(R.id.txtContrasenia);
        consejoPassword = (TextInputLayout)findViewById(R.id.titlePassword);
        consejoUsername = (TextInputLayout)findViewById(R.id.titleUsuario);
        mProgressbar = (ProgressBar)findViewById(R.id.progress);
        mProgressbar.setVisibility(View.INVISIBLE);
    }

    /**
     * Metodo para iniciar SharedPreferences
     */
    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    /**
     * Metodo para validar errores en el log In
     */
    private void logIn() {

        setError();

        String nombreUsuario = userName.getText().toString();
        String contrasenia = password.getText().toString();

        int err = 0;

        if (!Validacion.validateFields(nombreUsuario)) {

            err++;
            consejoUsername.setError("Nombre de usuario debe ser valido!");
        }

        if (!Validacion.validateFields(contrasenia)) {

            err++;
            consejoPassword.setError("Contraseña debe ser valida!");
        }

        if (err == 0) {

            inicioSesion(nombreUsuario,contrasenia);
            mProgressbar = (ProgressBar) findViewById(R.id.progress);
            mProgressbar.setVisibility(View.VISIBLE);

        } else {

            showMessage("Ingrese datos validos!");
        }
    }

    /**
     * Metodo para el proceso de inicio de sesion
     */
    private void inicioSesion(String userName, String password) {

        mSubscriptions.add(BackendClient.getRetrofit(userName, password).logIn()
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
     * Metodo que permite manejar la respuesta
     */
    private void handleResponse(Response response) {

        mProgressbar.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.getToken());
        editor.putString(Constants.USERNAME,response.getMessage());
        editor.apply();

        userName.setText(null);
        password.setText(null);
        startActivity(new Intent(getApplicationContext(), ChatActivity.class));

    }

    private void setError() {

       consejoUsername.setError(null);
       consejoUsername.setError(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


}
