package com.proyectoed2.kevin.proyecto_ed2.Network;

import com.proyectoed2.kevin.proyecto_ed2.Modelos.UsuarioModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkCall {
    @GET("post")
    Call<UsuarioModel> getUsuarios();
}
