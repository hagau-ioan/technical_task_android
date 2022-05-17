@file:Suppress("unused")

package com.sliidepoc.ui.startup

import android.content.Context
import com.sliidepoc.common.utils.CoroutineScopeApp
import com.sliidepoc.common.utils.eventbus.AppEvent
import com.sliidepoc.common.utils.eventbus.EventBus
import com.sliidepoc.common.utils.ext.TAG
import com.sliidepoc.domain.api.data.OAuthRepository
import com.sliidepoc.stats.Stats
import com.sliidepoc.stats.StatsEventsLogs
import com.tinder.StateMachine
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class handle all the actions required by the app boot state machine to be done.
 * This class is straight forward related to BootAppState. This class should contain only CONCRETE implementation
 * of the events states used in the BootAppState. Here we implement the functionality required to be executed
 * when a specific state machine app boot event is reached.
 *
 * @author Ioan Hagau
 * @since 2020.12.02
 */
@Singleton
class BootAppHandler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stats: Stats,
    private val eventBus: EventBus,
    private val coroutineScopeApp: CoroutineScopeApp,
    private val oAuthRepository: OAuthRepository
) {

    /**
     * This is the handler when we receive an "Event.EventError" event inside the machine boot.
     * This should be called by:
     *
     * @see bootAppState .transition(BootAppState.Event.EventError)
     *
     * only in case of some critical exceptions we can get during a boot process.
     * Take in consideration also that the activity can be started in background also.
     */
    fun handleError(bootAppState: StateMachine<BootAppState.State, BootAppState.Event, BootAppState.HandleEvent>) {
        logStep("Handle the Error", bootAppState)
    }

    fun handleFinish(bootAppState: StateMachine<BootAppState.State, BootAppState.Event, BootAppState.HandleEvent>) {
        coroutineScopeApp.launch {
            eventBus.send(AppEvent.BootCompleted)
        }
        logStep("Boot finished", bootAppState)
    }

    fun handleProvisioning(bootAppState: StateMachine<BootAppState.State, BootAppState.Event, BootAppState.HandleEvent>) {
        logStep("Provisioning process started", bootAppState, false)

        // Step 1. Identify the clientId/ClientSecret/Fingerprint to be saved using keyStore encryption
        // use oAuthRepository to resolve this
        // Step 2. Based on the saved information we will proceed to get the token for http calls.

        // TODO: SYNC process between local saved data and remote data, must be done at specific interval of time
        // like a heartbeat.

        coroutineScopeApp.launch {
            oAuthRepository.getClientId()
                .zip(oAuthRepository.getClientSecret()) { clientId, clientSecret ->
                    Pair(clientId, clientSecret)
                }.collectLatest {
                oAuthRepository.resolveOauth(it.first, it.second).collectLatest {
                    //if(it) {
                    bootAppState.transition(BootAppState.Event.EventFinish)
                    //} else {
                    // TODO: in case we were not able to do the resolveOauth, the user will start the
                    // authentication process from the beginning
                    //  bootAppState.transition(BootAppState.Event.EventFinish)
                    //}
                }
            }
        }
    }

    fun handleWelcomeScreen(bootAppState: StateMachine<BootAppState.State, BootAppState.Event, BootAppState.HandleEvent>) {
        logStep("Display the welcome screen", bootAppState, false)
    }

    fun handleCheckInternetConnection(bootAppState: StateMachine<BootAppState.State, BootAppState.Event, BootAppState.HandleEvent>) {
        logStep("Check Internet Connection", bootAppState)
    }

    fun handleBootNormalStart(bootAppState: StateMachine<BootAppState.State, BootAppState.Event, BootAppState.HandleEvent>) {
        logStep("Boot Normal start", bootAppState)
        bootAppState.transition(BootAppState.Event.EventWelcomeScreen)
        bootAppState.transition(BootAppState.Event.EventProvisioning)
//        bootAppState.transition(BootAppState.Event.EventCheckInternetConnection)
    }

    fun decideTheBootType(bootAppState: StateMachine<BootAppState.State, BootAppState.Event, BootAppState.HandleEvent>) {
        logStep("Decide the boot type", bootAppState)
        bootAppState.transition(BootAppState.Event.EventStartTypeBootNormal) // EventStartTypeBootFTI
    }

    // ************** Delegate boot step continuity from internal and external case from app. ***********

    private fun logStep(
        msg: String,
        bootAppState: StateMachine<BootAppState.State, BootAppState.Event, BootAppState.HandleEvent>,
        withToast: Boolean = false
    ) {
        when {
            withToast -> {
                //            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
        stats.sendLogEvent(StatsEventsLogs.D(TAG, bootAppState.state.toString() + ": " + msg))
    }

    private fun hasNetworkContinueBoot(): Boolean {
        return true
    }
}
