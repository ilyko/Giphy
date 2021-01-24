package com.ilyko.giphy.di

import com.ilyko.giphy.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ilyko.giphy.features.main.search.data.api.GiphyApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*

object NetworkModule {
    fun get() = Kodein.Module("Network") {
        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(instance())
                .build()
        }

        bind<OkHttpClient>() with singleton {
            val builder = OkHttpClient.Builder()

            builder.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            builder.retryOnConnectionFailure(true)

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }

            builder.build()
        }

        bind<GiphyApi>() with singleton {
            instance<Retrofit>().create(GiphyApi::class.java)
        }

    }

    private const val TIMEOUT_SEC: Long = 10
}
