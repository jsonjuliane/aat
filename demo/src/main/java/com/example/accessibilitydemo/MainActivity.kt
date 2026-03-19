package com.example.accessibilitydemo

import android.content.Context
import android.app.Application
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitytoolkit.AccessibilitySettings
import com.example.accessibilitytoolkit.AccessibilityToolkit
import com.example.accessibilitytoolkit.FontStyle
import com.example.accessibilitytoolkit.SettingsStore
import com.example.accessibilitytoolkit.SettingsStoreFactory
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var settingsStore: SettingsStore

    override fun attachBaseContext(newBase: Context) {
        // Apply locale before any resource access (prevents applyOverrideConfiguration crash).
        val store = SettingsStoreFactory.create(newBase)
        AccessibilityToolkit.initialize(application = (newBase.applicationContext as Application), store = store)
        super.attachBaseContext(AccessibilityToolkit.wrapBaseContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingsStore = SettingsStoreFactory.create(this)
        AccessibilityToolkit.initialize(application, settingsStore)

        AccessibilityToolkit.applyOnActivity(this)
        setContentView(R.layout.activity_main)

        val rootView: View = findViewById(R.id.root)
        var currentSettings: AccessibilitySettings = settingsStore.load()

        // Apply current settings to the inflated view tree.
        AccessibilityToolkit.applyToViewTree(rootView)

        val textSizeSeekBar: SeekBar = findViewById(R.id.textSizeSeekBar)
        textSizeSeekBar.max = 100

        val textSizeValue: TextView = findViewById(R.id.textSizeValue)
        val highContrastSwitch: Switch = findViewById(R.id.highContrastSwitch)
        val fontStyleGroup: RadioGroup = findViewById(R.id.fontStyleGroup)
        val languageGroup: RadioGroup = findViewById(R.id.languageGroup)
        val readButton: Button = findViewById(R.id.readAloudButton)
        val openSecondButton: Button = findViewById(R.id.openSecondButton)

        fun scaleToProgress(scale: Float, minScale: Float, maxScale: Float): Int {
            val ratio = (scale - minScale) / (maxScale - minScale)
            return (ratio * textSizeSeekBar.max).roundToInt().coerceIn(0, textSizeSeekBar.max)
        }

        fun progressToScale(progress: Int, minScale: Float, maxScale: Float): Float {
            val ratio = progress.toFloat() / textSizeSeekBar.max.toFloat()
            return minScale + ratio * (maxScale - minScale)
        }

        val minScale = 0.8f
        val maxScale = 1.8f
        val initialProgress = scaleToProgress(currentSettings.textScale, minScale, maxScale)

        textSizeValue.text = "${(currentSettings.textScale * 100).roundToInt()}%"
        textSizeSeekBar.progress = initialProgress

        highContrastSwitch.isChecked = currentSettings.highContrastEnabled

        fontStyleGroup.check(
            if (currentSettings.fontStyle == FontStyle.Sans) R.id.fontSansRadio else R.id.fontMonoRadio
        )

        languageGroup.check(
            if (currentSettings.languageTag == "es") R.id.languageEsRadio else R.id.languageEnRadio
        )

        // SeekBar: text size changes do not require recreating the Activity.
        textSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!fromUser) return
                val newScale = progressToScale(progress, minScale, maxScale)
                currentSettings = currentSettings.copy(textScale = newScale)
                settingsStore.save(currentSettings)
                textSizeValue.text = "${(newScale * 100).roundToInt()}%"
                AccessibilityToolkit.applyToViewTree(rootView)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        highContrastSwitch.setOnCheckedChangeListener { _, isChecked ->
            currentSettings = currentSettings.copy(highContrastEnabled = isChecked)
            settingsStore.save(currentSettings)
            // Theme requires re-inflation.
            recreate()
        }

        fontStyleGroup.setOnCheckedChangeListener { _, checkedId ->
            val newFontStyle = when (checkedId) {
                R.id.fontMonoRadio -> FontStyle.Mono
                else -> FontStyle.Sans
            }
            currentSettings = currentSettings.copy(fontStyle = newFontStyle)
            settingsStore.save(currentSettings)
            AccessibilityToolkit.applyToViewTree(rootView)
        }

        languageGroup.setOnCheckedChangeListener { _, checkedId ->
            val languageTag = when (checkedId) {
                R.id.languageEsRadio -> "es"
                else -> "en"
            }
            currentSettings = currentSettings.copy(languageTag = languageTag)
            settingsStore.save(currentSettings)
            // Locale requires re-inflation to update translated strings.
            recreate()
        }

        readButton.setOnClickListener {
            val body = findViewById<TextView>(R.id.body).text.toString()
            AccessibilityToolkit.speak(body, utteranceId = "main_body")
        }

        openSecondButton.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}

