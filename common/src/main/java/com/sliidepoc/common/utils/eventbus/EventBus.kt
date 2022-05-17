package com.sliidepoc.common.utils.eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use this approach of sending / receiving event only if required. Do not abuse on this.
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
@Singleton
class EventBus @Inject constructor() {

    private val _events = MutableSharedFlow<AppEvent>()

    suspend fun send(event: AppEvent) = _events.emit(event)

    /**
     * Use tis carefully and keep in mind that everytime this register need to be bound to a view lifecycle.
     * Or you need to make sure that callback will be nullable when you don't need handle any events.
     */
    suspend fun register(events: List<AppEvent>, callback: ((e: AppEvent) -> Unit)? = null) {
        _events.filter { appEvent ->
            events.contains(appEvent)
        }.collectLatest {
            callback?.run { this(it) }
        }
    }
}