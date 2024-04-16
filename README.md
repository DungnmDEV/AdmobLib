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





