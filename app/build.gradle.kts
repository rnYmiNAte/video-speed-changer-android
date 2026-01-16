plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    kotlin("kapt")
}

android {
    namespace = "com.dola.videospeedchanger"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.dola.videospeedchanger"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Signing config for release builds
    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("app/app-keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val keystoreProperties = java.util.Properties()
                keystoreProperties.load(java.io.FileInputStream(keystorePropertiesFile))

                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            // Keep FFmpeg-Kit native libraries
            pickFirsts += "lib/**/libavcodec.so"
            pickFirsts += "lib/**/libavformat.so"
        }
    }
}

dependencies {
    // Core Android
    implementation libs.bundles.androidCore

    // Coroutines & Lifecycle
    implementation libs.bundles.coroutines
    implementation libs.bundles.lifecycle

    // Dependency Injection
    implementation libs.bundles.hilt
    kapt libs.hilt.compiler
    kapt libs.hilt.lifecycle.compiler

    // Video Processing
    implementation libs.ffmpeg.kit.full

    // Core Module
    implementation(project(":core"))

    // Unit Tests
    testImplementation libs.bundles.testingUnit

    // Instrumented Tests
    androidTestImplementation libs.bundles.testingInstrumented
    androidTestImplementation("androidx.test:runner:1.5.2")
}

// Allow kapt to process Hilt annotations
kapt {
    correctErrorTypes = true
}
