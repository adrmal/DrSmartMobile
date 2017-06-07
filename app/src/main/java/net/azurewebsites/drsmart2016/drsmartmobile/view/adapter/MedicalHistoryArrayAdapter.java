package net.azurewebsites.drsmart2016.drsmartmobile.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Record;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;

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
        date.setText(ActivityUtils.with().getDateText(record.getDate()));
    }

}
