package com.proyectoed2.kevin.proyecto_ed2.Network;


import com.proyectoed2.kevin.proyecto_ed2.Modelos.Chat;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface NetworkCall {
    @POST("users")
    Observable<Response> registro(@Body Usuario user);//Ya implementado

    @POST("authenticate")
    Observable<Response>  logIn();//Ya implementado

    @GET("users/{userName}")
    Observable<Usuario> obtenerUsuario(@Path("userName") String userName);//Ya implementado

    @DELETE("users/delete/{userName}")
    Observable<Response> eliminarUsuario(@Path("userName") String userName);//Ya implementado

    @GET("users/get/allcontacts")
    Observable<List<Usuario>> obtenerContactos();//Ya implementado

    @GET("chats/get/{userName}")
    Observable<ArrayList<Chat>> obtenerChats(@Path("userName,Receptor")String userName);//No implementado

    @PUT("chats/put/{userName}")
    Observable<Usuario> agregarMensajesChats(@Path("userName,Receptor") String userName,@Body Usuario user);//No implementado

    @POST("chats/nuevochat/create")
    Observable<Response>  crearchat(@Body Chat chat);//Ya implementado
}
