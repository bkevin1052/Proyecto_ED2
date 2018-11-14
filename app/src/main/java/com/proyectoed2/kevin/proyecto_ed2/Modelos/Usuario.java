package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Usuario {
    @SerializedName("_id")
    private String id;
    private String userName;
    private String contrasenia;
    private String Correo;
    private Mensaje ultimoMensaje;
    private ArrayList<Chat> listaChats;
    @SerializedName("__v")


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

    public Mensaje getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(Mensaje ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Chat> getListaChats() {
        return listaChats;
    }

    public void setListaChats(ArrayList<Chat> listaChats) {
        this.listaChats = listaChats;
    }
}
