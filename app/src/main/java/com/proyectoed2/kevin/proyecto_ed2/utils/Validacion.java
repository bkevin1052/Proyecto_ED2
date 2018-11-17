package com.proyectoed2.kevin.proyecto_ed2.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Validacion {

    public static boolean validateFields(String name){

        if (TextUtils.isEmpty(name)) {

            return false;

        } else {

            return true;
        }
    }

    public static boolean validateCorreo(String string) {

        if (TextUtils.isEmpty(string) || !Patterns.EMAIL_ADDRESS.matcher(string).matches()) {

            return false;

        } else {

            return  true;
        }
    }
}
