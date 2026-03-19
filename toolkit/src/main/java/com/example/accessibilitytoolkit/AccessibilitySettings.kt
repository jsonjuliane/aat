package com.example.accessibilitytoolkit

/**
 * Toolkit settings that can be persisted and re-applied across Activities.
 */
data class AccessibilitySettings(
    /**
     * Scale factor applied to text sizes (1.0 = default).
     */
    val textScale: Float = 1.0f,

    /**
     * If true, the toolkit applies a high-contrast theme and adjusts text colors.
     */
    val highContrastEnabled: Boolean = false,

    /**
     * If true, the toolkit swaps the font family for text widgets.
     */
    val fontStyle: FontStyle = FontStyle.Sans,

    /**
     * BCP-47 language tag (e.g. "en", "es") used for mock translation.
     */
    val languageTag: String = "en"
)

enum class FontStyle {
    Sans,
    Mono
}

