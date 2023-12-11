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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.AdInterGD;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NativeAds.AdsNativeHelper;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.databinding.ActivityWheelBinding;
import com.mysweat.pedocounter.walkerstep.fragments.HomeFragment;
import com.mysweat.pedocounter.walkerstep.utils.offline;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LuckySpinWinActivity extends AppCompatActivity {
    int[] sectordegrees;
    String[] sectors;
    TextView tv_coin;
    Random random = new Random();
    int degree = 0;
    Boolean isSpin = false;
    int spin_coins = 0;
    int spinnedcoins = 0;
    RelativeLayout rl_spin_ly;
    Animation rl_spin, more_chance;
    ActivityWheelBinding binding;
    String[] strArr;
    private int clickCount = 0;

    public LuckySpinWinActivity() {
        strArr = new String[]{"100", "800", "700", "600", "500", "400", "300", "200"};
        this.sectors = strArr;
        this.sectordegrees = new int[strArr.length];
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = ActivityWheelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tv_coin = (TextView) findViewById(R.id.txt_home_coin);
        rl_spin_ly = findViewById(R.id.rl_spin_ly);

        rl_spin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);
        more_chance = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);

        binding.rlSpinLy.startAnimation(rl_spin);
        binding.ivSpinWheel.startAnimation(more_chance);
        ClickListner();
        ToolBar();

    }

    private boolean buttonEnabled = true;

    private void ClickListner() {
        binding.ivBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.withdrawMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpin(view);

//                if (buttonEnabled) {
//                    clickCount++;
//
//                    if (clickCount == 3) {
//                        openDialog();
//                    } else {
//                        setSpin(view);
//                    }
//                }
            }
        });
    }

    public void setSpin(View view) {
        if (this.isSpin) {
            return;
        }
        spin(view);
        this.isSpin = true;
    }

    private void spin(final View view) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(135);
        arrayList.add(360);
        arrayList.add(180);
        arrayList.add(225);
        arrayList.add(315);
        arrayList.add(270);
        arrayList.add(45);
        arrayList.add(90);
        final int intValue = arrayList.get(new Random().nextInt(arrayList.size()));
        this.degree = this.random.nextInt(this.sectors.length - 1);
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, intValue + 2880, 1, 0.5f, 1, 0.5f);

        rotateAnimation.setDuration(4500L);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {

                    isSpin = false;
                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.lay_congrats1);
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
                    dialog.setCancelable(true);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    Window window = dialog.getWindow();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    if (window != null) {
                        window.setGravity(Gravity.CENTER);
                        window.setLayout((int) (0.9 * Resources.getSystem().getDisplayMetrics().widthPixels), android.widget.Toolbar.LayoutParams.WRAP_CONTENT);
                    }
                    ImageView textView = (ImageView) dialog.findViewById(R.id.collect1x);
                    TextView textView3 = (TextView) dialog.findViewById(R.id.cooins);
                    TextView txt_go_on = (TextView) dialog.findViewById(R.id.txt_go_on);
                    new AdsNativeHelper().shownativeads_small(LuckySpinWinActivity.this, dialog.findViewById(R.id.Admob_Native_Frame_small), dialog.findViewById(R.id.native_ad_container));

                    int i = intValue;
                    if (i == 45) {
                        spinnedcoins = 400;
                    } else if (i == 90) {
                        spinnedcoins = 350;
                    } else if (i == 135) {
                        spinnedcoins = 300;
                    } else if (i == 180) {
                        spinnedcoins = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
                    } else if (i == 225) {
                        spinnedcoins = 200;
                    } else if (i == 270) {
                        spinnedcoins = 150;
                    } else if (i == 315) {
                        spinnedcoins = 100;
                    } else if (i == 360) {
                        spinnedcoins = 50;
                    } else {
                        spinnedcoins = 0;
                    }
                    textView3.setText(String.valueOf(spinnedcoins));

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            dialog.dismiss();

                            AdInterGD.getInstance().loadRewardInterOne(LuckySpinWinActivity.this, new AdInterGD.MyCallback() {
                                @Override
                                public void callbackCall() {
                                    offline offlineVar = new offline(LuckySpinWinActivity.this);
                                    spin_coins--;
                                    offlineVar.SaveCount(spin_coins, 1);
                                    offlineVar.SaveTotalCoins(spinnedcoins);
                                    tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                                    getAllCoin.setValue(String.valueOf(offlineVar.getTotalCoins()));
                                    finish();
                                }
                            });
                        }
                    });

                    txt_go_on.setOnClickListener(v -> {
                        dialog.dismiss();
                    });
                    dialog.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.ivSpinWheel.startAnimation(rotateAnimation);
    }

    private int[] predefinedNumbers = {1000, 2000, 2500, 3500, 3000, 4500, 4000, 5000};

    public void BonusDialog() {
        int randomNumber = generateRandomNumber();
        int selectedNumber = predefinedNumbers[randomNumber];
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.bonus_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);
            TextView txt_bonus = dialog.findViewById(R.id.txt_bonus);

            txt_bonus.setText(String.valueOf(selectedNumber));

            ((LinearLayout) dialog.findViewById(R.id.ll_bonus_coin)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    AdInterGD.getInstance().loadInterOne(LuckySpinWinActivity.this, new AdInterGD.MyCallback() {
                        @Override
                        public void callbackCall() {
                            offline offlineVar = new offline(LuckySpinWinActivity.this);
                            offlineVar.SaveTotalCoins(selectedNumber);
                            txt_bonus.setText(String.valueOf(offlineVar.getTotalCoins()));
                            HomeFragment.tv_coin.setText(String.valueOf(offlineVar.getTotalCoins()));
                            dialog.dismiss();
                        }
                    });
                }
            });
            ((TextView) dialog.findViewById(R.id.tv_no_tx)).setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(predefinedNumbers.length);
    }

    private void ToolBar() {
        this.tv_coin.setText(String.valueOf(new offline(this).getTotalCoins()));
    }

    @Override
    public void onBackPressed() {
        AdInterGD.getInstance().loadInterOne(this, new AdInterGD.MyCallback() {
            @Override
            public void callbackCall() {
                LuckySpinWinActivity.super.onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(LuckySpinWinActivity.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.lay_watch_ad);
        getWindow().setLayout(-1, -1);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        ((TextView) dialog.findViewById(R.id.oktxt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                AdInterGD.getInstance().loadRewardInterOne(LuckySpinWinActivity.this, new AdInterGD.MyCallback() {
                    @Override
                    public void callbackCall() {
                        dialog.dismiss();
                        buttonEnabled = true;
                    }
                });
            }
        });
        ((ImageView) dialog.findViewById(R.id.iv_close)).setOnClickListener(v -> {
            dialog.dismiss();
            buttonEnabled = false;
        });
        dialog.show();

    }
}

