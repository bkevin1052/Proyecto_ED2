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
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.utils.Validacion;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RegistroActivity extends AppCompatActivity {


    private ProgressBar mProgressbar;
    private EditText userName;
    private EditText password;
    private EditText correo;
    Usuario user = new Usuario();

    TextView LogIn;
    Button CrearUsuario;

    private CompositeSubscription mSubscriptions;



    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        init();

        //Boton  para registrarse
        CrearUsuario.setOnClickListener(view -> registrarse());

        //Boton Log In
        LogIn.setOnClickListener(view -> startActivity(new Intent(RegistroActivity.this, LoginActivity.class)));
    }


    /**
     * Metodo para iniciar todos los objetos en el layout
     */
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

    /**
     * Metodo para validar errores en el registro
     */
    private void registrarse() {

        setError();

        String nombreUsuario = userName.getText().toString();
        String email = correo.getText().toString();
        String contrasenia = password.getText().toString();

        int err = 0;

        if (!Validacion.validateCorreo(email)) {

            err++;
            Toast.makeText(getApplicationContext(), "Nombre de usuario no debe ser vacia", Toast.LENGTH_SHORT);
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
            user.setUserName(nombreUsuario);
            //EN ESTA PARTE SE CIFRA LA CONTRASEÑA
            user.setCorreo(email);
            user.setContrasenia(contrasenia);
            mProgressbar.setVisibility(View.VISIBLE);
            mSubscriptions = new CompositeSubscription();
            Registro(user);
        } else {

            showMessage("Ingrese valores validos!");
        }

    }

    /**
     * Metodo que permite iniciar el proceso del registro en la api y base de datos
     */
    private void Registro(Usuario user) {

        mSubscriptions.add(BackendClient.getRetrofit().registro(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::Response,this::onError));
    }

    /**
     * Metodo que permite manejar la respuesta
     */
    private void Response(Response response) {

        mProgressbar.setVisibility(View.GONE);
        showMessage(response.getMessage());
    }

    /**
     * Metodo que permite manejar el error
     */
    private void onError(Throwable error) {

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

        private void showMessage (String message){
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        private void setError() {

            userName.setError(null);
            correo.setError(null);
            password.setError(null);
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}

