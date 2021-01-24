package com.ilyko.giphy.core.base.presentation.view_model

import androidx.annotation.StringRes
import com.ilyko.giphy.core.library.resources.StringResource

interface Event

open class ErrorMessageEvent private constructor(val errorMessageResource: StringResource) : Event {
    constructor(errorMessage: String) : this(StringResource(errorMessage))
    constructor(@StringRes errorMessage: Int) : this(StringResource(errorMessage))
}

open class MessageEvent private constructor(
    val messageResource: StringResource,
    val isTextInCenter: Boolean = false
) : Event {
    constructor(message: String, isTextInCenter: Boolean = false) : this(
        StringResource(message),
        isTextInCenter
    )

    constructor(@StringRes message: Int, isTextInCenter: Boolean = false) : this(
        StringResource(
            message
        ), isTextInCenter
    )
}

class AlertMessageEventWithActionButton : MessageEvent {
    var title: StringResource
        private set
    var onDismissListener: () -> Unit = {}
        private set
    var actionListener: () -> Unit = {}
        private set
    var buttonId: Int
        private set

    constructor(
        @StringRes title: Int,
        @StringRes messageId: Int,
        @StringRes buttonId: Int,
        onDismissListener: () -> Unit = {},
        actionListener: () -> Unit = {}
    ) : super(message = messageId) {
        this.title = StringResource(title)
        this.onDismissListener = onDismissListener
        this.buttonId = buttonId
        this.actionListener = actionListener
    }

    constructor(
        @StringRes title: Int,
        message: String,
        @StringRes buttonId: Int,
        onDismissListener: () -> Unit = {},
        actionListener: () -> Unit = {}
    ) : super(message = message) {
        this.title = StringResource(title)
        this.onDismissListener = onDismissListener
        this.buttonId = buttonId
        this.actionListener = actionListener
    }
}
