package com.ilyko.giphy.core.base.presentation.extensions

import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.google.android.material.snackbar.Snackbar
import com.ilyko.giphy.R
import com.ilyko.giphy.core.library.resources.getColor

fun Snackbar.decorate(
    backgroundColor: Int,
    @ColorRes textColorId: Int,
    isCentered: Boolean
): Snackbar {
    val layout = view as Snackbar.SnackbarLayout

    val textView = with(layout) {
        setBackgroundColor(backgroundColor)
        findViewById<TextView>(R.id.snackbar_text)
    }

    with(textView) {
        setTextColor(getColor(textColorId))
        maxLines = Int.MAX_VALUE
        ellipsize = null
        if (isCentered) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            } else {
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }
    }

    with(layout.findViewById(R.id.snackbar_action) as TextView) {
        setTextColor(getColor(textColorId))
    }

    return this
}
