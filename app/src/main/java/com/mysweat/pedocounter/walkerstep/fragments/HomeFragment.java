package com.mysweat.pedocounter.walkerstep.fragments;

import static android.content.Context.MODE_PRIVATE;
import static com.mysweat.pedocounter.walkerstep.utils.offline.getAllCoin;
import static com.unity3d.services.core.misc.Utilities.runOnUiThread;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.mysweat.pedocounter.walkerstep.service.MyCustom_Service;
import com.mysweat.pedocounter.walkerstep.Database.Db_fun;
import com.mysweat.pedocounter.walkerstep.Database.UserTable;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.AdInterGD;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NativeAds.AdsNativeHelper;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.utils.offline;
import com.mysweat.pedocounter.walkerstep.ui.LuckSlotActivity;
import com.mysweat.pedocounter.walkerstep.ui.GlassFlipActivity;
import com.mysweat.pedocounter.walkerstep.ui.LuckySpinWinActivity;
import com.mysweat.pedocounter.walkerstep.ui.MainActivity;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;


public class HomeFragment extends Fragment {
    private static WeakReference<TextView> cal_ref;
    private static WeakReference<TextView> dis_ref;
    private static WeakReference<TextView> steps_ref, main_step_coin;
    Button btn_start, btn_pause, btn_watch_get_coin;
    public static TextView tv_coin;
    public TextView txt_head, txt_step, txt_dis, distance_txt, goal_txt, txt_f, txt_s, txt_t_, txt_four_, txt_step_coin, txt_step_get_coin, txt_invite_times, txt_watch_times, txt_times, txt_water_coin;
    Activity activity;
    public TextView calorie_txt;
    Db_fun db;
    String goal;
    Context mContext;
    MainActivity mainAct;
    String unit_distance;
    View view;
    boolean showed = false;
    public int setted_goal = 0;
    ImageView ic_spin, ic_luck_slot;
    offline offlineVar;
    TextView watch_start_btn;
    LinearLayout ll_water_habit;
    RelativeLayout rl_ad,rl_one, rl_two, rl_three, rl_four, rl_add_coin, rl_watch_add_coin, rl_invite_coin;
    SimpleDateFormat simpleDateFormat;

    public double convertKmsToMiles(double d) {
        return d * 0.621371d;
    }

    public HomeFragment(MainActivity mainAct) {
        this.mainAct = mainAct;

    }

