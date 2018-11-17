package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Usuario {
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("contrasenia")
    @Expose
    private String contrasenia;
    @SerializedName("correo")
    @Expose
    private String Correo;
    @SerializedName("token")
    @Expose
    private String token;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
