package com.dungvnhh98.percas.studio.example

import android.app.Application
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel
import com.dungvnhh98.percas.studio.admoblib.Admob.AdjustManager
import com.dungvnhh98.percas.studio.admoblib.Admob.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.Admob.AppResumeAdsManager


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AdmobManager.initAdmob(this, timeOut = 10000, isTestAd = true, isEnableAd = true)
        AppResumeAdsManager.getInstance().init(/* application = */ this,/* appOnresmeAdsId = */ "")
        AdjustManager.initAdjust(this, "", isTestAdjust = true)
    }
}