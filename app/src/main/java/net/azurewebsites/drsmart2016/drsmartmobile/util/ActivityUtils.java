package net.azurewebsites.drsmart2016.drsmartmobile.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import net.azurewebsites.drsmart2016.drsmartmobile.R;
import net.azurewebsites.drsmart2016.drsmartmobile.model.Token;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ActivityUtils {

    private FragmentActivity activity;
    private static final String TOKEN_KEY = "TOKEN_KEY";

    public ActivityUtils() {
        // nop
    }

    public ActivityUtils(FragmentActivity activity) {
        this.activity = activity;
    }

    public void showToast(final int textResourceId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplicationContext(), textResourceId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public <T> T mapJsonToObject(String json, Class clazz) {
        return (T) JsonTool.fromJson(json, clazz);
    }

    public String mapObjectToJson(Object object) {
        return JsonTool.toJson(object);
    }

    public Token getTokenFromSharedPreferences() {
        SharedPreferences preferences = activity.getSharedPreferences(activity.getString(R.string.app_name), Context.MODE_PRIVATE);
        String accessToken = preferences.getString(TOKEN_KEY, null);
        return new Token(accessToken);
    }

    public void saveTokenToSharedPreferences(Token token) {
        SharedPreferences.Editor editor = activity.getSharedPreferences(activity.getString(R.string.app_name), Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN_KEY, token.getAccessToken());
        editor.apply();
    }

    public String getDateText(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

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

    public String getDateTimeText(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String text = getDateText(date) + "  ";
        if(calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            text = text + "0" + calendar.get(Calendar.HOUR_OF_DAY) + ":";
        }
        else {
            text = text + calendar.get(Calendar.HOUR_OF_DAY) + ":";
        }
        if(calendar.get(Calendar.MINUTE) < 10) {
            text = text + "0" + calendar.get(Calendar.MINUTE);
        }
        else {
            text = text + calendar.get(Calendar.MINUTE);
        }

        return text;
    }

    public String getTimeText(int hour, int minute) {
        String text;
        if(hour < 10) {
            text = "0" + hour + ":";
        }
        else {
            text = hour + ":";
        }
        if(minute < 10) {
            text = text + "0" + minute;
        }
        else {
            text = text + minute;
        }

        return text;
    }

}
