package net.azurewebsites.drsmart2016.drsmartmobile.rest;

import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.util.JsonTool;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RESTTest {

    private RESTClient client;

    @Before
    public void prepareTests() {
        client = RESTClient.getClient();
    }

    @Test
    public void loginUserTest() {
        client.loginUser("patient", "test1234", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("coś poszło nie tak (być może brak połączenia z Internetem)");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Token token = (Token) JsonTool.fromJson(json, Token.class);
                if(token.getAccessToken() == null) {
                    System.out.println("złe dane logowania");
                }
                else {
                    System.out.println(token.getAccessToken());
                }
            }
        });
    }

}
