package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.azurewebsites.drsmart2016.drsmartmobile.R;

public class RecordDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        setTitle(R.string.recordDetails);
    }

}
