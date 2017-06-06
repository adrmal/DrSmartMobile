package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Patient;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.util.JsonTool;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserDetailsActivity extends AppCompatActivity {

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setTitle(R.string.userDetails);

        RESTClient.getClient().getUserDetails(getTokenFromSharedPreferences(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast(R.string.connectionError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    patient = mapJsonToPatient(json);
                    setPatientUiFields();
                }
                catch(JsonSyntaxException e) {
                    showToast(R.string.webServiceError);
                }
            }
        });
    }

    private Token getTokenFromSharedPreferences() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String accessToken = preferences.getString(LoginActivity.TOKEN_KEY, null);
        return new Token(accessToken);
    }

    private void showToast(final int textResourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UserDetailsActivity.this, textResourceId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Patient mapJsonToPatient(String json) {
        return (Patient) JsonTool.fromJson(json, Patient.class);
    }

    private void setPatientUiFields() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(getFullNameText());
                TextView gender = (TextView) findViewById(R.id.gender);
                gender.setText(patient.getGender().toString());
                TextView pesel = (TextView) findViewById(R.id.pesel);
                pesel.setText(patient.getPesel());
                TextView dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
                dateOfBirth.setText(getDateText());
                TextView address = (TextView) findViewById(R.id.address);
                address.setText(patient.getAddress());
                TextView city = (TextView) findViewById(R.id.city);
                city.setText(patient.getZipCode() + " " + patient.getCity());
                TextView province = (TextView) findViewById(R.id.province);
                province.setText(patient.getProvince().toLowerCase());
            }
        });
    }

    private String getFullNameText() {
        return patient.getFirstName() + " "
                + (patient.getSecondName() == null ? "" : patient.getSecondName() + " ")
                + patient.getLastName();
    }

    private String getDateText() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(patient.getDateOfBirth());

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
