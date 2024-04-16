package com.dungvnhh98.percas.studio.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dungvnhh98.percas.studio.admoblib.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.AppOpenAdsManager
import com.dungvnhh98.percas.studio.admoblib.callback.AppOpenAdsListener
import com.dungvnhh98.percas.studio.admoblib.callback.LoadInterCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.LoadNativeCallback
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd

class SplashActivity : AppCompatActivity() {
    private val TAG = "TAG === LoadNative"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AdmobManager.loadNativeAds(this, Ads.nativeHolder, object : LoadNativeCallback {
            override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
                Log.d(TAG, "onLoadedAndGetNativeAd: ")
            }

            override fun onAdsLoadFail(error: String?) {
                Log.d(TAG, "onAdFail: "+error)
            }

            override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
            }

        })
        AdmobManager.loadAdInterstitial(this, Ads.interholder,object : LoadInterCallBack{
            override fun onAdInterLoaded(ad: InterstitialAd?) {

            }

            override fun onAdInterFail(error: String?) {
            }

        })
        val appOpenAdsManager = AppOpenAdsManager(this,"ca-app-pub-3940256099942544/3419835294",
            timeOut = 10000, object : AppOpenAdsListener {
            override fun onAdsClose() {
                Log.d("TAG ===", "onAdsClose: ")
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAdsFailed(message: String) {
                Log.d("TAG ===", "onAdsFailed: "+message)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAdPaid(adValue: AdValue, adUnitAds: String) {
            }

        })
        appOpenAdsManager.loadAndShowAoA()
    }
}