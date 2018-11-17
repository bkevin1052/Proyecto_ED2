package com.proyectoed2.kevin.proyecto_ed2.Modelos;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
