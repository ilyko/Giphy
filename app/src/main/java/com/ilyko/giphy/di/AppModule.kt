package com.ilyko.giphy.di

import android.app.Application
import com.ilyko.giphy.core.library.dispatcher.DispatcherProvider
import com.ilyko.giphy.core.library.dispatcher.DispatcherProviderImpl
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

object AppModule {

    fun get(application: Application) = Kodein.Module("App") {
        import(androidModule(application))
        import(NetworkModule.get())
        import(DomainModule.get())
        import(DataModule.get())

        bind<DispatcherProvider>() with singleton { DispatcherProviderImpl() }
    }
}