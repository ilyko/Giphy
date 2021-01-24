package com.ilyko.giphy.core.library.dispatcher

import com.ilyko.giphy.core.library.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl : DispatcherProvider {
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
    override val network: CoroutineDispatcher get() = io
    override val database: CoroutineDispatcher get() = io
    override val computation: CoroutineDispatcher get() = io
}