package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class Mensaje {

    private String nombre;
    private String mensaje;
    private File documento;
    private Image imagen;
    @SerializedName("_id")
    private String mId;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public File getDocumento() {
        return documento;
    }

    public void setDocumento(File documento) {
        this.documento = documento;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
