package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Usuario {
    private String userName;
    private String contrasenia;
    private String Correo;
    private String token;
    private ArrayList<Chat> listaChats;
    private ArrayList<Mensaje> listaMensajes;


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



    public ArrayList<Chat> getListaChats() {
        return listaChats;
    }

    public void setListaChats(ArrayList<Chat> listaChats) {
        this.listaChats = listaChats;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(ArrayList<Mensaje> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }
}
