package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Record;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RecordDetailsActivity extends AppCompatActivity {

    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        setTitle(R.string.recordDetails);

        if(getIntent() != null) {
            record = (Record) getIntent().getSerializableExtra(MedicalHistoryActivity.RECORD_KEY);
        }

        setRecordUiFields();
    }

    private void setRecordUiFields() {
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(getDateText());
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

    private String getDateText() {
        if(record.getDate() == null) {
            return getString(R.string.recordEmpty);
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(record.getDate());

        String text;
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            text = "0" + calendar.get(Calendar.DAY_OF_MONTH) + ".";
        }
        else {
            text = calendar.get(Calendar.DAY_OF_MONTH) + ".";
        }
        if(calendar.get(Calendar.MONTH)  < 10) {
            text = text + "0" + (calendar.get(Calendar.MONTH) + 1) + ".";
        }
        else {
            text = text + (calendar.get(Calendar.MONTH) + 1) + ".";
        }
        text = text + calendar.get(Calendar.YEAR) + " r.";

        return text;
    }

}
