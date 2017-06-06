package net.azurewebsites.drsmart2016.drsmartmobile.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class JsonTool {

    public static Object fromJson(String json, Class clazz) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .registerTypeAdapter(Gender.class, new GenderDeserializer())
                    .create();
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private static class GenderDeserializer implements JsonDeserializer<Gender> {
        @Override
        public Gender deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if(json.getAsString().equals("M")) {
                return Gender.MALE;
            }
            return Gender.FEMALE;
        }
    }

}
