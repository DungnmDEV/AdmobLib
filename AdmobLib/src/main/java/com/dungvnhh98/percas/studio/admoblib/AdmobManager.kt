package com.dungvnhh98.percas.studio.admoblib

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.dungvnhh98.percas.studio.admoblib.ViewControl.gone
import com.dungvnhh98.percas.studio.admoblib.callback.BannerCallBack
import com.dungvnhh98.percas.studio.admoblib.enum.CollapsibleBanner
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.RequestConfiguration
import java.util.UUID

object AdmobManager {
    private val TAG = "TAG ==="

    var isAdsTest: Boolean = true
    var isEnableAds: Boolean = false

    private var timeOut = 0

    var mAdRequest: AdRequest? = null

    val mTestDeviceIds : ArrayList<String> = ArrayList()

    var mShimmerFrameLayout: ShimmerFrameLayout? = null
    var isAdsShowing = false

    @JvmStatic
    fun initAdmob(context: Context?, timeOut: Int, isAdsTest: Boolean, isEnableAds: Boolean){

        if (timeOut < 5000 && timeOut != 0) {
            Toast.makeText(context, "Limit time ~10000", Toast.LENGTH_LONG).show()
        }
        this.timeOut = if (timeOut > 0) {
            timeOut
        } else {
            10000
        }
        this.isAdsTest = isAdsTest
        this.isEnableAds = isEnableAds

        MobileAds.initialize(context!!) {}

        initTestDeviceIds()

        val configuration = RequestConfiguration.Builder()
            .setTestDeviceIds(mTestDeviceIds)
            .build()
        MobileAds.setRequestConfiguration(configuration)

        initAdRequest(timeOut)
    }

    @JvmStatic
    fun initAdRequest(timeOut: Int) {
        mAdRequest = AdRequest.Builder()
            .setHttpTimeoutMillis(timeOut)
            .build()
    }

    private fun initTestDeviceIds() {
        mTestDeviceIds.add("d7e28f987358016e")
    }

    @JvmStatic
    fun Context.isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun getAdSize(activity: Activity, adWidth: Float): AdSize {
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        var adWidthPixels = adWidth
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, (adWidthPixels / density).toInt())
    }

    @JvmStatic
    fun loadBanner(activity: Activity,
                   idBanner: String,
                   viewGroup: ViewGroup,
                   bannerCallBack: BannerCallBack)
    {
        val mAdView = AdView(activity)
        mAdView.setAdSize(getAdSize(activity,viewGroup.width.toFloat()))
        mAdView.adUnitId = if (isAdsTest){
            activity.getString(R.string.id_test_banner_admob)
        }else{
            idBanner
        }

        //clean view
        viewGroup.removeAllViews()
        //init shimmer view
        val overlayView = activity.layoutInflater.inflate(R.layout.layout_banner_loading,null,false)
        //add view
        viewGroup.addView(overlayView,0)
        viewGroup.addView(mAdView,1)

        mShimmerFrameLayout = overlayView.findViewById(R.id.shimmerBanner)
        mShimmerFrameLayout?.startShimmer()

        mAdView.adListener = object: AdListener() {

            override fun onAdLoaded() {
                mAdView.onPaidEventListener = OnPaidEventListener { adValue ->  bannerCallBack.onPaid(adValue,mAdView)}
                mShimmerFrameLayout?.stopShimmer()
                viewGroup.removeView(overlayView)
                bannerCallBack.onAdLoaded()
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                mShimmerFrameLayout?.stopShimmer()
                viewGroup.removeView(overlayView)
                bannerCallBack.onAdFailedToLoad(adError.message)
            }

            override fun onAdClicked() {
                bannerCallBack.onAdClicked()
            }

            override fun onAdImpression() {}

            override fun onAdClosed() {}

            override fun onAdOpened() {}
        }

        if (mAdRequest != null){
            mAdView.loadAd(mAdRequest!!)
        }
    }

    @JvmStatic
    fun loadCollapsibleBanner(activity: Activity,
                              idBanner: String,
                              viewGroup: ViewGroup,
                              mCollapsibleBanner: CollapsibleBanner,
                              bannerCallBack: CollapsibleBannerCallBack)
    {
        val mAdView = AdView(activity)
        mAdView.setAdSize(getAdSize(activity,viewGroup.width.toFloat()))
        mAdView.adUnitId = if (isAdsTest){
            activity.getString(R.string.id_test_collapsible_banner_admob)
        }else{
            idBanner
        }

        //remove view
        viewGroup.removeAllViews()
        //init shimmer view
        val overlayView = activity.layoutInflater.inflate(R.layout.layout_banner_loading,null,false)
        //add view
        viewGroup.addView(overlayView,0)
        viewGroup.addView(mAdView,1)

        mShimmerFrameLayout = overlayView.findViewById(R.id.shimmerBanner)
        mShimmerFrameLayout?.startShimmer()

        mAdView.adListener = object: AdListener() {

            override fun onAdLoaded() {
                mAdView.onPaidEventListener = OnPaidEventListener { adValue ->  bannerCallBack.onPaid(adValue,mAdView)}
                mShimmerFrameLayout?.stopShimmer()
                viewGroup.removeView(overlayView)
                mAdView.adSize?.let { bannerCallBack.onAdLoaded(adSize = it) }
                Log.d("TAG==", "onAdClosed: onAdLoaded")
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                mShimmerFrameLayout?.stopShimmer()
                viewGroup.removeView(overlayView)
                bannerCallBack.onAdFailedToLoad(adError.message)
                Log.d("TAG==", "onAdClosed: onAdFailedToLoad")
            }

            override fun onAdClicked() {
                bannerCallBack.onAdClicked()
                Log.d("TAG==", "onAdClosed: onAdClicked")
            }

            override fun onAdImpression() {
                Log.d("TAG==", "onAdClosed: onAdImpression")
            }

            override fun onAdClosed() {
                Log.d("TAG==", "onAdClosed: onAdClosed")
            }

            override fun onAdOpened() {
                Log.d("TAG==", "onAdClosed: onAdOpened")
            }
        }

        val extras = Bundle()
        val position = if (mCollapsibleBanner == CollapsibleBanner.TOP){
            "top"
        }else{
            "bottom"
        }
        extras.putString("collapsible", position)
        extras.putString("collapsible_request_id", UUID.randomUUID().toString());

        val adRequest = AdRequest.Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            .build()

        mAdView.loadAd(adRequest)
    }

    interface CollapsibleBannerCallBack{
        fun onAdLoaded(adSize: AdSize)
        fun onAdFailedToLoad(message: String)
        fun onAdClicked()
        fun onPaid(adValue: AdValue, mAdView: AdView)
    }

}