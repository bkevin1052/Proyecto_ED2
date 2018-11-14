package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Chat {
    private String nombre;
    private String ultimoMensaje;
    public ArrayList<Mensaje> listaMensajes;
    @SerializedName("_id")
    private String cId;

    public Chat(){
            listaMensajes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }


    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }
}
