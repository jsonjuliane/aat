pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Needed only when consuming the toolkit from JitPack (demo supports an opt-in flag).
        maven("https://jitpack.io")
    }
}

rootProject.name = "AccessibilityToolkit"
include(":toolkit", ":demo")

