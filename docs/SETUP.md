# Setup & navigation (for reviewers)

This repo contains:
- `:toolkit` — reusable Android library (“SDK-like”)
- `:demo` — demo app consuming the toolkit

## Quick start (Android Studio)
1. Open the repo root in Android Studio.
2. Let Gradle sync finish.
3. Run the `demo` configuration (launches `MainActivity`).

## Quick start (command line)

Build and install the demo:

```bash
./gradlew :demo:installDebug
```

Run toolkit unit tests:

```bash
./gradlew :toolkit:testDebugUnitTest
```

## How to navigate the code
- **Toolkit entry point**: `toolkit/src/main/java/com/example/accessibilitytoolkit/AccessibilityToolkit.kt`
- **Controllers** (text scale / font / contrast / locale / TTS): `toolkit/src/main/java/com/example/accessibilitytoolkit/`
- **Persistence**: `SettingsStore` + `SharedPreferencesSettingsStore`
- **Demo Activities**:
  - `demo/src/main/java/com/example/accessibilitydemo/MainActivity.kt`
  - `demo/src/main/java/com/example/accessibilitydemo/SecondActivity.kt`
- **Demo layouts**: `demo/src/main/res/layout/`

## Documentation
Start at [docs/README.md](README.md) which links to:
- **Engineering docs**: `docs/logic/*`
- **Library consumer docs**: `docs/library/*`

