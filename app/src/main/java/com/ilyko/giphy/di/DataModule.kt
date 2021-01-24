package com.ilyko.giphy.di

import com.ilyko.giphy.core.base.data.network.AppErrorHandler
import com.ilyko.giphy.features.main.search.data.repositories.SearchRepositoryImpl
import com.ilyko.giphy.features.main.search.domain.SearchRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object DataModule {
    fun get() = Kodein.Module("Data") {

        bind() from singleton { AppErrorHandler() }
        bind<SearchRepository>() with singleton {
            SearchRepositoryImpl(
                api = instance(),
                errorHandler = instance<AppErrorHandler>(),
                dispatcherProvider = instance()
            )
        }
    }
}
