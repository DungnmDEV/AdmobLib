package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.nativead.NativeAd

interface LoadAndShowNativeCallBack {
    fun onLoadedAndGetNativeAd(ad: NativeAd?)
    fun onNativeAdsShowed()
    fun onAdFail(error: String)
    fun onAdPaid(adValue: AdValue?, adUnitAds: String?)
    fun onClickAds()
}