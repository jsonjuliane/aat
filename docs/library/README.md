# Accessibility Toolkit (Library README)

This module (`:toolkit`) provides a small, reusable accessibility toolkit for Android apps:
- Text scaling across a view tree
- High-contrast theme toggle
- Font family toggle (sans / mono)
- Locale wrapping (mock translation support)
- Text-to-speech (TTS) helper

## Installation

### Local Gradle dependency (multi-module)

```kotlin
dependencies {
  implementation(project(":toolkit"))
}
```

### Published dependency (example)

#### JitPack (this repo)

1) Add JitPack to repositories (usually in `settings.gradle.kts`):

```kotlin
dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
  }
}
```

2) Add the dependency:

```kotlin
dependencies {
  implementation("com.github.jsonjuliane:aat:<tag>")
}
```

## Quick start

### 1) Provide a SettingsStore

Apps can provide any persistence mechanism by implementing:
- `SettingsStore.load(): AccessibilitySettings`
- `SettingsStore.save(settings: AccessibilitySettings)`

For the demo, `SettingsStoreFactory.create(context)` returns a SharedPreferences-backed store.

### 2) Apply locale early (Activity)

```kotlin
override fun attachBaseContext(newBase: Context) {
  super.attachBaseContext(AccessibilityToolkit.wrapBaseContext(newBase))
}
```

### 3) Apply theme before inflating views

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)

  val store = SettingsStoreFactory.create(this)
  AccessibilityToolkit.initialize(application, store)

  AccessibilityToolkit.applyOnActivity(this)
  setContentView(R.layout.your_layout)

  AccessibilityToolkit.applyToViewTree(findViewById(R.id.root))
}
```

### 4) Speak visible content

```kotlin
AccessibilityToolkit.speak(textToRead, utteranceId = "screen_body")
```

## Notes
- For theme and locale changes at runtime, recreating the Activity is the simplest approach (`Activity.recreate()`).
- `applyToViewTree(root)` can be called repeatedly (e.g. during slider drags); `TextSizeController` avoids compounding by keeping a baseline per `TextView`.

