package com.sliidepoc.stats

import android.content.Context
import android.util.Log
import com.sliidepoc.common.utils.ext.TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager class to deliver events to different analytics services like: MixPanel, Firebase
 * Events are Sealed objects so can be filtered and selective sent to different analytics services.
 *
 * @author Ioan Hagau
 * @since 2020.11.19
 */
@Singleton
class StatsImpl @Inject constructor(@ApplicationContext private val context: Context) : Stats {

    override fun sendUIEvent(event: StatsEventsUI) {
        event.eventName?.let {
            Log.d(TAG, "$it = ${event.value}")
        }
    }

    override fun sendNetworkEvent(event: StatsEventsNetwork) {
        event.eventName?.let {
            Log.d(TAG, "$it = ${event.value}")
        }
    }

    override fun sendLogEvent(event: StatsEventsLogs) {
        when (event) {
            is StatsEventsLogs.W -> {
                event.eventName?.let {
                    Log.w(TAG, "$it = ${event.value}")
                }
            }
            is StatsEventsLogs.D -> {
                event.eventName?.let {
                    Log.d(event.tag, "$it = ${event.value}")
                }
            }
            is StatsEventsLogs.E -> {
                event.eventName?.let {
                    Log.e(TAG, "$it = ${event.value}")
                }
            }
        }
    }
}
