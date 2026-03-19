# Accessibility features (Engineering)

## 1) Text size adjustment
- **Toolkit**: `TextSizeController`
- **Demo UI**: `MainActivity` `SeekBar` (`R.id.textSizeSeekBar`)
- **Behavior**: scales all `TextView` widgets in the screen’s view tree.

## 2) Colour / theme toggle (high contrast)
- **Toolkit**: `ThemeController` (theme selection) + `HighContrastController` (view-tree color pass)
- **Demo UI**: `MainActivity` switch (`R.id.highContrastSwitch`)
- **Behavior**:
  - on change, the Activity is recreated so theme + resources refresh cleanly
  - text is forced to high-contrast colors when enabled

## 3) Text-to-speech (TTS)
- **Toolkit**: `TtsController` exposed through `AccessibilityToolkit.speak(...)`
- **Demo UI**:
  - `MainActivity` “Read aloud”
  - `SecondActivity` “Read aloud”
- **Behavior**: speaks visible body text using the selected locale.

## Bonus: persistence
- **Toolkit**: `SharedPreferencesSettingsStore` persists:
  - `textScale`
  - `highContrastEnabled`
  - `fontStyle`
  - `languageTag`

## Bonus: second screen reuse
- `SecondActivity` uses the exact same toolkit facade API and applies it to its own view tree.

## Bonus: mock translation (resources)
- Language selection flips between `res/values/strings.xml` and `res/values-es/strings.xml`.
- Locale is applied via `attachBaseContext()` using `AccessibilityToolkit.wrapBaseContext(...)`.

## Bonus: font family changes
- Radio buttons toggle between system sans-serif and monospace.
- Applied across the view tree to demonstrate cross-screen reach without editing each layout.

