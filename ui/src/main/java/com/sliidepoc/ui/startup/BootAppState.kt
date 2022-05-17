package com.sliidepoc.ui.startup

import com.tinder.StateMachine
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Advantages:
 * 1. Back propagation of steps
 * 2. Similar steps handled in the same order.
 * 3. Can skip some steps from other STATES by ignore those steps, not handling the events
 * 4. Possibility to notify a STATE event from any app location.
 * Ex: Let's say you open a fragment and after some changes you want to continue the STATE flow.
 * you just need to use something like: appMachineState.stateMachine.transition(BootAppState.Event.Event[ ContinueTheBootStepForwardCase1 ])
 *
 * @Reference: https://github.com/Tinder/StateMachine/blob/master/src/test/kotlin/com/tinder/StateMachineTest.kt
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
@Singleton
class BootAppState @Inject constructor(
    private val bootAppStateHandler: BootAppHandler
) {

    private var currentEvent: Event? = null

    fun isBootCompleted(): Boolean {
        return bootAppStateMachine.state == State.BootFinish
    }

    /**
     * We can have boot error only if the previous boot state was: isBootInProgress()
     */
    fun isBootError(): Boolean {
        return bootAppStateMachine.state == State.BootError
    }

    fun isBootInStart(): Boolean {
        return bootAppStateMachine.state == State.BootStart
    }

    fun isBootInProgress(): Boolean {
        return bootAppStateMachine.state == State.BootNORMAL
    }

    /**
     * This is used when we display a general error screen or a networkReceiver connection error screen and we need
     * during the boot process --> to recover the current step.
     */
    fun recoverCurrentEventState() {
        currentEvent?.let {
            getBootAppState().transition(it)
        }
    }

    fun restartSoftAppState() {
        currentEvent = null
        getBootAppState().transition(Event.EventSoftRestart)
    }

    fun getHandler(): BootAppHandler {
        return bootAppStateHandler
    }

    private val bootAppStateMachine = StateMachine.create<State, Event, HandleEvent> {

        initialState(State.None)

        state<State.None> {
            on<Event.EventStart> {
                transitionTo(State.BootStart, HandleEvent.HandleStartTypeBoot)
            }
        }

        state<State.BootStart> {
            on<Event.EventStartTypeBootNormal> {
                transitionTo(State.BootNORMAL, HandleEvent.HandleBootNormalStart)
            }
        }

        state<State.BootNORMAL> {

            on<Event.EventWelcomeScreen> {
                transitionTo(State.BootNORMAL, HandleEvent.HandleWelcomeScreen)
            }
            on<Event.EventProvisioning> {
                transitionTo(State.BootNORMAL, HandleEvent.HandleProvisioning)
            }
            on<Event.EventWelcomeScreen> {
                transitionTo(State.BootNORMAL, HandleEvent.HandleWelcomeScreen)
            }
            on<Event.EventCheckInternetConnection> {
                transitionTo(State.BootNORMAL, HandleEvent.HandleCheckInternetConnection)
            }
            on<Event.EventError> {
                transitionTo(State.BootError, HandleEvent.HandleError)
            }
            on<Event.EventFinish> {
                transitionTo(State.BootFinish, HandleEvent.HandleFinish)
            }
            /**
             * In case the boot flow cycle got interrupted by some unexpected situations when we start
             * the activity will resume the boot flow. A possible case can be when the app is in background.
             */
            on<Event.EventStart> {
                transitionTo(State.BootStart, HandleEvent.HandleStartTypeBoot)
            }
            on<Event.EventNetworkInterrupt> {
                transitionTo(State.BootError, HandleEvent.HandleError)
            }
            on<Event.EventStopInterrupt> {
                // Not necessary a hard reboot. Can be something soft.
                transitionTo(State.BootError, HandleEvent.HandleError)
            }
        }

        // GENERAL handler of some cases related to ALL STATES
        onTransition {
            val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition

            traceCurrentEvent(validTransition)

            // DO NOT REMOVE THIS.
//            when (validTransition.toState) {
//                State.BootStart -> {}
//                State.BootNORMAL -> {}
//                State.BootFTI -> {}
//                State.BootFinish -> {}
//                State.BootError -> {}
//                else -> {}
//            }

            // Delegate Handler for each assetType of received event.
            when (validTransition.sideEffect) {
                HandleEvent.HandleStartTypeBoot ->
                    bootAppStateHandler.decideTheBootType(getBootAppState())
                HandleEvent.HandleBootNormalStart ->
                    bootAppStateHandler.handleBootNormalStart(getBootAppState())
                HandleEvent.HandleProvisioning ->
                    bootAppStateHandler.handleProvisioning(getBootAppState())
                HandleEvent.HandleWelcomeScreen ->
                    bootAppStateHandler.handleWelcomeScreen(getBootAppState())
                HandleEvent.HandleCheckInternetConnection ->
                    bootAppStateHandler.handleCheckInternetConnection(getBootAppState())
                HandleEvent.HandleFinish -> bootAppStateHandler.handleFinish(getBootAppState())
                HandleEvent.HandleError -> bootAppStateHandler.handleError(getBootAppState())
                else -> {
                }
            }
        }

        state<State.BootFinish> {
            on<Event.EventHardRestart> {
                transitionTo(State.BootStart, HandleEvent.HandleStartTypeBoot)
            }
            on<Event.EventSoftRestart> {
                transitionTo(State.None)
            }
        }

        state<State.BootError> {
            on<Event.EventStart> {
                transitionTo(State.BootStart, HandleEvent.HandleStartTypeBoot)
            }
        }
    }

    fun getBootAppState(): StateMachine<State, Event, HandleEvent> {
        return bootAppStateMachine
    }

    private fun traceCurrentEvent(validTransition: StateMachine.Transition.Valid<State, Event, HandleEvent>) {
        currentEvent = if (isBootInProgress()) {
            validTransition.event
        } else {
            null
        }
    }

    /**
     * Define the main states of the machine.
     */
    sealed class State {
        object None : State()
        object BootStart : State() // State: Start
        object BootNORMAL : State() // State: InProgress
        object BootFinish : State() // State: Finished
        object BootError : State() // State: Error
    }

    /**
     * Events for each state: General events and particular events can be defined here.
     * These must respect the Single Responsibility principle and should run independent.
     */
    sealed class Event {
        object EventStart : Event()
        object EventNetworkInterrupt : Event()
        object EventStopInterrupt : Event()
        object EventFinish : Event()
        object EventError : Event()
        object EventHardRestart : Event()
        object EventSoftRestart : Event()

        object EventStartTypeBootNormal : Event()

        object EventProvisioning : Event()
        object EventWelcomeScreen : Event()
        object EventCheckInternetConnection : Event()
    }

    /**
     * Handler for all Events defined above.
     * These must respect the Single Responsibility principle and should run independent.
     */
    sealed class HandleEvent {

        object HandleStartTypeBoot : HandleEvent()

        object HandleBootNormalStart : HandleEvent()

        object HandleProvisioning : HandleEvent()
        object HandleWelcomeScreen : HandleEvent()
        object HandleCheckInternetConnection : HandleEvent()

        object HandleFinish : HandleEvent()
        object HandleError : HandleEvent()
    }
}
