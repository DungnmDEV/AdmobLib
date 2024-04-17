# Admob Library
<h3 align="center">From Percas Studio by Dungvnhh98</h3>

<p align="left"> <a href="https://developer.android.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original-wordmark.svg" alt="android" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://kotlinlang.org" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/kotlinlang/kotlinlang-icon.svg" alt="kotlin" width="40" height="40"/> </a> </p>

Step1: Add it in your root build.gradle at the end of repositories:
```bash
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2: Add the dependency
```bash
          implementation 'com.google.android.gms:play-services-ads:{version}'
	        implementation 'com.github.dungvnhh98:AdmobLib:{version}'
```


Step 3: Add to AndroidManifest.xml
```bash
<!--    ID Ads Test:    ca-app-pub-3940256099942544~3347511713-->

<meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="{YourAdsID}" />
```

Step 4: Create MyApplication extend Application
```bash
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

    }
}
```
Step 5: Use MyApplication in AndroidManifest.xml
```bash
<application
        android:name=".MyApplication"
	........
</application>
```
Step 6: Init Admob 
```bash
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AdmobManager.initAdmob(this, timeOut = 10000, isAdsTest = true, isEnableAds = true) \\change isAdsTest = false when you use live Ads ID
    }
}
```


Now you can use Admob Library


- AppResumeAdsManager:
```bash
//Init AppResumeAdsManager in MyApplication, if your use test Ads ID,you can leave the appOnresmeAdsId blank

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AdmobManager.initAdmob(this, timeOut = 10000, isAdsTest = true, isEnableAds = true)
        AppResumeAdsManager.getInstance().init(this, appOnresmeAdsId})
    }
}
```
- AppOpenAdsManager:
```bash
//Use AppOpenAdsManager in SplashScreen, if your use test Ads ID,you can leave the yourAppOpenAdsID blank


val appOpenAdsManager = AppOpenAdsManager(this,{yourAppOpenAdsID} ,timeout = 10000, object :AppOpenAdsManager.AppOpenAdsListener{
            override fun onAdsClose() {
                Log.d("TAG ===", "onAdsClose: ")
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAdsFailed(message: String) {
                Log.d("TAG ===", "onAdsFailed: "+message)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAdPaid(adValue: AdValue, adUnitAds: String) {
            }

        })
        appOpenAdsManager.loadAndShowAoA()
```

- Banner Ads:
```bash
fun showAdBanner(activity: Activity, bannerAdsID: String, viewBanner: ViewGroup, line: View) {
        if (activity.isNetworkConnected()) {
            AdmobManager.loadAndShowBanner(this, bannerAdsID, viewBanner, object : BannerCallBack {
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
        } else {
             viewBanner.gone()
	     line.gone()
        }
    }

```
- Native Ads:
```bash
// Load Native Ads before show it
// Use NativeAdHolder to hold Native ads id

val nativeHolder = NativeAdHolder(idNativeAd)

AdmobManager.loadNativeAds(this, nativeHolder, object : LoadNativeCallback {
            override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
                Log.d(TAG, "onLoadedAndGetNativeAd: ")
            }

            override fun onAdsLoadFail(error: String?) {
                Log.d(TAG, "onAdFail: "+error)
            }

            override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
            }

        })

// After load Native Ad, you can show it on a ViewGroup
fun showNativeMedium(activity: Activity, nativeHolder: NativeAdHolder, viewNativeAd: ViewGroup,
        layout: Int,
        nativeSize: NativeAdsSize,
        callback: ShowNativeCallBack) {

        AdmobManager.showNativeAds(
            activity,
            nativeHolder,
            viewNativeAd,
           layout,
            nativeSize,
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

```

- Other extention:
```bash
\\ Visible view
	View.visible()

\\ Invisible view
	View.invisible()

\\ Gone view
	View.gone()

\\ Add animation when change layout
	ViewGroup.actionAnimation()

\\ For example:
	viewContainer.actionAnimation() // viewContainer is the view containing the textview
	textView.gone()
```
