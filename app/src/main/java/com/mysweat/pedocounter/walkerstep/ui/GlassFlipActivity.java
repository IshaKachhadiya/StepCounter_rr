package com.mysweat.pedocounter.walkerstep.ui;

import static com.mysweat.pedocounter.walkerstep.utils.offline.getAllCoin;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.AdInterGD;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NativeAds.AdsNativeHelper;
import com.mysweat.pedocounter.walkerstep.utils.PreferenceManager;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.databinding.ActivityFlipCardBinding;
import com.mysweat.pedocounter.walkerstep.utils.offline;

import java.util.ArrayList;
import java.util.Random;

public class GlassFlipActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_coin;
    private int daysSinceLastCheckIn = 0;
    private PreferenceManager preferenceManager;
    public static String[] strArr;
    private String[] strArrRandom;
    ArrayList<String> randomarray = new ArrayList<>();
    int isClick = 3;
    ActivityFlipCardBinding binding;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = ActivityFlipCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tv_coin = (TextView) findViewById(R.id.txt_update);
        ToolBar();

        CheckPref();
        initView();
        ClickListner();
        RandomArrayist();
    }

    private void RandomArrayist() {

        isClick = Integer.parseInt(preferenceManager.getString("selectnumberFlip", "3"));
        strArrRandom = this.preferenceManager.getWatchvideoArray("Flip_CHECK", new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0"});
        randomarray = this.preferenceManager.getRandomArray("Random_Flip_list");

        try {
            if (randomarray != null || !randomarray.isEmpty()) {
            } else {
                randomarray.add("1");
                randomarray.add("2");
                randomarray.add("3");
                randomarray.add("4");
                randomarray.add("5");
                randomarray.add("6");
                randomarray.add("7");
                randomarray.add("8");
                randomarray.add("9");
                this.preferenceManager.setRandomArray("Random_Flip_list", randomarray);
            }
        } catch (Exception e) {
            randomarray = new ArrayList<>();
            randomarray.add("1");
            randomarray.add("2");
            randomarray.add("3");
            randomarray.add("4");
            randomarray.add("5");
            randomarray.add("6");
            randomarray.add("7");
            randomarray.add("8");
            randomarray.add("9");
            this.preferenceManager.setRandomArray("Random_Flip_list", randomarray);
            e.printStackTrace();
        }

        checkrandomList();
    }

    private void checkrandomList() {
        if (isClick >= 3) {
            isClick = 0;
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            int j;
            for (j = 0; j < 3; j++) {
                Random random = new Random();
                int result = random.nextInt(randomarray.size());
                strArrRandom[Integer.parseInt(randomarray.get(result)) - 1] = "2";
                randomarray.remove(result);
                this.preferenceManager.setRandomArray("Random_Flip_list", randomarray);
            }
        }
        preferenceManager.putWatchvideoArray("Flip_CHECK", strArrRandom);


        int i = 0;
        while (i < strArrRandom.length) {
            int i2 = i + 1;
            int resourceId = getResourceId("img" + i2);
            if (strArrRandom[i].matches("2")) {
                findViewById(resourceId).setVisibility(View.VISIBLE);
            } else if (strArrRandom[i].matches("1")) {
                findViewById(resourceId).setVisibility(View.GONE);
                findViewById(resourceId).setClickable(true);
            } else {
                findViewById(resourceId).setVisibility(View.VISIBLE);
                findViewById(resourceId).setClickable(true);
            }
            i = i2;
        }
    }

    private void initView() {
        strArr = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0"};


        String[] stringArray = this.preferenceManager.getWatchvideoArray("Flip_CHECK", new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0"});
        int i = 0;
        while (i < stringArray.length) {
            int i2 = i + 1;
            int resourceId2 = getResourceId("img" + i2);
            if (stringArray[i].matches("2")) {
                findViewById(resourceId2).setVisibility(View.VISIBLE);
            } else if (stringArray[i].matches("1")) {
                findViewById(resourceId2).setVisibility(View.GONE);
                findViewById(resourceId2).setClickable(true);
            } else {
                findViewById(resourceId2).setVisibility(View.VISIBLE);
                findViewById(resourceId2).setClickable(true);
            }
            i = i2;
        }
    }


    private int getResourceId(String str) {
        return getResources().getIdentifier(str, "id", getPackageName());
    }

    private void CheckPref() {
        preferenceManager = new PreferenceManager(this);

        String checkastDate = preferenceManager.getEarMoneyin("saveFlipDate");
        this.daysSinceLastCheckIn = preferenceManager.calculateDaysSinceLastCheckIn(checkastDate);

        if (daysSinceLastCheckIn == 0) {
            preferenceManager.saveEarMoneyDate("saveFlipDate");
        } else {
            preferenceManager.saveEarMoneyDate("saveFlipDate");
            randomarray = new ArrayList<>();
            randomarray.add("1");
            randomarray.add("2");
            randomarray.add("3");
            randomarray.add("4");
            randomarray.add("5");
            randomarray.add("6");
            randomarray.add("7");
            randomarray.add("8");
            randomarray.add("9");
            this.preferenceManager.setRandomArray("Random_Flip_list", randomarray);
            this.preferenceManager.putStringArray("Flip_CHECK", new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0"});
            preferenceManager.putString("selectnumberFlip", "3");
        }
    }

    private void ClickListner() {
        binding.img1.setOnClickListener(this);
        binding.img2.setOnClickListener(this);
        binding.img3.setOnClickListener(this);
        binding.img4.setOnClickListener(this);
        binding.img5.setOnClickListener(this);
        binding.img6.setOnClickListener(this);
        binding.img7.setOnClickListener(this);
        binding.img8.setOnClickListener(this);
        binding.img9.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    private void Collect(final int i) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.lay_congrats1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            ((TextView) dialog.findViewById(R.id.cooins)).setText(String.valueOf(i));
            Window window = dialog.getWindow();
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            if (window != null) {
                window.setGravity(Gravity.CENTER);
                window.setLayout((int) (0.9 * Resources.getSystem().getDisplayMetrics().widthPixels), android.widget.Toolbar.LayoutParams.WRAP_CONTENT);
            }
            TextView txt_go_on = dialog.findViewById(R.id.txt_go_on);
            new AdsNativeHelper().shownativeads_small(GlassFlipActivity.this, dialog.findViewById(R.id.Admob_Native_Frame_small), dialog.findViewById(R.id.native_ad_container));

            txt_go_on.setOnClickListener(v -> {
                dialog.dismiss();
            });
            ((ImageView) dialog.findViewById(R.id.collect1x)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    AdInterGD.getInstance().loadRewardInterOne(GlassFlipActivity.this, new AdInterGD.MyCallback() {
                        @Override
                        public void callbackCall() {
                            offline offlineVar = new offline(GlassFlipActivity.this);
                            offlineVar.SaveTotalCoins(i);
                            GlassFlipActivity.this.tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                            getAllCoin.setValue(String.valueOf(offlineVar.getTotalCoins()));
                        }
                    });
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ToolBar() {
        this.tv_coin.setText(String.valueOf(new offline(this).getTotalCoins()));
    }

    @Override
    public void onBackPressed() {
        AdInterGD.getInstance().loadInterOne(this, new AdInterGD.MyCallback() {
            @Override
            public void callbackCall() {
                GlassFlipActivity.super.onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });
    }


    private void unCollected(ImageView imageView, ImageView lockImage) {
        imageView.setVisibility(View.GONE);
        lockImage.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.img1) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img1, binding.img1);
            stringArray[0] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(500);
        } else if (view.getId() == R.id.img2) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img2, binding.img2);
            stringArray[1] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(400);
        } else if (view.getId() == R.id.img3) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img3, binding.img3);
            stringArray[2] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(700);
        } else if (view.getId() == R.id.img4) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img4, binding.img4);
            stringArray[3] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(200);
        } else if (view.getId() == R.id.img5) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img5, binding.img5);
            stringArray[4] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(100);
        } else if (view.getId() == R.id.img6) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img6, binding.img6);
            stringArray[5] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(300);
        } else if (view.getId() == R.id.img7) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img7, binding.img7);
            stringArray[6] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(100);
        } else if (view.getId() == R.id.img8) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img8, binding.img8);
            stringArray[7] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(800);
        } else if (view.getId() == R.id.img9) {
            preferenceManager.putString("selectnumberFlip", Integer.toString(isClick));
            String[] stringArray = preferenceManager.getWatchvideoArray("Flip_CHECK", strArr);
            unCollected(binding.img9, binding.img9);
            stringArray[8] = "1";
            preferenceManager.putWatchvideoArray("Flip_CHECK", stringArray);
            Collect(500);
        } else if (view.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }
}
