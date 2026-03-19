# Decisions & tradeoffs (Engineering)

## Views vs Compose
The demo uses XML + Views (AppCompat) to keep the project approachable and aligned with the “toolkit across apps” idea without requiring Compose-specific infrastructure.

## `recreate()` for theme / locale changes
- High-contrast theme selection is applied before content inflation via `AccessibilityToolkit.applyOnActivity()`.
- Locale selection is applied even earlier via `attachBaseContext()` using `AccessibilityToolkit.wrapBaseContext()` to avoid Android’s `applyOverrideConfiguration` timing restrictions.

When the user toggles either feature at runtime, the demo uses `Activity.recreate()` so styles and translated strings re-resolve from resources cleanly.

## Text scaling strategy
`TextSizeController` traverses a view tree and scales each `TextView`.

To avoid compounding scaling changes over multiple slider updates, it stores each `TextView`'s original text size (px) in a view tag and re-computes the scaled value from that baseline every time.

## Font changes
The demo switches between `Typeface.SANS_SERIF` and `Typeface.MONOSPACE` across all `TextView` instances in the view tree.

## Minimalism
The toolkit facade (`AccessibilityToolkit`) keeps the public API intentionally small:
- initialize
- wrapBaseContext
- applyOnActivity
- applyToViewTree
- speak

