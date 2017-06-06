package net.azurewebsites.drsmart2016.drsmartmobile.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Record;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MedicalHistoryArrayAdapter extends ArrayAdapter<Record> {

    public MedicalHistoryArrayAdapter(Context context, int resource, List<Record> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_medical_history_element, parent, false);
        }
        setRecordUiFields(record, convertView);

        return convertView;
    }

    private void setRecordUiFields(Record record, View recordView) {
        TextView diagnosis = (TextView) recordView.findViewById(R.id.diagnosis);
        diagnosis.setText(record.getDiagnosis());
        TextView date = (TextView) recordView.findViewById(R.id.date);
        date.setText(getDateText(record.getDate()));
    }

    private String getDateText(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

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
