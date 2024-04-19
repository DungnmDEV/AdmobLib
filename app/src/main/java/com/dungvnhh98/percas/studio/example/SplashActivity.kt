package com.dungvnhh98.percas.studio.example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dungvnhh98.percas.studio.admoblib.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.AppOpenAdsManager
import com.dungvnhh98.percas.studio.admoblib.model.InterAdHolder
import com.dungvnhh98.percas.studio.admoblib.model.NativeAdHolder
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.MediaAspectRatio
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd

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
}