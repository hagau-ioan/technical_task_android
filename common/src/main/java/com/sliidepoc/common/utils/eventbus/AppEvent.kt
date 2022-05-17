package com.sliidepoc.common.utils.eventbus

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
sealed class AppEvent {
    object NetworkState : AppEvent()
    object BootCompleted : AppEvent()
}