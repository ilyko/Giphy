package com.ilyko.giphy.core.base.presentation.view_model

import androidx.annotation.StringRes
import com.ilyko.giphy.R
import com.ilyko.giphy.domain.entity.error.ErrorEntity
import com.ilyko.giphy.domain.entity.error.NetworkErrorEntity

object BaseErrorLocalizer : Localizer {

    @StringRes
    override fun localize(error: ErrorEntity): Int {
        return when (error) {
            is NetworkErrorEntity.NoConnectionError -> R.string.error_server_no_internet_connection
            else -> R.string.error_server_internal
        }
    }
}