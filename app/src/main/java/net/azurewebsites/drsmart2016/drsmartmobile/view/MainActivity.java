package net.azurewebsites.drsmart2016.drsmartmobile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.azurewebsites.drsmart2016.drsmartmobile.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
