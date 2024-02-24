plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.x_chat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.x_chat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.01.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

//    Dagger Hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation ("com.google.dagger:hilt-android:2.50")
    ksp ("com.google.dagger:hilt-compiler:2.50")
    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.50")
    kspAndroidTest ("com.google.dagger:hilt-compiler:2.50")
    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:2.50")
    kspTest ("com.google.dagger:hilt-compiler:2.50")

//    coil
    implementation("io.coil-kt:coil-compose:2.5.0")

//    Socket Io
    implementation("io.socket:socket.io-client:2.1.0") {
        exclude(group = "org.json", module = "json")
    }

//    Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

//    Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

//    Json Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

//        Live Data
    implementation("androidx.compose.runtime:runtime-livedata:1.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")


//        ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")


//        Navigation
    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")

//    DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

//    Compose Foundation
    implementation("androidx.compose.foundation:foundation:1.6.2")

//    Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

//    Extended Icon
    implementation("androidx.compose.material:material-icons-extended-android:1.6.1")

//        SplashApi
    implementation("androidx.core:core-splashscreen:1.0.1")


//    CameraX
    implementation ("androidx.camera:camera-core:1.4.0-alpha04")
    implementation ("androidx.camera:camera-camera2:1.4.0-alpha04")
    implementation ("androidx.camera:camera-lifecycle:1.4.0-alpha04")
    implementation ("androidx.camera:camera-video:1.4.0-alpha04")
    implementation ("androidx.camera:camera-view:1.4.0-alpha04")
    implementation ("androidx.camera:camera-mlkit-vision:1.4.0-alpha04")
    implementation ("androidx.camera:camera-extensions:1.4.0-alpha04")
}
