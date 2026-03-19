package com.example.accessibilitytoolkit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesSettingsStoreTest {
    @Test
    fun save_then_load_returnsSameSettings() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val prefs = context.getSharedPreferences("accessibility_toolkit_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().commit()

        val store = SharedPreferencesSettingsStore(context)

        val settings = AccessibilitySettings(
            textScale = 1.23f,
            highContrastEnabled = true,
            fontStyle = FontStyle.Mono,
            languageTag = "es"
        )

        store.save(settings)
        val loaded = store.load()

        assertEquals(settings.textScale, loaded.textScale, 0.0001f)
        assertEquals(settings.highContrastEnabled, loaded.highContrastEnabled)
        assertEquals(settings.fontStyle, loaded.fontStyle)
        assertEquals(settings.languageTag, loaded.languageTag)
    }
}

