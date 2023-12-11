package com.mysweat.pedocounter.walkerstep.ui;

import static com.mysweat.pedocounter.walkerstep.utils.offline.getAllCoin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.AdInterGD;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NativeAds.AdsNativeHelper;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.utils.offline;

import java.util.Random;

public class LuckSlotActivity extends AppCompatActivity {

    ImageView click_to_win, iv1, iv2, iv3, iv_back, iv_extra_win;
    private TextView tv_coin;

    private int[] imageResources = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9, R.drawable.image10, R.drawable.image11, R.drawable.image12, R.drawable.image13, R.drawable.image14, R.drawable.image15};
    private Handler handler = new Handler();
    Animation slot, win;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_win);
        click_to_win = (ImageView) findViewById(R.id.click_to_win);
        tv_coin = (TextView) findViewById(R.id.txt_home_coin);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv_back = findViewById(R.id.iv_back);
        iv_extra_win = findViewById(R.id.iv_extra_win);
        slot = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        win = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);

        iv_extra_win.startAnimation(slot);
        click_to_win.startAnimation(win);

        ClickListner();
    }

    private void ClickListner() {
        this.click_to_win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValueRunable(view);
            }
        });
        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        AdInterGD.getInstance().loadInterOne(this, new AdInterGD.MyCallback() {
            @Override
            public void callbackCall() {
                LuckSlotActivity.super.onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });
    }
    private int[] predefinedNumbers = {1000, 2000, 2500, 3500, 3000, 4500, 4000, 5000};

    public void setValueRunable(View view) {
        int randomNumber = generateRandomNumber();
        int selectedNumber = predefinedNumbers[randomNumber];

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Collect(selectedNumber);
            }
        }, 3500L);
        startFastMovement(iv1);
        startFastMovement(iv2);
        startFastMovement(iv3);
    }
    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(predefinedNumbers.length);
    }
    private void startFastMovement(final ImageView imageView) {
        final Random random = new Random();
        final int nextInt = random.nextInt(this.imageResources.length);
        this.handler.postDelayed(new Runnable() {
            int elapsedDuration = 0;

            @Override
            public void run() {
                if (this.elapsedDuration < 3000) {
                    imageView.setImageResource(LuckSlotActivity.this.imageResources[random.nextInt(LuckSlotActivity.this.imageResources.length)]);
                    this.elapsedDuration += 100;
                    LuckSlotActivity.this.handler.postDelayed(this, 100L);
                    return;
                }
                imageView.setImageResource(LuckSlotActivity.this.imageResources[nextInt]);
            }
        }, 200L);
    }

    public void Collect(final int i) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.lay_congrats1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            Window window = dialog.getWindow();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            if (window != null) {
                window.setGravity(Gravity.CENTER);
                window.setLayout((int) (0.9 * Resources.getSystem().getDisplayMetrics().widthPixels), android.widget.Toolbar.LayoutParams.WRAP_CONTENT);
            }
            new AdsNativeHelper().shownativeads_small(LuckSlotActivity.this, dialog.findViewById(R.id.Admob_Native_Frame_small), dialog.findViewById(R.id.native_ad_container));

            ((TextView) dialog.findViewById(R.id.cooins)).setText(String.valueOf(i));
            ((ImageView) dialog.findViewById(R.id.collect1x)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    AdInterGD.getInstance().loadRewardInterOne(LuckSlotActivity.this, new AdInterGD.MyCallback() {
                        @Override
                        public void callbackCall() {
                            offline offlineVar = new offline(LuckSlotActivity.this);
                            offlineVar.SaveTotalCoins(i);
                            tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                            getAllCoin.setValue(String.valueOf(offlineVar.getTotalCoins()));
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

}