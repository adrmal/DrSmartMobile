package net.azurewebsites.drsmart2016.drsmartmobile.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Visit;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;

import java.util.List;

public class VisitArrayAdapter extends ArrayAdapter<Visit> {

    private ActivityUtils utils;

    public VisitArrayAdapter(Context context, int resource, List<Visit> objects) {
        super(context, resource, objects);

        utils = new ActivityUtils();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Visit visit = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_visits_element, parent, false);
        }
        setVisitUiFields(visit, convertView);

        return convertView;
    }

    private void setVisitUiFields(Visit visit, View recordView) {
        TextView doctor = (TextView) recordView.findViewById(R.id.doctor);
        doctor.setText(visit.getDoctorFullName());
        TextView date = (TextView) recordView.findViewById(R.id.date);
        date.setText(utils.getDateTimeText(visit.getDate()));
    }

}
