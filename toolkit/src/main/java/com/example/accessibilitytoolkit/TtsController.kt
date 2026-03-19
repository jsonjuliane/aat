package com.example.accessibilitytoolkit

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * Small wrapper around Android's TextToSpeech.
 *
 * The wrapper is intentionally minimal: it lazily initializes and queues one pending utterance
 * if `speak()` is called before TTS finishes initializing.
 */
class TtsController(
    private val appContext: Context
) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var ready: Boolean = false

    private data class Pending(val text: String, val utteranceId: String, val locale: Locale)
    private var pending: Pending? = null

    fun speak(text: String, utteranceId: String, locale: Locale) {
        ensureInit()
        val instance = tts ?: return
        if (!ready) {
            pending = Pending(text, utteranceId, locale)
            return
        }

        instance.language = locale
        instance.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        ready = false
        pending = null
    }

    override fun onInit(status: Int) {
        ready = status == TextToSpeech.SUCCESS
        if (ready) {
            val p = pending
            pending = null
            if (p != null) {
                speak(p.text, p.utteranceId, p.locale)
            }
        }
    }

    private fun ensureInit() {
        if (tts != null) return
        tts = TextToSpeech(appContext, this)
    }
}

