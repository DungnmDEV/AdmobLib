package com.dungvnhh98.percas.studio.admoblib.callback

import com.google.android.gms.ads.AdValue

interface RewardCallBack {
    fun onAdClosed()
    fun onAdShowed()
    fun onAdFail(message: String?)
    fun onEarned()
    fun onPaid(adValue: AdValue?, adUnitAds: String?)
}