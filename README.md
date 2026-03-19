# Accessibility Toolkit (Android)

This is a technical test project demonstrating an accessibility “toolkit/SDK”-like approach for Android:
- Text size adjustment
- High-contrast theme toggle
- Text-to-speech (TTS)
- Bonus: persisted settings, second screen reuse, mock translation via resources, and font family toggle

## Project modules
- `:toolkit` — reusable Android library module
- `:demo` — demo application that consumes `:toolkit`

## Run
Open in Android Studio (recommended), or run:
```bash
./gradlew :demo:installDebug
```

## Notes
- Ensure you have Android SDK + build tools installed and `local.properties` pointing to it.
- For local builds, create `local.properties` with your SDK path if missing.

## What to try in the demo
- Change text size with the slider and observe all `TextView` widgets updating.
- Toggle “High contrast” and “Font style” and see the toolkit re-apply settings.
- Pick a language (English/French) to demonstrate mock translation via `strings.xml`.
- Press “Read aloud” (main screen + second screen) to trigger TTS using the selected locale.

## Docs
- Start here: [docs/README.md](./docs/README.md)
- Reviewer setup guide: [docs/SETUP.md](./docs/SETUP.md)

