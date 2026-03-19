package com.example.accessibilitytoolkit

import android.app.Activity

/**
 * Chooses a high-contrast theme for the Activity.
 *
 * Call this before the Activity inflates its layout (i.e., before `setContentView()`).
 */
class ThemeController {
    fun applyTheme(activity: Activity, highContrastEnabled: Boolean) {
        val resId = if (highContrastEnabled) {
            com.example.accessibilitytoolkit.R.style.Theme_AccessibilityToolkit_HighContrast
        } else {
            com.example.accessibilitytoolkit.R.style.Theme_AccessibilityToolkit_Default
        }
        activity.setTheme(resId)
    }
}

