package com.ilyko.giphy.core.base.presentation.navigator

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.ilyko.giphy.core.base.presentation.BaseActivity
import java.lang.ref.WeakReference

abstract class BaseNavigator {

    private var activityWr = WeakReference<BaseActivity>(null)

    protected val activity: BaseActivity?
        get() = activityWr.get()
    protected val fManager: FragmentManager?
        get() = activity?.supportFragmentManager
    val backStackCount: Int?
        get() = fManager?.backStackEntryCount

    fun attachActivity(activity: BaseActivity) {
        activityWr = WeakReference(activity)
    }

    fun activityDetached() {
        activityWr = WeakReference<BaseActivity>(null)
    }

    fun isContainerEmpty(@IdRes containerId: Int): Boolean {
        return fManager?.findFragmentById(containerId) == null
    }
}
