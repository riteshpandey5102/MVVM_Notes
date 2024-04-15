plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKapt)
    alias(libs.plugins.navigation)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.mvvm_notes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mvvm_notes"
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
    buildFeatures{
        dataBinding=true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    Testing
    testImplementation(libs.junit)
    testImplementation (libs.androidx.core.testing)
    testImplementation (libs.mockk)
    testImplementation (libs.truth)
    testImplementation (libs.kotlinx.coroutines.test)
    androidTestImplementation (libs.kotlinx.coroutines.test)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.core.testing)
    androidTestImplementation (libs.truth)
    androidTestImplementation (libs.androidx.espresso.core)
//    LifeCycle
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.extensions)
//    Room DB
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)
//    Room KTX for Coroutines
    implementation (libs.androidx.room.ktx)
//    Recycler View
    implementation( libs.androidx.recyclerview)
//    Card View
    implementation (libs.androidx.cardview)
//    Navigation
    implementation (libs.androidx.navigation.fragment.ktx)

}