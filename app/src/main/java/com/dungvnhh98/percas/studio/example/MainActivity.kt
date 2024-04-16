package com.dungvnhh98.percas.studio.example

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dungvnhh98.percas.studio.admoblib.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.ViewControl.gone
import com.dungvnhh98.percas.studio.admoblib.ViewControl.visible
import com.dungvnhh98.percas.studio.admoblib.callback.ShowNativeCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.BannerCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.LoadAndShowNativeCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.LoadInterCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.RewardCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.ShowInterAdCallBack
import com.dungvnhh98.percas.studio.admoblib.enum.NativeAdsSize
import com.dungvnhh98.percas.studio.example.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
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
            AdmobManager.loadBanner(this, "", binding.flBanner, object : BannerCallBack {
                override fun onAdLoaded() {
                    binding.flBanner.visible()
                    binding.line.visible()
                }

                override fun onAdFailedToLoad(message: String) {
                    binding.flBanner.gone()
                    binding.line.gone()
                }

                override fun onAdClicked() {
                }

                override fun onAdsPaid(adValue: AdValue, mAdView: AdView) {
                }

            })
        }


        binding.btnNativeMedium.setOnClickListener {
            binding.flBanner.gone()
            binding.line.gone()
            binding.flNativeSmall.gone()
            binding.flNativeMedium.visible()
            showNativeMedium()
        }
        binding.btnNativeSmall.setOnClickListener {
            binding.flBanner.gone()
            binding.line.gone()
            binding.flNativeMedium.gone()
            binding.flNativeSmall.visible()
            showNativeSmall()
        }

        binding.btnLoadandshow.setOnClickListener {
            binding.flBanner.gone()
            binding.line.gone()
            binding.flNativeSmall.gone()
            binding.flNativeMedium.visible()
            loadAndShowNativeAds()
        }

        binding.btnInter.setOnClickListener {
            binding.flBanner.gone()
            binding.line.gone()
            binding.flNativeSmall.gone()
            binding.flNativeMedium.gone()
            showInter()
        }
        binding.btnReward.setOnClickListener {
            showreward()
        }
    }

    private fun showNativeMedium() {
        AdmobManager.showNativeAds(
            this,
            Ads.nativeHolder,
            binding.flNativeMedium,
            R.layout.ad_unified_medium,
            NativeAdsSize.MEDIUM,
            object :
                ShowNativeCallBack {
                override fun onAdsNativeShowed() {
                }

                override fun onAdsNativeShowFailed(massage: String) {
                }

                override fun onAdsNativePaid(adValue: AdValue, adUnitAds: String) {
                }

            })
    }

    private fun showNativeSmall() {
        AdmobManager.showNativeAds(
            this,
            Ads.nativeHolder,
            binding.flNativeSmall,
            R.layout.ad_unified_small,
            NativeAdsSize.SMALL,
            object :
                ShowNativeCallBack {
                override fun onAdsNativeShowed() {

                }

                override fun onAdsNativeShowFailed(massage: String) {
                }

                override fun onAdsNativePaid(adValue: AdValue, adUnitAds: String) {
                }

            })
    }


    private fun loadAndShowNativeAds() {
        AdmobManager.loadAndShowNativeAds(
            this,
            Ads.nativeHolder2,
            binding.flNativeMedium,
            R.layout.ad_unified_medium,
            NativeAdsSize.MEDIUM,
            object : LoadAndShowNativeCallBack {
                override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
                    Log.d(TAG, "onLoadedAndGetNativeAd: ")
                }

                override fun onNativeAdsShowed() {
                    Log.d(TAG, "onNativeAdsShowed: ")
                }

                override fun onAdFail(error: String) {
                    Log.d(TAG, "onAdFail: ")
                }

                override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
                    Log.d(TAG, "onAdPaid: ")
                }

                override fun onClickAds() {
                    Log.d(TAG, "onClickAds: ")
                }

            })
    }

    private fun showInter(){
        AdmobManager.showAdInter(this,Ads.interholder, object :ShowInterAdCallBack{
            override fun onInterAdShow() {

            }

            override fun onInterAdClose() {
                AdmobManager.loadAdInterstitial(this@MainActivity, Ads.interholder,object : LoadInterCallBack {
                    override fun onAdInterLoaded(ad: InterstitialAd?) {

                    }

                    override fun onAdInterFail(error: String?) {
                    }
                })
            }

            override fun onInterAdFail(error: String?) {
            }

            override fun onAdPaid(adValue: AdValue, adUnitAds: String) {
            }

        } )
    }

    private fun showreward(){
        AdmobManager.loadAndShowAdReward(this, "", object : RewardCallBack{
            override fun onAdClosed() {
                Toast.makeText(this@MainActivity, "Reward is closed", Toast.LENGTH_LONG).show()
            }

            override fun onAdShowed() {
            }

            override fun onAdFail(message: String?) {

            }

            override fun onEarned() {
                Toast.makeText(this@MainActivity, "Reward is collected", Toast.LENGTH_LONG).show()
            }

            override fun onPaid(adValue: AdValue?, adUnitAds: String?) {
            }

        })
    }
}