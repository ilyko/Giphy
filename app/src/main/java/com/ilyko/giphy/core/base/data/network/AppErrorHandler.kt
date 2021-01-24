package com.ilyko.giphy.core.base.data.network

import com.ilyko.giphy.domain.entity.error.ErrorEntity
import com.ilyko.giphy.domain.entity.error.NetworkErrorEntity
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

private const val HTTP_UNPROCESSABLE_ENTITY = 422

open class AppErrorHandler : ErrorHandler {
    override fun toErrorEntity(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is HttpException -> fromHttpExceptionCode(throwable)
            is UnknownHostException -> NetworkErrorEntity.NoConnectionError
            else -> NetworkErrorEntity.NotImplementedError(throwable)
        }
    }

    private fun fromHttpExceptionCode(httpException: HttpException): ErrorEntity {
        return when (httpException.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> NetworkErrorEntity.UnauthorizedError
            HttpURLConnection.HTTP_UNAVAILABLE -> NetworkErrorEntity.ServiceUnavailable
            HttpURLConnection.HTTP_INTERNAL_ERROR -> NetworkErrorEntity.InternalServerError
            HttpURLConnection.HTTP_NOT_FOUND -> NetworkErrorEntity.NotFoundError
            HttpURLConnection.HTTP_NOT_IMPLEMENTED -> NetworkErrorEntity.NotImplementedError()
            HttpURLConnection.HTTP_CLIENT_TIMEOUT -> NetworkErrorEntity.ClientTimeoutError
            HTTP_UNPROCESSABLE_ENTITY -> handleUnprocessableEntity(httpException)
            else -> NetworkErrorEntity.UnknownError
        }
    }

    protected open fun handleUnprocessableEntity(httpException: HttpException): ErrorEntity {
        return NetworkErrorEntity.UnknownError
    }
}