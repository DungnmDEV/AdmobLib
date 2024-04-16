package com.dungvnhh98.percas.studio.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dungvnhh98.percas.studio.admoblib.AppOpenAdsManager
import com.google.android.gms.ads.AdValue

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val appOpenAdsManager = AppOpenAdsManager(this,"ca-app-pub-3940256099942544/3419835294",10000, object :AppOpenAdsManager.AppOpenAdsListener{
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