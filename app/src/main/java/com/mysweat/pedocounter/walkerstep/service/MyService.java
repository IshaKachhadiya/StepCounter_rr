package com.mysweat.pedocounter.walkerstep.service;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import androidx.core.app.JobIntentService;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


public class MyService extends JobIntentService {
    static Context mContext;
    public static ServiceConnection serviceConnection1;

    @Override
    protected void onHandleWork(Intent intent) {
        if (Build.VERSION.SDK_INT < 26) {
            Intent intent2 = new Intent(getApplicationContext(), MyCustom_Service.class);
            ServiceConnection serviceConnection = serviceConnection1;
            if (serviceConnection != null) {
                mContext.bindService(intent2, serviceConnection, 1);
            }
            startService(intent2);
        } else if (Build.VERSION.SDK_INT >= 31) {
            WorkManager.getInstance().enqueue(new OneTimeWorkRequest.Builder(BackupWorker.class).addTag("BACKUP_WORKER_TAG").build());
        } else {
            Intent intent3 = new Intent(getApplicationContext(), MyCustom_Service.class);
            ServiceConnection serviceConnection2 = serviceConnection1;
            if (serviceConnection2 != null) {
                mContext.bindService(intent3, serviceConnection2, 1);
            }
            intent3.setAction(MyCustom_Service.ACTION_START_FOREGROUND_SERVICE);
            ContextCompat.startForegroundService(getApplicationContext(), intent3);
        }
    }
}
