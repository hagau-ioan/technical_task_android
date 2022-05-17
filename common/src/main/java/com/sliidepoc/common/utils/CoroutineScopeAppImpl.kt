package com.sliidepoc.common.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.23
 */
class CoroutineScopeAppImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val coroutineContextThread: CoroutineContextThread
) : CoroutineScopeApp {
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return coroutineScope.launch(coroutineContextThread.io) {
            block()
        }
    }
}
