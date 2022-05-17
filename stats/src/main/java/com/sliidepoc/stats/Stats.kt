package com.sliidepoc.stats

/**
 * Interface required for StatsImpl class
 *
 * @Inject
 * late init var stats: Stats
 * stats.uiEvent(StatsEventsUI.ScreenPageOpened(TAG!!))
 * stats.uiEvent(StatsEventsUI.ButtonClicked("CANCEL_BUTTON"))
 *
 * @author Ioan Hagau
 * @since 2020.11.19
 */
interface Stats {

    fun sendUIEvent(event: StatsEventsUI)

    fun sendNetworkEvent(event: StatsEventsNetwork)

    fun sendLogEvent(event: StatsEventsLogs)
}
