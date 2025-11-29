
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.explorenow"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.explorenow"
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("com.google.zxing:core:3.5.4")
    implementation (libs.zxing.android.embedded)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material.v1110)

    implementation(libs.room.runtime.v261)
    implementation(libs.play.services.mlkit.barcode.scanning)
    implementation(libs.camera.view)
    implementation(libs.camera.lifecycle)
    annotationProcessor(libs.room.compiler.v261)

    implementation(libs.recyclerview)
    implementation(libs.camera.view)
    implementation(libs.core)
    implementation(libs.zxing.android.embedded)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common.jvm)
    implementation(libs.room.runtime)
    implementation(libs.play.services.maps)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor(libs.room.compiler)
}
