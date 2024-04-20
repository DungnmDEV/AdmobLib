package com.dungvnhh98.percas.studio.admoblib.Admob

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.AdjustEvent
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
        config.setLogLevel(LogLevel.WARN);
        Adjust.onCreate(config)

        application.registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
    }
    fun postRevenue(adValue: AdValue, adUnit: String){
        val adjustEvent = AdjustEvent(adUnit)
        adjustEvent.setRevenue(adValue.valueMicros.toDouble(), adValue.currencyCode)
        Adjust.trackEvent(adjustEvent)
    }

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