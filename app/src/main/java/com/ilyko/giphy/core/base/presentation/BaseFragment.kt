package com.ilyko.giphy.core.base.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ilyko.giphy.core.base.presentation.view_model.ErrorMessageEvent
import com.ilyko.giphy.core.base.presentation.view_model.Event
import com.ilyko.giphy.core.base.presentation.view_model.EventsQueue
import com.ilyko.giphy.core.base.presentation.view_model.MessageEvent
import com.ilyko.giphy.core.library.dialog.ProgressDialog
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import com.ilyko.giphy.core.library.resources.getString
import java.util.*

abstract class BaseFragment(
    @LayoutRes
    protected val layoutId: Int
) : Fragment(layoutId), BaseView, KodeinAware {

    private val baseActivity by lazy { activity as? BaseView }
    protected var currentAlert: AlertDialog? = null

    @IdRes
    private val messagesContainer: Int = android.R.id.content
    private val _parentKodein: Kodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein, true)
        import(kodeinModule, true)
    }

    protected val appCompatActivity: AppCompatActivity? get() = (activity as? AppCompatActivity)
    open val kodeinModule: Kodein.Module = Kodein.Module("default") {}

    override val kodeinTrigger = KodeinTrigger()
    private val doOnNextResumeList = mutableListOf<Runnable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        kodeinTrigger.trigger()
        super.onAttach(context)
    }

    protected var showModalProgress: Boolean = false
        set(value) {
            if (value == field) return else field = value
            if (value) {
                showModalProgress()
            } else {
                hideModalProgress()
            }
        }

    protected open fun onEvent(event: Event) {
        when (event) {
            is MessageEvent -> showMessage(
                messageText = getString(event.messageResource),
                containerResId = messagesContainer,
                isCentered = event.isTextInCenter
            )
            is ErrorMessageEvent -> showError(
                messageText = getString(event.errorMessageResource),
                containerResId = messagesContainer
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    override fun onResume() {
        super.onResume()
        doOnNextResumeList.forEach {
            it.run()
        }
        doOnNextResumeList.clear()
    }

    protected open fun initToolbar() {}

    override fun showMessage(
        messageTextId: Int,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int,
        isCentered: Boolean
    ) {
        val messageText = getString(messageTextId)
        showMessage(messageText, actionTitleId, action, containerResId, duration, isCentered)
    }

    override fun showMessage(
        messageText: String,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int,
        isCentered: Boolean
    ) {
        baseActivity?.showMessage(
            messageText,
            actionTitleId,
            action,
            containerResId,
            duration,
            isCentered
        )
    }

    override fun showError(
        messageTextId: Int,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int,
        onDismiss: (() -> Unit)?
    ) {
        val messageText = getString(messageTextId)
        showError(messageText, actionTitleId, action, containerResId, duration)
    }

    override fun showError(
        messageText: String,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int,
        onDismiss: (() -> Unit)?
    ) {
        baseActivity?.showError(messageText, actionTitleId, action, containerResId, duration)
    }

    private fun showModalProgress() {
        hideModalProgress()
        ProgressDialog.newInstance()
            .showNow(childFragmentManager, ProgressDialog.PROGRESS_DIALOG_TAG)
    }

    private fun hideModalProgress() {
        dismissDialogByTag(ProgressDialog.PROGRESS_DIALOG_TAG)
    }

    private fun Fragment.dismissDialogByTag(tag: String) {
        (childFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismissAllowingStateLoss()
    }

    protected fun Fragment.observeEvents(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
        eventsQueue.observe(viewLifecycleOwner, { queue: Queue<Event>? ->
            while (queue != null && queue.isNotEmpty()) {
                eventHandler(queue.remove())
            }
        })
    }

}
