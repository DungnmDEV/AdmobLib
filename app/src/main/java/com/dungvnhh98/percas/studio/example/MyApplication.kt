package com.dungvnhh98.percas.studio.example

import android.app.Application
import com.dungvnhh98.percas.studio.admoblib.admob.AdjustManager
import com.dungvnhh98.percas.studio.admoblib.admob.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.admob.AppResumeAdsManager


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AdmobManager.initAdmob(this, timeOut = 10000, isTestAd = true, isEnableAd = true)
        AppResumeAdsManager.getInstance().init(/* application = */ this,/* appOnresmeAdsId = */ "")
    }
}