package com.dungvnhh98.percas.studio.admoblib.admob

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel
import com.google.android.gms.ads.AdValue


object AdjustManager {

    fun initAdjust(application: Application, appToken: String, isTestAdjust: Boolean) {
        val environment = if (isTestAdjust) {
            AdjustConfig.ENVIRONMENT_SANDBOX
        } else {
            AdjustConfig.ENVIRONMENT_PRODUCTION
        }
        val config = AdjustConfig(application, appToken, environment)
        config.setLogLevel(LogLevel.WARN)
        Adjust.onCreate(config)

        application.registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
    }

    fun postRevenue(ad: AdValue, adUnit: String?) {
        val adjustAdRevenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB)
        val rev = ad.valueMicros.toDouble() / 1000000
        adjustAdRevenue.setRevenue(rev, ad.currencyCode)
        adjustAdRevenue.setAdRevenueUnit(adUnit)
        Adjust.trackAdRevenue(adjustAdRevenue)
    }
//    fun postRevenueAdjustMax(ad : MaxAd){
//        val adjustAdRevenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_APPLOVIN_MAX)
//        adjustAdRevenue.setRevenue(ad.getRevenue(), "USD")
//        adjustAdRevenue.setAdRevenueNetwork(ad.getNetworkName())
//        adjustAdRevenue.setAdRevenueUnit(ad.getAdUnitId())
//        adjustAdRevenue.setAdRevenuePlacement(ad.getPlacement())
//        Adjust.trackAdRevenue(adjustAdRevenue)
//    }


    private class AdjustLifecycleCallbacks : ActivityLifecycleCallbacks {
         override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

         override fun onActivityStarted(activity: Activity) {}

         override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }

         override fun onActivityStopped(activity: Activity) {}

         override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

         override fun onActivityDestroyed(activity: Activity) {}
     }
}