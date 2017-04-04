package net.azurewebsites.drsmart2016.drsmartmobile.rest;

import net.azurewebsites.drsmart2016.drsmartmobile.model.MedicalHistory;
import net.azurewebsites.drsmart2016.drsmartmobile.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface RESTInterface {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    @GET("histories/{historyId}")
    Call<MedicalHistory> getMedicalHistory(@Path("historyId") String historyId);

}
