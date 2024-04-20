package com.dungvnhh98.percas.studio.example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dungvnhh98.percas.studio.admoblib.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.ViewControl.gone
import com.dungvnhh98.percas.studio.admoblib.ViewControl.visible
import com.dungvnhh98.percas.studio.admoblib.model.InterAdHolder
import com.dungvnhh98.percas.studio.admoblib.model.NativeAdHolder
import com.dungvnhh98.percas.studio.admoblib.model.RewardInterAdHolder
import com.dungvnhh98.percas.studio.example.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MediaAspectRatio
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val TAG = "TAG ==="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBanner.setOnClickListener {
            binding.flNativeMedium.gone()
            binding.flNativeSmall.gone()
            loadAndShowBanner(this, "", binding.flBanner, binding.line)
        }
        binding.btnNativeMedium.setOnClickListener {
        }
        binding.btnNativeSmall.setOnClickListener {
        }

        binding.btnLoadandshow.setOnClickListener {
        }

        binding.btnInter.setOnClickListener {
        }
        binding.btnReward.setOnClickListener {
        }
        binding.btnInterReward.setOnClickListener {
        }
        binding.btnLoadandshowinter.setOnClickListener {
        }

        binding.btnLoadandshowCollap.setOnClickListener {
        }
        binding.btnShownativefullscreen.setOnClickListener {
        }
    }

    fun loadAndShowBanner(
        activity: Activity,
        idBannerAd: String,
        viewBannerAd: ViewGroup,
        viewLine: View
    ) {
        AdmobManager.loadAndShowBannerAd(
            activity,
            idBannerAd,
            viewBannerAd,
            object : AdmobManager.LoadAndShowAdCallBack {
                override fun onAdLoaded() {}

                override fun onAdShowed() {
                    viewBannerAd.visible()
                    viewLine.visible()
                }

                override fun onAdFailed(error: String) {
                    viewBannerAd.gone()
                    viewLine.gone()
                }

                override fun onAdClosed() {}

                override fun onAdClicked() {}

                override fun onAdPaid(adValue: AdValue, adUnit: String) {}

            })
    }
    fun loadAndShowBannerCollapsibleAd(activity: Activity, idBannerCollapsible: String, isBottomCollapsible:Boolean, viewBannerCollapsibleAd: ViewGroup, viewLine: View) {
        AdmobManager.loadAndShowBannerCollapsibleAd(
            activity,
            idBannerCollapsible,
            isBottomCollapsible,
            viewBannerCollapsibleAd,
            object : AdmobManager.LoadAndShowAdCallBack {
                override fun onAdLoaded() {
                }

                override fun onAdShowed() {

                }

                override fun onAdFailed(error: String) {
                    viewBannerCollapsibleAd.gone()
                    viewLine.gone()
                }

                override fun onAdClosed() {

                }

                override fun onAdClicked() {

                }

                override fun onAdPaid(adValue: AdValue, adUnit: String) {

                }

            })
    }

    fun showNativeAd(activity: Activity, nativeAdHolder: NativeAdHolder, viewNativeAd: ViewGroup, layoutNativeFormat: Int, isNativeAdMedium: Boolean) {
        AdmobManager.showNativeAd(activity, nativeAdHolder, viewNativeAd, layoutNativeFormat, isNativeAdMedium, object : AdmobManager.ShowAdCallBack{
            override fun onAdShowed() {
            }

            override fun onAdFailed(error: String) {
                viewNativeAd.gone()
            }

            override fun onAdClosed() {
                
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
                
            }

        })
    }


    fun loadAndShowNativeAds(activity: Activity, nativeAdHolder: NativeAdHolder, viewNativeAd: ViewGroup, layoutNativeAdFormat: Int, isNativeAdMedium: Boolean) {
        AdmobManager.loadAndShowNativeAd(activity, nativeAdHolder, viewNativeAd, layoutNativeAdFormat, true,
            object : AdmobManager.LoadAndShowAdCallBack {
                override fun onAdLoaded() {
                    
                }

                override fun onAdShowed() {
                    
                }

                override fun onAdFailed(error: String) {
                    viewNativeAd.gone()
                }

                override fun onAdClosed() {
                    
                }

                override fun onAdClicked() {
                    
                }

                override fun onAdPaid(adValue: AdValue, adUnit: String) {
                    
                }

            })
    }
    
    fun showNativeAdFullScreen(activity: Activity, nativeAdHolder: NativeAdHolder, viewNativeAd: ViewGroup, layoutNativeAdFormat: Int){
        AdmobManager.showNativeAdFullScreen(activity, nativeAdHolder, viewNativeAd, layoutNativeAdFormat, object : AdmobManager.ShowAdCallBack{
            override fun onAdShowed() {
                
            }

            override fun onAdFailed(error: String) {
                viewNativeAd.gone()
            }

            override fun onAdClosed() {
                
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
                
            }

        })
    }
    
    fun loadAndShowNativeFullScreen(activity: Activity, idNativeAd: String, viewNativeAd: ViewGroup, layoutNativeFormat: Int, mediaAspectRatio: Int){
        AdmobManager.loadAndShowNativeAdFullScreen(activity, idNativeAd, viewNativeAd, layoutNativeFormat, mediaAspectRatio, object :AdmobManager.LoadAndShowAdCallBack{
            override fun onAdLoaded() {
                
            }

            override fun onAdShowed() {
                
            }

            override fun onAdFailed(error: String) {
                viewNativeAd.gone()
            }

            override fun onAdClosed() {
                
            }

            override fun onAdClicked() {
                
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
                
            }

        })
    }
    fun showInterstitialAd(activity: Activity, interAdHolder: InterAdHolder){
        AdmobManager.showInterstitialAd(activity, interAdHolder, object : AdmobManager.ShowAdCallBack{
            override fun onAdShowed() {
                
            }

            override fun onAdFailed(error: String) {
            }

            override fun onAdClosed() {
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
                
            }

        })
    }
    fun loadAndShowInterstitialAd(activity: Activity, interAdHolder: InterAdHolder){
        AdmobManager.loadAndShowInterstitialAd(activity, interAdHolder, object :AdmobManager.LoadAndShowAdCallBack{
            override fun onAdLoaded() {
                
            }

            override fun onAdShowed() {
                
            }

            override fun onAdFailed(error: String) {
            }

            override fun onAdClosed() {
            }

            override fun onAdClicked() {
                
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
                
            }

        })
    }

    fun loadAndShowRewardAd(activity: Activity, idRewardAd: String){
        AdmobManager.loadAndShowRewardAd(activity, idRewardAd, object : AdmobManager.LoadAndShowRewardAdCallBack{
            override fun onAdLoaded() {
            }

            override fun onAdShowed() {
            }

            override fun onAdFailed(error: String) {
            }

            override fun onAdClosed() {
            }

            override fun onAdEarned() {
                Log.d(TAG, "onAdEarned: Collected reward!")
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
            }

        })
    }
    fun showRewardInterAd(activity: Activity, rewardInterAdHolder: RewardInterAdHolder){
        AdmobManager.showInterReward(activity, rewardInterAdHolder, object : AdmobManager.ShowRewardAdCallBack{
            override fun onAdShowed() {

            }

            override fun onAdClosed() {
            }

            override fun onAdEarned() {
                Log.d(TAG, "onAdEarned: Collected reward!")
            }

            override fun onAdFailed(error: String) {
            }

            override fun onAdPaid(adValue: AdValue, adUnit: String) {
            }

        })
    }

}