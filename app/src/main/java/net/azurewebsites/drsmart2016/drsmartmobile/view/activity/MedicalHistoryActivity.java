package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonSyntaxException;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.MedicalHistory;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Record;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;
import net.azurewebsites.drsmart2016.drsmartmobile.view.adapter.MedicalHistoryArrayAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MedicalHistoryActivity extends AppCompatActivity {

    private MedicalHistory medicalHistory;
    public static final String RECORD_KEY = "RECORD_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        setTitle(R.string.medicalHistory);

        Token token = ActivityUtils.with(this).getTokenFromSharedPreferences();
        RESTClient.getClient().getMedicalHistory(token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ActivityUtils.with(MedicalHistoryActivity.this).showToast(R.string.connectionError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    Record[] records = ActivityUtils.with(MedicalHistoryActivity.this).mapJsonToObject(json, Record[].class);
                    medicalHistory = new MedicalHistory(records);
                    initializeListViewAndAdapter();
                }
                catch(JsonSyntaxException e) {
                    ActivityUtils.with(MedicalHistoryActivity.this).showToast(R.string.webServiceError);
                }
            }
        });
    }

    private void initializeListViewAndAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MedicalHistoryArrayAdapter adapter = new MedicalHistoryArrayAdapter(getApplicationContext(), R.layout.activity_medical_history_element, medicalHistory.getRecords());

                final ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MedicalHistoryActivity.this, RecordDetailsActivity.class);
                        intent.putExtra(RECORD_KEY, (Record) parent.getItemAtPosition(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }

}
