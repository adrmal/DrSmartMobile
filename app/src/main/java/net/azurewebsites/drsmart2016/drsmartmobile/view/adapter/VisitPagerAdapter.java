package net.azurewebsites.drsmart2016.drsmartmobile.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.view.fragment.AllVisitsFragment;
import net.azurewebsites.drsmart2016.drsmartmobile.view.fragment.FutureVisitsFragment;
import net.azurewebsites.drsmart2016.drsmartmobile.view.fragment.PastVisitsFragment;

public class VisitPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public VisitPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new AllVisitsFragment();
        }
        if(position == 1) {
            return new FutureVisitsFragment();
        }
        return new PastVisitsFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return context.getString(R.string.allVisits);
        }
        if(position == 1) {
            return context.getString(R.string.futureVisits);
        }
        return context.getString(R.string.pastVisits);
    }

}
