package net.azurewebsites.drsmart2016.drsmartmobile.rest;

import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RESTClient {

    private static RESTClient restClient;
    private OkHttpClient okHttpClient;
    private static final String TOKEN_URL = "http://www.drsmart.pl/token";
    private static final String BASE_URL = "http://www.drsmart.pl/api/";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
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
                .url(TOKEN_URL)
                .post(RequestBody.create(APPLICATION_JSON, requestBody))
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public void getUserDetails(Token token, Callback callback) {
        webServiceGETMethod(token, callback, "UserInfoes");
    }

    public void getMedicalHistory(Token token, Callback callback) {
        webServiceGETMethod(token, callback, "getHistoryDetails");
    }

    public void getAllVisits(Token token, Callback callback) {
        webServiceGETMethod(token, callback, "PatientVisitDetails");
    }

    public void getFutureVisits(Token token, Callback callback) {
        webServiceGETMethod(token, callback, "FutureVisitsDetails");
    }

    public void getPastVisits(Token token, Callback callback) {
        webServiceGETMethod(token, callback, "PatientVisitClosedDetails");
    }

    public void getAllSpecialties(Token token, Callback callback) {
        webServiceGETMethod(token, callback, "specialitiesList");
    }

    public void getDoctorsBySpecialtyId(String specialtyId, Token token, Callback callback) {
        webServiceGETMethod(token, callback, "specialityUsers?specialityId=" + specialtyId);
    }

    public void registerVisit(String visitAsJson, Token token, Callback callback) {
        webServicePOSTMethod(token, callback, "addVisit", visitAsJson);
    }

    private void webServiceGETMethod(Token token, Callback callback, String requestUrl) {
        String headerName = AUTHORIZATION;
        String headerBody = BEARER + " " + token.getAccessToken();

        Request request = new Request.Builder()
                .url(BASE_URL + requestUrl)
                .header(headerName, headerBody)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    private void webServicePOSTMethod(Token token, Callback callback, String requestUrl, String requestBody) {
        String headerName = AUTHORIZATION;
        String headerBody = BEARER + " " + token.getAccessToken();

        Request request = new Request.Builder()
                .url(BASE_URL + requestUrl)
                .header(headerName, headerBody)
                .post(RequestBody.create(APPLICATION_JSON, requestBody))
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

}
