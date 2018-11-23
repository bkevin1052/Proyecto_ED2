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

import com.example.mylibrary.SDES;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;
import com.proyectoed2.kevin.proyecto_ed2.Network.BackendClient;
import com.proyectoed2.kevin.proyecto_ed2.utils.Validacion;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RegistroActivity extends AppCompatActivity {


    private ProgressBar mProgressbar;
    private EditText userName;
    private EditText password;
    private EditText correo;
    int[] P10,P8,IP,EP,P4;

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
        P10 = new int[10];
        P8=new int[8];
        IP = new int[8];
        EP = new int[8];
        P4 = new int[4];

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
    }

    /**
     * Metodo para validar errores en el registro
     */
    private void registrarse() {

        setError();
        lecturaPermutaciones();
        String nombreUsuario = userName.getText().toString();
        String email = correo.getText().toString();
        String contrasenia = password.getText().toString();
        String contraseniaCifrada = "";
        String llave = "0001000101";

        char[] caracter = contrasenia.toCharArray();
        SDES sdesCifrado;
        try {
            for (char c : caracter) {
                sdesCifrado = new SDES((char) c, llave, P10, P8, IP, EP, P4);
                contraseniaCifrada += sdesCifrado.encriptar();
            }
        }
        catch(Exception e)
        {
            String error = e.getMessage();
        }
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
            user.setContrasenia(contraseniaCifrada);
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
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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

    private void lecturaPermutaciones(){
        try {
            InputStream in = this.getResources().openRawResource(R.raw.config);
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String linea;
            String[] permutaciones = new String[2];
            if (in != null) {
                while ((linea = rd.readLine()) != null) {
                    permutaciones = linea.split("\\|");
                }
                for (int i = 0; i < permutaciones[0].length(); i++) {
                    P10[i] = Integer.parseInt(String.valueOf(permutaciones[0].charAt(i)));
                }
                for (int i = 0; i < permutaciones[1].length(); i++) {
                    P8[i] = Integer.parseInt(String.valueOf(permutaciones[1].charAt(i)));
                }
                for (int i = 0; i < permutaciones[2].length(); i++) {
                    IP[i] = Integer.parseInt(String.valueOf(permutaciones[2].charAt(i)));
                }
                for (int i = 0; i < permutaciones[3].length(); i++) {
                    EP[i] = Integer.parseInt(String.valueOf(permutaciones[3].charAt(i)));
                }
                for (int i = 0; i < permutaciones[4].length(); i++) {
                    P4[i] = Integer.parseInt(String.valueOf(permutaciones[4].charAt(i)));
                }
            }
        }
        catch (Exception e)
        {
            String error = e.getMessage();
        }

    }
}

