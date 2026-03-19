package com.example.accessibilitydemo

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilitytoolkit.AccessibilityToolkit
import com.example.accessibilitytoolkit.SettingsStoreFactory

class SecondActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val store = SettingsStoreFactory.create(newBase)
        AccessibilityToolkit.initialize(application = (newBase.applicationContext as Application), store = store)
        super.attachBaseContext(AccessibilityToolkit.wrapBaseContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsStore = SettingsStoreFactory.create(this)
        AccessibilityToolkit.initialize(application, settingsStore)
        AccessibilityToolkit.applyOnActivity(this)

        setContentView(R.layout.activity_second)

        val bodyTextView = findViewById<TextView>(R.id.secondBody)
        AccessibilityToolkit.applyToViewTree(findViewById(R.id.secondRoot))

        val readButton: Button = findViewById(R.id.readAloudButtonSecond)
        readButton.setOnClickListener {
            AccessibilityToolkit.speak(bodyTextView.text.toString(), utteranceId = "second_body")
        }
    }
}

