package com.mysweat.pedocounter.walkerstep.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.ui.MainActivity;


public class AlReceiver extends BroadcastReceiver {
    public String chanelId = "144";
    Context mContext;
    PendingIntent pendingIntent;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        createNotificationChannel();
        NotificationManagerCompat from = NotificationManagerCompat.from(context);
        Intent intent2 = new Intent(context, MainActivity.class);
        intent2.addFlags(872415232);
        if (Build.VERSION.SDK_INT >= 23) {
            this.pendingIntent = PendingIntent.getActivity(context, 0, intent2, 201326592);
        } else {
            this.pendingIntent = PendingIntent.getActivity(context, 0, intent2, 134217728);
        }
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        from.notify(144, new NotificationCompat.Builder(context, this.chanelId).setSmallIcon(R.drawable.logo).setContentTitle(this.mContext.getString(R.string.alarm_heading)).setContentText(this.mContext.getString(R.string.alarm_message)).setSound(RingtoneManager.getDefaultUri(2)).setAutoCancel(true).setPriority(0).setContentIntent(this.pendingIntent).build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(this.chanelId, "MyChannelNew", NotificationManager.IMPORTANCE_DEFAULT));
        }
    }
}
