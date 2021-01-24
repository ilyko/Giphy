package com.ilyko.giphy.features.main

import androidx.fragment.app.commit
import com.ilyko.giphy.R
import com.ilyko.giphy.core.base.presentation.navigator.BaseNavigator
import com.ilyko.giphy.features.main.search.presentation.SearchFragment


class MainNavigator : BaseNavigator() {
    fun navigateToSearch() {
        fManager?.commit {
            replace(R.id.activity_container, SearchFragment.newInstance())
        }
    }
}
