package com.dungvnhh98.percas.studio.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dungvnhh98.percas.studio.admoblib.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.AppOpenAdsManager
import com.dungvnhh98.percas.studio.admoblib.model.InterAdHolder
import com.dungvnhh98.percas.studio.admoblib.model.NativeAdHolder
import com.dungvnhh98.percas.studio.admoblib.model.RewardInterAdHolder
import com.google.android.gms.ads.AdValue

class SplashActivity : AppCompatActivity() {
    private val TAG = "TAG ==="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        val appOpenID = "ca-app-pub-3940256099942544/3419835294"
        val appOpenAdsManager = AppOpenAdsManager(this,appOpenID,
            timeOut = 10000, object : AppOpenAdsManager.AppOpenAdListener {
            override fun onAdClose() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAdFail(error: String) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAdPaid(adValue: AdValue, adUnitAds: String) {
            }
        })
        
        appOpenAdsManager.loadAndShowAoA()
    }
    fun loadNativeAd(context: Context, nativeAdHolder: NativeAdHolder){
        AdmobManager.loadNativeAd(context, nativeAdHolder, object : AdmobManager.LoadAdCallBack{
            override fun onAdLoaded() {
                
            }

            override fun onAdFailed(error: String) {
                
            }

            override fun onAdClicked() {
                
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
                
            }

        })
    }
    
    fun loadNativeAdFullScreen(context: Context, nativeAdHolder: NativeAdHolder, mediaAspectRatio: Int){
        AdmobManager.loadNativeAdFullScreen(context, nativeAdHolder, mediaAspectRatio, object : AdmobManager.LoadAdCallBack{
            override fun onAdLoaded() {
                
            }

            override fun onAdFailed(error: String) {
                
            }

            override fun onAdClicked() {
                
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
                
            }

        })
    }
    fun loadInterstitialAd(context: Context, interAdHolder: InterAdHolder){
        AdmobManager.loadInterstitialAd(context, interAdHolder, object : AdmobManager.LoadAdCallBack{
            override fun onAdLoaded() {
            }

            override fun onAdFailed(error: String) {
            }

            override fun onAdClicked() {
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
            }
        })
    }
    fun loadInterRewardAd(context: Context, rewardInterAdHolder: RewardInterAdHolder){
        AdmobManager.loadInterReward(context, rewardInterAdHolder, object : AdmobManager.LoadAdCallBack{
            override fun onAdLoaded() {
            }

            override fun onAdFailed(error: String) {
            }

            override fun onAdClicked() {
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
                
            }

        })
    }
}