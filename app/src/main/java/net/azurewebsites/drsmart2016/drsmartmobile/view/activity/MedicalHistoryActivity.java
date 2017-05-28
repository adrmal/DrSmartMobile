package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.MedicalHistory;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Record;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.view.adapter.MedicalHistoryArrayAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MedicalHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        setTitle(R.string.medicalHistory);

        MedicalHistory medicalHistory = getPatientMedicalHistory();

        MedicalHistoryArrayAdapter adapter = new MedicalHistoryArrayAdapter(getApplicationContext(), R.layout.activity_medical_history_element, medicalHistory.getRecords());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MedicalHistoryActivity.this, RecordDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private MedicalHistory getPatientMedicalHistory() {
        /*Token token = getTokenFromSharedPreferences();
        RESTClient.getClient().getMedicalHistory(token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        return history;*/
        MedicalHistory history = new MedicalHistory();
        history.addRecord(new Record());
        history.addRecord(new Record());
        history.addRecord(new Record());
        return history;
    }

    private Token getTokenFromSharedPreferences() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String accessToken = preferences.getString(LoginActivity.TOKEN_KEY, null);
        return new Token(accessToken);
    }

}
