package com.mysweat.pedocounter.walkerstep.service;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class BackupWorker extends Worker {
    public Context mContext;

    public BackupWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
        this.mContext = context;
    }

    @Override
    public ListenableWorker.Result doWork() {
        Intent intent = new Intent(getApplicationContext(), MyCustom_Service.class);
        if (MyService.serviceConnection1 != null) {
            getApplicationContext().bindService(intent, MyService.serviceConnection1, Context.BIND_AUTO_CREATE);
        }
        intent.setAction(MyCustom_Service.ACTION_START_FOREGROUND_SERVICE);
        ContextCompat.startForegroundService(getApplicationContext(), intent);
        return ListenableWorker.Result.success();
    }
}
