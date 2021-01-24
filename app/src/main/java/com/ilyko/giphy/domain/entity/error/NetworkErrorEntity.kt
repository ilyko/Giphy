package com.ilyko.giphy.domain.entity.error

sealed class NetworkErrorEntity : ErrorEntity {
    object ClientTimeoutError : NetworkErrorEntity()

    object InternalServerError : NetworkErrorEntity()
    object ServiceUnavailable : NetworkErrorEntity()
    object NotFoundError : NetworkErrorEntity()
    object UnauthorizedError : NetworkErrorEntity()
    object EmptyResponseError : NetworkErrorEntity()
    object WrongResponseError : NetworkErrorEntity()

    object NoConnectionError : NetworkErrorEntity()

    class NotImplementedError(val throwable: Throwable? = null) : NetworkErrorEntity()
    object UnknownError : NetworkErrorEntity()
}
