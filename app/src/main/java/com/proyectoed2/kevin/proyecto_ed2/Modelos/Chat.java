package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import java.util.ArrayList;

public class Chat {
    private String contacto1;
    private String contacto2;
    private String llave;
    public ArrayList<Mensaje> listaMensajes;

    public Chat(){
            listaMensajes = new ArrayList<>();
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }


    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }
}
