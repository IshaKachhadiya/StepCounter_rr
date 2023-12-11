package com.mysweat.pedocounter.walkerstep.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.facebook.ads.AudienceNetworkAds;
import com.google.gson.Gson;
import com.mysweat.pedocounter.walkerstep.Database.Db_fun;
import com.mysweat.pedocounter.walkerstep.Database.AchiveDatabase;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.AdInterGD;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.MyApplicationAppOpen;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NewAppOpenAdManager;
import com.mysweat.pedocounter.walkerstep.PhpAd.api.AdsApiClient;
import com.mysweat.pedocounter.walkerstep.PhpAd.api.AdsApiInterface;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.utils.MySharePreferences;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends Activity {
    Db_fun db;
    AchiveDatabase db_achieve;
    List<String> list;
    String name;
    private NewAppOpenAdManager appOpenAdManager;
    public static int click, mcoin;
    public static String Privacy_police,appopen, ad_inter, fb_inter, unity_inter, ad_native, fb_native, unity_id, vc,nativepp, rewarded;
    int splashads;
    int app_status;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);

        reffereclient = InstallReferrerClient.newBuilder(this).build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(160);
        notificationManager.cancel(141);
        notificationManager.cancel(142);
        notificationManager.cancel(143);
        notificationManager.cancel(144);
        notificationManager.cancel(274);
        db = new Db_fun(this);
        ArrayList<String> allDB2 = db.getAllDB2();
        this.list = allDB2;

        if (getSharedPreferences("SavedDateFirstTime", 0).getString("firstTimeDateSet", "0").equals("0")) {
            String format = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            SharedPreferences.Editor edit2 = getSharedPreferences("SavedDate", 0).edit();
            edit2.putString("saveDte", format);
            edit2.apply();
            SharedPreferences.Editor edit3 = getSharedPreferences("SavedDateFirstTime", 0).edit();
            edit3.putString("firstTimeDateSet", "1");
            edit3.apply();
            if (this.list.size() > 0) {
                this.name = getSharedPreferences("CurrentProfile", 0).getString("name", "0");
                AchiveDatabase db_func_achieve = new AchiveDatabase(this);
                this.db_achieve = db_func_achieve;
                if (db_func_achieve.checkAchieveEntrySteps(this.name).size() == 0) {
                    db_achieve.insertAchieveSteps(this.name, "0");
                    db_achieve.insertAchieveDistance(this.name, "0");
                    db_achieve.insertAchieveCalories(this.name, "0");
                }
                int parseInt = Integer.parseInt(this.db.getTotalDaySteps());
                int parseInt2 = Integer.parseInt(this.db.getTotalCalories());
                double parseDouble = Double.parseDouble(this.db.getTotalDistance());
                checkAchieveSteps(parseInt);
                checkAchieveCalories(parseInt2);
                checkAchieveDistance(parseDouble);
            }
        }
        SharedPreferences.Editor edit4 = getSharedPreferences("OneTime", 0).edit();
        edit4.putString("val", "0");
        edit4.apply();

        getnetwork();
        MySharePreferences.getInstance(SplashActivity.this);

        AudienceNetworkAds.initialize(SplashActivity.this);
        getUser_Mode();

    }
    private void getnetwork() {
        if (isNetworkAvailable(this)) {
            new GetKeyid().execute();
        } else {
            Dialog nointer = new Dialog(SplashActivity.this);
            nointer.setContentView(R.layout.app_dialog_internet);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            nointer.setCancelable(false);
            TextView btn_no = nointer.findViewById(R.id.btn_no);
            btn_no.setOnClickListener(v -> {
                if (isNetworkAvailable(SplashActivity.this)) {
                    new GetKeyid().execute();
                    nointer.dismiss();
                } else {
                    Toast.makeText(SplashActivity.this, "Required internet connection..", Toast.LENGTH_SHORT).show();
                }
            });
            nointer.show();
        }
    }
    InstallReferrerClient reffereclient;
    String ck_type;

    public void getUser_Mode() {
        reffereclient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        ReferrerDetails response = null;

                        try {
                            response = reffereclient.getInstallReferrer();
                            String referrer = response.getInstallReferrer();
                            String data[] = referrer.split("&");

                            ck_type = data[1];

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        ck_type = "not_support";
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        ck_type = "service_unavailable";
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {

            }
        });
    }
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class GetKeyid extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String kk = "";
            try {
                ArrayList<String> urls = new ArrayList<>();
                URL url = new URL("https://myexternalip.com/raw");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;

                while ((str = in.readLine()) == null) {
                    urls.add(str);
                }
                in.close();
                kk = str;
                ArrayList<String> urls1 = new ArrayList<>();
                String u = "http://ip-api.com/php/" + kk;
                URL url1 = new URL(u);
                HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
                BufferedReader in1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                String str1;
                while ((str1 = in1.readLine()) == null) {
                    urls1.add(str1);
                }
                in1.close();
                kk = str1;
            } catch (java.io.IOException e) {
                e.printStackTrace();

                try {
                    java.util.Scanner s = new java.util.Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A");
                    kk = s.next();

                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            return kk;
        }

        @Override
        protected void onPostExecute(String kk1) {
            super.onPostExecute(kk1);
            getId(kk1, "");
        }
    }

    public void getId(String s, String ckk) {
        AdsApiInterface apiService = AdsApiClient.getClient().create(AdsApiInterface.class);
        Call call = apiService.getid(s, ckk);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    try {
                        if (statusCode == 200) {
                            String data = new Gson().toJson(response.body());
                            JSONObject jso = new JSONObject(data);

                            click = jso.getInt("click");
                            app_status = jso.getInt("app_status");
                            splashads = jso.getInt("splashads");

                            ad_inter = jso.getString("ad_inter");
                            rewarded = jso.getString("rewarded");
                            fb_inter = jso.getString("fb_inter");
                            unity_inter = jso.getString("unity_inter");

                            ad_native = jso.getString("ad_native");
                            fb_native = jso.getString("fb_native");
                            nativepp = jso.getString("nativepp");

                            unity_id = jso.getString("unity_id");
                            mcoin = jso.getInt("mcoin");
                            vc = jso.getString("vc");

                            appopen = jso.getString("appopen");

                            Privacy_police = jso.getString("Privacy_police");

                            int back = jso.getInt("back");

                            String app_link = jso.getString("app_link");


                            ADSharedPref.setInteger(SplashActivity.this, ADSharedPref.BACK, back);
                            ADSharedPref.setInteger(SplashActivity.this, ADSharedPref.CLICK, click);

                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.unity_intersitial, unity_inter);
                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.FBAD_INTER, fb_inter);
                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.admob_interstital, ad_inter);
                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.REWARd, rewarded);
                            ADSharedPref.setInteger(SplashActivity.this, ADSharedPref.MCOIN, mcoin);
                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.NATIVEAPP, nativepp);

                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.FBAD_NATIVE, fb_native);

                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.admob_native, ad_native);

                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.unity_adid, unity_id);

                            ADSharedPref.setInteger(SplashActivity.this, ADSharedPref.APP_STATUS, app_status);
                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.APP_LINK, app_link);

                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.openads, appopen);
                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.VC, vc);
                            ADSharedPref.setInteger(SplashActivity.this, ADSharedPref.SPLASH_AD, splashads);

                            ADSharedPref.setString(SplashActivity.this, ADSharedPref.Privacy_police, Privacy_police);

                            int stats = ADSharedPref.getInteger(SplashActivity.this, ADSharedPref.APP_STATUS, 0);
                            if (stats == 0) {
                                Load_next_intent();
                            } else {
                                dialog();
                            }
                        }
                    } catch (Exception e) {

                        int stats = ADSharedPref.getInteger(SplashActivity.this, ADSharedPref.APP_STATUS, 0);
                        if (stats == 0) {
                            Load_next_intent();
                        } else {
                            dialog();
                        }
                    }
                } else {
                    int stats = ADSharedPref.getInteger(SplashActivity.this, ADSharedPref.APP_STATUS, 0);
                    if (stats == 0) {
                        Load_next_intent();
                    } else {
                        dialog();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Load_next_intent();
            }
        });
    }

    private void Load_next_intent() {
        appOpenAdManager = new NewAppOpenAdManager(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (splashads == 0) {
                    appOpenAdManager.showAdIfAvailable(SplashActivity.this, new MyApplicationAppOpen.OnShowAdCompleteListener() {
                        @Override
                        public void onShowAdComplete() {
                            if (list.size() > 0) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                return;
                            }
                            startActivity(new Intent(SplashActivity.this, PermissitionActivity.class));
                        }
                    });
                } else {
                    Next_Step();
                }
            }
        }, 5500);
    }

    private void Next_Step() {
        list = db.getAllDB2();
        if (list.size() > 0) {
            AdInterGD.getInstance().showsplaceInter(SplashActivity.this, new AdInterGD.MyCallback() {
                @Override
                public void callbackCall() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            });
            return;
        }
        AdInterGD.getInstance().showsplaceInter(SplashActivity.this, new AdInterGD.MyCallback() {
            @Override
            public void callbackCall() {
                startActivity(new Intent(SplashActivity.this, PermissitionActivity.class));
            }
        });
    }

    public void dialog() {
        String applink = ADSharedPref.getString(getApplicationContext(), ADSharedPref.APP_LINK, "0");
        try {
            Dialog redirect;
            redirect = new Dialog(SplashActivity.this);
            redirect.requestWindowFeature(Window.FEATURE_NO_TITLE);
            redirect.setContentView(R.layout.app_dialog_redirect);
            redirect.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = redirect.getWindow();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            if (window != null) {
                window.setGravity(Gravity.CENTER);
                window.setLayout((int) (0.9 * Resources.getSystem().getDisplayMetrics().widthPixels), android.widget.Toolbar.LayoutParams.WRAP_CONTENT);
            }
            redirect.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            redirect.show();

            AppCompatButton btn = redirect.findViewById(R.id.btn_ok);
            btn.setOnClickListener(v -> {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(applink));
                startActivity(viewIntent);
                finishAffinity();
                redirect.dismiss();

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAchieveSteps(int i) {
        if (i >= 3000 && i < 6000) {
            this.db_achieve.updateAchieveSteps(this.name, "1");
        } else if (i >= 6000 && i < 10000) {
            this.db_achieve.updateAchieveSteps(this.name, "2");
        } else if (i >= 10000 && i < 15000) {
            this.db_achieve.updateAchieveSteps(this.name, "3");
        } else if (i >= 15000 && i < 20000) {
            this.db_achieve.updateAchieveSteps(this.name, "4");
        } else if (i >= 20000 && i < 30000) {
            this.db_achieve.updateAchieveSteps(this.name, "5");
        } else if (i >= 30000 && i < 40000) {
            this.db_achieve.updateAchieveSteps(this.name, "6");
        } else if (i >= 40000 && i < 60000) {
            this.db_achieve.updateAchieveSteps(this.name, "7");
        } else if (i >= 60000 && i < 80000) {
            this.db_achieve.updateAchieveSteps(this.name, "8");
        } else if (i >= 80000) {
            this.db_achieve.updateAchieveSteps(this.name, "9");
        }
    }

    private void checkAchieveCalories(int i) {
        if (i >= 10 && i < 20) {
            this.db_achieve.updateAchieveCalories(this.name, "1");
        } else if (i >= 20 && i < 40) {
            this.db_achieve.updateAchieveCalories(this.name, "2");
        } else if (i >= 40 && i < 60) {
            this.db_achieve.updateAchieveCalories(this.name, "3");
        } else if (i >= 60 && i < 100) {
            this.db_achieve.updateAchieveCalories(this.name, "4");
        } else if (i >= 100 && i < 150) {
            this.db_achieve.updateAchieveCalories(this.name, "5");
        } else if (i >= 150 && i < 200) {
            this.db_achieve.updateAchieveCalories(this.name, "6");
        } else if (i >= 200 && i < 300) {
            this.db_achieve.updateAchieveCalories(this.name, "7");
        } else if (i >= 300 && i < 400) {
            this.db_achieve.updateAchieveCalories(this.name, "8");
        } else if (i >= 400) {
            this.db_achieve.updateAchieveCalories(this.name, "9");
        }
    }

    private void checkAchieveDistance(double d) {
        AchiveDatabase db_func_achieve = new AchiveDatabase(this);
        this.db_achieve = db_func_achieve;
        if (d >= 1.0d && d < 2.0d) {
            db_func_achieve.updateAchieveDistance(this.name, "1");
        } else if (d >= 2.0d && d < 4.0d) {
            this.db_achieve.updateAchieveDistance(this.name, "2");
        } else if (d >= 4.0d && d < 8.0d) {
            this.db_achieve.updateAchieveDistance(this.name, "3");
        } else if (d >= 8.0d && d < 12.0d) {
            this.db_achieve.updateAchieveDistance(this.name, "4");
        } else if (d >= 12.0d && d < 16.0d) {
            this.db_achieve.updateAchieveDistance(this.name, "5");
        } else if (d >= 16.0d && d < 20.0d) {
            this.db_achieve.updateAchieveDistance(this.name, "6");
        } else if (d >= 20.0d && d < 30.0d) {
            this.db_achieve.updateAchieveDistance(this.name, "7");
        } else if (d >= 30.0d && d < 50.0d) {
            this.db_achieve.updateAchieveDistance(this.name, "8");
        } else if (d >= 50.0d) {
            this.db_achieve.updateAchieveDistance(this.name, "9");
        }
    }
}
