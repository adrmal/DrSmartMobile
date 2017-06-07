package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Visit;

public class VisitDetailsActivity extends AppCompatActivity {

    private Visit visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_details);
        setTitle(R.string.visitDetails);

        if(getIntent() != null) {
            visit = (Visit) getIntent().getSerializableExtra(VisitActivity.VISIT_KEY);
        }
    }
}
