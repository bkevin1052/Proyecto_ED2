package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Chat {
    private String Emisor;
    private String Receptor;
    public ArrayList<Mensaje> listaMensajes;

    public Chat(){
            listaMensajes = new ArrayList<>();
    }

    public String getEmisor() {
        return Emisor;
    }

    public void setEmisor(String emisor) {
        Emisor = emisor;
    }

    public String getReceptor() {
        return Receptor;
    }

    public void setReceptor(String receptor) {
        Receptor = receptor;
    }
}
