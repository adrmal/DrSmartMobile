package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Record;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;

public class RecordDetailsActivity extends AppCompatActivity {

    private Record record;
    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        setTitle(R.string.recordDetails);
        utils = new ActivityUtils(this);

        if(getIntent() != null) {
            record = (Record) getIntent().getSerializableExtra(MedicalHistoryActivity.RECORD_KEY);
        }

        setRecordUiFields();
    }

    private void setRecordUiFields() {
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(utils.getDateText(record.getDate()));
        TextView doctor = (TextView) findViewById(R.id.doctor);
        doctor.setText(getTextValue(record.getDoctorFullName()));
        TextView doctorSpecialty = (TextView) findViewById(R.id.doctorSpecialty);
        doctorSpecialty.setText(getTextValue(record.getDoctorSpecialtyName()));
        TextView diagnosis = (TextView) findViewById(R.id.diagnosis);
        diagnosis.setText(getTextValue(record.getDiagnosis()));
        TextView examinations = (TextView) findViewById(R.id.examinations);
        examinations.setText(getTextValue(record.getExaminations()));
        TextView recommendations = (TextView) findViewById(R.id.recommendations);
        recommendations.setText(getTextValue(record.getRecommendations()));
        TextView information = (TextView) findViewById(R.id.information);
        information.setText(getTextValue(record.getInformation()));
        TextView comments = (TextView) findViewById(R.id.comments);
        comments.setText(getTextValue(record.getComments()));
    }

    private String getTextValue(String value) {
        if(value == null) {
            return getString(R.string.recordEmpty);
        }
        return value;
    }

}
