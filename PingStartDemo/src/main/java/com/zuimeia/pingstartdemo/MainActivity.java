package com.zuimeia.pingstartdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.imczy.common_util.log.LogUtil;
import com.pingstart.adsdk.AdManager;
import com.pingstart.adsdk.BannerListener;
import com.pingstart.adsdk.InterstitialListener;
import com.pingstart.adsdk.NativeListener;
import com.pingstart.adsdk.model.Ad;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private AdManager mAdManager;
    int PING_START_APP_ID = 1021;
    int PING_START_SLOT_ID = 1013;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdManager = new AdManager(this, PING_START_APP_ID, PING_START_SLOT_ID);
        mAdManager.setListener(new BannerListener() {
            @Override
            public void onAdError() {
                LogUtil.d(TAG, "BannerListener onAdError");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                LogUtil.d(TAG, "BannerListener onAdLoaded ad = " + ad);
                LinearLayout layout = (LinearLayout) findViewById(R.id.adver_layout);
                layout.addView(mAdManager.getBannerView());
            }

            @Override
            public void onAdOpened() {
                LogUtil.d(TAG, "BannerListener onAdOpened");
            }

            @Override
            public void onAdClicked() {
                LogUtil.d(TAG, "BannerListener onAdClicked");
            }
        });
        mAdManager.setListener(new InterstitialListener() {
            @Override
            public void onAdClosed() {
                LogUtil.d(TAG, "InterstitialListener onAdClicked");
            }

            @Override
            public void onAdError() {
                LogUtil.d(TAG, "InterstitialListener onAdClicked");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                LogUtil.d(TAG, "InterstitialListener onAdClicked");
                mAdManager.showInterstitial();
            }

            @Override
            public void onAdOpened() {
                LogUtil.d(TAG, "InterstitialListener onAdClicked");
            }

            @Override
            public void onAdClicked() {
                LogUtil.d(TAG, "InterstitialListener onAdClicked");
            }
        });
        mAdManager.setListener(new NativeListener() {
            @Override
            public void onAdError() {
                LogUtil.d(TAG, "NativeListener onAdClicked");

            }

            @Override
            public void onAdLoaded(Ad ad) {
                LogUtil.d(TAG, "NativeListener onAdClicked");

            }

            @Override
            public void onAdOpened() {
                LogUtil.d(TAG, "NativeListener onAdClicked");


            }

            @Override
            public void onAdClicked() {
                LogUtil.d(TAG, "NativeListener onAdClicked");


            }
        });
        mAdManager.loadAd();
    }
}
