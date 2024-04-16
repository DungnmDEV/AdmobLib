package com.dungvnhh98.percas.studio.example

import android.app.Application
import com.dungvnhh98.percas.studio.admoblib.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.AppResumeAdsManager

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AdmobManager.initAdmob(this, timeOut = 10000, isAdsTest = true, isEnableAds = true)
        AppResumeAdsManager.getInstance().init(/* application = */ this,/* appOnresmeAdsId = */ "")
    }
}