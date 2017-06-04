package com.example.sheliza.grid_nav;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    private static final int REPEAT_TIME_IN_SECONDS = 1; //repeat every 60 seconds


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
                REPEAT_TIME_IN_SECONDS * 1000, pendingIntent);
        Toast.makeText(this, " Yes ", Toast.LENGTH_SHORT).show();

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
