package com.sliidepoc.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Using it as an interface because no multiple extension is possible (Interface Segregation).
 *
 * @author Hagău Ioan
 * @since 2022.01.24
 */
interface ViewModelLiFeCycle {

    fun onStart() {}

    fun onStop()

    fun onPause() {}

    fun onResume() {}

    fun onDestroy() {}
}

/**
 * This lifecycle is bound to the NavBackStackEntry
 */
@Composable
fun ScreenLifeCycle(viewModel: ViewModelLiFeCycle, lifecycleOwner: LifecycleOwner) {
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    viewModel.onStart()
                }
                Lifecycle.Event.ON_STOP -> {
                    viewModel.onStop()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.onPause()
                }
                Lifecycle.Event.ON_DESTROY -> {
                    viewModel.onDestroy()
                }
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.onResume()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
