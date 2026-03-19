package com.example.accessibilitytoolkit

/**
 * Abstraction to load/save toolkit settings.
 *
 * Kept intentionally small so the toolkit can be reused across apps with different persistence choices.
 */
interface SettingsStore {
    fun load(): AccessibilitySettings
    fun save(settings: AccessibilitySettings)
}

