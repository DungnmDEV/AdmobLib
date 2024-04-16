package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue

interface AppOpenAdsListener {
    fun onAdsClose()
    fun onAdsFailed(error: String)
    fun onAdPaid(adValue: AdValue, adUnitAds: String)
}