    private int[] multipliers = {2, 3, 4, 100, 4, 3, 2};
    private int currentIndex = 0;
    private Handler handler = new Handler();
    private int coinCounttt = 5000;
    public static int coinCount = 0;
    LinearLayout ll_toolbar, lll, ll_earn_more, ll_simple_ly, ll_invite;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String DIALOG_SHOWN_KEY = "dialogShown";
    private static final String COUNTER_KEY = "counter";
    private static final String CLICK_COUNT_KEY = "click_count";
    private static final String INVITE_CLICK_COUNT_KEY = "invite_click_count";
    private static final String LAST_CLICKED_DATE_KEY = "last_clicked_date";
    private static final String INVITE_LAST_CLICKED_DATE_KEY = "ivite_last_clicked_date";
    private int clickCount = 0;
    private int invite_clickCount = 0;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_one, viewGroup, false);
        view = inflate;
        mContext = inflate.getContext();
        offlineVar = new offline(requireActivity());

        txt_step = this.view.findViewById(R.id.stepTxt);
        btn_start = this.view.findViewById(R.id.btn_start);
        btn_pause = this.view.findViewById(R.id.btn_stop);
        tv_coin = view.findViewById(R.id.txt_home_coin);

        btn_watch_get_coin = this.view.findViewById(R.id.btn_watch_get_coin);
        distance_txt = (TextView) this.view.findViewById(R.id.distance_txt);
        calorie_txt = (TextView) this.view.findViewById(R.id.calorie_txt);
        goal_txt = view.findViewById(R.id.goal_txt);
        txt_dis = view.findViewById(R.id.distance);
        ic_spin = view.findViewById(R.id.ic_spin);
        ic_luck_slot = view.findViewById(R.id.ic_luck_slot);
        txt_f = view.findViewById(R.id.txt_f_);
        txt_s = view.findViewById(R.id.txt_s_);
        txt_t_ = view.findViewById(R.id.txt_t_);
        txt_four_ = view.findViewById(R.id.txt_four_);
        txt_step_coin = view.findViewById(R.id.txt_step_coin);
        txt_step_get_coin = view.findViewById(R.id.txt_step_get_coin);
        txt_invite_times = view.findViewById(R.id.txt_invite_times);
        watch_start_btn = view.findViewById(R.id.watch_start_btn);
        txt_watch_times = view.findViewById(R.id.txt_watch_times);
        txt_times = view.findViewById(R.id.txt_times);
        txt_water_coin = view.findViewById(R.id.txt_water_coin);
        rl_ad = view.findViewById(R.id.rl_ad);
        rl_one = view.findViewById(R.id.rl_one);
        rl_two = view.findViewById(R.id.rl_two);
        rl_three = view.findViewById(R.id.rl_three);
        rl_four = view.findViewById(R.id.rl_four);
        ll_toolbar = view.findViewById(R.id.tool_bar);
        lll = view.findViewById(R.id.lll);
        ll_earn_more = view.findViewById(R.id.ll_earn_more);
        ll_simple_ly = view.findViewById(R.id.ll_simple_ly);
        rl_add_coin = view.findViewById(R.id.rl_add_coin);
        rl_watch_add_coin = view.findViewById(R.id.rl_watch_add_coin);
        rl_invite_coin = view.findViewById(R.id.rl_invite_coin);
        txt_head = view.findViewById(R.id.txt_head);
        ll_invite = view.findViewById(R.id.ll_invite);
        ll_water_habit = view.findViewById(R.id.ll_water_habit);

        steps_ref = new WeakReference<>(this.txt_step);
        main_step_coin = new WeakReference<>(this.txt_step_coin);
        dis_ref = new WeakReference<>(this.distance_txt);
        cal_ref = new WeakReference<>(this.calorie_txt);

        db = new Db_fun(this.mContext);
        activity = getActivity();

