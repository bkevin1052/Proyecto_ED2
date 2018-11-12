package com.proyectoed2.kevin.proyecto_ed2.Modelos;

public class Usuario {

    private String userName;
    private String contrasenia;
    private String Correo;
    private Mensaje ultimoMensaje;

    public Usuario(){

    }

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
}
