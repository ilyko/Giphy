package com.ilyko.giphy.core.base.presentation

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar

interface BaseView {

    fun showMessage(
        @StringRes messageTextId: Int,
        @StringRes actionTitleId: Int? = null,
        action: ((View) -> Unit)? = null,
        containerResId: Int = android.R.id.content,
        duration: Int = Snackbar.LENGTH_LONG,
        isCentered: Boolean
    )

    fun showMessage(
        messageText: String,
        @StringRes actionTitleId: Int? = null,
        action: ((View) -> Unit)? = null,
        containerResId: Int = android.R.id.content,
        duration: Int = Snackbar.LENGTH_LONG,
        isCentered: Boolean
    )

    fun showError(
        @StringRes messageTextId: Int,
        @StringRes actionTitleId: Int? = null,
        action: ((View) -> Unit)? = null,
        containerResId: Int = android.R.id.content,
        duration: Int = Snackbar.LENGTH_LONG,
        onDismiss: (() -> Unit)? = null
    )

    fun showError(
        messageText: String,
        @StringRes actionTitleId: Int? = null,
        action: ((View) -> Unit)? = null,
        containerResId: Int = android.R.id.content,
        duration: Int = Snackbar.LENGTH_LONG,
        onDismiss: (() -> Unit)? = null
    )

    fun DialogFragment.show(tag: String) {}
}
