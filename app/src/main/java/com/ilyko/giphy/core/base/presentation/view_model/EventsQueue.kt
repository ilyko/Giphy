package com.ilyko.giphy.core.base.presentation.view_model

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import java.util.*

class EventsQueue : MutableLiveData<Queue<Event>>() {

    @MainThread
    fun offer(event: Event) {
        val queue = (value ?: LinkedList()).apply {
            add(event)
        }

        value = queue
    }
}

fun EventsQueue.merge(eventsQueue: EventsQueue) {
    observeForever {
        if (it.isNotEmpty()) {
            eventsQueue.offer(it.remove())
        }
    }
}