//        mainAct.startSer();

        Click();
        ObserVableData();
        tv_coin.setText(String.valueOf(new offline(requireActivity()).getTotalCoins()));
        get();


        showed = true;
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        clickCount = sharedPreferences.getInt(CLICK_COUNT_KEY, 0);
        invite_clickCount = sharedPreferences.getInt(INVITE_CLICK_COUNT_KEY, 0);

        String lastClickedDate = sharedPreferences.getString(LAST_CLICKED_DATE_KEY, "");
        String invite_lastClickedDate = sharedPreferences.getString(INVITE_LAST_CLICKED_DATE_KEY, "");

        // Check if the last clicked date is different from today
        if (!isSameDay(lastClickedDate)) {
            // Reset the click count if it's a new day
            clickCount = 0;
        }

        updateClickCountTextView();

        if (!isSameDay(invite_lastClickedDate)) {
            // Reset the click count if it's a new day
            invite_clickCount = 0;
        }

        updateInviteClickCountTextView();

        int my_coin = ADSharedPref.getInteger(getContext(), ADSharedPref.MCOIN, 0);
        if (my_coin == 0) {
            ll_toolbar.setVisibility(View.VISIBLE);
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            rl_four.setVisibility(View.VISIBLE);
            ll_earn_more.setVisibility(View.VISIBLE);
            lll.setVisibility(View.VISIBLE);
            ll_simple_ly.setVisibility(View.GONE);
            rl_ad.setVisibility(View.GONE);
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String currentDate = dateFormat.format(new Date());

            boolean dialogShownToday = sharedPreferences.getBoolean(DIALOG_SHOWN_KEY + currentDate, false);

            if (!dialogShownToday) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WelcmBackDialog();
                    }
                }, 500);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(DIALOG_SHOWN_KEY + currentDate, true);
                editor.apply();
            }
        } else {
            ll_toolbar.setVisibility(View.GONE);
            rl_one.setVisibility(View.GONE);
            rl_two.setVisibility(View.GONE);
            rl_three.setVisibility(View.GONE);
            rl_four.setVisibility(View.GONE);
            ll_earn_more.setVisibility(View.GONE);
            lll.setVisibility(View.GONE);
            ll_simple_ly.setVisibility(View.VISIBLE);
            rl_ad.setVisibility(View.VISIBLE);
            new AdsNativeHelper().shownativeads_small(getActivity(), view.findViewById(R.id.Admob_Native_Frame_small), view.findViewById(R.id.native_ad_container));

        }
        return this.view;
    }

    private void get() {
        String str;
        String str2;
        String str3;
        int i;
        String goal = this.db.getGoal(this.mContext.getSharedPreferences("CurrentProfile", 0).getString("name", "0"));
        this.goal = goal;

        setted_goal = Integer.parseInt(goal);
        goal_txt.setText(this.setted_goal + "\n" + getString(R.string.goal));
        String string = this.mContext.getSharedPreferences("Goals", 0).getString(UserTable.Unit, "0");
        this.unit_distance = string;
        if (string.equals("Miles")) {
            this.txt_dis.setText(getString(R.string.distance_mi));
        } else {
            this.txt_dis.setText(getString(R.string.distance_km));
        }
        if (MyCustom_Service.isRunning()) {
            if (mContext.getSharedPreferences("Status", 0).getString(NotificationCompat.CATEGORY_SERVICE, "0").equals("start")) {
                btn_start.setVisibility(View.GONE);
                btn_pause.setVisibility(View.VISIBLE);
                mainAct.init();
                return;
            }
            btn_start.setVisibility(View.VISIBLE);
            btn_pause.setVisibility(View.GONE);
            return;
        }
        String fromCurrDate = this.db.getFromCurrDate(getOrderedFullDate());
        if (fromCurrDate == null || fromCurrDate.equals("")) {
            str = "0";
            str2 = str;
            str3 = str2;
            i = 0;
        } else {
            String[] split = fromCurrDate.split(",");
            str2 = split[0];
            str3 = split[1];
            str = split[2];
        }
        txt_step.setText(str2);
        txt_step_coin.setText(str2);

        requireActivity().runOnUiThread(() -> {
            int number = Integer.parseInt(str2);
            coinCount = 0;
            for (int j = 1; j < number; j++) {
                if (j % 10 == 0) {
                    coinCount++;
                }
            }
            txt_step_get_coin.setText(String.valueOf(coinCount));
        });


        calorie_txt.setText(str);
        if (unit_distance.equals("Kilometers")) {
            distance_txt.setText(str3);
        } else if (unit_distance.equals("Miles")) {
            distance_txt.setText(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(convertKmsToMiles(Double.parseDouble(str3))));
        }
        if (mContext.getSharedPreferences("Status", 0).getString(NotificationCompat.CATEGORY_SERVICE, "0").equals("start")) {
            btn_start.setVisibility(View.GONE);
            btn_pause.setVisibility(View.VISIBLE);
        }
    }

    private void ObserVableData() {
        getAllCoin.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_coin.setText(String.valueOf(new offline(requireActivity()).getTotalCoins()));
            }
        });
    }

    Animation slide_in_right, slide_in_left, txtheading;

    private void Click() {
        startDaily();
        ll_water_habit.setOnClickListener(v -> {
            AdInterGD.getInstance().loadInterOne(getActivity(), new AdInterGD.MyCallback() {
                @Override
                public void callbackCall() {
                    startActivity(new Intent(getContext(), GlassFlipActivity.class));
                }
            });
        });
        ll_invite.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        btn_watch_get_coin.setOnClickListener(v -> {
            offlineVar = new offline(requireActivity());
            offlineVar.SaveTotalCoins(coinCount);
            tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
        });

        btn_start.setOnClickListener(v -> {
            btn_start.setVisibility(View.GONE);
            btn_pause.setVisibility(View.VISIBLE);
            mainAct.startSer();

        });

        btn_pause.setOnClickListener(v -> {
            btn_pause.setVisibility(View.GONE);
            btn_start.setVisibility(View.VISIBLE);
            mainAct.StopSer();
        });

        slide_in_right = AnimationUtils.loadAnimation(requireContext().getApplicationContext(), R.anim.slide_in_right);
        slide_in_left = AnimationUtils.loadAnimation(requireContext().getApplicationContext(), R.anim.slide_in_left);
        txtheading = AnimationUtils.loadAnimation(requireContext().getApplicationContext(), R.anim.slide_in_right);

        ic_spin.startAnimation(slide_in_right);
        ic_luck_slot.startAnimation(slide_in_left);
        txt_head.startAnimation(slide_in_right);

        ic_spin.setOnClickListener(v -> {
            AdInterGD.getInstance().loadInterOne(requireActivity(), new AdInterGD.MyCallback() {
                @Override
                public void callbackCall() {
                    startActivity(new Intent(getContext(), LuckySpinWinActivity.class));
                    requireActivity().overridePendingTransition(
                            R.anim.activity_slide_from_right,
                            R.anim.activity_slide_to_left
                    );
                }
            });
        });

        ic_luck_slot.setOnClickListener(v -> {
            AdInterGD.getInstance().loadInterOne(requireActivity(), new AdInterGD.MyCallback() {
                @Override
                public void callbackCall() {
                    startActivity(new Intent(getContext(), LuckSlotActivity.class));
                    requireActivity().overridePendingTransition(
                            R.anim.activity_slide_from_right,
                            R.anim.activity_slide_to_left
                    );
                }
            });
        });

        rl_one.setOnClickListener(v -> {
            CoinDg();
        });

        rl_two.setOnClickListener(v -> {
            CoinDg();
        });

        rl_three.setOnClickListener(v -> {
            CoinDg();
        });

        rl_four.setOnClickListener(v -> {
            CoinDg();

        });
        rl_invite_coin.setOnClickListener(v -> {
            if (invite_clickCount < 2) {
                invite_clickCount++;
                updateInviteClickCountTextView();
                inviteClickCoin();
                InviteCoinDg();
            }
        });

        rl_watch_add_coin.setOnClickListener(v -> {
            if (clickCount < 9) {
                clickCount++;
                updateClickCountTextView();
                saveClickCountAndDateToSharedPreferences();
                Watch_Coin2125Dg();
            }
        });
    }

    private void updateClickCountTextView() {
        txt_watch_times.setText("" + clickCount + "/9");
    }

    private void updateInviteClickCountTextView() {
        txt_invite_times.setText("" + invite_clickCount + "/2");
    }

    private void saveClickCountAndDateToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CLICK_COUNT_KEY, clickCount);
        editor.putString(LAST_CLICKED_DATE_KEY, getCurrentDate());
        editor.apply();
    }

    private void inviteClickCoin() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(INVITE_CLICK_COUNT_KEY, invite_clickCount);
        editor.putString(INVITE_LAST_CLICKED_DATE_KEY, getCurrentDate());
        editor.apply();
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        return dateFormat.format(date);
    }

    private boolean isSameDay(String lastClickedDate) {
        String today = getCurrentDate();
        return today.equals(lastClickedDate);
    }

    SharedPreferences sharedPreferences;

    private int[] predefinedNumbers = {1000, 500, 200, 800, 600, 100, 50, 300};

    private int generateInviteNumber() {
        Random random = new Random();
        return random.nextInt(invite_rndm.length);
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(predefinedNumbers.length);
    }

    private int generateBubbleRandomNumber() {
        Random random = new Random();
        return random.nextInt(bubble.length);
    }

    private void WelcmBackDialog() {
        int randomNumber = generateRandomNumber();
        int selectedNumber = predefinedNumbers[randomNumber];

        final Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_exit__screen);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        TextView txt_claim = dialog.findViewById(R.id.txt_claim);
        TextView txt_rndm_no = dialog.findViewById(R.id.txt_rndm_no);
        LinearLayout ll_bonus_coin = dialog.findViewById(R.id.ll_bonus_coin);
        txt_rndm_no.setText(String.valueOf(selectedNumber));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentNumber = multipliers[currentIndex];
                txt_claim.setText("Claim x" + String.valueOf(currentNumber));
                currentIndex = (currentIndex + 1) % multipliers.length; // Loop through the numbers
                handler.postDelayed(this, 100); // Repeat every 1 second (1000 milliseconds)
            }
        }, 0);

        dialog.show();

        ((LinearLayout) dialog.findViewById(R.id.ll_bonus_coin)).setOnClickListener(v -> {
            dialog.dismiss();

            AdInterGD.getInstance().loadRewardInterOne(getActivity(), new AdInterGD.MyCallback() {
                @Override
                public void callbackCall() {
                    if (currentIndex < multipliers.length) {
                        int multiplier = multipliers[currentIndex];
                        coinCounttt *= multiplier;

                        txt_claim.setText("Claim x" + formatCoinValue(coinCounttt));
                        currentIndex = (currentIndex + 1) % multipliers.length; // Loop through the multipliers
                        offline offlineVar = new offline(requireActivity());
                        offlineVar.SaveTotalCoins(coinCounttt);
                        tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                        tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                        Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.coin_collect_animation);
                        ll_bonus_coin.startAnimation(animation);
                    }
                }
            });
        });

        ((TextView) dialog.findViewById(R.id.txt_no_tx)).setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private String formatCoinValue(int value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(value);
    }

    private int[] watchAd_rndm = {250, 350, 430, 111, 420, 100, 50, 164, 250};

    public void Watch_Coin2125Dg() {
        int randomNumber = generateRandomNumber();
        int selectedNumber = watchAd_rndm[randomNumber];
        try {
            final Dialog dialog = new Dialog(requireActivity());
            dialog.setContentView(R.layout.lay_congrats_3600);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);
            new AdsNativeHelper().shownativeads_small(requireActivity(), dialog.findViewById(R.id.Admob_Native_Frame_small), dialog.findViewById(R.id.native_ad_container));

            ((TextView) dialog.findViewById(R.id.cooins)).setText(String.valueOf(selectedNumber));
            ((ImageView) dialog.findViewById(R.id.collect1x)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    AdInterGD.getInstance().loadRewardInterOne(getActivity(), new AdInterGD.MyCallback() {
                        @Override
                        public void callbackCall() {
                            offlineVar = new offline(requireActivity());
                            offlineVar.SaveTotalCoins(selectedNumber);
                            tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                        }
                    });
                }
            });
            ((TextView) dialog.findViewById(R.id.txt_go_on)).setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final int[] invite_rndm = {3000, 2000};

    public void InviteCoinDg() {
        int randomNumber = generateInviteNumber();
        int slno = invite_rndm[randomNumber];

        final Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.lay_congrats_3600);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window window = dialog.getWindow();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setLayout((int) (0.9 * Resources.getSystem().getDisplayMetrics().widthPixels), android.widget.Toolbar.LayoutParams.WRAP_CONTENT);
        }
        new AdsNativeHelper().shownativeads_small(requireActivity(), dialog.findViewById(R.id.Admob_Native_Frame_small), dialog.findViewById(R.id.native_ad_container));

        ((TextView) dialog.findViewById(R.id.cooins)).setText(String.valueOf(slno));
        ((ImageView) dialog.findViewById(R.id.collect1x)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                AdInterGD.getInstance().loadRewardInterOne(getActivity(), new AdInterGD.MyCallback() {
                    @Override
                    public void callbackCall() {
                        offlineVar = new offline(requireActivity());
                        offlineVar.SaveTotalCoins(slno);
                        tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                    }
                });
            }
        });
        ((TextView) dialog.findViewById(R.id.txt_go_on)).setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();

    }
    private int[] bubble = {5, 8, 3, 4, 11, 21, 25, 20};

    public void CoinDg() {
        int randomNumber = generateBubbleRandomNumber();
        int selectedNumber = bubble[randomNumber];
        try {
            final Dialog dialog = new Dialog(requireActivity());
            dialog.setContentView(R.layout.lay_congrats1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            Window window = dialog.getWindow();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            requireActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            if (window != null) {
                window.setGravity(Gravity.CENTER);
                window.setLayout((int) (0.9 * Resources.getSystem().getDisplayMetrics().widthPixels), android.widget.Toolbar.LayoutParams.WRAP_CONTENT);
            }
            new AdsNativeHelper().shownativeads_small(requireActivity(), dialog.findViewById(R.id.Admob_Native_Frame_small), dialog.findViewById(R.id.native_ad_container));

            ((TextView) dialog.findViewById(R.id.cooins)).setText(String.valueOf(selectedNumber));

            ((ImageView) dialog.findViewById(R.id.collect1x)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    AdInterGD.getInstance().loadRewardInterOne(getActivity(), new AdInterGD.MyCallback() {
                        @Override
                        public void callbackCall() {
                            offlineVar = new offline(requireActivity());
                            offlineVar.SaveTotalCoins(selectedNumber);
                            tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                        }
                    });

                }
            });
            ((TextView) dialog.findViewById(R.id.txt_go_on)).setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startDaily() {
        String string = this.mContext.getSharedPreferences("TimeDay", 0).getString("time", "0");
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(5, 1);
        if (System.currentTimeMillis() >= calendar.getTimeInMillis() || string.equals("0")) {
            int parseInt = Integer.parseInt(this.mContext.getSharedPreferences("TimeDay", 0).getString("position", "0"));
            if (parseInt >= 4) {
                parseInt = 0;
            }
            int i = parseInt + 1;
            String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            SharedPreferences.Editor edit = this.mContext.getSharedPreferences("TimeDay", 0).edit();
            edit.putString("time", format);
            edit.putString("position", String.valueOf(i));
            edit.commit();
            SharedPreferences.Editor edit2 = this.mContext.getSharedPreferences("DailyTasks", 0).edit();
            edit2.putString("water", "0");
            edit2.commit();
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
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar2.getTime());
    }

    public void upDateUIFrag(Long l, String str, int i, String str2, int i2) {
        try {

            requireActivity().runOnUiThread(() -> {
                int number = l.intValue();
                coinCount = 0;
                for (int j = 1; j < number; j++) {
                    if (j % 10 == 0) {
                        coinCount++;
                    }
                }
                txt_step_get_coin.setText(String.valueOf(coinCount));
            });
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            myEdit.putString("coin", String.valueOf(coinCount));
            myEdit.apply();

            steps_ref.get().setText("" + l);
            main_step_coin.get().setText("" + l);
            cal_ref.get().setText("" + i);
            dis_ref.get().setText(str2);
        } catch (Exception unused) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mContext.getSharedPreferences("Status", 0).getString(NotificationCompat.CATEGORY_SERVICE, "0").equals("start")) {
            btn_start.setVisibility(View.GONE);
            btn_pause.setVisibility(View.VISIBLE);
            return;
        }
        this.btn_start.setVisibility(View.VISIBLE);
        this.btn_pause.setVisibility(View.GONE);
    }
}
