package com.sliidepoc.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.sliidepoc.common.utils.eventbus.AppEvent
import com.sliidepoc.common.utils.eventbus.EventBus
import com.sliidepoc.common.utils.ext.TAG
import com.sliidepoc.stats.Stats
import com.sliidepoc.stats.StatsEventsLogs
import com.sliidepoc.ui.components.DrawerContent
import com.sliidepoc.ui.startup.BootAppState
import com.sliidepoc.ui.themes.SliidepocTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bootAppState: BootAppState

    @Inject
    lateinit var eventBus: EventBus

    @Inject
    lateinit var stats: Stats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SliidepocTheme {
                DrawerContent(applicationContext)
            }
        }
        createAppGlobalStates()
    }

    override fun onStart() {
        super.onStart()
        bootAppState.getBootAppState().transition(BootAppState.Event.EventStart)
    }

    /**
     * Be sure that this is called only once and is bound to the activity lifecycle "onCreate" method. We need to prevent
     * by any reasons any re-register of this.
     */
    private fun createAppGlobalStates() {
        lifecycleScope.launch {
            eventBus.register(
                listOf(
                    AppEvent.BootCompleted,
                    AppEvent.NetworkState
                )
            ) {
                when (it) {
                    AppEvent.NetworkState -> {
                        stats.sendLogEvent(StatsEventsLogs.D(TAG, "AppEvent.NetworkState"))
                    }
                    AppEvent.BootCompleted -> {
                        stats.sendLogEvent(StatsEventsLogs.D(TAG, "AppEvent.BootCompleted"))
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onStop() {
        bootAppState.getBootAppState().transition(BootAppState.Event.EventStopInterrupt)
        super.onStop()
    }
}
