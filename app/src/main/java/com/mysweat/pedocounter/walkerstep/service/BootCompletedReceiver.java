package com.mysweat.pedocounter.walkerstep.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.ui.MainActivity;

import java.util.Calendar;

public class BootCompletedReceiver extends BroadcastReceiver {
    Calendar calendar;
    Calendar calendar2;
    public String channelRunAg = "128";
    Context context2;
    PendingIntent pendingIntent;
    PendingIntent pendingIntent2;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED") || intent.getAction().equals("android.intent.action.MY_PACKAGE_REPLACED")) {
            this.context2 = context;
            if (context.getSharedPreferences("AlarmStatus", 0).getString(NotificationCompat.CATEGORY_STATUS, "0").equals("yes")) {
                String string = context.getSharedPreferences("Alarm", 0).getString("timer", "0");
                if (!string.equals("0")) {
                    String[] split = string.split(":");
                    setTimeWithTime(split[0], split[1]);
                }
            }
            setNotiDaily();
            if (context.getSharedPreferences("Status", 0).getString(NotificationCompat.CATEGORY_SERVICE, "0").equals("start")) {
                releaseNotification();
                SharedPreferences.Editor edit = context.getSharedPreferences("bootstatus", 0).edit();
                edit.putString("check", "1");
                edit.commit();
            }
        }
    }

    public void setTimeWithTime(String str, String str2) {
        int parseInt = Integer.parseInt(str);
        int parseInt2 = Integer.parseInt(str2);
        String.valueOf(parseInt);
        String.valueOf(parseInt2);
        if (parseInt2 < 10) {
            String.valueOf(parseInt2);
        }
        Calendar calendar = Calendar.getInstance();
        this.calendar = calendar;
        calendar.set(11, parseInt);
        this.calendar.set(12, parseInt2);
        Intent intent = new Intent(this.context2, AlReceiver.class);
        if (Build.VERSION.SDK_INT >= 23) {
            this.pendingIntent = PendingIntent.getBroadcast(this.context2, 8, intent, 201326592);
        } else {
            this.pendingIntent = PendingIntent.getBroadcast(this.context2, 8, intent, 134217728);
        }
        ((AlarmManager) this.context2.getSystemService(Context.ALARM_SERVICE)).setRepeating(0, 86400000 + this.calendar.getTimeInMillis(), 86400000L, this.pendingIntent);
    }

    public void setNotiDaily() {
        Calendar calendar = Calendar.getInstance();
        this.calendar2 = calendar;
        calendar.set(11, 10);
        this.calendar2.set(12, 10);
        this.calendar2.set(13, 0);
        Intent intent = new Intent(this.context2, AlReceiverDailyNoti.class);
        if (Build.VERSION.SDK_INT >= 23) {
            this.pendingIntent2 = PendingIntent.getBroadcast(this.context2, 10, intent, 201326592);
        } else {
            this.pendingIntent2 = PendingIntent.getBroadcast(this.context2, 10, intent, 134217728);
        }
        ((AlarmManager) this.context2.getSystemService(Context.ALARM_SERVICE)).setRepeating(0, 86400000 + this.calendar2.getTimeInMillis(), 86400000L, this.pendingIntent2);
        SharedPreferences.Editor edit = this.context2.getSharedPreferences("AlarmDailyBB", 0).edit();
        edit.putString("statusAlarmBB", "1");
        edit.apply();
    }

    public void releaseNotification() {
        PendingIntent activity;
        createNotificationChannelAchieve(this.channelRunAg);
        NotificationManagerCompat from = NotificationManagerCompat.from(this.context2);
        Intent intent = new Intent(this.context2, MainActivity.class);
        intent.addFlags(872415232);
        if (Build.VERSION.SDK_INT >= 23) {
            activity = PendingIntent.getActivity(this.context2, 0, intent, 201326592);
        } else {
            activity = PendingIntent.getActivity(this.context2, 0, intent, 134217728);
        }
        if (ActivityCompat.checkSelfPermission(context2, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        from.notify(128, new NotificationCompat.Builder(this.context2, this.channelRunAg).setSmallIcon(R.drawable.logo).setContentTitle(this.context2.getString(R.string.app_title)).setContentText(this.context2.getString(R.string.alarm_message)).setSound(RingtoneManager.getDefaultUri(2)).setAutoCancel(true).setPriority(0).setContentIntent(activity).build());
    }

    private void createNotificationChannelAchieve(String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) this.context2.getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(str, "Start", 3));
        }
    }
}
