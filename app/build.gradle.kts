plugins {
    alias(libs.plugins.android.application)
<<<<<<< HEAD
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.sejam"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sejam"
        minSdk = 28
=======
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.tb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tb"
        minSdk = 24
>>>>>>> f1b2b907418f2ec746951bb8298aa82323833147
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
<<<<<<< HEAD
=======
        vectorDrawables {
            useSupportLibrary = true
        }
>>>>>>> f1b2b907418f2ec746951bb8298aa82323833147
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
<<<<<<< HEAD
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
=======
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
>>>>>>> f1b2b907418f2ec746951bb8298aa82323833147
    }
}

dependencies {
<<<<<<< HEAD
=======

>>>>>>> f1b2b907418f2ec746951bb8298aa82323833147
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
<<<<<<< HEAD
=======
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.tracing.perfetto.handshake)
    implementation(libs.androidx.appcompat)
>>>>>>> f1b2b907418f2ec746951bb8298aa82323833147
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
<<<<<<< HEAD
    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation.compose)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler.v2511)

    implementation ("com.pusher:pusher-java-client:2.2.1")
    implementation ("androidx.compose.material:material-icons-extended:1.7.6") // Adjust version as needed

    implementation (libs.coil.compose)


    // KSP
    implementation(libs.symbol.processing.api)

    implementation(libs.accompanist.navigation.animation)
=======
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Tambahkan Retrofit dan Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp.logging)

>>>>>>> f1b2b907418f2ec746951bb8298aa82323833147
}