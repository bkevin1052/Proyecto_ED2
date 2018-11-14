package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import java.util.ArrayList;

public class UsuarioModel {
    private boolean success;
    private ArrayList<Usuario> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Usuario> getData() {
        return data;
    }

    public void setData(ArrayList<Usuario> data) {
        this.data = data;
    }
}
