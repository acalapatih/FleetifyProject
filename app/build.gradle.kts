plugins {
    alias(libs.plugins.android.application)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.acalapatih.fleetifyproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.acalapatih.fleetifyproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding {
            enable = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packagingOptions {
        exclude("META-INF/gradle/incremental.annotation.processors")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.hilt.android)
    implementation(libs.dagger.hilt.android)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    implementation(libs.lifecycle.extensions)
    implementation(libs.hilt.compiler)
    annotationProcessor(libs.hilt.android.compiler)

    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    implementation(libs.gson)

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
}