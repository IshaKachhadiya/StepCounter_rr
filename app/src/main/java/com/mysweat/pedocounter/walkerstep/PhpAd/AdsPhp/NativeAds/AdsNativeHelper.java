package com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.NativeAds;

import static com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref.admob_native;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.mysweat.pedocounter.walkerstep.PhpAd.AdsPhp.ADSharedPref;
import com.mysweat.pedocounter.walkerstep.R;
import com.mysweat.pedocounter.walkerstep.ui.SplashActivity;

public class AdsNativeHelper {

    Dialog dialog;

    public void shownativeads_small(Activity activity, final ViewGroup viewGroup, NativeAdLayout nativeAdLayout) {
        String admobnative = ADSharedPref.getString(activity, admob_native, SplashActivity.ad_native);
//        String admobnative = FAFL_ADSharedPref.getString(activity, "ca-app-pub-3940256099942544/224769611000", "ca-app-pub-3940256099942544/224769611000");
        AdLoader.Builder builder = new AdLoader.Builder(activity, admobnative);
        builder.forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                new AdsInflatAds(activity).inflat_admobnativesmall(nativeAd, viewGroup);
            }

        });
        AdLoader adLoader = builder.withAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                small_fb(activity, nativeAdLayout);

            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void small_fb(Activity activity, NativeAdLayout nativeAdLayout) {
        String small_fb = ADSharedPref.getString(activity, ADSharedPref.FBAD_NATIVE, SplashActivity.fb_native);

//        String small_fb = ADSharedPref.getString(activity, "YOUR_PLACEMENT_ID", "YOUR_PLACEMENT_ID");
        NativeAd nativeAd = new NativeAd(activity, small_fb);
        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("TAG", "onError: " + ad);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                new AdsInflatAds(activity).inflate_NATIV_FB(nativeAd, nativeAdLayout);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());

    }
}
