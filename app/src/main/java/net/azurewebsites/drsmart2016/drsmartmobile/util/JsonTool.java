package net.azurewebsites.drsmart2016.drsmartmobile.util;

import com.google.gson.Gson;

public class JsonTool {

    public static Object fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
