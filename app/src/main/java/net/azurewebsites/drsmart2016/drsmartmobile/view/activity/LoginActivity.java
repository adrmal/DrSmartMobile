package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonSyntaxException;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button button;
    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utils = new ActivityUtils(this);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeUiFields();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLoginEmpty() || isPasswordEmpty()) {
                    setLoginErrorIfNeeded();
                    setPasswordErrorIfNeeded();
                }
                else if(!hasInternetPermission()) {
                    requestPermission();
                }
                else {
                    String username = editTextLogin.getText().toString();
                    String password = editTextPassword.getText().toString();
                    RESTClient.getClient().loginUser(username, password, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            utils.showToast(R.string.connectionError);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                if(response.code() == 200) {
                                    String json = response.body().string();
                                    Token token = utils.mapJsonToObject(json, Token.class);
                                    utils.saveTokenToSharedPreferences(token);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                if(response.code() == 400) {
                                    utils.showToast(R.string.incorrectLoginOrPassword);
                                }
                            }
                            catch(JsonSyntaxException e) {
                                utils.showToast(R.string.webServiceError);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initializeUiFields() {
        editTextLogin = (EditText) findViewById(R.id.login);
        editTextPassword = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);
    }

    private void setLoginErrorIfNeeded() {
        if(isLoginEmpty()) {
            String error = getString(R.string.loginEmpty);
            editTextLogin.setError(error);
        }
    }

    private void setPasswordErrorIfNeeded() {
        if(isPasswordEmpty()) {
            String error = getString(R.string.passwordEmpty);
            editTextPassword.setError(error);
        }
    }

    private boolean isLoginEmpty() {
        return editTextLogin.getText().toString().isEmpty();
    }

    private boolean isPasswordEmpty() {
        return editTextPassword.getText().toString().isEmpty();
    }

    private boolean hasInternetPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{ Manifest.permission.INTERNET }, 0);
    }

}
