package com.ilyko.giphy.di

import com.ilyko.giphy.features.main.search.domain.SearchInteractor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

object DomainModule {
    fun get() = Kodein.Module("Domain") {

        bind() from provider {
            SearchInteractor(
                repository = instance()
            )
        }
    }
}
