package net.azurewebsites.drsmart2016.drsmartmobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.azurewebsites.drsmart2016.drsmartmobile.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button button;

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
                else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
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

}
