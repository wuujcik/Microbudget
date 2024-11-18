plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.wuujcik.microbudget"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wuujcik.microbudget"
        minSdk = 26
        targetSdk = 34
        versionCode = 1020000
        versionName = "1.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            isDebuggable = false
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
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    //DI
    implementation(libs.koin.annotations)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    ksp(libs.koin.compiler)
    //DATA
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.dateTime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    //NAVIGATION
    implementation(libs.navigation.ui)
    implementation(libs.raamcosta.destinations.core)
    ksp(libs.raamcosta.destinations.ksp)

    //TEST
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}