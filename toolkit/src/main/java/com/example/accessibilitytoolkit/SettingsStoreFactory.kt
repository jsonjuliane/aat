package com.example.accessibilitytoolkit

import android.content.Context

/**
 * Tiny factory used by the demo app to create the default SharedPreferences implementation.
 *
 * In a real SDK, apps would provide their own SettingsStore (e.g. account-backed, cloud sync, etc).
 */
object SettingsStoreFactory {
    fun create(context: Context): SettingsStore = SharedPreferencesSettingsStore(context)
}

