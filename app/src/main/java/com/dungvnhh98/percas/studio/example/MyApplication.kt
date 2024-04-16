package com.dungvnhh98.percas.studio.example

import android.app.Application
import com.dungvnhh98.percas.studio.admoblib.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.AppResumeAdsManager

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AdmobManager.initAdmob(this, 10000, true, true)
        AppResumeAdsManager.getInstance().init(this,"")
    }
}