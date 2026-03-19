package com.example.accessibilitytoolkit

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes

/**
 * Applies the selected font style to all TextViews in a view tree.
 */
class FontController(
    private val fontStyle: FontStyle
) {
    fun apply(root: View, @IdRes appliedFontTagKey: Int) {
        walk(root, appliedFontTagKey)
    }

    private fun walk(view: View, @IdRes appliedFontTagKey: Int) {
        if (view is TextView) {
            val currentOrdinal = view.getTag(appliedFontTagKey) as? Int
            if (currentOrdinal != fontStyle.ordinal) {
                view.typeface = when (fontStyle) {
                    FontStyle.Sans -> Typeface.SANS_SERIF
                    FontStyle.Mono -> Typeface.MONOSPACE
                }
                view.setTag(appliedFontTagKey, fontStyle.ordinal)
            }
        }

        if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                walk(view.getChildAt(i), appliedFontTagKey)
            }
        }
    }
}

