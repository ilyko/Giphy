package com.ilyko.giphy.core.base.presentation.view_model

import androidx.annotation.StringRes
import com.ilyko.giphy.domain.entity.error.ErrorEntity

interface Localizer {
    @StringRes
    fun localize(error: ErrorEntity): Int
}