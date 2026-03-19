plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
}

// JitPack sets JITPACK_VERSION to the git tag (e.g. "v0.1.0").
version = System.getenv("JITPACK_VERSION") ?: "0.0.0-SNAPSHOT"

