plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.dungvnhh98.percas.studio.admoblib"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}
publishing{
    publications{
        register<MavenPublication>("release"){
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-process:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    //google services
    implementation ("com.google.android.gms:play-services-ads:23.0.0")
    //shimmer view load
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation ("com.airbnb.android:lottie:6.4.0")

    //adjust
    implementation ("com.adjust.sdk:adjust-android:4.33.5")
    implementation ("com.android.installreferrer:installreferrer:2.2")
    implementation ("com.adjust.sdk:adjust-android-webbridge:4.33.5")
    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation ("com.google.android.gms:play-services-appset:16.0.2")
}