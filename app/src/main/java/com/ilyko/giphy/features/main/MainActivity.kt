package com.ilyko.giphy.features.main

import android.os.Bundle
import com.ilyko.giphy.R
import com.ilyko.giphy.core.base.presentation.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun diModule() = Kodein.Module(this::class.java.simpleName) {
        bind<MainNavigator>() with singleton { MainNavigator() }
    }

    private val navigator: MainNavigator by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.attachActivity(this)
        if (navigator.isContainerEmpty(R.id.activity_container)) {
            navigator.navigateToSearch()
        }
    }
}
