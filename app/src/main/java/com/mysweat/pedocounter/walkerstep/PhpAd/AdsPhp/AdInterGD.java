package com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.ui.SplashActivity;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

public class AdInterGD {

    private InterstitialAd FBinterstitialAd;
    public com.google.android.gms.ads.interstitial.InterstitialAd interstitialOne;
    private RewardedInterstitialAd mrewardedInterstitialAd;
    MyCallback myCallback;
    private static AdInterGD mInstance;

    public static int gclick = 0;

    public static AdInterGD getInstance() {
        if (mInstance == null) {
            mInstance = new AdInterGD();
        }
        return mInstance;
    }

    String unityGameID = "";
    Boolean testMode = true;
    String adUnitId = "";

    Dialog dialog;

    public interface MyCallback {
        void callbackCall();
    }

    private void showDialog(Activity context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
    }

    public void loadInterTwo_main(Activity activity) {
        String uGI = ADSharedPref.getString(activity, ADSharedPref.unity_adid, ADSharedPref.unity_adid);
        String adUnit = ADSharedPref.getString(activity, "", SplashActivity.unity_inter);

//        String uGI = ADSharedPref.getString(activity, "14851", "14851");
//        String adUnit = ADSharedPref.getString(activity, "video", "video");

        adUnitId = adUnit;
        unityGameID = uGI;


        UnityAds.load(adUnit, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                UnityAds.show(activity, adUnitId, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                        if (myCallback != null) {
                            myCallback.callbackCall();
                            myCallback = null;
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {
                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                        if (myCallback != null) {
                            myCallback.callbackCall();
                            myCallback = null;
                        }
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }
                dialog.dismiss();

            }
        });

        UnityAds.initialize(activity, uGI, testMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {

            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {

            }
        });
    }

    public void LoadFB_mainl(Activity activity) {
        String FB_inter = ADSharedPref.getString(activity, ADSharedPref.FBAD_INTER, SplashActivity.fb_inter);

        FBinterstitialAd = new InterstitialAd(activity, FB_inter);
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                dialog.dismiss();
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                FBinterstitialAd = null;
                loadInterTwo_main(activity);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (FBinterstitialAd != null) {

                    FBinterstitialAd.show();
                }
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        FBinterstitialAd.loadAd(
                FBinterstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());


    }

    public void loadRewardInterOne(Activity activity, MyCallback _myCallback) {

        showDialog(activity);
        this.myCallback = _myCallback;

        int click = ADSharedPref.getInteger(activity, ADSharedPref.CLICK, SplashActivity.click);
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadRewardedInterstitialAd(activity);
            }
        });
        if (gclick == click) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gclick = 1;
                    if (mrewardedInterstitialAd != null) {
                        mrewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                mrewardedInterstitialAd = null;
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                Log.e("TAG", "Ad failed to show fullscreen content.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                                Log.e("TAG", "Ad showed fullscreen content.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                mrewardedInterstitialAd = null;
                                loadRewardedInterstitialAd(activity);
                                if (myCallback != null) {
                                    myCallback.callbackCall();
                                    myCallback = null;
                                }
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                Log.e("TAG", "Ad dismissed fullscreen content.");
                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }

                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                            }
                        });
                    }

                    if (mrewardedInterstitialAd != null) {
                        mrewardedInterstitialAd.show(activity, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                if (myCallback != null) {
                                    myCallback.callbackCall();
                                    myCallback = null;
                                }
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });

                        loadRewardedInterstitialAd(activity);

                    } else {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                }
            }, 2000);
        } else {
            gclick = gclick + 1;

            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    public void loadInterOne(Activity activity, MyCallback _myCallback) {

        showDialog(activity);

        MobileAds.initialize(activity, initializationStatus -> {
        });

        String admobinter = ADSharedPref.getString(activity, ADSharedPref.admob_interstital, SplashActivity.ad_inter);
        this.myCallback = _myCallback;

        int click = ADSharedPref.getInteger(activity, ADSharedPref.CLICK, SplashActivity.click);
        if (gclick == click) {
            gclick = 1;
            AdRequest adRequest = new AdRequest.Builder().build();
            com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, admobinter, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {

                    interstitialOne = interstitialAd;
                    interstitialAd.show(activity);
                    interstitialOne.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            interstitialOne = null;

                            if (myCallback != null) {
                                myCallback.callbackCall();
                                myCallback = null;
                            }
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                            interstitialOne = null;
                            if (myCallback != null) {
                                myCallback.callbackCall();
                                myCallback = null;
                            }
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    interstitialOne = null;
                    LoadFB_mainl(activity);
                }
            });
        } else {
            gclick = gclick + 1;
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    private void loadRewardedInterstitialAd(Activity activity) {
        String admobinter = ADSharedPref.getString(activity, ADSharedPref.REWARd, SplashActivity.rewarded);

        Log.e("TAG", "loadRewardedInterstitialAd:99 " + admobinter);
        RewardedInterstitialAd.load(activity, admobinter, new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                super.onAdLoaded(rewardedInterstitialAd);
                mrewardedInterstitialAd = rewardedInterstitialAd;
                Log.e("TAG", "onAdLoaded: "+rewardedInterstitialAd );

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mrewardedInterstitialAd = null;

                Log.e("TAG", "onAdFailedToLoad: "+loadAdError.getMessage() );

            }
        });
    }

    public void showsplaceInter(Activity activity, MyCallback __mycallback) {

        dialog = new Dialog(activity);
        myCallback = __mycallback;
        MobileAds.initialize(activity, initializationStatus -> {
        });

        String admobinter = ADSharedPref.getString(activity, ADSharedPref.admob_interstital, SplashActivity.ad_inter);
//        String admobinter = ADSharedPref.getString(activity, "ca-app-pub-3940256099942544/103317371200", "ca-app-pub-3940256099942544/103317371200");
        AdRequest adRequest = new AdRequest.Builder().build();
        com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, admobinter, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                interstitialOne = interstitialAd;
                interstitialAd.show(activity);
                interstitialOne.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        interstitialOne = null;
                        if (myCallback != null) {
                            myCallback.callbackCall();
                            myCallback = null;
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                        interstitialOne = null;
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                interstitialOne = null;
                LoadFB_mainl(activity);
            }
        });
    }

}