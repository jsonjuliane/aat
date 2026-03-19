plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.accessibilitydemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.accessibilitydemo"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    buildFeatures {
        viewBinding = false
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
    // Default: develop against the local module.
    // Optional: `./gradlew :demo:assembleDebug -PuseJitpack` to consume the published artifact.
        if (providers.gradleProperty("useJitpack").isPresent) {
        val jitpackVersion = providers.gradleProperty("jitpackVersion").orNull ?: "v0.1.0"
        implementation("com.github.jsonjuliane:aat:$jitpackVersion")
    } else {
        implementation(project(":toolkit"))
    }
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")
}

