package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView

interface BannerCallBack {
    fun onAdLoaded()
    fun onAdFailedToLoad(message: String)
    fun onAdClicked()
    fun onPaid(adValue: AdValue, mAdView: AdView)
}