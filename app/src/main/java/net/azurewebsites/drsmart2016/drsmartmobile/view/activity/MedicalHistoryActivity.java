package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.MedicalHistory;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Record;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.util.JsonTool;
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

        RESTClient.getClient().getMedicalHistory(getTokenFromSharedPreferences(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast(R.string.connectionError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    Record[] records = mapJsonToRecords(json);
                    medicalHistory = new MedicalHistory(records);
                    initializeListViewAndAdapter();
                }
                catch(JsonSyntaxException e) {
                    e.printStackTrace();
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

    private void showToast(final int textResourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MedicalHistoryActivity.this, textResourceId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Record[] mapJsonToRecords(String json) {
        return (Record[]) JsonTool.fromJson(json, Record[].class);
    }

}
