package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RegisterVisitActivity extends AppCompatActivity {

    private Spinner spinnerSpecialty;
    private Spinner spinnerDoctor;
    private TextView textViewDate;
    private TextView textViewTime;
    private Spinner spinnerVisitType;
    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_visit);
        setTitle(R.string.registerVisit);
        utils = new ActivityUtils(this);

        initializeUiFields();
        disableUiFields();

        setSpinnerSpecialtyActions();
        setSpinnerDoctorActions();
        setTextViewDateActions();
        setTextViewTimeActions();
        setSpinnerVisitTypeActions();
    }

    private void initializeUiFields() {
        spinnerSpecialty = (Spinner) findViewById(R.id.specialty);
        spinnerDoctor = (Spinner) findViewById(R.id.doctor);
        textViewDate = (TextView) findViewById(R.id.date);
        textViewTime = (TextView) findViewById(R.id.time);
        spinnerVisitType = (Spinner) findViewById(R.id.visitType);
    }

    private void disableUiFields() {
        spinnerDoctor.setEnabled(false);
        textViewDate.setEnabled(false);
        textViewTime.setEnabled(false);
        spinnerVisitType.setEnabled(false);
    }

    private void setSpinnerSpecialtyActions() {
        List<String> specialties = new ArrayList<>();
        specialties.add("Chirurg");
        specialties.add("Ortopeda");
        specialties.add("Laryngolog");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_main_item, specialties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(adapter);
        spinnerSpecialty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerDoctor.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerDoctorActions() {
        List<String> doctors = new ArrayList<>();
        doctors.add("Jan Pelikan");
        doctors.add("Jan Kowalski");
        doctors.add("Kamil Legierski");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_main_item, doctors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);
        spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewDate.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setTextViewDateActions() {
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dialog = new DateDialog();
                dialog.show(getSupportFragmentManager(), "DateDialog");
            }
        });
    }

    private void setTextViewTimeActions() {
        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog dialog = new TimeDialog();
                dialog.show(getSupportFragmentManager(), "TimeDialog");
            }
        });
    }

    private void setSpinnerVisitTypeActions() {
        List<String> visitTypes = new ArrayList<>();
        visitTypes.add("Konsultacja");
        visitTypes.add("Badanie");
        visitTypes.add("Zabieg");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_main_item, visitTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVisitType.setAdapter(adapter);
        spinnerVisitType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register_visit, menu);
        menu.getItem(0).getIcon().setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_IN);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_register:
                utils.showToast(R.string.registerVisitDone);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class DateDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final RegisterVisitActivity activity = (RegisterVisitActivity) getActivity();
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                    String dateText = activity.utils.getDateText(calendar.getTime());
                    activity.textViewDate.setText(dateText);
                    activity.textViewTime.setEnabled(true);
                }
            };
            return new DatePickerDialog(
                    getContext(),
                    listener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
        }
    }

    public static class TimeDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final RegisterVisitActivity activity = (RegisterVisitActivity) getActivity();
            TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String timeText = activity.utils.getTimeText(hourOfDay, minute);
                    activity.textViewTime.setText(timeText);
                    activity.spinnerVisitType.setEnabled(true);
                }
            };
            return new TimePickerDialog(
                    getContext(),
                    listener,
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    true
            );
        }
    }

}
