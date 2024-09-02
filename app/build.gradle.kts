plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.gamefriends"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gamefriends"
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
        buildConfig = true
        dataBinding = true
    }
}

dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Fragment
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")


    // Image Networks
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.5.0")

    // Flexbox
    implementation ("com.google.android.flexbox:flexbox:3.0.0")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.github.qamarelsafadi:CurvedBottomNavigation:0.1.3")

    implementation ("androidx.datastore:datastore-preferences:1.1.1")

    implementation("net.zetetic:android-database-sqlcipher:4.4.0")
    implementation("androidx.sqlite:sqlite-ktx:2.1.0")

    implementation("com.google.dagger:hilt-android:2.44")
    ksp("com.google.dagger:hilt-android-compiler:2.44")
    ksp("com.google.dagger:hilt-compiler:2.44")

}