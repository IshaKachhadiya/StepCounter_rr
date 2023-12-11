package com.mysweat.pedocounter.walkerstep.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.RemoteViews;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mysweat.pedocounter.walkerstep.Database.Db_fun;
import com.mysweat.pedocounter.walkerstep.Database.AchiveDatabase;
import com.mysweat.pedocounter.walkerstep.Database.TableAchive;
import com.mysweat.pedocounter.walkerstep.Database.UserTable;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.sensor.ServiceCallbacks;
import com.mysweat.pedocounter.walkerstep.sensor.StepListener;
import com.mysweat.pedocounter.walkerstep.ui.MainActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class MyCustom_Service extends Service implements SensorEventListener, StepListener {
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    public static boolean isRunning = false;
    public static boolean updateGoal = false;
    PendingIntent contentIntent;
    RemoteViews contentView;
    String date;
    Db_fun db;
    AchiveDatabase db_achieve;
    public String dis;
    String distancStr;
    String fullDate;
    SharedPreferences getShared;
    String goal;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mNotifyBuilder;
    NotificationCompat.Builder mNotifyBuilderlow;
    public double meters;
    String name;
    Notification notification;
    Notification.Builder notificationBuilderAndS;
    public int progress;
    SharedPreferences.Editor saveEditor;
    SharedPreferences savePref;
    String savedDate;
    private SensorManager sensorManager;
    private ServiceCallbacks serviceCallbacks;
    public String steps;
    int totalCalories;
    int totalSteps;
    PowerManager.WakeLock wl;
    public long numSteps = 0;
    public int calorie_count = 0;
    double km = 7.62E-4d;
    public double total_distance = 0.0d;
    private final IBinder binder = new LocalBinder();
    boolean dateChange = false;
    public String chanelId = "160";
    public String chanelIdAchieveSteps = "141";
    public String chanelIdAchieveCalories = "142";
    private Sensor mSensor;


    public double convertKmsToMiles(double d) {
        return d * 0.621371d;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void step(long j) {
    }

    
    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public MyCustom_Service getService() {
            return MyCustom_Service.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setCallbacks(ServiceCallbacks serviceCallbacks) {
        this.serviceCallbacks = serviceCallbacks;
    }

    @Override
    public void onCreate() {
        try {
            String string = getSharedPreferences("ServBind", 0).getString(NotificationCompat.CATEGORY_STATUS, "0");
            PowerManager.WakeLock newWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(1, "mywake:lockk");
            wl = newWakeLock;
            newWakeLock.acquire();
            if (string.equals("0")) {
                SharedPreferences.Editor edit = getSharedPreferences("ServBind", 0).edit();
                edit.putString(NotificationCompat.CATEGORY_STATUS, "1");
                edit.commit();
                sensor_init();
                SharedPreferences sharedPreferences = getSharedPreferences("AllValues", 0);
                savePref = sharedPreferences;
                saveEditor = sharedPreferences.edit();
                getShared = getSharedPreferences("AllValues", 0);
                db = new Db_fun(this);
                db_achieve = new AchiveDatabase(this);
                totalSteps = Integer.parseInt(db.getTotalDaySteps());
                totalCalories = Integer.parseInt(db.getTotalCalories());
                String string2 = getSharedPreferences("CurrentProfile", 0).getString("name", "0");
                name = string2;
                goal = db.getGoal(string2);
                savedDate = getSharedPreferences("SavedDate", 0).getString("saveDte", "0");
                if (!savedDate.equals(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))) {
                    dateChange = true;
                } else {
                    dateChange = false;
                }
                if (!dateChange) {
                    SharedPreferences sharedPreferences2 = getSharedPreferences("RecentValues", 0);
                    String string3 = sharedPreferences2.getString("step", "0");
                    String string4 = sharedPreferences2.getString("dis", "0");
                    String string5 = sharedPreferences2.getString(TableAchive.Calorie, "0");
                    String string6 = sharedPreferences2.getString("cc", "0");
                    numSteps = Long.parseLong(string3);
                    dis = string4;
                    calorie_count = Integer.parseInt(string5);
                    progress = Integer.parseInt(string6);
                }
                String orderedFullDate = getOrderedFullDate();
                date = orderedFullDate;
                if (db.checkIfDateEntered(orderedFullDate) == null) {
                    db.insert(name, "0", "0", date, getdateFullArranged(date), "0");
                    numSteps = 0L;
                    dis = "0";
                    calorie_count = 0;
                    progress = 0;
                }
                if (getSharedPreferences("bootstatus", 0).getString("check", "0").equals("1")) {
                    String fromCurrDate = db.getFromCurrDate(date);
                    if (fromCurrDate != null) {
                        String[] split = fromCurrDate.split(",");
                        String str = split[0];
                        String str2 = split[1];
                        String str3 = split[2];
                        numSteps = Long.parseLong(str);
                        dis = str2;
                        total_distance = Double.parseDouble(str2);
                        calorie_count = Integer.parseInt(str3);
                        int parseInt = Integer.parseInt(goal);
                        double parseDouble = Double.parseDouble(str);
                        double d = parseInt;
                        Double.isNaN(d);
                        progress = (int) ((parseDouble / d) * 100.0d);
                    }
                    SharedPreferences.Editor edit2 = getSharedPreferences("bootstatus", 0).edit();
                    edit2.putString("check", "0");
                    edit2.commit();
                }
                runForeground();
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        try {
            isRunning = false;
            stopsensor();
        } catch (Exception unused) {
        }
        super.onDestroy();
    }

    public static boolean isRunning() {
        return isRunning;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        String action;
        if (intent != null && (action = intent.getAction()) != null) {
            char c = 65535;
            int hashCode = action.hashCode();
            if (hashCode != -1964342113) {
                if (hashCode == 1969030125 && action.equals(ACTION_STOP_FOREGROUND_SERVICE)) {
                    c = 0;
                }
            } else if (action.equals(ACTION_START_FOREGROUND_SERVICE)) {
                c = 1;
            }
            if (c == 0) {
                stopForegroundService();
                stopsensor();
            } else if (c == 1) {
                runForeground();
                isRunning = true;
            }
        }
        return 1;
    }

    private void stopForegroundService() {
        try {
            stopForeground(true);
            stopSelf();
        } catch (Exception unused) {
        }
    }

    public void stopsensor() {
        try {
            if (wl.isHeld()) {
                wl.release();
            }
            if (sensorManager != null) {
                sensorManager.unregisterListener(this);
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        float[] fArr = sensorEvent.values;
        if (fArr.length > 0) {
            float f = fArr[0];
        }
        if (sensor.getType() == 18) {
            addStep();
        }
    }

    public void addStep() {
        try {
            if (!checkDate()) {
                numSteps++;
                totalSteps++;
                getDistance();
                if (numSteps % 36 == 0) {
                    calorie_count++;
                    totalCalories++;
                }
                steps = String.valueOf(numSteps);
                if (numSteps % 80 == 0) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager = notificationManager;
                        notificationManager.createNotificationChannel(new NotificationChannel("200", "myChannel", NotificationManager.IMPORTANCE_LOW));
                        contentView = new RemoteViews(getPackageName(), (int) R.layout.custom_notification);
                        if (Build.VERSION.SDK_INT >= 31) {
                            Notification.Builder ongoing = new Notification.Builder(this, "200").setSmallIcon(R.drawable.logo).setCustomContentView(contentView).setOngoing(true);
                            notificationBuilderAndS = ongoing;
                            ongoing.setForegroundServiceBehavior(Notification.FOREGROUND_SERVICE_IMMEDIATE);
                            Notification build = notificationBuilderAndS.build();
                            notification = build;
                            build.contentIntent = contentIntent;
                        } else {
                            NotificationCompat.Builder ongoing2 = new NotificationCompat.Builder(this, "200").setSmallIcon(R.drawable.logo).setCustomContentView(contentView).setOngoing(true);
                            mNotifyBuilder = ongoing2;
                            ongoing2.setContentIntent(contentIntent);
                            notification = mNotifyBuilder.build();
                        }
                    } else {
                        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        contentView = new RemoteViews(getPackageName(), (int) R.layout.custom_notification);
                        NotificationCompat.Builder customContentView = new NotificationCompat.Builder(this, "200").setSmallIcon(R.drawable.logo).setCustomContentView(contentView);
                        mNotifyBuilderlow = customContentView;
                        customContentView.setContentIntent(contentIntent);
                        notification = mNotifyBuilderlow.build();
                    }
                }
                dis = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(total_distance);
                contentView.setTextViewText(R.id.stepTxt, steps);
                contentView.setTextViewText(R.id.calorieTxt, "" + calorie_count);
                if (getSharedPreferences("Goals", 0).getString(UserTable.Unit, "0").equals("Miles")) {
                    String format = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(convertKmsToMiles(Double.parseDouble(dis)));
                    contentView.setTextViewText(R.id.distanceTxt, format);
                    contentView.setTextViewText(R.id.distanceUnit, getString(R.string.mile));
                    distancStr = format + " mi";
                } else {
                    contentView.setTextViewText(R.id.distanceUnit, getString(R.string.km));
                    contentView.setTextViewText(R.id.distanceTxt, "" + dis);
                    distancStr = dis + " km";
                }
                if (Build.VERSION.SDK_INT >= 26) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        mNotificationManager.notify(8, notificationBuilderAndS.build());
                    } else {
                        mNotificationManager.notify(8, mNotifyBuilder.build());
                    }
                } else {
                    mNotificationManager.notify(8, mNotifyBuilderlow.build());
                }
                if (updateGoal) {
                    goal = db.getGoal(name);
                    updateGoal = false;
                }
                int parseInt = Integer.parseInt(goal);
                double parseDouble = Double.parseDouble(steps);
                double d = parseInt;
                Double.isNaN(d);
                int i = (int) ((parseDouble / d) * 100.0d);
                progress = i;
                if (serviceCallbacks != null) {
                    serviceCallbacks.updateUI(numSteps, dis, calorie_count, meters, i);
                }
                db.updateDateWise(distancStr, String.valueOf(calorie_count), date, steps);
                if (totalSteps <= 90000) {
                    checkAchieveSteps(totalSteps);
                }
                if (totalCalories <= 400) {
                    checkAchieveCalories(totalCalories);
                }
                if (goal.equals(steps)) {
                    releaseNotification();
                    return;
                }
                return;
            }
            numSteps = 0L;
            total_distance = 0.0d;
            calorie_count = 0;
            progress = 0;
            String orderedFullDate = getOrderedFullDate();
            date = orderedFullDate;
            db.insert(name, "0", "0", date, getdateFullArranged(orderedFullDate), "0");
            totalSteps = Integer.parseInt(db.getTotalDaySteps());
            totalCalories = Integer.parseInt(db.getTotalCalories());
        } catch (Exception unused) {
        }
    }

    String getOrderedFullDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String.valueOf(calendar.get(1) % 100);
        String.valueOf(calendar.get(2) + 1);
        String.valueOf(calendar.get(5));
        int i = calendar.get(1);
        int i2 = calendar.get(2);
        int i3 = calendar.get(5);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(i, i2, i3);
        String format = new SimpleDateFormat("yyyy-MM-dd").format(calendar2.getTime());
        fullDate = format;
        return format;
    }

    boolean checkDate() {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        if (!savedDate.equals(format)) {
            SharedPreferences.Editor edit = getSharedPreferences("SavedDate", 0).edit();
            edit.putString("saveDte", format);
            edit.commit();
            savedDate = format;
            dateChange = true;
        } else {
            dateChange = false;
        }
        return dateChange;
    }

    public void getDistance() {
        try {
            double pow = (float) Math.pow(10.0d, 4.0d);
            double d = numSteps;
            double d2 = km;
            Double.isNaN(d);
            Double.isNaN(pow);
            double floor = Math.floor(d * d2 * pow);
            Double.isNaN(pow);
            double d3 = floor / pow;
            total_distance = d3;
            meters = d3 * 1000.0d;
        } catch (Exception unused) {
        }
    }

    public void sensor_init() {
        try {
            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensorManager = sensorManager;
            Sensor defaultSensor = sensorManager.getDefaultSensor(18);
            mSensor = defaultSensor;
            sensorManager.registerListener(this, defaultSensor, 0);
        } catch (Exception unused) {
        }
    }

    public void releaseNotification() {
        PendingIntent activity;
        createNotificationChannel();
        NotificationManagerCompat from = NotificationManagerCompat.from(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(872415232);
        if (Build.VERSION.SDK_INT >= 23) {
            activity = PendingIntent.getActivity(this, 0, intent, 201326592);
        } else {
            activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        from.notify(160, new NotificationCompat.Builder(this, chanelId).setSmallIcon(R.drawable.logo).setContentTitle(getString(R.string.goal_completed)).setContentText(getString(R.string.seetxt)).setSound(RingtoneManager.getDefaultUri(2)).setAutoCancel(true).setPriority(0).setContentIntent(activity).build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(chanelId, "MyChannel", 3));
        }
    }

    public void releaseNotificationAchieveSteps() {
        PendingIntent activity;
        createNotificationChannelAchieve(chanelIdAchieveSteps);
        NotificationManagerCompat from = NotificationManagerCompat.from(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(872415232);
        if (Build.VERSION.SDK_INT >= 23) {
            activity = PendingIntent.getActivity(this, 0, intent, 201326592);
        } else {
            activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        from.notify(141, new NotificationCompat.Builder(this, chanelIdAchieveSteps).setSmallIcon(R.drawable.logo).setContentTitle(getString(R.string.congrats)).setContentText(getString(R.string.challenge_done_steps)).setSound(RingtoneManager.getDefaultUri(2)).setAutoCancel(true).setPriority(0).setContentIntent(activity).build());
    }

    public void releaseNotificationAchieveCalories() {
        PendingIntent activity;
        createNotificationChannelAchieve(chanelIdAchieveCalories);
        NotificationManagerCompat from = NotificationManagerCompat.from(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(872415232);
        if (Build.VERSION.SDK_INT >= 23) {
            activity = PendingIntent.getActivity(this, 0, intent, 201326592);
        } else {
            activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        from.notify(142, new NotificationCompat.Builder(this, chanelIdAchieveCalories).setSmallIcon(R.drawable.logo).setContentTitle(getString(R.string.congrats)).setContentText(getString(R.string.challenge_done_calories)).setSound(RingtoneManager.getDefaultUri(2)).setAutoCancel(true).setPriority(0).setContentIntent(activity).build());
    }

    private void createNotificationChannelAchieve(String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(str, "Challenge", 3));
        }
    }

    private void checkAchieveSteps(int i) {
        if (i >= 3000 && i < 6000) {
            if (Integer.parseInt(db_achieve.getUserAchievementSteps(name)) < 1) {
                db_achieve.updateAchieveSteps(name, "1");
                releaseNotificationAchieveSteps();
            }
        } else if (i >= 6000 && i < 10000) {
            if (Integer.parseInt(db_achieve.getUserAchievementSteps(name)) < 2) {
                db_achieve.updateAchieveSteps(name, "2");
                releaseNotificationAchieveSteps();
            }
        } else if (i >= 10000 && i < 15000) {
            if (Integer.parseInt(db_achieve.getUserAchievementSteps(name)) < 3) {
                db_achieve.updateAchieveSteps(name, "3");
                releaseNotificationAchieveSteps();
            }
        } else if (i >= 15000 && i < 20000) {
            if (Integer.parseInt(db_achieve.getUserAchievementSteps(name)) < 4) {
                db_achieve.updateAchieveSteps(name, "4");
                releaseNotificationAchieveSteps();
            }
        } else if (i >= 20000 && i < 30000) {
            if (Integer.parseInt(db_achieve.getUserAchievementSteps(name)) < 5) {
                db_achieve.updateAchieveSteps(name, "5");
                releaseNotificationAchieveSteps();
            }
        } else if (i >= 30000 && i < 40000) {
            if (Integer.parseInt(db_achieve.getUserAchievementSteps(name)) < 6) {
                db_achieve.updateAchieveSteps(name, "6");
                releaseNotificationAchieveSteps();
            }
        } else if (i >= 40000 && i < 60000) {
            if (Integer.parseInt(db_achieve.getUserAchievementSteps(name)) < 7) {
                db_achieve.updateAchieveSteps(name, "7");
                releaseNotificationAchieveSteps();
            }
        } else if (i >= 60000 && i < 80000) {
            if (Integer.parseInt(db_achieve.getUserAchievementSteps(name)) < 8) {
                db_achieve.updateAchieveSteps(name, "8");
                releaseNotificationAchieveSteps();
            }
        } else if (i < 80000 || Integer.parseInt(db_achieve.getUserAchievementSteps(name)) >= 9) {
        } else {
            db_achieve.updateAchieveSteps(name, "9");
            releaseNotificationAchieveSteps();
        }
    }

    private void checkAchieveCalories(int i) {
        if (i >= 10 && i < 20) {
            if (Integer.parseInt(db_achieve.getUserAchievementCalories(name)) < 1) {
                db_achieve.updateAchieveCalories(name, "1");
                releaseNotificationAchieveCalories();
            }
        } else if (i >= 20 && i < 40) {
            if (Integer.parseInt(db_achieve.getUserAchievementCalories(name)) < 2) {
                db_achieve.updateAchieveCalories(name, "2");
                releaseNotificationAchieveCalories();
            }
        } else if (i >= 40 && i < 60) {
            if (Integer.parseInt(db_achieve.getUserAchievementCalories(name)) < 3) {
                db_achieve.updateAchieveCalories(name, "3");
                releaseNotificationAchieveCalories();
            }
        } else if (i >= 60 && i < 100) {
            if (Integer.parseInt(db_achieve.getUserAchievementCalories(name)) < 4) {
                db_achieve.updateAchieveCalories(name, "4");
                releaseNotificationAchieveCalories();
            }
        } else if (i >= 100 && i < 150) {
            if (Integer.parseInt(db_achieve.getUserAchievementCalories(name)) < 5) {
                db_achieve.updateAchieveCalories(name, "5");
                releaseNotificationAchieveCalories();
            }
        } else if (i >= 150 && i < 200) {
            if (Integer.parseInt(db_achieve.getUserAchievementCalories(name)) < 6) {
                db_achieve.updateAchieveCalories(name, "6");
                releaseNotificationAchieveCalories();
            }
        } else if (i >= 200 && i < 300) {
            if (Integer.parseInt(db_achieve.getUserAchievementCalories(name)) < 7) {
                db_achieve.updateAchieveCalories(name, "7");
                releaseNotificationAchieveCalories();
            }
        } else if (i >= 300 && i < 400) {
            if (Integer.parseInt(db_achieve.getUserAchievementCalories(name)) < 8) {
                db_achieve.updateAchieveCalories(name, "8");
                releaseNotificationAchieveCalories();
            }
        } else if (i < 400 || Integer.parseInt(db_achieve.getUserAchievementCalories(name)) >= 9) {
        } else {
            db_achieve.updateAchieveCalories(name, "9");
            releaseNotificationAchieveCalories();
        }
    }

    String getdateFullArranged(String str) {
        Date date;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String valueOf = String.valueOf(calendar.get(1) % 100);
        String valueOf2 = String.valueOf(calendar.get(2) + 1);
        String valueOf3 = String.valueOf(calendar.get(5));
        Calendar.getInstance().set(calendar.get(1), calendar.get(2), calendar.get(5));
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        String format = new SimpleDateFormat("EEEE").format(date);
        return valueOf3 + "/" + valueOf2 + "/" + valueOf + " " + format;
    }

    public void runForeground() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
            mNotificationManager = notificationManager;
            notificationManager.createNotificationChannel(new NotificationChannel("200", "myChannel", NotificationManager.IMPORTANCE_LOW));
            contentView = new RemoteViews(getPackageName(), (int) R.layout.custom_notification);
            if (Build.VERSION.SDK_INT >= 31) {
                Notification.Builder ongoing = new Notification.Builder(this, "200").setSmallIcon(R.drawable.logo).setCustomContentView(contentView).setOngoing(true);
                notificationBuilderAndS = ongoing;
                ongoing.setForegroundServiceBehavior(1);
                notification = notificationBuilderAndS.build();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(872415232);
                PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 201326592);
                contentIntent = activity;
                notification.contentIntent = activity;
            } else {
                mNotifyBuilder = new NotificationCompat.Builder(this, "200").setSmallIcon(R.drawable.logo).setCustomContentView(contentView).setOngoing(true);
                Intent intent2 = new Intent(this, MainActivity.class);
                intent2.addFlags(872415232);
                PendingIntent activity2 = PendingIntent.getActivity(this, 0, intent2, 201326592);
                contentIntent = activity2;
                mNotifyBuilder.setContentIntent(activity2);
                notification = mNotifyBuilder.build();
            }
            startForeground(8, notification);
        } else {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            contentView = new RemoteViews(getPackageName(), (int) R.layout.custom_notification);
            Intent intent3 = new Intent(this, MainActivity.class);
            intent3.addFlags(872415232);
            contentIntent = PendingIntent.getActivity(this, 8, intent3, 134217728);
            NotificationCompat.Builder customContentView = new NotificationCompat.Builder(this, "200").setSmallIcon(R.drawable.logo).setCustomContentView(contentView);
            mNotifyBuilderlow = customContentView;
            customContentView.setContentIntent(contentIntent);
            Notification build = mNotifyBuilderlow.build();
            notification = build;
            startForeground(8, build);
        }
        RemoteViews remoteViews = contentView;
        remoteViews.setTextViewText(R.id.stepTxt, "" + numSteps);
        remoteViews.setTextViewText(R.id.calorieTxt, "" + calorie_count);
        if (getSharedPreferences("Goals", 0).getString(UserTable.Unit, "0").equals("Miles")) {
            contentView.setTextViewText(R.id.distanceUnit, getString(R.string.mile));
            contentView.setTextViewText(R.id.distanceTxt, String.valueOf(convertKmsToMiles(total_distance)));
            return;
        }
        contentView.setTextViewText(R.id.distanceUnit, getString(R.string.km));
        remoteViews.setTextViewText(R.id.distanceTxt, "" + total_distance);
    }
}
