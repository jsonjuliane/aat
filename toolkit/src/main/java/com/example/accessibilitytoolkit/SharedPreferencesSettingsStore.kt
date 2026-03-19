package com.example.accessibilitytoolkit

import android.content.Context

/**
 * Simple SharedPreferences-backed persistence for the toolkit settings.
 */
class SharedPreferencesSettingsStore(
    private val context: Context
) : SettingsStore {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun load(): AccessibilitySettings {
        val defaults = AccessibilitySettings()
        val textScale = prefs.getFloat(KEY_TEXT_SCALE, defaults.textScale)
        val highContrastEnabled = prefs.getBoolean(KEY_HIGH_CONTRAST, defaults.highContrastEnabled)

        val fontOrdinal = prefs.getInt(KEY_FONT_STYLE, defaults.fontStyle.ordinal)
        val fontStyle = FontStyle.values().getOrNull(fontOrdinal) ?: defaults.fontStyle

        val languageTag = prefs.getString(KEY_LANGUAGE_TAG, defaults.languageTag) ?: defaults.languageTag

        return AccessibilitySettings(
            textScale = textScale,
            highContrastEnabled = highContrastEnabled,
            fontStyle = fontStyle,
            languageTag = languageTag
        )
    }

    override fun save(settings: AccessibilitySettings) {
        prefs.edit()
            .putFloat(KEY_TEXT_SCALE, settings.textScale)
            .putBoolean(KEY_HIGH_CONTRAST, settings.highContrastEnabled)
            .putInt(KEY_FONT_STYLE, settings.fontStyle.ordinal)
            .putString(KEY_LANGUAGE_TAG, settings.languageTag)
            .apply()
    }

    private companion object {
        const val PREFS_NAME = "accessibility_toolkit_prefs"
        const val KEY_TEXT_SCALE = "text_scale"
        const val KEY_HIGH_CONTRAST = "high_contrast_enabled"
        const val KEY_FONT_STYLE = "font_style"
        const val KEY_LANGUAGE_TAG = "language_tag"
    }
}

