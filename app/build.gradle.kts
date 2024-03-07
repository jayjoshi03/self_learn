plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.mycourse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mycourse"
        minSdk = 26
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

    dataBinding {
        enable = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation(("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20"))
    implementation(("androidx.legacy:legacy-support-v4:1.0.0"))
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //hilt dagger
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-compiler:2.48.1")

    // For instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48.1")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.48.1")

    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:2.48.1")
    kaptTest("com.google.dagger:hilt-compiler:2.48.1")

    //For rememberLauncherForActivityResult()
    implementation("androidx.activity:activity-compose:1.8.2")

    //viewModel dependency
    val devVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${devVersion}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${devVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${devVersion}")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:${devVersion}")

    // Retrofit - Gson
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.3.0")
    implementation("com.squareup.retrofit2:converter-simplexml:2.1.0")
    implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")

    // okHttpClient
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))

    // ImageLoading
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //ViewModels() important
    implementation("androidx.activity:activity-ktx:1.8.2")
}