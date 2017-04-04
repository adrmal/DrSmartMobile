package net.azurewebsites.drsmart2016.drsmartmobile.view.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import net.azurewebsites.drsmart2016.drsmartmobile.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setElevation(toolbar, 4);

        initializeNavigationDrawerFields();
    }

    private void initializeNavigationDrawerFields() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                doNavigationDrawerAction(item);
                return true;
            }
        });
    }

    private void doNavigationDrawerAction(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent;
        switch(item.getItemId()) {
            case R.id.drawerVisits:
                intent = new Intent(this, VisitsActivity.class);
                startActivity(intent);
                break;
            case R.id.drawerMedicalHistory:
                intent = new Intent(this, MedicalHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.drawerUserDetails:
                intent = new Intent(this, UserDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.drawerLogOut:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            moveTaskToBack(true);
        }
    }

}
