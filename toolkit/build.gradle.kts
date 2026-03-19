plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

val isJitpackBuild = System.getenv("JITPACK")?.isNotBlank() == true

android {
    namespace = "com.example.accessibilitytoolkit"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34

        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

androidComponents {
    // JitPack runs publication tasks and may attempt to resolve release unit-test classpaths.
    // Disable unit tests for the release variant there to avoid resolution failures while still
    // keeping unit tests working locally.
    beforeVariants(selector().withBuildType("release")) { variant ->
        if (isJitpackBuild) {
            variant.enableUnitTest = false
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.13")
    testImplementation("androidx.test:core:1.5.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                // JitPack coordinates:
                // implementation("com.github.jsonjuliane:aat:<tag>")
                groupId = "com.github.jsonjuliane"
                artifactId = "aat"
                version = project.version.toString()
            }
        }
    }
}

