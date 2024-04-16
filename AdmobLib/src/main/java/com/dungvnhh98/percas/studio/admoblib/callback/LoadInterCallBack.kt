package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.interstitial.InterstitialAd

interface LoadInterCallBack {
    fun onAdInterLoaded(ad: InterstitialAd?)
    fun onAdInterFail(error: String?)
}