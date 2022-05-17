package com.sliidepoc.ui.mock.utils

import com.sliidepoc.stats.Stats
import com.sliidepoc.stats.StatsEventsLogs
import com.sliidepoc.stats.StatsEventsNetwork
import com.sliidepoc.stats.StatsEventsUI

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
class MockStats : Stats {
    override fun sendUIEvent(event: StatsEventsUI) {
    }

    override fun sendNetworkEvent(event: StatsEventsNetwork) {
    }

    override fun sendLogEvent(event: StatsEventsLogs) {
    }
}
