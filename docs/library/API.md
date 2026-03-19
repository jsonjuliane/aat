# Public API (Library)

The intended public entry point is:
- `com.example.accessibilitytoolkit.AccessibilityToolkit`

## `AccessibilityToolkit.initialize(application, store)`
Initializes the toolkit and loads persisted settings.

## `AccessibilityToolkit.wrapBaseContext(base): Context`
Wraps a base context with the currently selected locale. Use from `attachBaseContext()` to ensure resources inflate using the correct locale.

## `AccessibilityToolkit.applyOnActivity(activity)`
Applies settings that must be applied before view inflation (currently: theme selection).

## `AccessibilityToolkit.applyToViewTree(rootView)`
Applies view-tree settings (text scale, font, high-contrast paint pass).

## `AccessibilityToolkit.speak(text, utteranceId)`
Speaks the provided text using Android `TextToSpeech`, selecting the locale from persisted settings.

