package com.android.structure.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by muhammadhumzakhan on 10/23/2017.
 */

public class AlarmManagers {

    public static void createAlarmManager(Context mContext, Class<?> serviceToStart, String token, int field, int duration) {
        Intent myIntent = new Intent(mContext, serviceToStart);
        myIntent.putExtra("verification_code", token);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, createID(), myIntent, 0);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(field, duration);
        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    static int createID() {
        Date now = new Date();
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
    }
}
