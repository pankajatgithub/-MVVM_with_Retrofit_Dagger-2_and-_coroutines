plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.mvvm_with_retrofit_dagger_2_and__coroutines"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mvvm_with_retrofit_dagger_2_and__coroutines"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.fragment)
    val lifecycle_version = "2.9.3"
    val nav_version = "2.9.3"
    val lottieVersion = "3.4.0" // Replace with the latest stable version

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)

    // Retrofit core library
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Use the latest stable version

    // Converter library (e.g., Gson) for parsing JSON responses
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Use the same version as Retrofit

    // Optional: OkHttp for logging and interceptors
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0") // Use the latest stable version

// Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:${nav_version}")
    implementation("androidx.navigation:navigation-ui:${nav_version}")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation ("com.airbnb.android:lottie:$lottieVersion")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}