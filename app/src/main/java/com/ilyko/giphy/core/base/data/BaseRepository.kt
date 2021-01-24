package com.ilyko.giphy.core.base.data

import com.ilyko.giphy.core.base.data.network.ErrorHandler
import com.ilyko.giphy.core.base.data.network.ResponseResult
import com.ilyko.giphy.core.library.dispatcher.DispatcherProvider
import com.ilyko.giphy.domain.entity.error.NetworkErrorEntity
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

open class BaseRepository(
    private val errorHandler: ErrorHandler,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun <Input : Any> execute(
        block: suspend () -> Response<Input>
    ): ResponseResult<Input> {
        return withContext(dispatcherProvider.network) {
            try {
                val x = block()
                calculateResult(x)
            } catch (e: Exception) {
                ResponseResult.Error(errorHandler.toErrorEntity(e))
            }
        }
    }

    private fun <Input : Any> calculateResult(
        data: Response<Input>
    ): ResponseResult<Input> {
        return data.code().takeIf {
            it !in CODE_SUCCESS_START..CODE_SUCCESS_END
        }?.let {
            ResponseResult.Error(errorHandler.toErrorEntity(HttpException(data)))
        } ?: run {
            data.body()?.let {
                ResponseResult.Success(it)
            } ?: run {
                ResponseResult.Error(NetworkErrorEntity.EmptyResponseError)
            }
        }
    }

    companion object {
        private const val CODE_SUCCESS_START = 200
        private const val CODE_SUCCESS_END = 299
    }
}
