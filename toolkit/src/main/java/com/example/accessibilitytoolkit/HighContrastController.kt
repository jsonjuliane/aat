package com.example.accessibilitytoolkit

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes

/**
 * Applies a minimal high-contrast presentation to a view tree.
 *
 * Note: for a production toolkit you'd likely integrate with theming + styles more deeply.
 * For this kata we keep it simple and deterministic.
 */
class HighContrastController(
    private val enabled: Boolean
) {
    fun apply(root: View, @IdRes appliedHighContrastTagKey: Int) {
        if (!enabled) return
        walk(root, appliedHighContrastTagKey)
    }

    private fun walk(view: View, @IdRes appliedHighContrastTagKey: Int) {
        // Avoid re-applying repeatedly (especially during slider drags).
        val already = view.getTag(appliedHighContrastTagKey) as? Boolean ?: false
        if (already) return
        view.setTag(appliedHighContrastTagKey, true)

        if (view === view.rootView) {
            view.setBackgroundColor(Color.BLACK)
        }

        if (view is TextView) {
            view.setTextColor(Color.WHITE)
        }

        if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                walk(view.getChildAt(i), appliedHighContrastTagKey)
            }
        }
    }
}

