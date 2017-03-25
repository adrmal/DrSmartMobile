package net.azurewebsites.drsmart2016.drsmartmobile.rest;

import net.azurewebsites.drsmart2016.drsmartmobile.model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;

public class RESTClient {

    private static RESTClient restClient;
    private static RESTInterface restInterface;

    private RESTClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://drsmart2016.azurewebsites.net/api")
                .build();

        restInterface = retrofit.create(RESTInterface.class);
    }

    public static RESTClient getClient() {
        if(restClient == null) {
            restClient = new RESTClient();
        }
        return restClient;
    }

    public List<User> getUsers() throws IOException {
        return restInterface.getUsers().execute().body();
    }

    public User getUser(String userId) throws IOException {
        return restInterface.getUser(userId).execute().body();
    }

}
