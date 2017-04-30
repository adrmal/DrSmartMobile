package net.azurewebsites.drsmart2016.drsmartmobile.rest;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RESTClient {

    private static RESTClient restClient;
    private OkHttpClient okHttpClient;
    private static final String BASE_URL = "http://drsmartmobile.azurewebsites.net/";
    private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");

    private RESTClient() {
        okHttpClient = new OkHttpClient();
    }

    public static RESTClient getClient() {
        if(restClient == null) {
            restClient = new RESTClient();
        }
        return restClient;
    }

    public void loginUser(String username, String password, Callback callback) {
        String requestBody = "username=" + username + "&password=" + password + "&grant_type=password";
        Request request = new Request.Builder()
                .url(BASE_URL + "token")
                .post(RequestBody.create(APPLICATION_JSON, requestBody))
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

}
