    plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

        //Hilt
        id("kotlin-kapt")
        id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.branchinternational"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.branchinternational"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Hilt
//    implementation("com.google.dagger:hilt-android:2.48")
//    kapt("com.google.dagger:hilt-compiler:2.48")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    //retrofit - Gson parser
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.activity:activity-compose:1.7.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.1")

    // Navigation component for Compose
    implementation ("androidx.navigation:navigation-compose:2.6.0")

    //Jetpack Compose stable
    implementation("androidx.compose.ui:ui:1.5.1")
    implementation ("androidx.compose.material3:material3:1.1.1")
    implementation ("androidx.compose.foundation:foundation:1.5.1")
    implementation ("androidx.activity:activity-compose:1.7.2")

    //Material library
    implementation("androidx.compose.material:material-icons-core:1.5.1")
    implementation ("androidx.compose.material:material-icons-extended:1.5.1")
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")
    implementation ("androidx.compose.material3:material3:1.1.1")
    implementation ("androidx.compose.ui:ui:1.5.1")
    implementation ("androidx.compose.foundation:foundation:1.5.1")
    implementation ("androidx.compose.runtime:runtime:1.5.1")
    implementation ("androidx.compose.material:material:1.5.1")

    //Serilaizable

    dependencies {
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    }

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Room dependencies
//    implementation("androidx.room:room-runtime:2.6.1")
//    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
}

