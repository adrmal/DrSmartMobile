package net.azurewebsites.drsmart2016.drsmartmobile.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonSyntaxException;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Visit;
import net.azurewebsites.drsmart2016.drsmartmobile.rest.RESTClient;
import net.azurewebsites.drsmart2016.drsmartmobile.util.ActivityUtils;
import net.azurewebsites.drsmart2016.drsmartmobile.view.activity.VisitActivity;
import net.azurewebsites.drsmart2016.drsmartmobile.view.activity.VisitDetailsActivity;
import net.azurewebsites.drsmart2016.drsmartmobile.view.adapter.VisitArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PastVisitsFragment extends Fragment {

    private List<Visit> visits;
    private ActivityUtils utils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_past_visits, container, false);
        utils = new ActivityUtils(getActivity());

        Token token = utils.getTokenFromSharedPreferences();
        RESTClient.getClient().getPastVisits(token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                utils.showToast(R.string.connectionError);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    Visit[] visitsArray = utils.mapJsonToObject(json, Visit[].class);
                    visits = new ArrayList<>(Arrays.asList(visitsArray));
                    initializeListViewAndAdapter(view);
                }
                catch(JsonSyntaxException e) {
                    utils.showToast(R.string.webServiceError);
                }
            }
        });

        return view;
    }

    private void initializeListViewAndAdapter(final View view) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                VisitArrayAdapter adapter = new VisitArrayAdapter(getContext(), R.layout.fragment_visits_element, visits);

                final ListView listView = (ListView) view.findViewById(R.id.listView);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), VisitDetailsActivity.class);
                        intent.putExtra(VisitActivity.VISIT_KEY, (Visit) parent.getItemAtPosition(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }

}
