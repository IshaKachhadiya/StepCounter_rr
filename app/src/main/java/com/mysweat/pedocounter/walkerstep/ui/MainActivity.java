package com.mysweat.pedocounter.walkerstep.ui;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref;
import com.mysweat.pedocounter.walkerstep.service.AlReceiverDailyNoti;
import com.mysweat.pedocounter.walkerstep.service.MyCustom_Service;
import com.mysweat.pedocounter.walkerstep.Database.Db_fun;
import com.mysweat.pedocounter.walkerstep.Database.AchiveDatabase;
import com.mysweat.pedocounter.walkerstep.Database.TableAchive;
import com.mysweat.pedocounter.walkerstep.Database.UserTable;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.fragments.BadgesFragment;
import com.mysweat.pedocounter.walkerstep.fragments.HomeFragment;
import com.mysweat.pedocounter.walkerstep.fragments.SettingFragment;
import com.mysweat.pedocounter.walkerstep.fragments.HistoryFragment;
import com.mysweat.pedocounter.walkerstep.utils.offline;
import com.mysweat.pedocounter.walkerstep.sensor.ServiceCallbacks;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements ServiceCallbacks {
    private static final String[] INITIAL_PERMS = {"android.permission.ACTIVITY_RECOGNITION"};
    private static final String[] INITIAL_PERMS_NOTI = {"android.permission.POST_NOTIFICATIONS"};
    public static List<String> perm;
    Calendar calendar2;
    Context context;
//    public static TextView tv_coin;
    int currentCaloriesFromService;
    String currentDistanceFromService;
    double currentMetersFromService;
    int currentProgressFromService;
    long currentStepsFromService;
    Db_fun db;
    AchiveDatabase db_achieve;
    public double final_time_sec;
    FragmentTransaction fragmentTransaction;
    public HomeFragment fragment_one;
    ActionBarDrawerToggle mToggle;
    private MyCustom_Service myService;
    PendingIntent pendingIntent2;
    String spdFinal;
    String unit_distance;
    Handler customHandler = new Handler();
    long timeInMilliseconds = 0;
    long timeSwapBuff = 0;
    long updatedTime = 0;
    private long startTime = 0;
    public double final_speed = 0.0d;
    private boolean bound = false;
    FrameLayout content;
    LinearLayout ll_home, llhistory, llbadge, ll_sett;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myService = ((MyCustom_Service.LocalBinder) iBinder).getService();
            bound = true;
            myService.setCallbacks(MainActivity.this);
            currentStepsFromService = myService.numSteps;
            currentCaloriesFromService = myService.calorie_count;
            currentDistanceFromService = myService.dis;
            currentProgressFromService = myService.progress;
            currentMetersFromService = myService.meters;
            init();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            MainActivity.this.bound = false;
        }
    };

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            try {
                timeInMilliseconds = SystemClock.uptimeMillis() - MainActivity.this.startTime;
                updatedTime = timeSwapBuff + MainActivity.this.timeInMilliseconds;
                int i = (int) (updatedTime / 1000);
                int i2 = i / 60;
                int i3 = i % 60;
                MainActivity.this.customHandler.postDelayed(this, 0L);
                MainActivity.this.final_time_sec = i2 * 60;
                double d = final_time_sec;
                double d2 = i3;
                Double.isNaN(d2);
                final_time_sec = d + d2;
            } catch (Exception unused) {
            }
        }
    };
    String mSpeed = "0";
    public String chanelIdAchieveDistance = "143";
    LinearLayout home_withdraw;

    public double convertKmsToMiles(double d) {
        return d * 0.621371d;
    }

    @Override
    public void updateUI(long j, String str, int i, double d, int i2) {
        currentStepsFromService = j;
        currentDistanceFromService = str;
        currentCaloriesFromService = i;
        currentMetersFromService = d;
        currentProgressFromService = i2;
        String speeds = getSpeeds(String.valueOf(d));
        spdFinal = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(Double.parseDouble((speeds.equals("Infinity") || speeds.equals("∞")) ? "0" : "0"));
        if (!this.unit_distance.equals("Kilometers")) {
            str = this.unit_distance.equals("Miles") ? new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(convertKmsToMiles(Double.parseDouble(str))) : "";
        }
        this.fragment_one.upDateUIFrag(Long.valueOf(j), spdFinal, i, str, this.currentProgressFromService);
    }

    ImageView iv_anim, iv_wp, iv_badge, iv_sett;
    TextView txt_home, txt_his, txt_badge, txt_sett;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        context = this;
        db = new Db_fun(this);

        iv_anim = findViewById(R.id.iv_anim);
        iv_wp = findViewById(R.id.iv_wp);
        iv_badge = findViewById(R.id.iv_badge);
        iv_sett = findViewById(R.id.iv_sett);
        ll_home = findViewById(R.id.ll_home);
        llhistory = findViewById(R.id.ll_wp);
        llbadge = findViewById(R.id.ll_badge);
        ll_sett = findViewById(R.id.ll_sett);
        txt_home = findViewById(R.id.txt_home);
        txt_his = findViewById(R.id.txt_his);
        txt_badge = findViewById(R.id.txt_badge);
        txt_sett = findViewById(R.id.txt_sett);
        home_withdraw = findViewById(R.id.home_withdraw);
        content = findViewById(R.id.content);
