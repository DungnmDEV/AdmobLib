package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.nativead.NativeAd

interface LoadNativeCallback {
    fun onLoadedAndGetNativeAd(ad: NativeAd?)
    fun onAdsLoadFail(error: String?)
    fun onAdPaid(adValue: AdValue?, adUnitAds: String?)
}
