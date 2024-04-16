package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue

interface ShowInterAdCallBack {
    fun onInterAdShow()
    fun onInterAdClose()
    fun onInterAdFail(error: String?)
    fun onAdPaid(adValue : AdValue, adUnitAds : String)
}