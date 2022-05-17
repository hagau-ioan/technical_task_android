package com.sliidepoc.stats

/**
 *
 * @author Ioan Hagau
 * @since 2020.11.19
 */
/**
 * Here we define some events for Network domain
 */
sealed class StatsEventsLogs(val tag: String, val eventName: String?, val value: String) {
    /**
     * eventName - must be composed by one of the following chars [0-9a-zA-Z_]
     */
    data class W(private val _tag: String, private val _value: String) :
        StatsEventsLogs(_tag, W::class.simpleName, _value)

    data class E(private val _tag: String, private val _value: String) :
        StatsEventsLogs(_tag, E::class.simpleName, _value)

    data class D(private val _tag: String, private val _value: String) :
        StatsEventsLogs(_tag, D::class.simpleName, _value)
}
