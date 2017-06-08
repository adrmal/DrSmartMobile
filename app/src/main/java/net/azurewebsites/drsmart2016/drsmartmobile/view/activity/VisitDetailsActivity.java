package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Visit;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;

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

        setVisitUiFields();
    }

    private void setVisitUiFields() {
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(ActivityUtils.with().getDateText(visit.getDate()));
        TextView doctor = (TextView) findViewById(R.id.doctor);
        doctor.setText(getTextValue(visit.getDoctorFullName()));
        TextView doctorSpecialty = (TextView) findViewById(R.id.doctorSpecialty);
        doctorSpecialty.setText(getTextValue(visit.getDoctorSpecialtyName()));
        TextView type = (TextView) findViewById(R.id.type);
        type.setText(getTextValue(visit.getType()));
        TextView status = (TextView) findViewById(R.id.status);
        status.setText(getTextValue(visit.getStatus()));
    }

    private String getTextValue(String value) {
        if(value == null) {
            return getString(R.string.recordEmpty);
        }
        return value;
    }

}
