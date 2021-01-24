package com.ilyko.giphy

import android.app.Application
import com.ilyko.giphy.di.AppModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger

class GiphyApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(AppModule.get(this@GiphyApp))
    }

    override val kodeinTrigger = KodeinTrigger()
    override fun onCreate() {
        super.onCreate()

        kodeinTrigger.trigger()
    }
}
