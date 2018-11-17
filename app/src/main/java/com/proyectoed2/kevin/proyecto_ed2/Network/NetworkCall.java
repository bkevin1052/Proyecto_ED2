package com.proyectoed2.kevin.proyecto_ed2.Network;


import com.proyectoed2.kevin.proyecto_ed2.Modelos.Response;
import com.proyectoed2.kevin.proyecto_ed2.Modelos.Usuario;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface NetworkCall {
    @POST("users")
    Observable<Response> registro(@Body Usuario user);

    @POST("authenticate")
    Observable<Response> logIn();

    @GET("users/{userName}")
    Observable<Usuario> obtenerUsuario(@Path("userName") String userName);

    @GET("chats/{userName}")
    Observable<Usuario> obtenerChats(@Path("userName,Receptor")String userName);

    @PUT("chats/{userName}")
    Observable<Usuario> agregarMensajesChats(@Path("userName,Receptor") String userName,@Body Usuario user);

    @DELETE("users/{userName}")
    Observable<Response> eliminarUsuario(@Path("userName") String userName, @Body Usuario user);
}
