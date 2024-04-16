package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue

interface ShowNativeCallBack {
    fun onAdsNativeShowed()
    fun onAdsNativeShowFailed(error : String)
    fun onAdsNativePaid(adValue : AdValue, adUnitAds : String)
}