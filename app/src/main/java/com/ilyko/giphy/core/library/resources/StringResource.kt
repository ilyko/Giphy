package com.ilyko.giphy.core.library.resources

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

@Suppress("DataClassPrivateConstructor")
data class StringResource private constructor(
    private val string: String?,
    @StringRes
    private val stringRes: Int
) {
    constructor(string: String) : this(string, stringRes = 0)
    constructor(@StringRes stringRes: Int) : this(string = null, stringRes = stringRes)

    fun toString(context: Context): String = string ?: context.getString(stringRes)
}

fun Fragment.getString(stringResource: StringResource): String =
    stringResource.toString(requireContext())

fun Fragment.getStringOrNull(stringResource: StringResource?): String? =
    context?.let { stringResource?.toString(it) }
