package com.ilyko.giphy.core.base.presentation.view_model

import androidx.lifecycle.ViewModel
import com.ilyko.giphy.domain.entity.error.ErrorEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    private val job = Job()
    protected val scope = CoroutineScope(Dispatchers.IO + job)
    val eventsQueue = EventsQueue()

    protected fun offerBaseError(errorEntity: ErrorEntity) {
        eventsQueue.offer(
            MessageEvent(
                BaseErrorLocalizer.localize(errorEntity),
                isTextInCenter = true
            )
        )
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
