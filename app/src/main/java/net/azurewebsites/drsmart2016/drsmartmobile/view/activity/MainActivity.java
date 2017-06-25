package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import net.azurewebsites.drsmart2016.drsmartmobile.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeCardViews();
    }

    private void initializeCardViews() {
        CardView cardViewVisits = (CardView) findViewById(R.id.visits);
        cardViewVisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VisitActivity.class);
                startActivity(intent);
            }
        });
        CardView cardViewMedicalHistory = (CardView) findViewById(R.id.medicalHistory);
        cardViewMedicalHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MedicalHistoryActivity.class);
                startActivity(intent);
            }
        });
        CardView cardViewUserDetails = (CardView) findViewById(R.id.userDetails);
        cardViewUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
                startActivity(intent);
            }
        });
        CardView cardViewLogOut = (CardView) findViewById(R.id.logOut);
        cardViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
