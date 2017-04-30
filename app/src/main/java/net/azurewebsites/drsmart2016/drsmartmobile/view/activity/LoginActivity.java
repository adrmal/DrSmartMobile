package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.util.JsonTool;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button button;
    public static final String TOKEN = "TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeFields();

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
                            showToast(R.string.connectionError);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Token token = parseJsonToToken(response.body().string());
                            if(token.getAccessToken() == null) {
                                showToast(R.string.incorrectLoginOrPassword);
                            }
                            else {
                                saveTokenToSharedPreferences(token);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
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

    private void initializeFields() {
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

    private void showToast(final int textResourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, textResourceId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Token parseJsonToToken(String json) {
        return (Token) JsonTool.fromJson(json, Token.class);
    }

    private void saveTokenToSharedPreferences(Token token) {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN, token.getAccessToken());
        editor.apply();
    }

}
