plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dungvnhh98.percas.studio.example"
    compileSdk = 34

    defaultConfig {
        ndk {
            abiFilters.clear()
            abiFilters.addAll(mutableSetOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }
        applicationId = "com.dungvnhh98.percas.studio.example"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    bundle{
        language{
            enableSplit = false
        }
    }

}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(project(":AdmobLib"))
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.android.gms:play-services-ads:23.0.0")
    implementation ("com.airbnb.android:lottie:6.4.0")
    implementation(files("libs/adjust-lib.aar"))

//    //adjust
//    implementation ("com.adjust.sdk:adjust-android:4.33.5")
//    implementation ("com.android.installreferrer:installreferrer:2.2")
//    implementation ("com.adjust.sdk:adjust-android-webbridge:4.33.5")
//    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
//    implementation ("com.google.android.gms:play-services-appset:16.0.2")
}