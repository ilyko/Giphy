package com.ilyko.giphy.core.base.data.network

import com.ilyko.giphy.domain.entity.error.ErrorEntity


interface ErrorHandler {
    fun toErrorEntity(throwable: Throwable): ErrorEntity
}