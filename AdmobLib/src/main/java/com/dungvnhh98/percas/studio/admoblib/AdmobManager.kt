@file:Suppress("DEPRECATION")

package com.dungvnhh98.percas.studio.admoblib

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.airbnb.lottie.LottieAnimationView
import com.dungvnhh98.percas.studio.admoblib.callback.BannerCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.LoadAndShowNativeCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.LoadInterCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.LoadNativeCallback
import com.dungvnhh98.percas.studio.admoblib.callback.RewardCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.ShowInterAdCallBack
import com.dungvnhh98.percas.studio.admoblib.callback.ShowNativeCallBack
import com.dungvnhh98.percas.studio.admoblib.enum.NativeAdsSize
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.vapp.admoblibrary.ads.model.InterAdHolder
import com.vapp.admoblibrary.ads.model.NativeAdHolder

@SuppressLint("InflateParams")
object AdmobManager {
    private const val TAG = "TAG ==="

    var isAdsTest = true
    var isEnableAds = false
    var isOverlayAdShowing = false

    private var timeOut = 0

    private var mAdRequest: AdRequest? = null

    private val mTestDeviceIds: ArrayList<String> = ArrayList()

    var mShimmerFrameLayout: ShimmerFrameLayout? = null


    private var dialogFullScreen: Dialog? = null

