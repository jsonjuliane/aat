package com.example.accessibilitytoolkit

import android.content.Context
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TextSizeControllerTest {
    @Test
    fun apply_scalesTextViewsWithoutCompounding() {
        val context: Context = ApplicationProvider.getApplicationContext()

        val root = LinearLayout(context)
        val tv = TextView(context)
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20f)
        root.addView(tv)

        val controller = TextSizeController(textScale = 1.5f)

        controller.apply(root, R.id.accessibility_originalTextSizePx)
        assertEquals(30f, tv.textSize, 0.01f)

        // Re-apply should use the stored baseline (20px), not the already-scaled size.
        controller.apply(root, R.id.accessibility_originalTextSizePx)
        assertEquals(30f, tv.textSize, 0.01f)
    }
}

