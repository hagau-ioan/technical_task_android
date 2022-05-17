@file:Suppress("unused")

package com.sliidepoc.stats

/**
 *
 * @author Ioan Hagau
 * @since 2020.11.19
 */
/**
 * Here we define some events for Network domain
 */
sealed class StatsEventsNetwork(val eventName: String?, val value: String) {
    /**
     * eventName - must be composed by one of the following chars [0-9a-zA-Z_]
     */
    data class BackendErrorNetwork(private val _value: String) :
        StatsEventsNetwork(BackendErrorNetwork::class.simpleName, _value)

    data class ErrorNetwork(private val _value: String) :
        StatsEventsNetwork(ErrorNetwork::class.simpleName, _value)

    data class MessageNetwork(private val _value: String) :
        StatsEventsNetwork(MessageNetwork::class.simpleName, _value)
}
