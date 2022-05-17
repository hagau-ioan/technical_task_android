package com.sliidepoc.stats

/**
 *
 * @author Ioan Hagau
 * @since 2020.11.19
 */
/**
 * Here we define some events for UI domain
 */
sealed class StatsEventsUI(val eventName: String?, val value: String) {
    /**
     * eventName - must be composed by one of the following chars [0-9a-zA-Z_].
     * Do not use any other type of chars.
     */
    data class ScreenPageOpened(private val _value: String) :
        StatsEventsUI(ScreenPageOpened::class.simpleName, _value)

    data class ButtonClicked(private val _value: String) :
        StatsEventsUI(ButtonClicked::class.simpleName, _value)
}