//        tv_coin = findViewById(R.id.txt_home_coin);

        try {
            this.fragment_one = new HomeFragment(this);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, this.fragment_one, HomeFragment.class.getName());
            fragmentTransaction.commit();

            txt_home.setTextColor(getResources().getColor(R.color.text_color));
            txt_his.setTextColor(getResources().getColor(R.color.black_33));
            txt_badge.setTextColor(getResources().getColor(R.color.black_33));
            txt_sett.setTextColor(getResources().getColor(R.color.black_33));

            iv_anim.setColorFilter(getResources().getColor(R.color.text_color));
            iv_wp.setColorFilter(getResources().getColor(R.color.black_33));
            iv_badge.setColorFilter(getResources().getColor(R.color.black_33));
            iv_sett.setColorFilter(getResources().getColor(R.color.black_33));

            home_withdraw.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        tv_coin.setText(String.valueOf(new offline(this).getTotalCoins()));

        if (MyCustom_Service.isRunning()) {
            initService();
        } else if (getSharedPreferences("Status", 0).getString(NotificationCompat.CATEGORY_SERVICE, "0").equals("start")) {
            SharedPreferences.Editor edit = this.context.getSharedPreferences("bootstatus", 0).edit();
            edit.putString("check", "1");
            edit.commit();
            startSer();
            initService();
        }

        checkForRate();
        checkPermission();
        if (getSharedPreferences("sensitivity", 0).getString("val", "0").equals("0")) {
            SharedPreferences.Editor edit2 = getSharedPreferences("sensitivity", 0).edit();
            edit2.putString("val", "15");
            edit2.commit();
        }

        if (!getSharedPreferences("AlarmDailyBB", 0).getString("statusAlarmBB", "0").equals("1")) {
            setNotiDaily();
        }

        this.db_achieve = new AchiveDatabase(this);

        int my_coin = ADSharedPref.getInteger(MainActivity.this, ADSharedPref.MCOIN, 0);

        if (my_coin == 0) {
            home_withdraw.setVisibility(View.VISIBLE);
        } else {
            home_withdraw.setVisibility(View.GONE);
        }

        home_withdraw.setOnClickListener(v -> {
            replaceFragment(new SettingFragment());
            txt_home.setTextColor(getResources().getColor(R.color.black_33));
            txt_his.setTextColor(getResources().getColor(R.color.black_33));
            txt_badge.setTextColor(getResources().getColor(R.color.black_33));
            txt_sett.setTextColor(getResources().getColor(R.color.text_color));

            iv_anim.setColorFilter(getResources().getColor(R.color.black_33));
            iv_wp.setColorFilter(getResources().getColor(R.color.black_33));
            iv_badge.setColorFilter(getResources().getColor(R.color.black_33));
            iv_sett.setColorFilter(getResources().getColor(R.color.text_color));

//            if (my_coin == 0) {
//                home_withdraw.setVisibility(View.VISIBLE);
//
//            } else {
                home_withdraw.setVisibility(View.GONE);

//            }
        });

        ll_home.setOnClickListener(v -> {
            replaceFragment(new HomeFragment(this));
            txt_home.setTextColor(getResources().getColor(R.color.text_color));
            txt_his.setTextColor(getResources().getColor(R.color.black_33));
            txt_badge.setTextColor(getResources().getColor(R.color.black_33));
            txt_sett.setTextColor(getResources().getColor(R.color.black_33));

            iv_anim.setColorFilter(getResources().getColor(R.color.text_color));
            iv_wp.setColorFilter(getResources().getColor(R.color.black_33));
            iv_badge.setColorFilter(getResources().getColor(R.color.black_33));
            iv_sett.setColorFilter(getResources().getColor(R.color.black_33));

//            home_withdraw.setVisibility(View.GONE);

        });

        llhistory.setOnClickListener(v -> {
            replaceFragment(new HistoryFragment());
            txt_home.setTextColor(getResources().getColor(R.color.black_33));
            txt_his.setTextColor(getResources().getColor(R.color.text_color));
            txt_badge.setTextColor(getResources().getColor(R.color.black_33));
            txt_sett.setTextColor(getResources().getColor(R.color.black_33));

            iv_anim.setColorFilter(getResources().getColor(R.color.black_33));
            iv_wp.setColorFilter(getResources().getColor(R.color.text_color));
            iv_badge.setColorFilter(getResources().getColor(R.color.black_33));
            iv_sett.setColorFilter(getResources().getColor(R.color.black_33));

            home_withdraw.setVisibility(View.GONE);

        });

        llbadge.setOnClickListener(v -> {
            replaceFragment(new BadgesFragment());
            txt_home.setTextColor(getResources().getColor(R.color.black_33));
            txt_his.setTextColor(getResources().getColor(R.color.black_33));
            txt_badge.setTextColor(getResources().getColor(R.color.text_color));
            txt_sett.setTextColor(getResources().getColor(R.color.black_33));

            iv_anim.setColorFilter(getResources().getColor(R.color.black_33));
            iv_wp.setColorFilter(getResources().getColor(R.color.black_33));
            iv_badge.setColorFilter(getResources().getColor(R.color.text_color));
            iv_sett.setColorFilter(getResources().getColor(R.color.black_33));

            home_withdraw.setVisibility(View.GONE);

        });

        ll_sett.setOnClickListener(v -> {
            replaceFragment(new SettingFragment());
            txt_home.setTextColor(getResources().getColor(R.color.black_33));
            txt_his.setTextColor(getResources().getColor(R.color.black_33));
            txt_badge.setTextColor(getResources().getColor(R.color.black_33));
            txt_sett.setTextColor(getResources().getColor(R.color.text_color));

            iv_anim.setColorFilter(getResources().getColor(R.color.black_33));
            iv_wp.setColorFilter(getResources().getColor(R.color.black_33));
            iv_badge.setColorFilter(getResources().getColor(R.color.black_33));
            iv_sett.setColorFilter(getResources().getColor(R.color.text_color));

            home_withdraw.setVisibility(View.GONE);

        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.unit_distance = getSharedPreferences("Goals", 0).getString(UserTable.Unit, "0");
        if (MyCustom_Service.isRunning()) {
            checkSer();
            customHandler = new Handler();
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(this.updateTimerThread, 0L);
        }
    }

    public void checkSer() {
        checkBinder();
        init();
    }

    public void checkBinder() {
        SharedPreferences.Editor edit = getSharedPreferences("ServBind", 0).edit();
        edit.putString(NotificationCompat.CATEGORY_STATUS, "1");
        edit.commit();
        callBind();
    }

    public void init() {
        unit_distance = getSharedPreferences("Goals", 0).getString(UserTable.Unit, "0");
        String speeds = getSpeeds(String.valueOf(currentMetersFromService));
        if (speeds != null) {
            speeds.equals("Infinity");
        }
        String str = currentDistanceFromService;
        String str2 = "";
        if (str == null || str.equals("")) {
            currentDistanceFromService = "0";
        }
        if (unit_distance.equals("Kilometers")) {
            str2 = currentDistanceFromService;
        } else if (unit_distance.equals("Miles")) {
            str2 = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(convertKmsToMiles(Double.parseDouble(currentDistanceFromService)));
        }
        fragment_one.upDateUIFrag(Long.valueOf(currentStepsFromService), "0", currentCaloriesFromService, str2, this.currentProgressFromService);

    }

    public void initService() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(this.updateTimerThread, 0L);
//        checkStepInfo();
    }

    public void callBind() {
        bindService(new Intent(this, MyCustom_Service.class), this.serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void checkStepInfo() {
        if (Build.VERSION.SDK_INT > 32) {
            requestPermissions(MainActivity.INITIAL_PERMS_NOTI, 126);
        }
    }

    public String getSpeeds(String str) {
        try {
            double parseDouble = Double.parseDouble(str);
            double pow = (float) Math.pow(10.0d, 3.0d);
            double d = parseDouble / this.final_time_sec;
            Double.isNaN(pow);
            double floor = Math.floor(d * pow);
            Double.isNaN(pow);
            double d2 = floor / pow;
            this.final_speed = d2;
            this.mSpeed = String.valueOf(d2);
        } catch (Exception unused) {
        }
        String format = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(Double.parseDouble(this.mSpeed));
        SharedPreferences.Editor edit = getSharedPreferences("SpeedVal", 0).edit();
        edit.putString("speed", format);
        edit.apply();
        return format;
    }

    public void StopSer() {
        try {
            if (bound) {
                myService.setCallbacks(null);
                unbindService(serviceConnection);
                bound = false;
            }
        } catch (Exception unused) {
        }
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(8);
        if (Build.VERSION.SDK_INT < 26) {
            Intent intent = new Intent(this, MyCustom_Service.class);
            intent.setAction(MyCustom_Service.ACTION_STOP_FOREGROUND_SERVICE);
            startService(intent);
        } else {
            stopService(new Intent(this, MyCustom_Service.class));
        }

        SharedPreferences.Editor edit = getSharedPreferences("Status", 0).edit();
        edit.putString(NotificationCompat.CATEGORY_SERVICE, "stop");
        edit.commit();
        SharedPreferences.Editor edit2 = getSharedPreferences("RecentValues", 0).edit();
        edit2.putString("step", String.valueOf(currentStepsFromService));
        edit2.putString("speed", spdFinal);
        edit2.putString("dis", currentDistanceFromService);
        edit2.putString(TableAchive.Calorie, String.valueOf(currentCaloriesFromService));
        edit2.putString("cc", String.valueOf(currentProgressFromService));
        edit2.commit();
        Db_fun db_func = new Db_fun(this);
        db = db_func;
        String totalDistance = db_func.getTotalDistance();
        if (totalDistance == null || totalDistance.equals("")) {
            return;
        }
        checkAchieveDistance(Double.parseDouble(totalDistance));
    }

    private void checkAchieveDistance(double d) {
        db_achieve = new AchiveDatabase(this);
        String string = getSharedPreferences("CurrentProfile", 0).getString("name", "0");
        if (d >= 1.0d && d < 2.0d) {
            if (Integer.parseInt(this.db_achieve.getUserAchievementDistance(string)) < 1) {
                db_achieve.updateAchieveDistance(string, "1");
                releaseNotificationAchieveDistance();
            }
        } else if (d >= 2.0d && d < 4.0d) {
            if (Integer.parseInt(db_achieve.getUserAchievementDistance(string)) < 2) {
                db_achieve.updateAchieveDistance(string, "2");
                releaseNotificationAchieveDistance();
            }
        } else if (d >= 4.0d && d < 8.0d) {
            if (Integer.parseInt(db_achieve.getUserAchievementDistance(string)) < 3) {
                db_achieve.updateAchieveDistance(string, "3");
                releaseNotificationAchieveDistance();
            }
        } else if (d >= 8.0d && d < 12.0d) {
            if (Integer.parseInt(db_achieve.getUserAchievementDistance(string)) < 4) {
                db_achieve.updateAchieveDistance(string, "4");
                releaseNotificationAchieveDistance();
            }
        } else if (d >= 12.0d && d < 16.0d) {
            if (Integer.parseInt(db_achieve.getUserAchievementDistance(string)) < 5) {
                db_achieve.updateAchieveDistance(string, "5");
                releaseNotificationAchieveDistance();
            }
        } else if (d >= 16.0d && d < 20.0d) {
            if (Integer.parseInt(db_achieve.getUserAchievementDistance(string)) < 6) {
                db_achieve.updateAchieveDistance(string, "6");
                releaseNotificationAchieveDistance();
            }
        } else if (d >= 20.0d && d < 30.0d) {
            if (Integer.parseInt(db_achieve.getUserAchievementDistance(string)) < 7) {
                db_achieve.updateAchieveDistance(string, "7");
                releaseNotificationAchieveDistance();
            }
        } else if (d >= 30.0d && d < 50.0d) {
            if (Integer.parseInt(db_achieve.getUserAchievementDistance(string)) < 8) {
                db_achieve.updateAchieveDistance(string, "8");
                releaseNotificationAchieveDistance();
            }
        } else if (d < 50.0d || Integer.parseInt(db_achieve.getUserAchievementDistance(string)) >= 9) {
        } else {
            db_achieve.updateAchieveDistance(string, "9");
            releaseNotificationAchieveDistance();
        }
    }

    public void releaseNotificationAchieveDistance() {
        PendingIntent activity;
        createNotificationChannelAchieve(this.chanelIdAchieveDistance);
        NotificationManagerCompat from = NotificationManagerCompat.from(this);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(872415232);
        if (Build.VERSION.SDK_INT >= 23) {
            activity = PendingIntent.getActivity(this, 0, intent, 201326592);
        } else {
            activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        from.notify(143, new NotificationCompat.Builder(this, this.chanelIdAchieveDistance).setSmallIcon(R.drawable.logo).setContentTitle(getString(R.string.congrats)).setContentText(getString(R.string.challenge_done_distance)).setSound(RingtoneManager.getDefaultUri(2)).setAutoCancel(true).setPriority(0).setContentIntent(activity).build());
    }

    private void createNotificationChannelAchieve(String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(str, "Challenge", 3));
        }
    }

    public void startSer() {
        SensorManager sensorManager = (SensorManager) getSystemService("sensor");
        if (sensorManager.getDefaultSensor(1) != null) {
            checkDate();
            SharedPreferences.Editor edit = getSharedPreferences("Status", 0).edit();
            edit.putString(NotificationCompat.CATEGORY_SERVICE, "start");
            edit.commit();
            SharedPreferences.Editor edit2 = getSharedPreferences("ServRunCount", 0).edit();
            edit2.putInt("counts", 0);
            edit2.commit();
            SharedPreferences.Editor edit3 = getSharedPreferences("ServBind", 0).edit();
            edit3.putString(NotificationCompat.CATEGORY_STATUS, "0");
            edit3.commit();
            if (Build.VERSION.SDK_INT < 26) {
                Intent intent = new Intent(this, MyCustom_Service.class);
                ServiceConnection serviceConnection = this.serviceConnection;
                if (serviceConnection != null) {
                    bindService(intent, serviceConnection, 1);
                }
                startService(intent);
            } else {
                Intent intent2 = new Intent(this, MyCustom_Service.class);
                ServiceConnection serviceConnection2 = this.serviceConnection;
                if (serviceConnection2 != null) {
                    bindService(intent2, serviceConnection2, 1);
                }
                intent2.setAction(MyCustom_Service.ACTION_START_FOREGROUND_SERVICE);
                ContextCompat.startForegroundService(this, intent2);
            }
            initService();
            return;
        }
        Toast.makeText(this, getString(R.string.not_compat), 0).show();
    }

    void checkDate() {
        String string = getSharedPreferences("SavedDate", 0).getString("saveDte", "0");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        if (string.equals(format)) {
            return;
        }
        SharedPreferences.Editor edit = getSharedPreferences("SavedDate", 0).edit();
        edit.putString("saveDte", format);
        edit.commit();
    }


    public void checkForRate() {
        if (!getSharedPreferences("ReviewOnce", 0).getString("check", "0").equals("0") || Integer.parseInt(this.db.getTotalDaySteps()) <= 100) {
            return;
        }
        SharedPreferences.Editor edit = getSharedPreferences("ReviewOnce", 0).edit();
        edit.putString("check", "1");
        edit.apply();
        final ReviewManager create = ReviewManagerFactory.create(this);
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(Task task) {
                MainActivity.this.checkRate(create, task);
            }
        });
    }

    public void checkRate(ReviewManager reviewManager, Task task) {
        if (task.isSuccessful()) {
            reviewManager.launchReviewFlow(this, (ReviewInfo) task.getResult()).addOnCompleteListener(MainActList.INSTANCE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (mToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void checkPermission() {
        if (getSharedPreferences("permission", 0).getString("askedonce", "0").equals("true") || Build.VERSION.SDK_INT <= 28) {
            return;
        }
        perm = Arrays.asList(getResources().getStringArray(R.array.permissions));
        requestPermissions(INITIAL_PERMS, 123);
        SharedPreferences.Editor edit = getSharedPreferences("permission", 0).edit();
        edit.putString("askedonce", "true");
        edit.apply();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        FragmentManager supportFragmentManager = MainActivity.this.getSupportFragmentManager();
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackEntryCount; i++) {
            supportFragmentManager.popBackStack();
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    public void setNotiDaily() {
        Calendar calendar = Calendar.getInstance();
        calendar2 = calendar;
        calendar.set(11, 10);
        calendar2.set(12, 10);
        calendar2.set(13, 0);
        Intent intent = new Intent(this, AlReceiverDailyNoti.class);
        if (Build.VERSION.SDK_INT >= 23) {
            pendingIntent2 = PendingIntent.getBroadcast(this, 10, intent, 201326592);
        } else {
            pendingIntent2 = PendingIntent.getBroadcast(this, 10, intent, 134217728);
        }
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).setRepeating(AlarmManager.RTC_WAKEUP, 86400000 + this.calendar2.getTimeInMillis(), 86400000L, this.pendingIntent2);
        SharedPreferences.Editor edit = getSharedPreferences("AlarmDailyBB", 0).edit();
        edit.putString("statusAlarmBB", "1");
        edit.apply();
    }

}
