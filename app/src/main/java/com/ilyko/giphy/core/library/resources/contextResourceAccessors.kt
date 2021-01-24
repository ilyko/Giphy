package com.ilyko.giphy.core.library.resources

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes

fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    @ColorRes defaultColor: Int = android.R.color.black
): Int = resolveAttribute(attrColor)?.resourceId ?: defaultColor

fun Context.resolveAttribute(@AttrRes attributeResId: Int): TypedValue? {
    val typedValue = TypedValue()
    return if (theme.resolveAttribute(attributeResId, typedValue, true)) typedValue else null
}
