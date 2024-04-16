package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView

interface BannerCallBack {
    fun onAdLoaded()
    fun onAdFailedToLoad(error: String)
    fun onAdClicked()
    fun onAdsPaid(adValue: AdValue, mAdView: AdView)
}