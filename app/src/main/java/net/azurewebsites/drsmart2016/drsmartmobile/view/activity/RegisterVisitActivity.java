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

import com.google.gson.JsonSyntaxException;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Visit;
import net.azurewebsites.drsmart2016.drsmartmobile.model.VisitStatus;
import net.azurewebsites.drsmart2016.drsmartmobile.model.VisitType;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Specialty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterVisitActivity extends AppCompatActivity {

    private Spinner spinnerSpecialty;
    private Spinner spinnerDoctor;
    private TextView textViewDate;
    private TextView textViewTime;
    private Spinner spinnerVisitType;
    private List<Specialty> specialties;
    private List<HashMap> doctors;
    private List<VisitType> visitTypes;
    private ActivityUtils utils;
    private int specialtyCounter;
    private int doctorCounter;
    private Visit visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_visit);
        setTitle(R.string.registerVisit);
        utils = new ActivityUtils(this);

        visit = new Visit();

        initializeUiFields();
        disableUiFields();

        setSpinnerSpecialtyActions();
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
        RESTClient.getClient().getAllSpecialties(utils.getTokenFromSharedPreferences(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                utils.showToast(R.string.connectionError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    Specialty[] specialtiesArray = utils.mapJsonToObject(json, Specialty[].class);
                    specialties = new ArrayList<>(Arrays.asList(specialtiesArray));
                    setSpinnerSpecialtyValues();
                }
                catch(JsonSyntaxException e) {
                    utils.showToast(R.string.webServiceError);
                }
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_main_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialtyCounter = 0;
        spinnerSpecialty.setAdapter(adapter);
        spinnerSpecialty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(specialtyCounter > 0) {
                    spinnerDoctor.setEnabled(true);
                    visit.setDoctorSpecialtyId(specialties.get(position).getId());
                    setSpinnerDoctorActions();
                }
                else {
                    specialtyCounter++;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerDoctorActions() {
        RESTClient.getClient().getDoctorsBySpecialtyId(visit.getDoctorSpecialtyId(), utils.getTokenFromSharedPreferences(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                utils.showToast(R.string.connectionError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    HashMap[] doctorsArray = utils.mapJsonToObject(json, HashMap[].class);
                    doctors = new ArrayList<>(Arrays.asList(doctorsArray));
                    setSpinnerDoctorValues();
                }
                catch(JsonSyntaxException e) {
                    utils.showToast(R.string.webServiceError);
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_main_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorCounter = 0;
        spinnerDoctor.setAdapter(adapter);
        spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(doctorCounter > 0) {
                    textViewDate.setEnabled(true);
                    visit.setDoctorId(doctors.get(position).get("Id").toString());
                    visit.setDoctorFullName(doctors.get(position).get("FullName").toString());
                    setTextViewDateActions();
                }
                else {
                    doctorCounter++;
                }
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
        VisitType[] visitTypesArray = VisitType.values();
        visitTypes = new ArrayList<>(Arrays.asList(visitTypesArray));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_main_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVisitType.setAdapter(adapter);
        spinnerVisitType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visit.setType(visitTypes.get(position).getTextValue());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        setSpinnerVisitTypeValues();
    }

    private void setSpinnerSpecialtyValues() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<String> specialtiesNames = new ArrayList<>();
                for(Specialty specialty : specialties) {
                    specialtiesNames.add(specialty.getName());
                }
                ((ArrayAdapter) spinnerSpecialty.getAdapter()).addAll(specialtiesNames);
            }
        });
    }

    private void setSpinnerDoctorValues() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<String> doctorsNames = new ArrayList<>();
                for(HashMap doctor : doctors) {
                    doctorsNames.add(doctor.get("FullName").toString());
                }
                ((ArrayAdapter) spinnerDoctor.getAdapter()).addAll(doctorsNames);
            }
        });
    }

    private void setSpinnerVisitTypeValues() {
        List<String> visitTypesNames = new ArrayList<>();
        for(VisitType visitType : visitTypes) {
            visitTypesNames.add(visitType.getTextValue());
        }
        ((ArrayAdapter) spinnerVisitType.getAdapter()).addAll(visitTypesNames);
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
                visit.setStatus(VisitStatus.ACTIVE.getTextValue());
                RESTClient.getClient().registerVisit(utils.mapObjectToJson(visit), utils.getTokenFromSharedPreferences(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        utils.showToast(R.string.connectionError);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        utils.showToast(R.string.registerVisitDone);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(RegisterVisitActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
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
                    activity.setTextViewTimeActions();
                    activity.visit.setDate(calendar.getTime());
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
                    activity.visit.getDate().setHours(hourOfDay);
                    activity.visit.getDate().setMinutes(minute);
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
