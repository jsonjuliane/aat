package com.example.accessibilitytoolkit

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

/**
 * Applies a selected locale to an Activity before views are inflated.
 */
class LocaleController(
    private val languageTag: String
) {
    /**
     * Wrap a base context so resource lookups use the selected locale.
     *
     * Call from Activity.attachBaseContext().
     */
    fun wrap(base: Context): Context {
        val locale = localeFromTag(languageTag)
        Locale.setDefault(locale)

        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)

        return base.createConfigurationContext(config)
    }

    fun locale(): Locale = localeFromTag(languageTag)

    private fun localeFromTag(tag: String): Locale {
        return try {
            if (tag.isBlank()) {
                Locale.ENGLISH
            } else {
                Locale.forLanguageTag(tag)
            }
        } catch (_: Exception) {
            Locale.ENGLISH
        }
    }
}

