package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Patient;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserDetailsActivity extends AppCompatActivity {

    private Patient patient;
    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setTitle(R.string.userDetails);
        utils = new ActivityUtils(this);

        Token token = utils.getTokenFromSharedPreferences();
        RESTClient.getClient().getUserDetails(token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                utils.showToast(R.string.connectionError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    patient = utils.mapJsonToObject(json, Patient.class);
                    setPatientUiFields();
                }
                catch(JsonSyntaxException e) {
                    utils.showToast(R.string.webServiceError);
                }
            }
        });
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
                dateOfBirth.setText(utils.getDateText(patient.getDateOfBirth()));
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

}