    @JvmStatic
    fun initAdmob(context: Context?, timeOut: Int, isAdsTest: Boolean, isEnableAds: Boolean) {

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
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
            activity,
            (adWidthPixels / density).toInt()
        )
    }


    @JvmStatic
    fun loadBanner(
        activity: Activity,
        idBanner: String,
        viewGroup: ViewGroup,
        bannerCallBack: BannerCallBack
    ) {
        val mAdView = AdView(activity)
        mAdView.setAdSize(getAdSize(activity, viewGroup.width.toFloat()))
        mAdView.adUnitId = if (isAdsTest) {
            activity.getString(R.string.id_test_banner_admob)
        } else {
            idBanner
        }

        //clean view
        viewGroup.removeAllViews()
        //init shimmer view
        val overlayView =
            activity.layoutInflater.inflate(R.layout.layout_banner_loading, null, false)
        //add view
        viewGroup.addView(overlayView, 0)
        viewGroup.addView(mAdView, 1)

        mShimmerFrameLayout = overlayView.findViewById(R.id.shimmerBanner)
        mShimmerFrameLayout?.startShimmer()

        mAdView.adListener = object : AdListener() {

            override fun onAdLoaded() {
                mAdView.onPaidEventListener =
                    OnPaidEventListener { adValue -> bannerCallBack.onAdsPaid(adValue, mAdView) }
                mShimmerFrameLayout?.stopShimmer()
                viewGroup.removeView(overlayView)
                bannerCallBack.onAdLoaded()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
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

        if (mAdRequest != null) {
            mAdView.loadAd(mAdRequest!!)
        }
    }


    @JvmStatic
    fun loadNativeAds(
        context: Context,
        nativeHolder: NativeAdHolder,
        adCallback: LoadNativeCallback
    ) {
        if (!isEnableAds) {
            adCallback.onAdsLoadFail("Ads is Disable now")
            return
        }
        if (!context.isNetworkConnected()) {
            adCallback.onAdsLoadFail("No internet")
            return
        }
        //If native is loaded return
        if (nativeHolder.nativeAd != null) {
            adCallback.onAdsLoadFail("This Native ads is not empty. Don't need to load again")
            return
        }
        if (isAdsTest) {
            nativeHolder.ads = context.getString(R.string.id_test_native_admob)
        }
        nativeHolder.isLoading = true

        VideoOptions.Builder().setStartMuted(false).build()

        val adLoader: AdLoader = AdLoader.Builder(context, nativeHolder.ads)
            .forNativeAd { nativeAd ->
                nativeHolder.nativeAd = nativeAd
                nativeHolder.isLoading = false
                nativeHolder.native_mutable.value = nativeAd
                nativeAd.setOnPaidEventListener { adValue: AdValue? ->
                    adValue?.let {
                        adCallback.onAdPaid(
                            it, nativeHolder.ads
                        )
                    }
                }
                adCallback.onLoadedAndGetNativeAd(nativeAd)
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(TAG, "onAdFailedToLoad" + adError.message)
                    Log.e(TAG, "errorCodeAds" + adError.cause)
                    nativeHolder.nativeAd = null
                    nativeHolder.isLoading = false
                    nativeHolder.native_mutable.value = null
                    adCallback.onAdsLoadFail(adError.message)
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build()).build()


        if (mAdRequest != null) {
            adLoader.loadAd(mAdRequest!!)
        } else {
            adCallback.onAdsLoadFail("Admob is not init now. Check it before load ads!")
        }
    }

    @JvmStatic
    fun showNativeAds(
        activity: Activity,
        nativeHolder: NativeAdHolder,
        viewNativeAds: ViewGroup,
        layout: Int,
        nativeSize: NativeAdsSize,
        callback: ShowNativeCallBack
    ) {
        if (!isEnableAds) {
            viewNativeAds.visibility = View.GONE
            callback.onAdsNativeShowFailed("Ads is DISABLE now")
            return
        }
        if (!activity.isNetworkConnected()) {
            viewNativeAds.visibility = View.GONE
            callback.onAdsNativeShowFailed("No Internet")
            return
        }

        if (mShimmerFrameLayout != null) {
            mShimmerFrameLayout?.stopShimmer()
        }

        viewNativeAds.removeAllViews()

        if (!nativeHolder.isLoading) {

            if (nativeHolder.nativeAd != null) {
                val adView = activity.layoutInflater.inflate(layout, null) as NativeAdView
                bindNativeAdView(nativeHolder.nativeAd!!, adView, nativeSize)
                if (mShimmerFrameLayout != null) {
                    mShimmerFrameLayout?.stopShimmer()
                }
                nativeHolder.native_mutable.removeObservers((activity as LifecycleOwner))
                viewNativeAds.removeAllViews()
                viewNativeAds.addView(adView)
                callback.onAdsNativeShowed()
            } else {
                if (mShimmerFrameLayout != null) {
                    mShimmerFrameLayout?.stopShimmer()
                }
                nativeHolder.native_mutable.removeObservers((activity as LifecycleOwner))
                callback.onAdsNativeShowFailed("Native is not loaded")
            }

        } else {

            val tagView: View = if (nativeSize === NativeAdsSize.MEDIUM) {
                activity.layoutInflater.inflate(R.layout.layoutnative_loading_medium, null, false)
            } else {
                activity.layoutInflater.inflate(R.layout.layoutnative_loading_small, null, false)
            }
            viewNativeAds.addView(tagView, 0)

            if (mShimmerFrameLayout == null) mShimmerFrameLayout =
                tagView.findViewById(R.id.shimmer_view_container)

            mShimmerFrameLayout?.startShimmer()


            nativeHolder.native_mutable.observe((activity as LifecycleOwner)) { nativeAd: NativeAd? ->
                if (nativeAd != null) {
                    nativeAd.setOnPaidEventListener {
                        callback.onAdsNativePaid(it, nativeHolder.ads)
                    }
                    val adView = activity.layoutInflater.inflate(layout, null) as NativeAdView
                    bindNativeAdView(nativeAd, adView, nativeSize)
                    if (mShimmerFrameLayout != null) {
                        mShimmerFrameLayout?.stopShimmer()
                    }
                    viewNativeAds.removeAllViews()
                    viewNativeAds.addView(adView)
                    callback.onAdsNativeShowed()
                    nativeHolder.native_mutable.removeObservers((activity as LifecycleOwner))
                } else {
                    if (mShimmerFrameLayout != null) {
                        mShimmerFrameLayout?.stopShimmer()
                    }
                    callback.onAdsNativeShowFailed("None Show")
                    nativeHolder.native_mutable.removeObservers((activity as LifecycleOwner))
                }
            }
        }
    }


    @JvmStatic
    fun loadAndShowNativeAds(
        activity: Activity,
        nativeHolder: NativeAdHolder,
        viewNativeAds: ViewGroup,
        layout: Int,
        size: NativeAdsSize,
        adCallback: LoadAndShowNativeCallBack
    ) {
        if (!isEnableAds) {
            viewNativeAds.visibility = View.GONE
            adCallback.onAdFail("Ads is DISABLE now")
            return
        }
        if (!activity.isNetworkConnected()) {
            viewNativeAds.visibility = View.GONE
            adCallback.onAdFail("No Internet")
            return
        }

        viewNativeAds.removeAllViews()

        if (isAdsTest) {
            nativeHolder.ads = activity.getString(R.string.id_test_native_admob)
        }

        val tagView: View = if (size === NativeAdsSize.MEDIUM) {
            activity.layoutInflater.inflate(R.layout.layoutnative_loading_medium, null, false)
        } else {
            activity.layoutInflater.inflate(R.layout.layoutnative_loading_small, null, false)
        }

        viewNativeAds.addView(tagView, 0)

        if (mShimmerFrameLayout == null) mShimmerFrameLayout =
            tagView.findViewById(R.id.shimmer_view_container)

        mShimmerFrameLayout?.startShimmer()


        val adLoader = AdLoader.Builder(activity, nativeHolder.ads)
            .forNativeAd { nativeAd ->
                adCallback.onLoadedAndGetNativeAd(nativeAd)
                val adView = activity.layoutInflater
                    .inflate(layout, null) as NativeAdView
                bindNativeAdView(nativeAd, adView, size)
                mShimmerFrameLayout!!.stopShimmer()
                viewNativeAds.removeAllViews()
                viewNativeAds.addView(adView)
                adCallback.onNativeAdsShowed()
                nativeAd.setOnPaidEventListener { adValue: AdValue ->
                    adCallback.onAdPaid(adValue, nativeHolder.ads)
                }
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mShimmerFrameLayout!!.stopShimmer()
                    viewNativeAds.removeAllViews()
                    nativeHolder.isLoading = false
                    adCallback.onAdFail(adError.message + "\nError Code Ads:\n" + adError.cause)
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    adCallback.onClickAds()
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build()).build()
        if (mAdRequest != null) {
            adLoader.loadAd(mAdRequest!!)
        } else {
            adCallback.onAdFail("Admob is not init now. Check it before load ads!")
        }
    }

    private fun bindNativeAdView(
        nativeAd: NativeAd,
        adView: NativeAdView,
        size: NativeAdsSize
    ) {
        adView.findViewById<MediaView>(R.id.ad_media)?.let {
            adView.mediaView = it
        }

        adView.findViewById<TextView>(R.id.ad_headline)?.let {
            adView.headlineView = it
        }

        adView.findViewById<TextView>(R.id.ad_body)?.let {
            adView.bodyView = it
        }

        adView.findViewById<Button>(R.id.ad_call_to_action)?.let {
            adView.callToActionView = it
        }

        adView.findViewById<ImageView>(R.id.ad_app_icon)?.let {
            adView.iconView = it
        }

        adView.findViewById<RatingBar>(R.id.ad_stars)?.let {
            adView.starRatingView = it
        }

        if (nativeAd.mediaContent != null) {
            if (size == NativeAdsSize.MEDIUM) {
                adView.mediaView?.let {
                    it.setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                    val mediaContent = nativeAd.mediaContent
                    if (mediaContent != null && mediaContent.hasVideoContent()) {
                        // Create a MediaView and set its media content.
                        val mediaView = MediaView(it.context)
                        mediaView.mediaContent = mediaContent
                        it.addView(mediaView)
                    }
                }
            }
        }

        if (nativeAd.headline != null) {
            (adView.headlineView as TextView).text = nativeAd.headline
        }
        if (nativeAd.body == null) {
            adView.bodyView!!.visibility = View.INVISIBLE
        } else {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView!!.visibility = View.INVISIBLE

        } else {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }


        if (adView.iconView != null) {
            if (nativeAd.icon == null) {
                adView.iconView!!.visibility = View.GONE
            } else {
                (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon!!.drawable
                )
                adView.iconView!!.visibility = View.VISIBLE
            }
        }

        if (nativeAd.starRating != null) {
            (adView.starRatingView as RatingBar).rating = 5f
        }

        adView.setNativeAd(nativeAd)

        val vc = nativeAd.mediaContent!!.videoController
        if (vc.hasVideoContent()) {
            vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
            }
        }
    }


    @JvmStatic
    fun loadAdInterstitial(
        activity: Context,
        interHolder: InterAdHolder,
        adLoadCallback: LoadInterCallBack
    ) {
        if (!isEnableAds) {
            adLoadCallback.onAdInterFail("Ads is DISABLE now")
            return
        }

        if (!activity.isNetworkConnected()) {
            adLoadCallback.onAdInterFail("No Internet")
            return
        }
        if (interHolder.inter != null) {
            adLoadCallback.onAdInterFail("This Interstitial Ad is not empty. Don't need to load again")
            return
        }
        interHolder.isloading = true

        if (mAdRequest == null) {
            initAdRequest(timeOut)
        }

        if (isAdsTest) {
            interHolder.ads = activity.getString(R.string.id_test_interstitial_admob)
        }

        InterstitialAd.load(
            activity,
            interHolder.ads,
            mAdRequest!!,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    if (isOverlayAdShowing) {
                        interHolder.mutable.value = interstitialAd
                    }
                    interHolder.inter = interstitialAd
                    interHolder.isloading = false
                    adLoadCallback.onAdInterLoaded(interstitialAd)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interHolder.isloading = false
                    if (isOverlayAdShowing) {
                        interHolder.mutable.value = null
                    }
                    adLoadCallback.onAdInterFail(loadAdError.message)
                }
            })
    }

    @JvmStatic
    fun showAdInter(
        activity: Activity,
        interHolder: InterAdHolder,
        adCallback: ShowInterAdCallBack,
    ) {
        if (isOverlayAdShowing) {
            adCallback.onInterAdFail("Other Ads is showing now!")
            return
        }
        isOverlayAdShowing = true
        if (!isEnableAds) {
            isOverlayAdShowing = false
            if (AppResumeAdsManager.getInstance().isInitialized) {
                AppResumeAdsManager.getInstance().isAppResumeEnabled = true
            }
            adCallback.onInterAdFail("Ads is DISABLE now")
            return
        }

        if (!activity.isNetworkConnected()) {
            isOverlayAdShowing = false
            if (AppResumeAdsManager.getInstance().isInitialized) {
                AppResumeAdsManager.getInstance().isAppResumeEnabled = true
            }
            adCallback.onInterAdFail("No Internet")
            return
        }

        adCallback.onInterAdShow()


        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            if (interHolder.isloading) {
                if (AppResumeAdsManager.getInstance().isInitialized) {
                    AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                }
                isOverlayAdShowing = false
                interHolder.mutable.removeObservers((activity as LifecycleOwner))
                dismissAdDialog()
                adCallback.onInterAdFail("Time out!")
            }
        }
        handler.postDelayed(runnable, 10000)

        if (interHolder.isloading) {
            dialogLoading(activity)

            interHolder.mutable.observe((activity as LifecycleOwner)) { interstitialAd: InterstitialAd? ->
                if (interstitialAd != null) {

                    interHolder.mutable.removeObservers((activity as LifecycleOwner))

                    Handler(Looper.getMainLooper()).postDelayed({

                        interstitialAd.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    isOverlayAdShowing = false

                                    if (AppResumeAdsManager.getInstance().isInitialized) {
                                        AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                                    }
                                    interHolder.inter = null
                                    interHolder.mutable.removeObservers((activity as LifecycleOwner))
                                    interHolder.mutable.value = null
                                    adCallback.onInterAdClose()
                                    dismissAdDialog()
                                }

                                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                    isOverlayAdShowing = false
                                    if (AppResumeAdsManager.getInstance().isInitialized) {
                                        AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                                    }
                                    isOverlayAdShowing = false
                                    interHolder.inter = null
                                    dismissAdDialog()
                                    interHolder.mutable.removeObservers((activity as LifecycleOwner))
                                    interHolder.mutable.value = null
                                    handler.removeCallbacksAndMessages(null)
                                    adCallback.onInterAdFail(adError.message + " \ncause: \n" + adError.cause)
                                }

                                override fun onAdShowedFullScreenContent() {
                                    handler.removeCallbacksAndMessages(null)
                                    isOverlayAdShowing = true
                                    adCallback.onInterAdShow()
                                    try {
                                        interstitialAd.setOnPaidEventListener { adValue ->
                                            adCallback.onAdPaid(
                                                adValue,
                                                interHolder.inter!!.adUnitId
                                            )
                                        }
                                    } catch (_: Exception) {
                                    }
                                }
                            }
                        showInterstitialAdNew(activity, interstitialAd, adCallback)
                    }, 400)
                } else {
                    interHolder.isloading = true
                }
            }
            return
        }


        //Load inter done
        if (interHolder.inter == null) {
            isOverlayAdShowing = false
            if (AppResumeAdsManager.getInstance().isInitialized) {
                AppResumeAdsManager.getInstance().isAppResumeEnabled = true
            }
            adCallback.onInterAdFail("Inter Ad is null. Load inter ad before show!!!")
            handler.removeCallbacksAndMessages(null)
        } else {
            dialogLoading(activity)
            Handler(Looper.getMainLooper()).postDelayed({
                interHolder.inter?.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            isOverlayAdShowing = false
                            if (AppResumeAdsManager.getInstance().isInitialized) {
                                AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                            }
                            interHolder.mutable.removeObservers((activity as LifecycleOwner))
                            interHolder.inter = null
                            adCallback.onInterAdClose()
                            dismissAdDialog()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            isOverlayAdShowing = false
                            if (AppResumeAdsManager.getInstance().isInitialized) {
                                AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                            }
                            handler.removeCallbacksAndMessages(null)
                            interHolder.inter = null
                            interHolder.mutable.removeObservers((activity as LifecycleOwner))
                            dismissAdDialog()
                            adCallback.onInterAdFail(adError.message + " \ncause:\n" + adError.cause)
                            Log.e("Admodfail", "onAdFailedToLoad" + adError.message)
                            Log.e("Admodfail", "errorCodeAds" + adError.cause)
                        }

                        override fun onAdShowedFullScreenContent() {
                            handler.removeCallbacksAndMessages(null)
                            isOverlayAdShowing = true
                            adCallback.onInterAdShow()
                        }
                    }
                showInterstitialAdNew(activity, interHolder.inter, adCallback)
            }, 400)
        }
    }

    fun dismissAdDialog() {
        try {
            if (dialogFullScreen != null && dialogFullScreen?.isShowing == true) {
                dialogFullScreen?.dismiss()
            }
        } catch (_: Exception) {

        }
    }

    @JvmStatic
    private fun showInterstitialAdNew(
        activity: Activity,
        mInterstitialAd: InterstitialAd?,
        callback: ShowInterAdCallBack
    ) {
        if (ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && mInterstitialAd != null) {
            isOverlayAdShowing = true
            Handler(Looper.getMainLooper()).postDelayed({
                callback.onInterAdShow()
                mInterstitialAd.setOnPaidEventListener { adValue ->
                    callback.onAdPaid(
                        adValue,
                        mInterstitialAd.adUnitId
                    )
                }
                mInterstitialAd.show(activity)
            }, 400)
        } else {
            isOverlayAdShowing = false
            if (AppResumeAdsManager.getInstance().isInitialized) {
                AppResumeAdsManager.getInstance().isAppResumeEnabled = true
            }
            dismissAdDialog()
            callback.onInterAdFail("Your App is showing on resume ad or inter ad is null")
        }
    }

    private fun dialogLoading(context: Activity) {
        dialogFullScreen = Dialog(context)
        dialogFullScreen?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogFullScreen?.setContentView(R.layout.dialog_full_screen)
        dialogFullScreen?.setCancelable(false)
        dialogFullScreen?.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialogFullScreen?.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val img = dialogFullScreen?.findViewById<LottieAnimationView>(R.id.imageView3)
        img?.setAnimation(R.raw.gifloading)
        try {
            if (!context.isFinishing && dialogFullScreen != null && dialogFullScreen?.isShowing == false) {
                dialogFullScreen?.show()
            }
        } catch (ignored: Exception) {
        }

    }

    fun loadAndShowAdReward(
        activity: Activity,
        admobId: String,
        adCallback: RewardCallBack
    ) {
        if (!isEnableAds) {
            adCallback.onAdFail("Ads is DISABLE now")
            return
        }

        if (!activity.isNetworkConnected()) {
            adCallback.onAdFail("No Internet")
            return
        }
        if (isOverlayAdShowing) {
            adCallback.onAdFail("Other ad is showing")
            return
        }
        if (mAdRequest == null) {
            initAdRequest(timeOut)
        }
        val idReward = if (isAdsTest) {
            activity.getString(R.string.id_test_reward_admob)
        } else {
            admobId
        }

        dialogLoading(activity)

        isOverlayAdShowing = true

        if (AppResumeAdsManager.getInstance().isInitialized) {
            AppResumeAdsManager.getInstance().isAppResumeEnabled = false
        }

        RewardedAd.load(activity, idReward,
            mAdRequest!!, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    adCallback.onAdFail(loadAdError.message + "\nCause:\n" + loadAdError.cause)
                    dismissAdDialog()
                    if (AppResumeAdsManager.getInstance().isInitialized) {
                        AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                    }
                    isOverlayAdShowing = false
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    rewardedAd.setOnPaidEventListener {
                        adCallback.onPaid(
                            it,
                            rewardedAd.adUnitId
                        )
                    }

                    rewardedAd.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdShowedFullScreenContent() {
                                isOverlayAdShowing = true
                                adCallback.onAdShowed()
                                if (AppResumeAdsManager.getInstance().isInitialized) {
                                    AppResumeAdsManager.getInstance().isAppResumeEnabled = false
                                }
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                isOverlayAdShowing = false
                                adCallback.onAdFail(adError.message + "\nCause:n\n" + adError.cause)
                                dismissAdDialog()

                                if (AppResumeAdsManager.getInstance().isInitialized) {
                                    AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                                }
                            }

                            override fun onAdDismissedFullScreenContent() {
                                isOverlayAdShowing = false
                                adCallback.onAdClosed()
                                dismissAdDialog()
                                if (AppResumeAdsManager.getInstance().isInitialized) {
                                    AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                                }
                            }
                        }

                    if (ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        if (AppResumeAdsManager.getInstance().isInitialized) {
                            AppResumeAdsManager.getInstance().isAppResumeEnabled = false
                        }
                        rewardedAd.show(activity) {
                            adCallback.onEarned()
                            dismissAdDialog()
                        }
                        isOverlayAdShowing = true
                    } else {
                        dismissAdDialog()
                        isOverlayAdShowing = false
                        if (AppResumeAdsManager.getInstance().isInitialized) {
                            AppResumeAdsManager.getInstance().isAppResumeEnabled = true
                        }
                        adCallback.onAdFail("Your App is showing on resume ad")
                    }
                }
            })
    }
}