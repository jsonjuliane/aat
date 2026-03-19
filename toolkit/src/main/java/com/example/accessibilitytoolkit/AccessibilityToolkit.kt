package com.example.accessibilitytoolkit

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

/**
 * Single entry point for the reusable accessibility toolkit.
 *
 * This facade is intentionally small so app code stays clean and the toolkit can evolve toward
 * an SDK-like structure.
 */
object AccessibilityToolkit {
    private var app: Application? = null
    private var settingsStore: SettingsStore? = null
    private var currentSettings: AccessibilitySettings = AccessibilitySettings()
    private var ttsController: TtsController? = null

    fun initialize(application: Application, store: SettingsStore) {
        this.app = application
        this.settingsStore = store
        this.currentSettings = store.load()

        if (ttsController == null) {
            ttsController = TtsController(application.applicationContext)
        }
    }

    /**
     * Applies settings that must happen before view inflation:
     * - Theme choice (high contrast)
     */
    fun applyOnActivity(activity: Activity) {
        refreshSettings()

        ThemeController().applyTheme(activity, currentSettings.highContrastEnabled)
    }

    /**
     * Wrap a base context for locale-aware resources.
     *
     * Use from Activity.attachBaseContext(base): super.attachBaseContext(AccessibilityToolkit.wrapBaseContext(base))
     */
    fun wrapBaseContext(base: Context): Context {
        refreshSettings()
        return LocaleController(currentSettings.languageTag).wrap(base)
    }

    /**
     * Applies settings to an already-inflated view tree (text size, font, colors).
     */
    fun applyToViewTree(root: View) {
        refreshSettings()

        val textScale = clamp(currentSettings.textScale, 0.8f, 1.8f)
        TextSizeController(textScale).apply(
            root = root,
            originalSizeTagKey = R.id.accessibility_originalTextSizePx
        )
        FontController(currentSettings.fontStyle).apply(
            root = root,
            appliedFontTagKey = R.id.accessibility_appliedFontStyle
        )
        HighContrastController(currentSettings.highContrastEnabled).apply(
            root = root,
            appliedHighContrastTagKey = R.id.accessibility_appliedHighContrast
        )
    }

    /**
     * Speaks the provided text using the app's selected locale.
     */
    fun speak(text: String, utteranceId: String) {
        refreshSettings()
        if (text.isBlank()) return

        val locale: Locale = LocaleController(currentSettings.languageTag).locale()
        val controller = ttsController ?: return
        controller.speak(text, utteranceId, locale)
    }

    private fun refreshSettings() {
        val store = settingsStore ?: return
        currentSettings = store.load()
    }

    private fun clamp(value: Float, minValue: Float, maxValue: Float): Float {
        return max(minValue, min(value, maxValue))
    }
}

