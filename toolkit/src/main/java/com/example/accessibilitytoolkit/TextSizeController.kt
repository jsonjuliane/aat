package com.example.accessibilitytoolkit

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes

/**
 * Scales text sizes in a view tree.
 *
 * Implementation stores each TextView's original text size (in px) in a tag, so repeated calls
 * don't compound scaling errors.
 */
class TextSizeController(
    private val textScale: Float
) {
    fun apply(root: View, @IdRes originalSizeTagKey: Int) {
        walk(root, originalSizeTagKey)
    }

    private fun walk(view: View, @IdRes originalSizeTagKey: Int) {
        if (view is TextView) {
            val originalPx = (view.getTag(originalSizeTagKey) as? Float) ?: view.textSize
            if (view.getTag(originalSizeTagKey) == null) {
                view.setTag(originalSizeTagKey, originalPx)
            }
            val scaledPx = originalPx * textScale
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledPx)
        }

        if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                walk(view.getChildAt(i), originalSizeTagKey)
            }
        }
    }
}

